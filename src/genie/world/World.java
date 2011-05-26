/*
 * To change this template, choose IOTools | Templates
 * and open the template in the editor.
 */
package genie.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import genie.IOTools;

/**
 *
 * @author Jay
 */
public class World {

    public static final int CUR_VERSION = 3;
    private String name;
    private int id;
    private int leftWorld, rightWorld, topWorld, bottomWorld;
    private int maxX, maxY; // maxTilesY, maxTilesX
    private int maxSectionX, maxSectionY;
    private int spawnX, spawnY;
    private double worldSurface, rockLayer;
    private double time;
    private boolean day; // dayTime
    private boolean bloodMoon;
    private int moonPhase;
    private int dungeonX, dungeonY;
    private boolean bossOneDowned, bossTwoDowned, bossThreeDowned;
    private boolean shadowOrbSmashed;
    private boolean meteorSpawned;
    private int shadowOrbCount; // byte
    private int invasionDelay, invasionSize, invasionType, invasionWarn;
    private double invasionX;
    private Tile[][] tiles;
    private Chest[] chests;
    private Sign[] signs;
    private NPC[] npcs;
    private Item[] items = new Item[200];
    private int spawnNPC;
    private boolean spawnEye;
    private int helpText;
    private boolean stopDrops, noLiquidCheck;
    private static World world;

    public static World getWorld() {
        return world;
    }

    public World(String path) {
        load(path);
        world = this;
    }

    private void load(String path) {
        FileInputStream fis = null;
        try {
            for (int i = 0; i < items.length; i++) {
                items[i] = new Item();
            }
            fis = new FileInputStream(new File(path));
            System.out.println(fis.available());
            SoftReference<ChannelBuffer> buffer = new SoftReference(ChannelBuffers.dynamicBuffer(ByteOrder.LITTLE_ENDIAN, 2048));
            int next = -1;
            byte[] tempBuff = new byte[512];
            while ((next = fis.read()) != -1) {
                tempBuff[0] = (byte) next;
                fis.read(tempBuff, 1, 511);
                buffer.get().writeBytes(tempBuff);
            }

            int version = buffer.get().readInt();
            System.out.println("Version: " + version);
            setName(IOTools.readFileString(buffer.get()));
            System.out.println(getName());
            setId(buffer.get().readInt());
            setLeftWorld(buffer.get().readInt());
            setRightWorld(buffer.get().readInt());
            setTopWorld(buffer.get().readInt());
            setBottomWorld(buffer.get().readInt());
            setMaxY(buffer.get().readInt());
            setMaxX(buffer.get().readInt());
            setTiles(new Tile[getMaxX()][getMaxY()]);
            for (int i = 0; i < getMaxX(); i++) {
                for (int j = 0; j < getMaxY(); j++) {
                    getTiles()[i][j] = new Tile();
                }
            }
            clearWorld();
            setSpawnX(buffer.get().readInt());
            setSpawnY(buffer.get().readInt());
            setWorldSurface(buffer.get().readDouble());
            setRockLayer(buffer.get().readDouble());
            setTime(buffer.get().readDouble());
            setDay(buffer.get().readByte() != 0 ? true : false);
            setMoonPhase(buffer.get().readInt());
            setBloodMoon(buffer.get().readByte() != 0 ? true : false);
            setDungeonX(buffer.get().readInt());
            setDungeonY(buffer.get().readInt());
            setBossOneDowned(buffer.get().readByte() != 0 ? true : false);
            setBossTwoDowned(buffer.get().readByte() != 0 ? true : false);
            setBossThreeDowned(buffer.get().readByte() != 0 ? true : false);
            setShadowOrbSmashed(buffer.get().readByte() != 0 ? true : false);
            setMeteorSpawned(buffer.get().readByte() != 0 ? true : false);
            setShadowOrbCount(buffer.get().readInt());
            setInvasionDelay(buffer.get().readInt());
            setInvasionSize(buffer.get().readInt());
            setInvasionType(buffer.get().readInt());
            setInvasionX(buffer.get().readDouble());

            int activeCount = 0;
            for (int x = 0; x < getMaxX(); x++) {
                if (x != 0 && (x % 50 == 0 || x == getMaxX() - 1)) {
                    System.out.println("Loaded: " + ((((float) x) / ((float) getMaxX())) * 100f + 1f) + "%");
                }

                for (int y = 0; y < getMaxY(); y++) {
                    Tile tile = getTiles()[x][y];
                    tile.setActive(buffer.get().readByte() != 0 ? true : false);
                    if (tile.isActive()) {
                        tile.setType(buffer.get().readByte());
                        if (isTileFrameImportant(tile.getType())) {
                            tile.setFrameX(buffer.get().readShort());
                            tile.setFrameY(buffer.get().readShort());
                        } else {
                            tile.setFrameX((short) -1);
                            tile.setFrameY((short) -1);
                        }
                    }
                    tile.setLight(buffer.get().readByte() != 0 ? true : false);
                    if (buffer.get().readByte() != 0 ? true : false) { // ?_?
                        tile.setWall(buffer.get().readByte());
                    }
                    if (buffer.get().readByte() != 0 ? true : false) {
                        tile.setLiquid(buffer.get().readByte());
                        tile.setLava(buffer.get().readByte() != 0 ? true : false);
                    }
                }
            }
            int chestCount = 0;
            setChests(new Chest[Chest.MAX_CHESTS]);
            for (int i = 0; i < 0x3e8; i++) { // CHESTS
                if (buffer.get().readByte() != 0 ? true : false) {
                    Chest chest = new Chest();
                    getChests()[i] = chest;
                    chest.setX(buffer.get().readInt());
                    chest.setY(buffer.get().readInt());
                    chestCount++;
                    for (int j = 0; j < 20; j++) {
                        Item item = new Item();
                        chest.setItem(j, item);
                        int stack = buffer.get().readByte();
                        if (stack > 0) {
                            String itemName = IOTools.readFileString(buffer.get());
                            item.setDefaults(itemName);
                            item.setStack(stack);
                        }
                    }
                }
            }

            for (int i = 0; i < 998/*0x3e8*/; i++) { // SIGNS
                if (buffer.get().readByte() != 0 ? true : false) {
                    String text = IOTools.readFileString(buffer.get());
                    int x = buffer.get().readInt();
                    int y = buffer.get().readInt();
                    if (getTiles()[x][y].isActive() && getTiles()[x][y].getType() == 0x37) {
                        Sign sign = new Sign();
                        getSigns()[i] = sign;
                        sign.setText(text);
                        sign.setX(x);
                        sign.setY(y);
                    }
                }
            }

            setNpcs(new NPC[NPC.MAX_NPCS]);
            for (int i = 0; i < NPC.MAX_NPCS; i++) {
                getNpcs()[i] = new NPC();
            }
            byte nNpcFlag = buffer.get().readByte();
            boolean npcFlag = nNpcFlag != 0 ? true : false;
            for (int i = 0; npcFlag; i++) {
                NPC npc = getNpcs()[i];
                npc.setDefaults(IOTools.readFileString(buffer.get()));
                npc.getPosition().setLocation(buffer.get().readFloat(), buffer.get().readFloat());
                npc.setHomeless(buffer.get().readByte() != 0 ? true : false);
                npc.setHomeTileX(buffer.get().readInt());
                npc.setHomeTileY(buffer.get().readInt());
                npcFlag = buffer.get().readByte() != 0 ? true : false;
            }
            buffer.get().resetReaderIndex();
            System.out.println(buffer.get().readableBytes());
            buffer.get().skipBytes(buffer.get().readableBytes());
            buffer.get().discardReadBytes();
            buffer.enqueue();
            Runtime.getRuntime().gc();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * TODO: Finish
     */
    private void clearWorld() {

        setSpawnEye(false);
        setSpawnNPC(0);
        setShadowOrbCount(0);
        setHelpText(0);
        setDungeonX(0);
        setDungeonY(0);
        setBossOneDowned(false);
        setBossTwoDowned(false);
        setBossThreeDowned(false);
        setShadowOrbSmashed(false);
        setMeteorSpawned(false);
        setStopDrops(false);
        setInvasionDelay(0);
        setInvasionType(0);
        setInvasionSize(0);
        setInvasionWarn(0);
        setInvasionX(0.0);
        setNoLiquidCheck(false);

        Liquid.LIQUID_COUNT = 0;
        //Liquidbuffer.get().numLiquidBuffer = 0;

    }

    public NPC getNpc(int position) {
        return npcs[position];
    }

    public Item getItem(int position) {
        return items[position];
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    /**
     * TODO: Make a switch
     * @param id the id to check
     * @return whether or not the frame is important
     */
    public boolean isTileFrameImportant(int id) {
        if (id == 3) {
            return true;
        }
        if (id == 5) {
            return true;
        }
        if (id == 10) {
            return true;
        }
        if (id == 11) {
            return true;
        }
        if (id == 12) {
            return true;
        }
        if (id == 13) {
            return true;
        }
        if (id == 14) {
            return true;
        }
        if (id == 15) {
            return true;
        }
        if (id == 0x10) {
            return true;
        }
        if (id == 0x11) {
            return true;
        }
        if (id == 0x12) {
            return true;
        }
        if (id == 20) {
            return true;
        }
        if (id == 0x15) {
            return true;
        }
        if (id == 0x18) {
            return true;
        }
        if (id == 0x1a) {
            return true;
        }
        if (id == 0x1b) {
            return true;
        }
        if (id == 0x1c) {
            return true;
        }
        if (id == 0x1d) {
            return true;
        }
        if (id == 0x1f) {
            return true;
        }
        if (id == 0x21) {
            return true;
        }
        if (id == 0x22) {
            return true;
        }
        if (id == 0x23) {
            return true;
        }
        if (id == 0x24) {
            return true;
        }
        if (id == 0x2a) {
            return true;
        }
        if (id == 50) {
            return true;
        }
        if (id == 0x37) {
            return true;
        }
        if (id == 0x3d) {
            return true;
        }
        if (id == 0x47) {
            return true;
        }
        if (id == 0x48) {
            return true;
        }
        if (id == 0x49) {
            return true;
        }
        if (id == 0x4a) {
            return true;
        }
        if (id == 0x4d) {
            return true;
        }
        if (id == 0x4e) {
            return true;
        }
        if (id == 0x4f) {
            return true;
        }
        return false;
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
     * @return the leftWorld
     */
    public int getLeftWorld() {
        return leftWorld;
    }

    /**
     * @param leftWorld the leftWorld to set
     */
    public void setLeftWorld(int leftWorld) {
        this.leftWorld = leftWorld;
    }

    /**
     * @return the rightWorld
     */
    public int getRightWorld() {
        return rightWorld;
    }

    /**
     * @param rightWorld the rightWorld to set
     */
    public void setRightWorld(int rightWorld) {
        this.rightWorld = rightWorld;
    }

    /**
     * @return the topWorld
     */
    public int getTopWorld() {
        return topWorld;
    }

    /**
     * @param topWorld the topWorld to set
     */
    public void setTopWorld(int topWorld) {
        this.topWorld = topWorld;
    }

    /**
     * @return the bottomWorld
     */
    public int getBottomWorld() {
        return bottomWorld;
    }

    /**
     * @param bottomWorld the bottomWorld to set
     */
    public void setBottomWorld(int bottomWorld) {
        this.bottomWorld = bottomWorld;
    }

    /**
     * @return the maxX
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * @param maxX the maxX to set
     */
    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    /**
     * @return the maxY
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * @param maxY the maxY to set
     */
    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    /**
     * @return the spawnX
     */
    public int getSpawnX() {
        return spawnX;
    }

    /**
     * @param spawnX the spawnX to set
     */
    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }

    /**
     * @return the spawnY
     */
    public int getSpawnY() {
        return spawnY;
    }

    /**
     * @param spawnY the spawnY to set
     */
    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }

    /**
     * @return the worldSurface
     */
    public double getWorldSurface() {
        return worldSurface;
    }

    /**
     * @param worldSurface the worldSurface to set
     */
    public void setWorldSurface(double worldSurface) {
        this.worldSurface = worldSurface;
    }

    /**
     * @return the rockLayer
     */
    public double getRockLayer() {
        return rockLayer;
    }

    /**
     * @param rockLayer the rockLayer to set
     */
    public void setRockLayer(double rockLayer) {
        this.rockLayer = rockLayer;
    }

    /**
     * @return the time
     */
    public double getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * @return the day
     */
    public boolean isDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(boolean day) {
        this.day = day;
    }

    /**
     * @return the bloodMoon
     */
    public boolean isBloodMoon() {
        return bloodMoon;
    }

    /**
     * @param bloodMoon the bloodMoon to set
     */
    public void setBloodMoon(boolean bloodMoon) {
        this.bloodMoon = bloodMoon;
    }

    /**
     * @return the moonPhase
     */
    public int getMoonPhase() {
        return moonPhase;
    }

    /**
     * @param moonPhase the moonPhase to set
     */
    public void setMoonPhase(int moonPhase) {
        this.moonPhase = moonPhase;
    }

    /**
     * @return the dungeonX
     */
    public int getDungeonX() {
        return dungeonX;
    }

    /**
     * @param dungeonX the dungeonX to set
     */
    public void setDungeonX(int dungeonX) {
        this.dungeonX = dungeonX;
    }

    /**
     * @return the dungeonY
     */
    public int getDungeonY() {
        return dungeonY;
    }

    /**
     * @param dungeonY the dungeonY to set
     */
    public void setDungeonY(int dungeonY) {
        this.dungeonY = dungeonY;
    }

    /**
     * @return the bossOneDowned
     */
    public boolean isBossOneDowned() {
        return bossOneDowned;
    }

    /**
     * @param bossOneDowned the bossOneDowned to set
     */
    public void setBossOneDowned(boolean bossOneDowned) {
        this.bossOneDowned = bossOneDowned;
    }

    /**
     * @return the bossTwoDowned
     */
    public boolean isBossTwoDowned() {
        return bossTwoDowned;
    }

    /**
     * @param bossTwoDowned the bossTwoDowned to set
     */
    public void setBossTwoDowned(boolean bossTwoDowned) {
        this.bossTwoDowned = bossTwoDowned;
    }

    /**
     * @return the bossThreeDowned
     */
    public boolean isBossThreeDowned() {
        return bossThreeDowned;
    }

    /**
     * @param bossThreeDowned the bossThreeDowned to set
     */
    public void setBossThreeDowned(boolean bossThreeDowned) {
        this.bossThreeDowned = bossThreeDowned;
    }

    /**
     * @return the shadowOrbSmashed
     */
    public boolean isShadowOrbSmashed() {
        return shadowOrbSmashed;
    }

    /**
     * @param shadowOrbSmashed the shadowOrbSmashed to set
     */
    public void setShadowOrbSmashed(boolean shadowOrbSmashed) {
        this.shadowOrbSmashed = shadowOrbSmashed;
    }

    /**
     * @return the meteorSpawned
     */
    public boolean isMeteorSpawned() {
        return meteorSpawned;
    }

    /**
     * @param meteorSpawned the meteorSpawned to set
     */
    public void setMeteorSpawned(boolean meteorSpawned) {
        this.meteorSpawned = meteorSpawned;
    }

    /**
     * @return the shadowOrbCount
     */
    public int getShadowOrbCount() {
        return shadowOrbCount;
    }

    /**
     * @param shadowOrbCount the shadowOrbCount to set
     */
    public void setShadowOrbCount(int shadowOrbCount) {
        this.shadowOrbCount = shadowOrbCount;
    }

    /**
     * @return the invasionDelay
     */
    public int getInvasionDelay() {
        return invasionDelay;
    }

    /**
     * @param invasionDelay the invasionDelay to set
     */
    public void setInvasionDelay(int invasionDelay) {
        this.invasionDelay = invasionDelay;
    }

    /**
     * @return the invasionSize
     */
    public int getInvasionSize() {
        return invasionSize;
    }

    /**
     * @param invasionSize the invasionSize to set
     */
    public void setInvasionSize(int invasionSize) {
        this.invasionSize = invasionSize;
    }

    /**
     * @return the invasionType
     */
    public int getInvasionType() {
        return invasionType;
    }

    /**
     * @param invasionType the invasionType to set
     */
    public void setInvasionType(int invasionType) {
        this.invasionType = invasionType;
    }

    /**
     * @return the invasionWarn
     */
    public int getInvasionWarn() {
        return invasionWarn;
    }

    /**
     * @param invasionWarn the invasionWarn to set
     */
    public void setInvasionWarn(int invasionWarn) {
        this.invasionWarn = invasionWarn;
    }

    /**
     * @return the invasionX
     */
    public double getInvasionX() {
        return invasionX;
    }

    /**
     * @param invasionX the invasionX to set
     */
    public void setInvasionX(double invasionX) {
        this.invasionX = invasionX;
    }

    /**
     * @return the tiles
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * @param tiles the tiles to set
     */
    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * @return the chests
     */
    public Chest[] getChests() {
        return chests;
    }

    /**
     * @param chests the chests to set
     */
    public void setChests(Chest[] chests) {
        this.chests = chests;
    }

    /**
     * @return the signs
     */
    public Sign[] getSigns() {
        return signs;
    }

    /**
     * @param signs the signs to set
     */
    public void setSigns(Sign[] signs) {
        this.signs = signs;
    }

    /**
     * @return the npcs
     */
    public NPC[] getNpcs() {
        return npcs;
    }

    /**
     * @param npcs the npcs to set
     */
    public void setNpcs(NPC[] npcs) {
        this.npcs = npcs;
    }

    /**
     * @return the spawnNPC
     */
    public int getSpawnNPC() {
        return spawnNPC;
    }

    /**
     * @param spawnNPC the spawnNPC to set
     */
    public void setSpawnNPC(int spawnNPC) {
        this.spawnNPC = spawnNPC;
    }

    /**
     * @return the spawnEye
     */
    public boolean isSpawnEye() {
        return spawnEye;
    }

    /**
     * @param spawnEye the spawnEye to set
     */
    public void setSpawnEye(boolean spawnEye) {
        this.spawnEye = spawnEye;
    }

    /**
     * @return the helpText
     */
    public int getHelpText() {
        return helpText;
    }

    /**
     * @param helpText the helpText to set
     */
    public void setHelpText(int helpText) {
        this.helpText = helpText;
    }

    /**
     * @return the stopDrops
     */
    public boolean isStopDrops() {
        return stopDrops;
    }

    /**
     * @param stopDrops the stopDrops to set
     */
    public void setStopDrops(boolean stopDrops) {
        this.stopDrops = stopDrops;
    }

    /**
     * @return the noLiquidCheck
     */
    public boolean isNoLiquidCheck() {
        return noLiquidCheck;
    }

    /**
     * @param noLiquidCheck the noLiquidCheck to set
     */
    public void setNoLiquidCheck(boolean noLiquidCheck) {
        this.noLiquidCheck = noLiquidCheck;
    }

    public int getMaxSectionX() {
        return getMaxX() / 200;
    }

    public int getMaxSectionY() {
        return getMaxY() / 150;
    }
}
