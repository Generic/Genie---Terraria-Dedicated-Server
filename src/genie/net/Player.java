/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genie.net;

import genie.IOTools;
import genie.world.Controller;
import genie.world.Inventory;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import genie.world.Item;
import genie.world.Tile;
import genie.world.Tile.TileFlags;
import genie.world.World;

/**
 * TODO: Implement loginstate!
 */
/**
 *
 * @author Jay
 */
public class Player {

    public static class PlayerFlags {

        public static final byte UP = 0x01,
                DOWN = 0x02,
                LEFT = 0x04,
                RIGHT = 0x08,
                JUMP = 0x10,
                CONTROL_USE_ITEM = 0x20, // ?_?
                DIRECTION = 0x40; // if direction == 1 ??
    }
    private Channel channel;
    private String name;
    private int id;
    private int hairId;
    private Color hairColor, skinColor, eyeColor, shirtColor, underColor, pantColor, shoeColor;
    private Map<Integer, Item> items = new HashMap<Integer, Item>(); // Packet editors could have fun, enforce somehow.
    private int health, maxHealth;
    private int mana, maxMana;
    private Point2D.Float location = new Point2D.Float();
    private Point2D.Float velocity = new Point2D.Float();
    private boolean[][] loadedTiles = new boolean[World.getWorld().getMaxX() / 200][World.getWorld().getMaxY() / 150];
    private boolean active;
    private Controller controller = new Controller();
    private Inventory inventory = new Inventory();
    private boolean pvpMode;
    private int pvpTeam;
    private boolean announced;
    private boolean spawned;
    private int fallStart;
    private boolean evil, meteor, dungeon, jungle;
    private short npcConvo;
    private int connectionState = 0;
    private boolean lock;
    private byte difficulty;

    public Player(Channel channel) {
        this.id = nextFreeId();
        System.out.println("Assigning id: " + id);
        this.channel = channel;
    }

    public void addItem(int slot, Item item) {
        getItems().put(slot, item);
    }

    public void removeItem(int slot) {
        getItems().remove(slot);
    }

    public Channel getChannel() {
        return channel;
    }

    private static int nextFreeId() {
        for (int i = 0; i < 8; i++) {
            boolean use = true;
            for (Player player : NetHandler.getPlayers()) {
                if (player.getId() == i) {
                    System.out.println(i + " is in use.");
                    use = false;
                    break;
                }
            }
            if (use) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the evil
     */
    public boolean isEvil() {
        return evil;
    }

    /**
     * @param evil the evil to set
     */
    public void setEvil(boolean evil) {
        this.evil = evil;
    }

    /**
     * @return the meteor
     */
    public boolean isMeteor() {
        return meteor;
    }

    /**
     * @param meteor the meteor to set
     */
    public void setMeteor(boolean meteor) {
        this.meteor = meteor;
    }

    /**
     * @return the dungeon
     */
    public boolean isDungeon() {
        return dungeon;
    }

    /**
     * @param dungeon the dungeon to set
     */
    public void setDungeon(boolean dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * @return the jungle
     */
    public boolean isJungle() {
        return jungle;
    }

    /**
     * @param jungle the jungle to set
     */
    public void setJungle(boolean jungle) {
        this.jungle = jungle;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the hairId
     */
    public int getHairId() {
        return hairId;
    }

    /**
     * @param hairId the hairId to set
     */
    public void setHairId(int hairId) {
        this.hairId = hairId;
    }

    /**
     * @return the hairColor
     */
    public Color getHairColor() {
        return hairColor;
    }

    /**
     * @param hairColor the hairColor to set
     */
    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    /**
     * @return the skinColor
     */
    public Color getSkinColor() {
        return skinColor;
    }

    /**
     * @param skinColor the skinColor to set
     */
    public void setSkinColor(Color skinColor) {
        this.skinColor = skinColor;
    }

    /**
     * @return the eyeColor
     */
    public Color getEyeColor() {
        return eyeColor;
    }

    /**
     * @param eyeColor the eyeColor to set
     */
    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    /**
     * @return the shirtColor
     */
    public Color getShirtColor() {
        return shirtColor;
    }

    /**
     * @param shirtColor the shirtColor to set
     */
    public void setShirtColor(Color shirtColor) {
        this.shirtColor = shirtColor;
    }

    /**
     * @return the underColor
     */
    public Color getUnderColor() {
        return underColor;
    }

    /**
     * @param underColor the underColor to set
     */
    public void setUnderColor(Color underColor) {
        this.underColor = underColor;
    }

    /**
     * @return the pantColor
     */
    public Color getPantColor() {
        return pantColor;
    }

    /**
     * @param pantColor the pantColor to set
     */
    public void setPantColor(Color pantColor) {
        this.pantColor = pantColor;
    }

    /**
     * @return the shoeColor
     */
    public Color getShoeColor() {
        return shoeColor;
    }

    /**
     * @param shoeColor the shoeColor to set
     */
    public void setShoeColor(Color shoeColor) {
        this.shoeColor = shoeColor;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * @return the items
     */
    public Map<Integer, Item> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(Map<Integer, Item> items) {
        this.setItems(items);
    }

    /**
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return the maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * @param maxHealth the maxHealth to set
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * @return the location
     */
    public Point2D.Float getLocation() {
        return location;
    }

    public Point2D.Float getVelocity() {
        return velocity;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Point2D.Float location) {
        this.location = location;
    }

    public int getSectionX(int x) {
        return x / 200;
    }

    public int getSectionY(int y) {
        return y / 150;
    }

    /*public static void SendData(int msgType, 10
    int remoteClient = -1,  whoAmi
    int ignoreClient = -1, -1
    string text = "",  ""
    int number = 0,  200
    float number2 = 0f, (float) toSendX
    float number3 = 0f, i
    float number4 = 0f) 0f
    
     */
    public void sendSection(int sectionX, int sectionY, ChannelBuffer buffer) {
        loadedTiles[sectionX][sectionY] = true;
        int toSendX = sectionX * 200;
        int toSendY = sectionY * 150;
        boolean sendData = buffer == null;
        buffer = buffer == null ? ChannelBuffers.dynamicBuffer(ByteOrder.LITTLE_ENDIAN, 256) : buffer;
        for (int i = toSendY; i < (toSendY + 150); i++) {
            // TODO: Make a non-dynamic buffer if possible see: math
            buffer.writeByte(Opcodes.SEND_TILE_SECTION);
            buffer.writeShort(200); // num12
            buffer.writeInt(toSendX); // num13
            buffer.writeInt(i); // num14
            for (int j = toSendX; j < (200 + toSendX); j++) {
                byte flag = 0;
                Tile tile = World.getWorld().getTile(j, i);
                if (tile.isActive()) {
                    flag |= TileFlags.ACTIVE;
                }
                if (tile.isLight()) {
                    flag |= TileFlags.LIGHT;
                }
                if (tile.getWall() > 0) {
                    flag |= TileFlags.WALL;
                }
                if (tile.getLiquid() > 0) {
                    flag |= TileFlags.LIQUID;
                }

                buffer.writeByte(flag);

                if (tile.isActive()) {
                    buffer.writeByte(tile.getType());
                    if (World.getWorld().isTileFrameImportant(tile.getType())) {
                        buffer.writeShort(tile.getFrameX());
                        buffer.writeShort(tile.getFrameY());
                    }
                }
                if (tile.getWall() > 0) {
                    buffer.writeByte(tile.getWall());
                }
                if (tile.getLiquid() > 0) {
                    buffer.writeByte(tile.getLiquid());
                    buffer.writeByte(tile.isLava() ? 1 : 0);
                }
            }
            ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, buffer.readableBytes() + 4);
            send.writeInt(buffer.slice(0, buffer.readableBytes()).readableBytes());
            send.writeBytes(buffer);
            if (sendData) {
                getChannel().write(send);
            }
        }
    }

    /*public static void SendData(int msgType, 20
    int remoteClient = -1,  whoAmi
    int ignoreClient = -1, -1
    string text = "",  ""
    int number = 0,  size
    float number2 = 0f, (float) tileX - num
    float number3 = 0f, (float) tileY - num
    float number4 = 0f) 0f
    
     */
    /*
     * SendData(20, whoAmi, -1, "", size, (float) (tileX - num), (float) (tileY - num), 0f);
     */
    public void sendTileSquare(int tileX, int tileY, int size) {
        int num = (size - 1) / 2;
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer(32);
        buffer.writeByte(Opcodes.SEND_TILE_SQUARE);
        buffer.writeShort(size);
        buffer.writeInt(tileX - num);
        buffer.writeInt(tileY - num);
        for (int i = tileX - num; i < (tileX - num + size); i++) {
            for (int j = tileY - num; j < (tileY - num + size); j++) {
                byte flag = 0;
                Tile tile = World.getWorld().getTile(i, j);
                if (tile.isActive()) {
                    flag |= TileFlags.ACTIVE;
                }
                if (tile.isLight()) {
                    flag |= TileFlags.LIGHT;
                }
                if (tile.getWall() > 0) {
                    flag |= TileFlags.WALL;
                }
                if (tile.getLiquid() > 0) {
                    flag |= TileFlags.LIQUID;
                }
                buffer.writeByte(flag);

                if (tile.isActive()) {
                    buffer.writeByte(tile.getType());
                    if (World.getWorld().isTileFrameImportant(tile.getType())) {
                        buffer.writeShort(tile.getFrameX());
                        buffer.writeShort(tile.getFrameY());
                    }
                }
                if (tile.getWall() > 0) {
                    buffer.writeByte(tile.getWall());
                }
                if (tile.getLiquid() > 0) {
                    buffer.writeByte(tile.getLiquid());
                    buffer.writeByte(tile.isLava() ? 1 : 0);
                }
            }
        }
        ChannelBuffer send = ChannelBuffers.buffer(buffer.writerIndex() + 4);
        send.writeInt(buffer.writerIndex());
        send.writeBytes(buffer);
        getChannel().write(send);
    }

    /**
     * @return the mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * @param mana the mana to set
     */
    public void setMana(int mana) {
        this.mana = mana;
    }

    /**
     * @return the maxMana
     */
    public int getMaxMana() {
        return maxMana;
    }

    /**
     * @param maxMana the maxMana to set
     */
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public boolean isTileLoaded(int x, int y) {
        return loadedTiles[x][y];
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Controller getController() {
        return controller;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isPvpMode() {
        return pvpMode;
    }

    public void setPvpMode(boolean pvpMode) {
        this.pvpMode = pvpMode;
    }

    public int getPvpTeam() {
        return pvpTeam;
    }

    public void setPvpTeam(int pvpTeam) {
        this.pvpTeam = pvpTeam;
    }

    public boolean isAnnounced() {
        return announced;
    }

    public void setAnnounced(boolean announced) {
        this.announced = announced;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public int getFallStart() {
        return fallStart;
    }

    public void setFallStart(int fallStart) {
        this.fallStart = fallStart;
    }

    public short getNpcConvo() {
        return npcConvo;
    }

    public void setNpcConvo(short npcConvo) {
        this.npcConvo = npcConvo;
    }

    public void sendMessage(String message, int id, int r, int g, int b) {
        ChannelBuffer buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 9 + message.length());
        buffer.writeInt(5 + message.length());
        buffer.writeByte(Opcodes.SEND_MESSAGE);
        buffer.writeByte(id);
        buffer.writeByte(r);
        buffer.writeByte(g);
        buffer.writeByte(b);
        IOTools.writeString(buffer, message);
        getChannel().write(buffer);
    }

    public void broadcastMessage(String message, int id, int r, int g, int b) {
        for (Player player : NetHandler.getPlayers()) {
            if (player.isSpawned()) {
                player.sendMessage(message, id, r, g, b);
            }
        }
    }

    public void sendUpdate(Player player) {

        byte flag = 0;
        if (controller.isUp()) {
            flag |= PlayerFlags.UP;
        }
        if (controller.isDown()) {
            flag |= PlayerFlags.DOWN;
        }
        if (controller.isLeft()) {
            flag |= PlayerFlags.LEFT;
        }
        if (controller.isRight()) {
            flag |= PlayerFlags.RIGHT;
        }
        if (controller.isJump()) {
            flag |= PlayerFlags.JUMP;
        }
        if (controller.isUseItem()) {
            flag |= PlayerFlags.CONTROL_USE_ITEM;
        }
        if (controller.getDirection() == 1) {
            flag |= PlayerFlags.DIRECTION;
        }
        ChannelBuffer buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 24);
        buffer.writeInt(20);
        buffer.writeByte(Opcodes.SEND_PLAYER_UPDATE);
        buffer.writeByte(getId());
        buffer.writeByte(flag);
        buffer.writeByte(getInventory().getSelectedItem());
        buffer.writeFloat(getLocation().x);
        buffer.writeFloat(getLocation().y);
        buffer.writeFloat(getVelocity().x);
        buffer.writeFloat(getVelocity().y);
        player.getChannel().write(buffer);
    }

    public byte getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(byte difficulty) {
        this.difficulty = difficulty;
    }

    public int getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(int connectionState) {
        this.connectionState = connectionState;
    }

    public void spawn() {
        //if (!isSpawned()) {
        System.out.println("Spawning: " + getName());
        ChannelBuffer send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 14);
        send.writeInt(10);
        send.writeByte(Opcodes.SEND_SPAWN);
        send.writeByte(getId());
        send.writeInt((int) getLocation().x);
        send.writeInt((int) getLocation().y);
        getChannel().write(send);
        setSpawned(true);
        setActive(true);
        for (Player other : NetHandler.getPlayers()) {
            if (getId() != other.getId() && other.isSpawned()) {
                other.getChannel().write(send);
                send = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 14);
                send.writeInt(10);
                send.writeByte(Opcodes.SEND_SPAWN);
                send.writeByte(other.getId());
                send.writeInt((int) other.getLocation().x);
                send.writeInt((int) other.getLocation().y);
                getChannel().write(send);
            }
            // }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 23 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
