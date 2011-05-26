/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genie.net;

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

    private Channel channel;
    private String name;
    private int id;
    private int hairId;
    private Color hairColor, skinColor, eyeColor, shirtColor, underColor, pantColor, shoeColor;
    private Map<Integer, Item> items = new HashMap<Integer, Item>(); // Packet editors could have fun, enforce somehow.
    private int health, maxHealth;
    private int mana, maxMana;
    private Point2D.Double location = new Point2D.Double();
    private boolean[][] loadedTiles = new boolean[World.getWorld().getMaxX() / 200][World.getWorld().getMaxY() / 150];
    private boolean active;
    private int statusMax = 0;

    public Player(Channel channel) {

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
    public Point2D.Double getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Point2D.Double location) {
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
            getChannel().write(send);
            System.out.println("Sent section sizeof: " + send.readableBytes()
                    + "buffer section sizeof: " + buffer.readableBytes()
                    + "Sent Capacity: " + send.capacity()
                    + "Buffer capacity: " + buffer.capacity());
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

    public int getStatusMax() {
        return statusMax;
    }

    public void addStatusMax(int add) {
        statusMax += add;
    }
}
