/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genie.net;

import genie.Broadcaster;
import java.awt.Color;
import java.nio.ByteOrder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import genie.IOTools;
import genie.Main;
import genie.world.Item;
import genie.world.NPC;
import genie.world.Projectile;
import genie.world.Tile;
import genie.world.World;

/**
 *
 * @author Jay
 */
public class NetResponse {

    public static void connect(Player player, ChannelBuffer buffer, int length) {
        String versionStr = IOTools.readString(buffer, 10);
        System.out.println(versionStr);
        int version = Integer.parseInt(versionStr.replaceAll("Terraria", ""));
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
        buffer.readByte(); // GENDER
        player.setHairColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setSkinColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setEyeColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setShirtColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setUnderColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setPantColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        player.setShoeColor(new Color(buffer.readUnsignedByte(), buffer.readUnsignedByte(), buffer.readUnsignedByte()));
        System.out.println("Length: " + length);
        player.setDifficulty(buffer.readByte());
        player.setName(IOTools.readString(buffer, length - 26));
        System.out.println(player.getName());
        
//        if (!player.isSpawned()) {
//            System.out.println("Spawning: " + player.getName());
//            ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 14);
//            send.writeInt(10);
//            send.writeByte(Opcodes.SEND_SPAWN);
//            send.writeByte(player.getId());
//            send.writeInt((int) player.getLocation().x);
//            send.writeInt((int) player.getLocation().y);
//            player.getChannel().write(send);
//            player.setSpawned(true);
//            player.setActive(true);
//        }
    }

    //TODO: Armor!
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
        nSend.writeBytes(send.slice(0, send.writerIndex()));
        player.getChannel().write(nSend);
        if (player.getConnectionState() == 1) {
            player.setConnectionState(2);
        }
    }

    public static void recvPassword(Player player, ChannelBuffer buffer, int length) {
        String password = IOTools.readString(buffer, length - 1);
        if (password.equals(NetHandler.PASSWORD)) {
            sendAuthSuccess(player);
        } else {
            sendAuthFailed(player, "Incorrect Password");
        }
    }

    public static void recvNpcTalk(Player player, ChannelBuffer buffer, int length) {
        buffer.readByte(); // playerId
        player.setNpcConvo(buffer.readShort());
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
        byte playerId = buffer.readByte();
        player.setId(playerId);
        int spawnX = buffer.readInt();
        int spawnY = buffer.readInt();
        if (!player.isSpawned()) {
            System.out.println("Spawning: " + player.getName());
            ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 14);
            send.writeInt(10);
            send.writeByte(Opcodes.SEND_SPAWN);
            send.writeByte(player.getId());
            send.writeInt((int) player.getLocation().x);
            send.writeInt((int) player.getLocation().y);
            player.getChannel().write(send);
            player.setSpawned(true);
            player.setActive(true);
        }
        player.sendMessage("Hello World!", 8, 255, 255, 255);
        player.setConnectionState(10);
    }

    public static void recvZoneInfo(Player player, ChannelBuffer buffer, int length) {
        // TODO: WHY YOU SEND 4 BYTES INSTEAD OF 5 EVEN THOUGH UPDATED CLIENT SAYS OTHERWISE?
        buffer.readByte(); // playerId
        player.setEvil(buffer.readByte() != 0 ? true : false);
        player.setMeteor(buffer.readByte() != 0 ? true : false);
        player.setDungeon(buffer.readByte() != 0 ? true : false);
        player.setJungle(buffer.readByte() != 0 ? true : false);
    }

    public static void recvManipulateTile(Player player, ChannelBuffer buffer, int length) {
        int op = buffer.readByte();
        int x = buffer.readInt();
        int y = buffer.readInt();
        int type = buffer.readByte();
        boolean fail = false;
        if (type == 1) {
            fail = true;
        }
        if (World.getWorld().getTile(x, y) == null) {
            World.getWorld().setTile(x, y, new Tile());
        }
        switch (op) {
            case 0:
                //WorldGen.KillTile(x, y, fail, false, false);
                break;

            case 1:
                //WorldGen.PlaceTile(x, y, type, false, true, -1);
                break;

            case 2:
                //WorldGen.KillWall(x, y, fail);
                break;

            case 3:
                //WorldGen.PlaceWall(x, y, type, false);
                break;

            case 4:
                //WorldGen.KillTile(x, y, fail, false, true);
                break;
        }
    }
    
    public static void recvMessage(Player player, ChannelBuffer buffer, int length) {
        System.out.println(length);
        System.out.println(ChannelBuffers.hexDump(buffer, buffer.readerIndex(), length - 1));
        int id = buffer.readByte(); // Player ID
        int r = buffer.readUnsignedByte();
        int g = buffer.readUnsignedByte();
        int b = buffer.readUnsignedByte();
        player.broadcastMessage(IOTools.readString(buffer, length - 5), id, r, g, b);
    }

    public static void recvProjectile(Player player, ChannelBuffer buffer, int length) {
        short uniqueId = buffer.readShort(); // unique id?
        float positionX = buffer.readFloat(); // pos x
        float positionY = buffer.readFloat(); // pos y
        float veloX = buffer.readFloat(); // velo x
        float veloY = buffer.readFloat(); // velo y
        float knockback = buffer.readFloat(); // knock back
        short damage = buffer.readShort(); // damage
        int owner = buffer.readByte(); // owner
        int id = buffer.readByte(); // regular id

        float[] ai = new float[Projectile.MAX_AI];
        for (int i = 0; i < Projectile.MAX_AI; i++) {
            ai[i] = buffer.readFloat();
        }
        buffer.skipBytes(5); // WTF?_?
        int num111 = 0x3e8;
        for (int i = 0; i < 0x3e8; i++) {
            Projectile projectile = World.getWorld().getProjectile(i);
            if (projectile.getOwner() == owner && projectile.getIdentity() == uniqueId && projectile.isActive()) {
                num111 = i;
                break;
            }
        }
        if (num111 == 0x3e8) {
            for (int i = 0; i < 0x3e8; i++) {
                if (!World.getWorld().getProjectile(i).isActive()) {
                    num111 = i;
                    break;
                }
            }
        }
        Projectile projectile = World.getWorld().getProjectile(num111);
        if (!projectile.isActive() || (projectile.getType() != id)) {
            projectile.setDefaults(id);
        }
        projectile.setIdentity(uniqueId);
        projectile.getPosition().x = positionX;
        projectile.getPosition().y = positionY;
        projectile.getVelocity().x = veloX;
        projectile.getVelocity().y = veloY;
        projectile.setDamage(damage);
        projectile.setType(id);
        projectile.setOwner(owner);
        projectile.setKnockBack(knockback);

        for (int i = 0; i < Projectile.MAX_AI; i++) {
            projectile.setAi(i, ai[i]);
        }

        for (Player other : NetHandler.getPlayers()) {
            if (other.getId() != player.getId()) {
                ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 31 + (Projectile.MAX_AI * 4));
                send.writeInt(27 + (Projectile.MAX_AI * 4));
                // b + s = 27
                send.writeByte(Opcodes.SEND_PROJECTILE);
                send.writeShort(projectile.getIdentity());
                send.writeFloat(projectile.getPosition().x);
                send.writeFloat(projectile.getPosition().y);
                send.writeFloat(projectile.getVelocity().x);
                send.writeFloat(projectile.getVelocity().y);
                send.writeFloat(projectile.getKnockBack());
                send.writeShort(projectile.getDamage());
                send.writeByte(projectile.getOwner());
                send.writeByte(projectile.getType());

                for (int i = 0; i < Projectile.MAX_AI; i++) {
                    send.writeFloat(projectile.getAi()[i]);
                }

                other.getChannel().write(send);
            }
        }
    }

    public static void recvPlayerUpdate(final Player player, ChannelBuffer buffer, int length) {
        player.setId(buffer.readByte()); // player ID
        byte controls = buffer.readByte(); // flag
        player.getInventory().setSelectedItem(buffer.readByte());
        player.getLocation().x = buffer.readFloat();
        player.getLocation().y = buffer.readFloat();
        player.getVelocity().x = buffer.readFloat();
        player.getVelocity().y = buffer.readFloat();
        //setOldVelocity = player.getVelocity() - wat? Going to ignore.
        player.setFallStart((int) (player.getLocation().y / 16f));

        player.getController().reset();
        if ((controls & 1) == 1) {
            player.getController().setUp(true);
        }
        if ((controls & 2) == 2) {
            player.getController().setDown(true);
        }
        if ((controls & 4) == 4) {
            player.getController().setLeft(true);
        }
        if ((controls & 8) == 8) {
            player.getController().setRight(true);
        }
        if ((controls & 0x10) == 0x10) {
            player.getController().setJump(true);
        }
        if ((controls & 0x20) == 0x20) {
            player.getController().setUseItem(true);
        }
        if ((controls & 0x40) == 0x40) {
            player.getController().setDirection(1);
        }
        if (player.isActive() && player.isSpawned()) {
            Main.broadcast(new Broadcaster() {

                public void broadcast(Player other) {
                    if (player.getId() != other.getId()) {
                        player.sendUpdate(other);
                    }
                }
            });
        }
    }

    public static void recvTiles(Player player, ChannelBuffer buffer, int length) {
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

        if (player.getConnectionState() == 2) {
            player.setConnectionState(3);
        }

        player.spawn();
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
        send.writeInt(player.getId());
        player.getChannel().write(send);
        player.setConnectionState(1);
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
