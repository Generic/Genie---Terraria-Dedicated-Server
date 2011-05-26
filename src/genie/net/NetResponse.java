/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genie.net;

import java.awt.Color;
import java.nio.ByteOrder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import genie.IOTools;
import genie.world.Item;
import genie.world.NPC;
import genie.world.World;

/**
 *
 * @author Jay
 */
public class NetResponse {

    public static void connect(Player player, ChannelBuffer buffer, int length) {
        int version = Integer.parseInt(IOTools.readString(buffer, 9).replaceAll("Terraria", ""));

        if (version != NetHandler.VERSION) {
            System.out.println("Version attempt from: " + version);
            sendAuthFailed(player, "Version Mismatch");
        } else if (!NetHandler.PASSWORD.equals("")) {
            System.out.println("Sending pass request.");
            ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 5);
            send.writeInt(1);
            send.writeByte(Opcodes.SEND_PASS_REQUEST);
            player.getChannel().write(send);
        } else {
            sendAuthSuccess(player);
        }
    }

    public static void recvData(Player player, ChannelBuffer buffer, int length) {
        player.setId(buffer.readByte());
        player.setHairId(buffer.readByte());
        player.setHairColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setSkinColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setEyeColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setShirtColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setUnderColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setPantColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setShoeColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));

        player.setName(IOTools.readString(buffer, length - 24));
    }

    public static void recvInventory(Player player, ChannelBuffer buffer, int length) {
        player.setId(buffer.readByte());
        int slot = buffer.readByte();
        int amount = buffer.readByte();
        Item item = new Item();
        item.setDefaults(IOTools.readString(buffer, length - 4));
        item.setStack(amount);
        player.addItem(slot, item);
    }

    public static void recvWorldRequest(Player player, ChannelBuffer buffer, int length) {
        ChannelBuffer send = ChannelBuffers.dynamicBuffer(ByteOrder.LITTLE_ENDIAN, 16);
        send.writeByte(Opcodes.SEND_WORLD_DATA);
        send.writeInt((int) World.getWorld().getTime());
        send.writeByte(World.getWorld().isDay() ? 1 : 0);
        send.writeByte((byte) World.getWorld().getMoonPhase());
        send.writeByte(World.getWorld().isBloodMoon() ? 1 : 0);
        send.writeInt(World.getWorld().getMaxX());
        send.writeInt(World.getWorld().getMaxY());
        send.writeInt(World.getWorld().getSpawnX());
        send.writeInt(World.getWorld().getSpawnY());
        send.writeInt((int) World.getWorld().getWorldSurface());
        send.writeInt((int) World.getWorld().getRockLayer());
        send.writeInt(World.getWorld().getId());
        IOTools.writeString(send, World.getWorld().getName());

        ChannelBuffer nSend = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, send.readableBytes() + 4);
        nSend.writeInt(send.writerIndex());
        nSend.writeBytes(send);

        player.getChannel().write(nSend);
    }

    public static void recvPassword(Player player, ChannelBuffer buffer, int length) {
        String password = IOTools.readString(buffer, length - 1);
        if (password.equals(NetHandler.PASSWORD)) {
            System.out.println("Auth success");
            sendAuthSuccess(player);
        } else {
            System.out.println("Auth Failed");
            sendAuthFailed(player, "Incorrect Password");
        }
    }

    public static void recvPlayerHealth(Player player, ChannelBuffer buffer, int length) {
        player.setId(buffer.readByte());
        player.setHealth(buffer.readShort());
        player.setMaxHealth(buffer.readShort());
    }

    public static void recvPlayerMana(Player player, ChannelBuffer buffer, int length) {
        player.setId(buffer.readByte());
        player.setMana(buffer.readShort());
        player.setMaxMana(buffer.readShort());
    }

    public static void recvSpawn(Player player, ChannelBuffer buffer, int length) {
        int x = buffer.readInt();
        int y = buffer.readInt();
        boolean flag2 = true;
        if ((x == -1) || (y == -1)) {
            flag2 = false;
        } else if ((x < 10) || (x > (World.getWorld().getMaxX() - 10))) {
            flag2 = false;
        } else if ((y < 10) || (y > (World.getWorld().getMaxY() - 10))) {
            flag2 = false;
        }
        int num16 = 1350;
        if (flag2) {
            num16 *= 2;
        }
        player.addStatusMax(num16);
        {
            String msg = "Receiving tile data";
            ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 9 + msg.length());
            send.writeInt(5 + msg.length());
            send.writeByte(Opcodes.SEND_TILE_LOADING);
            send.writeInt(num16);
            IOTools.writeString(send, msg);
            player.getChannel().write(send);
        }
        System.out.println(player.getName() + " is receiving tile data.");
        int sectionX = player.getSectionX(World.getWorld().getSpawnX());
        int sectionY = player.getSectionY(World.getWorld().getSpawnY());
        for (int n = sectionX - 2; n < (sectionX + 3); n++) {
            for (int num20 = sectionY - 1; num20 < (sectionY + 2); num20++) {
                player.sendSection(n, num20, null);
            }
        }
        if (flag2) {
            x = player.getSectionX(x);
            y = player.getSectionY(y);
            for (int num21 = x - 2; num21 < (x + 3); num21++) {
                for (int num22 = y - 1; num22 < (y + 2); num22++) {
                    player.sendSection(num21, num22, null);
                }
            }
            ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 21);
            send.writeInt(17);
            send.writeByte(Opcodes.SEND_CONFIRM_TILE);
            send.writeInt(x - 2);
            send.writeInt(y - 1);
            send.writeInt(x + 2);
            send.writeInt(y + 1);
            player.getChannel().write(send);
        }
        ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 21);
        send.writeInt(17);
        send.writeByte(Opcodes.SEND_CONFIRM_TILE);
        send.writeInt(sectionX - 2);
        send.writeInt(sectionY - 1);
        send.writeInt(sectionX + 2);
        send.writeInt(sectionY + 1);
        player.getChannel().write(send);
        for (int i = 0; i < 200; i++) {
            if (World.getWorld().getItem(i).isActive()) {
                sendItemInfo(player, i);
                sendItemOwnerInfo(player, i);
            }
        }
        for (int i = 0; i < 200; i++) {
            if (World.getWorld().getNpc(i).isActive()) {
                sendNpcInfo(player, i);
            }
        }
        player.setActive(true);
    }

    public static void sendAuthFailed(Player player, String message) {
        ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 5 + message.length());
        send.writeInt(1 + message.length());
        send.writeByte(Opcodes.SEND_DISCONNECT);
        IOTools.writeString(send, message);
        player.getChannel().write(send);
    }

    public static void sendAuthSuccess(Player player) {
        ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 9);
        send.writeInt(5);
        send.writeByte(Opcodes.SEND_CONNECT_RESPONSE);
        send.writeBytes(new byte[]{0, 0, 0, 0});
        player.getChannel().write(send);
    }

    public static void sendNpcInfo(Player player, int index) {
        NPC npc = World.getWorld().getNpc(index);
        ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 29 + (4 * NPC.MAX_AI) + npc.getName().length());
        send.writeInt(25 + (4 * NPC.MAX_AI) + npc.getName().length());
        send.writeByte(Opcodes.SEND_NPC_INFO);
        send.writeShort(index);
        send.writeFloat(npc.getPosition().x);
        send.writeFloat(npc.getPosition().y);
        send.writeFloat(npc.getVelocity().x);
        send.writeFloat(npc.getVelocity().y);
        send.writeShort(npc.getTarget());
        send.writeByte(npc.getDirection() + 1);
        send.writeByte(npc.getDirectionY() + 1);
        send.writeShort(npc.isActive() ? npc.getLife() : 0);
        for (int i = 0; i < NPC.MAX_AI; i++) { // 4 * MAX_AI
            send.writeFloat(npc.getAi()[i]);
        }
        IOTools.writeString(send, npc.getName());
    }

    public static void sendItemInfo(Player player, int index) {
        Item item = World.getWorld().getItem(index);
        byte stack = (byte) item.getStack();
        String name = "0";
        if (item.isActive() && stack > 0) {
            name = item.getName();
        }
        if (name.equals(null)) {
            name = "0";
        }
        ChannelBuffer send = ChannelBuffers.dynamicBuffer(ByteOrder.LITTLE_ENDIAN, name.length() + 24);
        send.writeInt(name.length() + 20);
        send.writeByte(Opcodes.SEND_ITEM_INFO);
        send.writeShort(index);
        send.writeFloat(item.getPosition().x);
        send.writeFloat(item.getPosition().y);
        send.writeFloat(item.getVelocity().x);
        send.writeFloat(item.getVelocity().y);
        send.writeByte(stack);
        IOTools.writeString(send, name);
        player.getChannel().write(send);
    }

    public static void sendItemOwnerInfo(Player player, int index) {
        ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 8);
        send.writeInt(4);
        send.writeByte(Opcodes.SEND_OWNER_INFO);
        send.writeShort(index);
        send.writeByte(World.getWorld().getItem(index).getOwner());
        player.getChannel().write(send);
    }

    public static void sendSection(Player player, int sectionX, int sectionY, int length) {
        player.sendSection(sectionX, sectionY, null);
    }
}
