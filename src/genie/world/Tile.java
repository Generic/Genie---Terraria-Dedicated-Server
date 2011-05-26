/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genie.world;

/**
 *
 * @author Jay
 */
public class Tile {
    public static class TileFlags {
        public static final byte ACTIVE = 1,
                LIGHT = 2,
                WALL = 4,
                LIQUID = 8;
    }

    private boolean active;
    private boolean checkingLiquid;
    private byte frameNumber;
    private short frameX;
    private short frameY;
    private boolean lava;
    private boolean light;
    private byte liquid;
    private boolean skipLiquid;
    private byte type;
    private byte wall;
    private byte wallFrameNumber;
    private byte wallFrameX;
    private byte wallFrameY;

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the checkingLiquid
     */
    public boolean isCheckingLiquid() {
        return checkingLiquid;
    }

    /**
     * @param checkingLiquid the checkingLiquid to set
     */
    public void setCheckingLiquid(boolean checkingLiquid) {
        this.checkingLiquid = checkingLiquid;
    }

    /**
     * @return the frameNumber
     */
    public byte getFrameNumber() {
        return frameNumber;
    }

    /**
     * @param frameNumber the frameNumber to set
     */
    public void setFrameNumber(byte frameNumber) {
        this.frameNumber = frameNumber;
    }

    /**
     * @return the frameX
     */
    public short getFrameX() {
        return frameX;
    }

    /**
     * @param frameX the frameX to set
     */
    public void setFrameX(short frameX) {
        this.frameX = frameX;
    }

    /**
     * @return the frameY
     */
    public short getFrameY() {
        return frameY;
    }

    /**
     * @param frameY the frameY to set
     */
    public void setFrameY(short frameY) {
        this.frameY = frameY;
    }

    /**
     * @return the lava
     */
    public boolean isLava() {
        return lava;
    }

    /**
     * @param lava the lava to set
     */
    public void setLava(boolean lava) {
        this.lava = lava;
    }

    /**
     * @return the light
     */
    public boolean isLight() {
        return light;
    }

    /**
     * @param light the light to set
     */
    public void setLight(boolean light) {
        this.light = light;
    }

    /**
     * @return the liquid
     */
    public byte getLiquid() {
        return liquid;
    }

    /**
     * @param liquid the liquid to set
     */
    public void setLiquid(byte liquid) {
        this.liquid = liquid;
    }

    /**
     * @return the skipLiquid
     */
    public boolean isSkipLiquid() {
        return skipLiquid;
    }

    /**
     * @param skipLiquid the skipLiquid to set
     */
    public void setSkipLiquid(boolean skipLiquid) {
        this.skipLiquid = skipLiquid;
    }

    /**
     * @return the type
     */
    public byte getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(byte type) {
        this.type = type;
    }

    /**
     * @return the wall
     */
    public byte getWall() {
        return wall;
    }

    /**
     * @param wall the wall to set
     */
    public void setWall(byte wall) {
        this.wall = wall;
    }

    /**
     * @return the wallFrameNumber
     */
    public byte getWallFrameNumber() {
        return wallFrameNumber;
    }

    /**
     * @param wallFrameNumber the wallFrameNumber to set
     */
    public void setWallFrameNumber(byte wallFrameNumber) {
        this.wallFrameNumber = wallFrameNumber;
    }

    /**
     * @return the wallFrameX
     */
    public byte getWallFrameX() {
        return wallFrameX;
    }

    /**
     * @param wallFrameX the wallFrameX to set
     */
    public void setWallFrameX(byte wallFrameX) {
        this.wallFrameX = wallFrameX;
    }

    /**
     * @return the wallFrameY
     */
    public byte getWallFrameY() {
        return wallFrameY;
    }

    /**
     * @param wallFrameY the wallFrameY to set
     */
    public void setWallFrameY(byte wallFrameY) {
        this.wallFrameY = wallFrameY;
    }
}
