/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genie;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import genie.net.NetHandler;
import genie.net.NetResponse;
import genie.net.Opcodes;
import genie.net.Player;
import genie.net.Player.PlayerFlags;
import genie.world.Controller;
import genie.world.Inventory;
import genie.world.World;
import java.awt.Color;

/**
 *
 * @author Jay
 */
public class Main {

    private static final HashedWheelTimer timer = new HashedWheelTimer();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new World("C:\\Users\\Jay\\Documents\\My Games\\Terraria\\Worlds\\world2.wld");
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));

        ChannelPipelineFactory pipe = new ChannelPipelineFactory() {

            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new IdleStateHandler(timer, 30, 0, 0), new NetHandler());
            }
        };
        bootstrap.setOption("child.bufferFactory", new HeapChannelBufferFactory(ByteOrder.LITTLE_ENDIAN));
        bootstrap.setPipelineFactory(pipe);
        bootstrap.bind(new InetSocketAddress(7777));

        System.out.println("Bound to port 7777");
        try {
            update();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static final int MAX_ITEM_UPDATES = 10;
    private static int netTimer = 0;
    private static int lastItemUpdate = 0;

    private static void update() throws InterruptedException {

        for (;;) {
            netTimer++;
            if (netTimer > 3600) {
                broadcast(new Broadcaster() { // Todo save object for reuse? ignore broadcast entirely and iterate. please, living is pain. making more work for future self :(

                    public void broadcast(Player player) {
                        sync(player);
                        NetResponse.recvWorldRequest(player, null, 0);
                        if (!player.isSpawned() && player.getConnectionState() == 3) {
                            player.setConnectionState(10);
                            sendUnloaded(player);
                            System.out.println("Spawning: " + player.getName());
                            ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 13);
                            send.writeInt(9);
                            send.writeByte(Opcodes.SEND_SPAWN);
                            send.writeInt((int) player.getLocation().x);
                            send.writeInt((int) player.getLocation().y);
                            player.getChannel().write(send);
                            player.setSpawned(true);
                            player.setActive(true);
                        }
                    }
                });
            }
            if (netTimer % 360 == 0) {
                boolean flag2 = true;
                int nLastItemUpdate = lastItemUpdate;
                int num4 = 0;
                while (flag2) {
                    nLastItemUpdate++;
                    if (nLastItemUpdate >= 200) {
                        nLastItemUpdate = 0;
                    }
                    num4++;
                    if (!World.getWorld().getItem(nLastItemUpdate).isActive() || (World.getWorld().getItem(nLastItemUpdate).getOwner() == 8)) {
                        int index = nLastItemUpdate;
                        for (Player player : NetHandler.getPlayers()) {
                            NetResponse.sendItemInfo(player, index);
                        }
                    }
                    if ((num4 >= MAX_ITEM_UPDATES) || (nLastItemUpdate == lastItemUpdate)) {
                        flag2 = false;
                    }
                }
                lastItemUpdate = nLastItemUpdate;
                for (Player player : NetHandler.getPlayers()) {
                    sendUnloaded(player);
                }
            }
            Thread.currentThread().sleep(2);
        }
    }

    private static void sendUnloaded(Player player) {
        if (player.isActive()) {

            int sectionX = player.getSectionX((int) (player.getLocation().getX() / 16f));
            int sectionY = player.getSectionY((int) (player.getLocation().getY() / 16f));
            int unloaded = 0;
            for (int k = sectionX - 1; k < (sectionX + 2); k++) {
                for (int m = sectionY - 1; m < (sectionY + 2); m++) {
                    if ((((k >= 0)
                            && (k < World.getWorld().getMaxSectionX()))
                            && ((m >= 0) && (m < World.getWorld().getMaxSectionY())))
                            && !player.isTileLoaded(k, m)) {
                        unloaded++;
                    }
                }
            }
            if (unloaded > 0) {

                System.out.println(player.getName() + " has unloaded tiles.");
                String msg = "Receiving tile data";
                ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 9 + msg.length());
                send.writeInt(5 + msg.length());
                send.writeByte(Opcodes.SEND_TILE_LOADING);
                send.writeInt(unloaded);
                IOTools.writeString(send, msg);
                player.getChannel().write(send);


                for (int n = sectionX - 1; n < (sectionX + 2); n++) {
                    for (int num14 = sectionY - 1; num14 < (sectionY + 2); num14++) {
                        if ((((n >= 0) && (n < World.getWorld().getMaxSectionX()))
                                && ((num14 >= 0)
                                && (num14 < World.getWorld().getMaxSectionY())))
                                && !player.isTileLoaded(n, num14)) {
                            player.sendSection(n, num14, null);
                            send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 21);
                            send.writeInt(17);
                            send.writeByte(Opcodes.SEND_CONFIRM_TILE);
                            send.writeInt(n);
                            send.writeInt(num14);
                            send.writeInt(n);
                            send.writeInt(num14);
                            player.getChannel().write(send);
                        }
                    }
                }
            }
        }
    }

    public static void broadcast(Broadcaster broadcaster) {
        for (Player player : NetHandler.getPlayers()) {
            broadcaster.broadcast(player);
        }
    }

    /*
     *         public static void syncPlayers()
    {
    for (int i = 0; i < 8; i++)
    {
    int num2 = 0;
    if (Main.player[i].active)
    {
    num2 = 1;
    }
    if (Netplay.serverSock[i].state == 10)
    {
    SendData(14, -1, i, "", i, (float) num2, 0f, 0f);
    SendData(13, -1, i, "", i, 0f, 0f, 0f);
    SendData(0x10, -1, i, "", i, 0f, 0f, 0f);
    SendData(30, -1, i, "", i, 0f, 0f, 0f);
    SendData(0x2d, -1, i, "", i, 0f, 0f, 0f);
    SendData(0x2a, -1, i, "", i, 0f, 0f, 0f);
    SendData(4, -1, i, Main.player[i].name, i, 0f, 0f, 0f);
    for (int j = 0; j < 0x2c; j++)
    {
    SendData(5, -1, i, Main.player[i].inventory[j].name, i, (float) j, 0f, 0f);
    }
    SendData(5, -1, i, Main.player[i].armor[0].name, i, 44f, 0f, 0f);
    SendData(5, -1, i, Main.player[i].armor[1].name, i, 45f, 0f, 0f);
    SendData(5, -1, i, Main.player[i].armor[2].name, i, 46f, 0f, 0f);
    SendData(5, -1, i, Main.player[i].armor[3].name, i, 47f, 0f, 0f);
    SendData(5, -1, i, Main.player[i].armor[4].name, i, 48f, 0f, 0f);
    SendData(5, -1, i, Main.player[i].armor[5].name, i, 49f, 0f, 0f);
    SendData(5, -1, i, Main.player[i].armor[6].name, i, 50f, 0f, 0f);
    SendData(5, -1, i, Main.player[i].armor[7].name, i, 51f, 0f, 0f);
    if (!Netplay.serverSock[i].announced)
    {
    Netplay.serverSock[i].announced = true;
    SendData(0x19, -1, i, Main.player[i].name + " has joined.", 8, 255f, 240f, 20f);
    }
    }
    else
    {
    SendData(14, -1, i, "", i, (float) num2, 0f, 0f);
    if (Netplay.serverSock[i].announced)
    {
    Netplay.serverSock[i].announced = false;
    SendData(0x19, -1, i, Netplay.serverSock[i].oldName + " has left.", 8, 255f, 240f, 20f);
    }
    }
    }
    }
     */
    private static void sync(final Player from) {
        Broadcaster broadcaster = new Broadcaster() {

            public void broadcast(Player player) {
                if (/*from.getConnectionState() == 10*/ true) {
                    if (player.getId() != from.getId()) {
//                        System.out.println(from.getId() + " is syncing to " + player.getId());
//                        ChannelBuffer buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 7);
//                        buffer.writeInt(3);
//                        buffer.writeByte(Opcodes.SEND_PLAYER_UPDATE2);
//                        buffer.writeByte(from.getId());
//                        buffer.writeByte(from.isActive() ? 1 : 0);
//                        player.getChannel().write(buffer);
//
//                        from.sendUpdate(player);
//
//                        buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 10);
//                        buffer.writeInt(6);
//                        buffer.writeByte(Opcodes.SEND_PLAYER_HEALTH);
//                        buffer.writeByte(from.getId());
//                        buffer.writeShort(from.getHealth());
//                        buffer.writeShort(from.getMaxHealth());
//                        player.getChannel().write(buffer);
//
//                        buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 7);
//                        buffer.writeInt(3);
//                        buffer.writeByte(Opcodes.SEND_PVP_MODE);
//                        buffer.writeByte(from.getId());
//                        buffer.writeByte(from.isPvpMode() ? 1 : 0);
//                        player.getChannel().write(buffer);
//
//                        buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 7);
//                        buffer.writeInt(3);
//                        buffer.writeByte(Opcodes.SEND_PVP_TEAM);
//                        buffer.writeByte(from.getId());
//                        buffer.writeByte(from.getPvpTeam());
//                        player.getChannel().write(buffer);
//
//                        buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 10);
//                        buffer.writeInt(6);
//                        buffer.writeByte(Opcodes.SEND_PLAYER_MANA);
//                        buffer.writeByte(from.getId());
//                        buffer.writeShort(from.getMana());
//                        buffer.writeShort(from.getMaxMana());
//                        player.getChannel().write(buffer);
//
//                        buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 28 + from.getName().length());
//                        buffer.writeInt(24 + from.getName().length());
//                        buffer.writeByte(Opcodes.SEND_PLAYER_DATA);
//                        buffer.writeByte(from.getId());
//                        buffer.writeByte(from.getHairId());
//
//                        Color rgb = from.getHairColor();
//                        buffer.writeByte(rgb.getRed());
//                        buffer.writeByte(rgb.getGreen());
//                        buffer.writeByte(rgb.getBlue());
//
//                        rgb = from.getSkinColor();
//                        buffer.writeByte(rgb.getRed());
//                        buffer.writeByte(rgb.getGreen());
//                        buffer.writeByte(rgb.getBlue());
//
//                        rgb = from.getEyeColor();
//                        buffer.writeByte(rgb.getRed());
//                        buffer.writeByte(rgb.getGreen());
//                        buffer.writeByte(rgb.getBlue());
//
//                        rgb = from.getShirtColor();
//                        buffer.writeByte(rgb.getRed());
//                        buffer.writeByte(rgb.getGreen());
//                        buffer.writeByte(rgb.getBlue());
//
//                        rgb = from.getUnderColor();
//                        buffer.writeByte(rgb.getRed());
//                        buffer.writeByte(rgb.getGreen());
//                        buffer.writeByte(rgb.getBlue());
//
//                        rgb = from.getPantColor();
//                        buffer.writeByte(rgb.getRed());
//                        buffer.writeByte(rgb.getGreen());
//                        buffer.writeByte(rgb.getBlue());
//
//                        rgb = from.getShoeColor();
//                        buffer.writeByte(rgb.getRed());
//                        buffer.writeByte(rgb.getGreen());
//                        buffer.writeByte(rgb.getBlue());
//
//                        IOTools.writeString(buffer, from.getName());
//
//                        player.getChannel().write(buffer);
//
//                        for (int i = 0; i < Inventory.INVENTORY_SLOTS; i++) { // 44 inventory slots without armor & coins
//                            buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 12 + from.getInventory().getItem(i).getName().length());
//                            buffer.writeInt(8 + from.getInventory().getItem(i).getName().length());
//                            buffer.writeByte(Opcodes.SEND_INVENTORY_DATA);
//                            buffer.writeByte(from.getId());
//                            buffer.writeByte(i);
//                            int stack = from.getInventory().getItem(i).getStack();
//                            buffer.writeByte(stack < 0 ? 0 : stack);
//                            IOTools.writeString(buffer, from.getInventory().getItem(i).getName());
//                            player.getChannel().write(buffer);
//                        }

                        if (from.isActive() && !from.isAnnounced()) {
                            player.sendMessage(from.getName() + " has joined!", 8, 255, 240, 20);
                            from.setAnnounced(true);
                        }
                    }
                } else {
                    //System.out.println("Sending a sync");
                    ChannelBuffer buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 7);
                    buffer.writeInt(3);
                    buffer.writeByte(Opcodes.SEND_PLAYER_UPDATE2);
                    buffer.writeByte(from.getId());
                    buffer.writeByte(from.isActive() ? 1 : 0);
                    player.getChannel().write(buffer);
                    if (from.isAnnounced()) {
                        from.setAnnounced(false);
                        player.sendMessage(from.getName() + " has left.", 8, 255, 240, 20);
                    }
                }
            }
        };
        broadcast(broadcaster);
    }
}
