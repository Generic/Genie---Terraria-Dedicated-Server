/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genie.world;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Jay
 */
public class NPC {
    public static final boolean DUMB_AI = false;
    public static final int MAX_AI = 4;
    public static final int MAX_NPCS = 0x3e8;

    /**
     * @return the activeRangeX
     */
    public static int getActiveRangeX() {
        return activeRangeX;
    }

    /**
     * @param aActiveRangeX the activeRangeX to set
     */
    public static void setActiveRangeX(int aActiveRangeX) {
        activeRangeX = aActiveRangeX;
    }

    /**
     * @return the activeRangeY
     */
    public static int getActiveRangeY() {
        return activeRangeY;
    }

    /**
     * @param aActiveRangeY the activeRangeY to set
     */
    public static void setActiveRangeY(int aActiveRangeY) {
        activeRangeY = aActiveRangeY;
    }

    /**
     * @return the activeTime
     */
    public static int getActiveTime() {
        return activeTime;
    }

    /**
     * @param aActiveTime the activeTime to set
     */
    public static void setActiveTime(int aActiveTime) {
        activeTime = aActiveTime;
    }

    /**
     * @return the defaultMaxSpawns
     */
    public static int getDefaultMaxSpawns() {
        return defaultMaxSpawns;
    }

    /**
     * @param aDefaultMaxSpawns the defaultMaxSpawns to set
     */
    public static void setDefaultMaxSpawns(int aDefaultMaxSpawns) {
        defaultMaxSpawns = aDefaultMaxSpawns;
    }

    /**
     * @return the defaultSpawnRate
     */
    public static int getDefaultSpawnRate() {
        return defaultSpawnRate;
    }

    /**
     * @param aDefaultSpawnRate the defaultSpawnRate to set
     */
    public static void setDefaultSpawnRate(int aDefaultSpawnRate) {
        defaultSpawnRate = aDefaultSpawnRate;
    }

    /**
     * @return the immuneTime
     */
    public static int getImmuneTime() {
        return immuneTime;
    }

    /**
     * @param aImmuneTime the immuneTime to set
     */
    public static void setImmuneTime(int aImmuneTime) {
        immuneTime = aImmuneTime;
    }

    /**
     * @return the maxSpawns
     */
    public static int getMaxSpawns() {
        return maxSpawns;
    }

    /**
     * @param aMaxSpawns the maxSpawns to set
     */
    public static void setMaxSpawns(int aMaxSpawns) {
        maxSpawns = aMaxSpawns;
    }

    /**
     * @return the safeRangeX
     */
    public static int getSafeRangeX() {
        return safeRangeX;
    }

    /**
     * @param aSafeRangeX the safeRangeX to set
     */
    public static void setSafeRangeX(int aSafeRangeX) {
        safeRangeX = aSafeRangeX;
    }

    /**
     * @return the safeRangeY
     */
    public static int getSafeRangeY() {
        return safeRangeY;
    }

    /**
     * @param aSafeRangeY the safeRangeY to set
     */
    public static void setSafeRangeY(int aSafeRangeY) {
        safeRangeY = aSafeRangeY;
    }

    /**
     * @return the spawnRangeX
     */
    public static int getSpawnRangeX() {
        return spawnRangeX;
    }

    /**
     * @param aSpawnRangeX the spawnRangeX to set
     */
    public static void setSpawnRangeX(int aSpawnRangeX) {
        spawnRangeX = aSpawnRangeX;
    }

    /**
     * @return the spawnRangeY
     */
    public static int getSpawnRangeY() {
        return spawnRangeY;
    }

    /**
     * @param aSpawnRangeY the spawnRangeY to set
     */
    public static void setSpawnRangeY(int aSpawnRangeY) {
        spawnRangeY = aSpawnRangeY;
    }

    /**
     * @return the spawnRate
     */
    public static int getSpawnRate() {
        return spawnRate;
    }

    /**
     * @param aSpawnRate the spawnRate to set
     */
    public static void setSpawnRate(int aSpawnRate) {
        spawnRate = aSpawnRate;
    }

    /**
     * @return the spawnSpaceX
     */
    public static int getSpawnSpaceX() {
        return spawnSpaceX;
    }

    /**
     * @param aSpawnSpaceX the spawnSpaceX to set
     */
    public static void setSpawnSpaceX(int aSpawnSpaceX) {
        spawnSpaceX = aSpawnSpaceX;
    }

    /**
     * @return the spawnSpaceY
     */
    public static int getSpawnSpaceY() {
        return spawnSpaceY;
    }

    /**
     * @param aSpawnSpaceY the spawnSpaceY to set
     */
    public static void setSpawnSpaceY(int aSpawnSpaceY) {
        spawnSpaceY = aSpawnSpaceY;
    }

    /**
     * @return the townRangeX
     */
    public static int getTownRangeX() {
        return townRangeX;
    }

    /**
     * @param aTownRangeX the townRangeX to set
     */
    public static void setTownRangeX(int aTownRangeX) {
        townRangeX = aTownRangeX;
    }

    /**
     * @return the townRangeY
     */
    public static int getTownRangeY() {
        return townRangeY;
    }

    /**
     * @param aTownRangeY the townRangeY to set
     */
    public static void setTownRangeY(int aTownRangeY) {
        townRangeY = aTownRangeY;
    }
    private boolean active;
    private static int activeRangeX = (800 * 2);
    private static int activeRangeY = (600 * 2);
    private static int activeTime = 0x3e8;
    private float[] ai = new float[MAX_AI];
    private int aiAction;
    private int aiStyle;
    private int alpha;
    private boolean behindTiles;
    private boolean boss;
    private boolean closeDoor;
    private boolean collideX;
    private boolean collideY;
    private Color color;
    private int damage;
    private static int defaultMaxSpawns = 4;
    private static int defaultSpawnRate = 700;
    private int defense;
    private int direction = 1;
    private int directionY = 1;
    private int doorX;
    private int doorY;
    private Rectangle2D frame = new Rectangle2D.Double();
    private double frameCounter;
    private boolean friendly;
    private int friendlyRegen;
    private int height;
    private boolean homeless;
    private int homeTileX = -1;
    private int homeTileY = -1;
    private int[] immune = new int[9];
    private static int immuneTime = 20;
    private float knockBackResist = 1f;
    private boolean lavaWet;
    private int life;
    private int lifeMax;
    private static int maxSpawns = defaultMaxSpawns;
    private String name;
    private boolean netUpdate;
    private boolean noGravity;
    private boolean noTileCollide;
    private int oldDirection;
    private int oldDirectionY;
    private Point.Float oldPosition = new Point.Float();
    private int oldTarget;
    private Rectangle2D.Float oldVelocity;
    private Point.Float position = new Point.Float();
    private float rotation;
    private static int safeRangeX = ((int) ((800 / 0x10) * 0.55));
    private static int safeRangeY = ((int) ((600 / 0x10) * 0.55));
    private float scale = 1f;
    private int soundDelay;
    private int soundHit;
    private int soundKilled;
    private static int spawnRangeX = ((int) ((800 / 0x10) * 1.2));
    private static int spawnRangeY = ((int) ((600 / 0x10) * 1.2));
    private static int spawnRate = defaultSpawnRate;
    private static int spawnSpaceX = 4;
    private static int spawnSpaceY = 4;
    private int spriteDirection = -1;
    private int target = -1;
    private Rectangle2D targetRect;
    private int timeLeft;
    private boolean townNPC;
    private static int townRangeX = (800 * 3);
    private static int townRangeY = (600 * 3);
    private int type;
    private float value;
    private Rectangle2D.Float velocity = new Rectangle2D.Float();
    private boolean wet;
    private byte wetCount;
    private int whoAmI;
    private int width;

    public Rectangle2D.Float getVelocity() {
        return velocity;
    }

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
     * @return the ai
     */
    public float[] getAi() {
        return ai;
    }

    /**
     * @param ai the ai to set
     */
    public void setAi(float[] ai) {
        this.ai = ai;
    }

    /**
     * @return the aiAction
     */
    public int getAiAction() {
        return aiAction;
    }

    /**
     * @param aiAction the aiAction to set
     */
    public void setAiAction(int aiAction) {
        this.aiAction = aiAction;
    }

    /**
     * @return the aiStyle
     */
    public int getAiStyle() {
        return aiStyle;
    }

    /**
     * @param aiStyle the aiStyle to set
     */
    public void setAiStyle(int aiStyle) {
        this.aiStyle = aiStyle;
    }

    /**
     * @return the alpha
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    /**
     * @return the behindTiles
     */
    public boolean isBehindTiles() {
        return behindTiles;
    }

    /**
     * @param behindTiles the behindTiles to set
     */
    public void setBehindTiles(boolean behindTiles) {
        this.behindTiles = behindTiles;
    }

    /**
     * @return the boss
     */
    public boolean isBoss() {
        return boss;
    }

    /**
     * @param boss the boss to set
     */
    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    /**
     * @return the closeDoor
     */
    public boolean isCloseDoor() {
        return closeDoor;
    }

    /**
     * @param closeDoor the closeDoor to set
     */
    public void setCloseDoor(boolean closeDoor) {
        this.closeDoor = closeDoor;
    }

    /**
     * @return the collideX
     */
    public boolean isCollideX() {
        return collideX;
    }

    /**
     * @param collideX the collideX to set
     */
    public void setCollideX(boolean collideX) {
        this.collideX = collideX;
    }

    /**
     * @return the collideY
     */
    public boolean isCollideY() {
        return collideY;
    }

    /**
     * @param collideY the collideY to set
     */
    public void setCollideY(boolean collideY) {
        this.collideY = collideY;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * @return the defense
     */
    public int getDefense() {
        return defense;
    }

    /**
     * @param defense the defense to set
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return the directionY
     */
    public int getDirectionY() {
        return directionY;
    }

    /**
     * @param directionY the directionY to set
     */
    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    /**
     * @return the doorX
     */
    public int getDoorX() {
        return doorX;
    }

    /**
     * @param doorX the doorX to set
     */
    public void setDoorX(int doorX) {
        this.doorX = doorX;
    }

    /**
     * @return the doorY
     */
    public int getDoorY() {
        return doorY;
    }

    /**
     * @param doorY the doorY to set
     */
    public void setDoorY(int doorY) {
        this.doorY = doorY;
    }

    /**
     * @return the frame
     */
    public Rectangle2D getFrame() {
        return frame;
    }

    /**
     * @param frame the frame to set
     */
    public void setFrame(Rectangle frame) {
        this.frame = frame;
    }

    /**
     * @return the frameCounter
     */
    public double getFrameCounter() {
        return frameCounter;
    }

    /**
     * @param frameCounter the frameCounter to set
     */
    public void setFrameCounter(double frameCounter) {
        this.frameCounter = frameCounter;
    }

    /**
     * @return the friendly
     */
    public boolean isFriendly() {
        return friendly;
    }

    /**
     * @param friendly the friendly to set
     */
    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    /**
     * @return the friendlyRegen
     */
    public int getFriendlyRegen() {
        return friendlyRegen;
    }

    /**
     * @param friendlyRegen the friendlyRegen to set
     */
    public void setFriendlyRegen(int friendlyRegen) {
        this.friendlyRegen = friendlyRegen;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the homeless
     */
    public boolean isHomeless() {
        return homeless;
    }

    /**
     * @param homeless the homeless to set
     */
    public void setHomeless(boolean homeless) {
        this.homeless = homeless;
    }

    /**
     * @return the homeTileX
     */
    public int getHomeTileX() {
        return homeTileX;
    }

    /**
     * @param homeTileX the homeTileX to set
     */
    public void setHomeTileX(int homeTileX) {
        this.homeTileX = homeTileX;
    }

    /**
     * @return the homeTileY
     */
    public int getHomeTileY() {
        return homeTileY;
    }

    /**
     * @param homeTileY the homeTileY to set
     */
    public void setHomeTileY(int homeTileY) {
        this.homeTileY = homeTileY;
    }

    /**
     * @return the immune
     */
    public int[] getImmune() {
        return immune;
    }

    /**
     * @param immune the immune to set
     */
    public void setImmune(int[] immune) {
        this.immune = immune;
    }

    /**
     * @return the knockBackResist
     */
    public float getKnockBackResist() {
        return knockBackResist;
    }

    /**
     * @param knockBackResist the knockBackResist to set
     */
    public void setKnockBackResist(float knockBackResist) {
        this.knockBackResist = knockBackResist;
    }

    /**
     * @return the lavaWet
     */
    public boolean isLavaWet() {
        return lavaWet;
    }

    /**
     * @param lavaWet the lavaWet to set
     */
    public void setLavaWet(boolean lavaWet) {
        this.lavaWet = lavaWet;
    }

    /**
     * @return the life
     */
    public int getLife() {
        return life;
    }

    /**
     * @param life the life to set
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * @return the lifeMax
     */
    public int getLifeMax() {
        return lifeMax;
    }

    /**
     * @param lifeMax the lifeMax to set
     */
    public void setLifeMax(int lifeMax) {
        this.lifeMax = lifeMax;
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
    public void setname(String name) {
        this.name = name;
    }

    /**
     * @return the netUpdate
     */
    public boolean isNetUpdate() {
        return netUpdate;
    }

    /**
     * @param netUpdate the netUpdate to set
     */
    public void setNetUpdate(boolean netUpdate) {
        this.netUpdate = netUpdate;
    }

    /**
     * @return the noGravity
     */
    public boolean isNoGravity() {
        return noGravity;
    }

    /**
     * @param noGravity the noGravity to set
     */
    public void setNoGravity(boolean noGravity) {
        this.noGravity = noGravity;
    }

    /**
     * @return the noTileCollide
     */
    public boolean isNoTileCollide() {
        return noTileCollide;
    }

    /**
     * @param noTileCollide the noTileCollide to set
     */
    public void setNoTileCollide(boolean noTileCollide) {
        this.noTileCollide = noTileCollide;
    }

    /**
     * @return the oldDirection
     */
    public int getOldDirection() {
        return oldDirection;
    }

    /**
     * @param oldDirection the oldDirection to set
     */
    public void setOldDirection(int oldDirection) {
        this.oldDirection = oldDirection;
    }

    /**
     * @return the oldDirectionY
     */
    public int getOldDirectionY() {
        return oldDirectionY;
    }

    /**
     * @param oldDirectionY the oldDirectionY to set
     */
    public void setOldDirectionY(int oldDirectionY) {
        this.oldDirectionY = oldDirectionY;
    }

    /**
     * @return the oldTarget
     */
    public int getOldTarget() {
        return oldTarget;
    }

    /**
     * @param oldTarget the oldTarget to set
     */
    public void setOldTarget(int oldTarget) {
        this.oldTarget = oldTarget;
    }

    /**
     * @return the rotation
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     * @return the scale
     */
    public float getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * @return the soundDelay
     */
    public int getSoundDelay() {
        return soundDelay;
    }

    /**
     * @param soundDelay the soundDelay to set
     */
    public void setSoundDelay(int soundDelay) {
        this.soundDelay = soundDelay;
    }

    /**
     * @return the soundHit
     */
    public int getSoundHit() {
        return soundHit;
    }

    /**
     * @param soundHit the soundHit to set
     */
    public void setSoundHit(int soundHit) {
        this.soundHit = soundHit;
    }

    /**
     * @return the soundKilled
     */
    public int getSoundKilled() {
        return soundKilled;
    }

    /**
     * @param soundKilled the soundKilled to set
     */
    public void setSoundKilled(int soundKilled) {
        this.soundKilled = soundKilled;
    }

    /**
     * @return the spriteDirection
     */
    public int getSpriteDirection() {
        return spriteDirection;
    }

    /**
     * @param spriteDirection the spriteDirection to set
     */
    public void setSpriteDirection(int spriteDirection) {
        this.spriteDirection = spriteDirection;
    }

    /**
     * @return the target
     */
    public int getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(int target) {
        this.target = target;
    }

    /**
     * @return the targetRect
     */
    public Rectangle2D getTargetRect() {
        return targetRect;
    }

    /**
     * @param targetRect the targetRect to set
     */
    public void setTargetRect(Rectangle2D targetRect) {
        this.targetRect = targetRect;
    }

    /**
     * @return the timeLeft
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * @param timeLeft the timeLeft to set
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * @return the townNPC
     */
    public boolean isTownNPC() {
        return townNPC;
    }

    /**
     * @param townNPC the townNPC to set
     */
    public void setTownNPC(boolean townNPC) {
        this.townNPC = townNPC;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the value
     */
    public float getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(float value) {
        this.value = value;
    }

    /**
     * @return the wet
     */
    public boolean isWet() {
        return wet;
    }

    /**
     * @param wet the wet to set
     */
    public void setWet(boolean wet) {
        this.wet = wet;
    }

    /**
     * @return the wetCount
     */
    public byte getWetCount() {
        return wetCount;
    }

    /**
     * @param wetCount the wetCount to set
     */
    public void setWetCount(byte wetCount) {
        this.wetCount = wetCount;
    }

    /**
     * @return the whoAmI
     */
    public int getWhoAmI() {
        return whoAmI;
    }

    /**
     * @param whoAmI the whoAmI to set
     */
    public void setWhoAmI(int whoAmI) {
        this.whoAmI = whoAmI;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    public void setDefaults(String name) {
        this.setDefaults(0);
        if (name.equals("Green Slime")) {
            this.setDefaults(1);
            this.name = name;
            this.scale = 0.9f;
            this.damage = 8;
            this.defense = 2;
            this.life = 15;
            this.knockBackResist = 1.1f;
            this.color = new Color(0, 220, 40, 100);
            this.value = 3f;
        } else if (name.equals("Pinky")) {
            this.setDefaults(1);
            this.name = name;
            this.scale = 0.6f;
            this.damage = 5;
            this.defense = 5;
            this.life = 150;
            this.knockBackResist = 1.4f;
            this.color = new Color(250, 30, 90, 90);
            this.value = 10000f;
        } else if (name.equals("Baby Slime")) {
            this.setDefaults(1);
            this.name = name;
            this.scale = 0.9f;
            this.damage = 13;
            this.defense = 4;
            this.life = 30;
            this.knockBackResist = 0.95f;
            this.alpha = 120;
            this.color = new Color(0, 0, 0, 50);
            this.value = 10f;
        } else if (name.equals("Black Slime")) {
            this.setDefaults(1);
            this.name = name;
            this.damage = 15;
            this.defense = 4;
            this.life = 0x2d;
            this.color = new Color(0, 0, 0, 50);
            this.value = 20f;
        } else if (name.equals("Purple Slime")) {
            this.setDefaults(1);
            this.name = name;
            this.scale = 1.2f;
            this.damage = 12;
            this.defense = 6;
            this.life = 40;
            this.knockBackResist = 0.9f;
            this.color = new Color(200, 0, 0xff, 150);
            this.value = 10f;
        } else if (name.equals("Red Slime")) {
            this.setDefaults(1);
            this.name = name;
            this.damage = 12;
            this.defense = 4;
            this.life = 0x23;
            this.color = new Color(0xff, 30, 0, 100);
            this.value = 8f;
        } else if (name.equals("Yellow Slime")) {
            this.setDefaults(1);
            this.name = name;
            this.scale = 1.2f;
            this.damage = 15;
            this.defense = 7;
            this.life = 0x2d;
            this.color = new Color(0xff, 0xff, 0, 100);
            this.value = 10f;
        } else if (!name.equals("")) {
            for (int i = 1; i < 0x2c; i++) {
                this.setDefaults(i);
                if (this.name.equals(name)) {
                    break;
                }
                if (i == 0x2b) {
                    this.setDefaults(0);
                    this.active = false;
                }
            }
        } else {
            this.active = false;
        }
        this.lifeMax = this.life;
    }

    public void setDefaults(int itemType) {
        this.lavaWet = false;
        this.wetCount = 0;
        this.wet = false;
        this.townNPC = false;
        this.homeless = false;
        this.homeTileX = -1;
        this.homeTileY = -1;
        this.friendly = false;
        this.behindTiles = false;
        this.boss = false;
        this.noTileCollide = false;
        this.rotation = 0f;
        this.active = true;
        this.alpha = 0;
        this.color = Color.red;
        this.collideX = false;
        this.collideY = false;
        this.direction = 0;
        this.oldDirection = this.direction;
        this.frameCounter = 0.0;
        this.netUpdate = false;
        this.knockBackResist = 1f;
        this.name = "";
        this.noGravity = false;
        this.scale = 1f;
        this.soundHit = 0;
        this.soundKilled = 0;
        this.spriteDirection = -1;
        this.target = 8;
        this.oldTarget = this.target;
        this.targetRect = new Rectangle2D.Double();
        this.timeLeft = activeTime;
        this.type = itemType;
        this.value = 0f;
        for (int i = 0; i < MAX_AI; i++) {
            this.ai[i] = 0f;
        }
        if (this.type == 1) {
            this.name = "Blue Slime";
            this.width = 0x18;
            this.height = 0x12;
            this.aiStyle = 1;
            this.damage = 7;
            this.defense = 2;
            this.lifeMax = 0x19;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.alpha = 0xaf;
            this.color = new Color(0, 80, 0xff, 100);
            this.value = 25f;
        }
        if (this.type == 2) {
            this.name = "Demon Eye";
            this.width = 30;
            this.height = 0x20;
            this.aiStyle = 2;
            this.damage = 0x12;
            this.defense = 2;
            this.lifeMax = 60;
            this.soundHit = 1;
            this.knockBackResist = 0.8f;
            this.soundKilled = 1;
            this.value = 75f;
        }
        if (this.type == 3) {
            this.name = "Zombie";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 3;
            this.damage = 14;
            this.defense = 6;
            this.lifeMax = 0x2d;
            this.soundHit = 1;
            this.soundKilled = 2;
            this.knockBackResist = 0.5f;
            this.value = 60f;
        }
        if (this.type == 4) {
            this.name = "Eye of Cthulhu";
            this.width = 100;
            this.height = 110;
            this.aiStyle = 4;
            this.damage = 0x12;
            this.defense = 12;
            this.lifeMax = 0xbb8;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0f;
            this.noGravity = true;
            this.noTileCollide = true;
            this.timeLeft = activeTime * 30;
            this.boss = true;
            this.value = 30000f;
        }
        if (this.type == 5) {
            this.name = "Servant of Cthulhu";
            this.width = 20;
            this.height = 20;
            this.aiStyle = 5;
            this.damage = 0x17;
            this.defense = 0;
            this.lifeMax = 8;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.noGravity = true;
            this.noTileCollide = true;
        }
        if (this.type == 6) {
            this.name = "Eater of Souls";
            this.width = 30;
            this.height = 30;
            this.aiStyle = 5;
            this.damage = 15;
            this.defense = 8;
            this.lifeMax = 40;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.noGravity = true;
            this.knockBackResist = 0.5f;
            this.value = 90f;
        }
        if (this.type == 7) {
            this.name = "Devourer Head";
            this.width = 0x16;
            this.height = 0x16;
            this.aiStyle = 6;
            this.damage = 0x1c;
            this.defense = 2;
            this.lifeMax = 40;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 300f;
        }
        if (this.type == 8) {
            this.name = "Devourer Body";
            this.width = 0x16;
            this.height = 0x16;
            this.aiStyle = 6;
            this.damage = 0x12;
            this.defense = 6;
            this.lifeMax = 60;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 300f;
        }
        if (this.type == 9) {
            this.name = "Devourer Tail";
            this.width = 0x16;
            this.height = 0x16;
            this.aiStyle = 6;
            this.damage = 13;
            this.defense = 10;
            this.lifeMax = 100;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 300f;
        }
        if (this.type == 10) {
            this.name = "Giant Worm Head";
            this.width = 14;
            this.height = 14;
            this.aiStyle = 6;
            this.damage = 8;
            this.defense = 0;
            this.lifeMax = 10;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 200f;
        }
        if (this.type == 11) {
            this.name = "Giant Worm Body";
            this.width = 14;
            this.height = 14;
            this.aiStyle = 6;
            this.damage = 4;
            this.defense = 4;
            this.lifeMax = 15;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 200f;
        }
        if (this.type == 12) {
            this.name = "Giant Worm Tail";
            this.width = 14;
            this.height = 14;
            this.aiStyle = 6;
            this.damage = 4;
            this.defense = 6;
            this.lifeMax = 20;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 200f;
        }
        if (this.type == 13) {
            this.name = "Eater of Worlds Head";
            this.width = 0x26;
            this.height = 0x26;
            this.aiStyle = 6;
            this.damage = 40;
            this.defense = 0;
            this.lifeMax = 120;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 300f;
        }
        if (this.type == 14) {
            this.name = "Eater of Worlds Body";
            this.width = 0x26;
            this.height = 0x26;
            this.aiStyle = 6;
            this.damage = 15;
            this.defense = 4;
            this.lifeMax = 200;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 300f;
        }
        if (this.type == 15) {
            this.name = "Eater of Worlds Tail";
            this.width = 0x26;
            this.height = 0x26;
            this.aiStyle = 6;
            this.damage = 10;
            this.defense = 8;
            this.lifeMax = 300;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 300f;
        }
        if (this.type == 0x10) {
            this.name = "Mother Slime";
            this.width = 0x24;
            this.height = 0x18;
            this.aiStyle = 1;
            this.damage = 20;
            this.defense = 7;
            this.lifeMax = 90;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.alpha = 120;
            this.color = new Color(0, 0, 0, 50);
            this.value = 75f;
            this.scale = 1.25f;
            this.knockBackResist = 0.6f;
        }
        if (this.type == 0x11) {
            this.townNPC = true;
            this.friendly = true;
            this.name = "Merchant";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 7;
            this.damage = 10;
            this.defense = 15;
            this.lifeMax = 250;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.5f;
        }
        if (this.type == 0x12) {
            this.townNPC = true;
            this.friendly = true;
            this.name = "Nurse";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 7;
            this.damage = 10;
            this.defense = 15;
            this.lifeMax = 250;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.5f;
        }
        if (this.type == 0x13) {
            this.townNPC = true;
            this.friendly = true;
            this.name = "Arms Dealer";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 7;
            this.damage = 10;
            this.defense = 15;
            this.lifeMax = 250;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.5f;
        }
        if (this.type == 20) {
            this.townNPC = true;
            this.friendly = true;
            this.name = "Dryad";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 7;
            this.damage = 10;
            this.defense = 15;
            this.lifeMax = 250;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.5f;
        }
        if (this.type == 0x15) {
            this.name = "Skeleton";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 3;
            this.damage = 20;
            this.defense = 8;
            this.lifeMax = 60;
            this.soundHit = 2;
            this.soundKilled = 2;
            this.knockBackResist = 0.5f;
            this.value = 250f;
        }
        if (this.type == 0x16) {
            this.townNPC = true;
            this.friendly = true;
            this.name = "Guide";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 7;
            this.damage = 10;
            this.defense = 100;
            this.lifeMax = 250;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.5f;
        }
        if (this.type == 0x17) {
            this.name = "Meteor Head";
            this.width = 0x16;
            this.height = 0x16;
            this.aiStyle = 5;
            this.damage = 0x19;
            this.defense = 10;
            this.lifeMax = 50;
            this.soundHit = 3;
            this.soundKilled = 3;
            this.noGravity = true;
            this.noTileCollide = true;
            this.value = 300f;
            this.knockBackResist = 0.8f;
        } else if (this.type == 0x18) {
            this.name = "Fire Imp";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 8;
            this.damage = 30;
            this.defense = 20;
            this.lifeMax = 80;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.5f;
            this.value = 800f;
        }
        if (this.type == 0x19) {
            this.name = "Burning Sphere";
            this.width = 0x10;
            this.height = 0x10;
            this.aiStyle = 9;
            this.damage = 0x19;
            this.defense = 0;
            this.lifeMax = 1;
            this.soundHit = 3;
            this.soundKilled = 3;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.alpha = 100;
        }
        if (this.type == 0x1a) {
            this.name = "Goblin Peon";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 3;
            this.damage = 12;
            this.defense = 4;
            this.lifeMax = 60;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.8f;
            this.value = 250f;
        }
        if (this.type == 0x1b) {
            this.name = "Goblin Thief";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 3;
            this.damage = 20;
            this.defense = 6;
            this.lifeMax = 80;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.7f;
            this.value = 600f;
        }
        if (this.type == 0x1c) {
            this.name = "Goblin Warrior";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 3;
            this.damage = 0x19;
            this.defense = 8;
            this.lifeMax = 110;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.5f;
            this.value = 500f;
        } else if (this.type == 0x1d) {
            this.name = "Goblin Sorcerer";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 8;
            this.damage = 20;
            this.defense = 2;
            this.lifeMax = 40;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.6f;
            this.value = 800f;
        } else if (this.type == 30) {
            this.name = "Chaos Ball";
            this.width = 0x10;
            this.height = 0x10;
            this.aiStyle = 9;
            this.damage = 20;
            this.defense = 0;
            this.lifeMax = 1;
            this.soundHit = 3;
            this.soundKilled = 3;
            this.noGravity = true;
            this.noTileCollide = true;
            this.alpha = 100;
            this.knockBackResist = 0f;
        } else if (this.type == 0x1f) {
            this.name = "Angry Bones";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 3;
            this.damage = 30;
            this.defense = 10;
            this.lifeMax = 100;
            this.soundHit = 2;
            this.soundKilled = 2;
            this.knockBackResist = 0.7f;
            this.value = 500f;
        } else if (this.type == 0x20) {
            this.name = "Dark Caster";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 8;
            this.damage = 20;
            this.defense = 4;
            this.lifeMax = 50;
            this.soundHit = 2;
            this.soundKilled = 2;
            this.knockBackResist = 0.6f;
            this.value = 800f;
        } else if (this.type == 0x21) {
            this.name = "Water Sphere";
            this.width = 0x10;
            this.height = 0x10;
            this.aiStyle = 9;
            this.damage = 20;
            this.defense = 0;
            this.lifeMax = 1;
            this.soundHit = 3;
            this.soundKilled = 3;
            this.noGravity = true;
            this.noTileCollide = true;
            this.alpha = 100;
            this.knockBackResist = 0f;
        }
        if (this.type == 0x22) {
            this.name = "Burning Skull";
            this.width = 0x1a;
            this.height = 0x1c;
            this.aiStyle = 10;
            this.damage = 0x19;
            this.defense = 30;
            this.lifeMax = 30;
            this.soundHit = 2;
            this.soundKilled = 2;
            this.noGravity = true;
            this.value = 300f;
            this.knockBackResist = 1.2f;
        }
        if (this.type == 0x23) {
            this.name = "Skeletron Head";
            this.width = 80;
            this.height = 0x66;
            this.aiStyle = 11;
            this.damage = 0x23;
            this.defense = 12;
            this.lifeMax = 0x1770;
            this.soundHit = 2;
            this.soundKilled = 2;
            this.noGravity = true;
            this.noTileCollide = true;
            this.value = 50000f;
            this.knockBackResist = 0f;
            this.boss = true;
        }
        if (this.type == 0x24) {
            this.name = "Skeletron Hand";
            this.width = 0x34;
            this.height = 0x34;
            this.aiStyle = 12;
            this.damage = 30;
            this.defense = 0x12;
            this.lifeMax = 0x4b0;
            this.soundHit = 2;
            this.soundKilled = 2;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
        }
        if (this.type == 0x25) {
            this.townNPC = true;
            this.friendly = true;
            this.name = "Old Man";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 7;
            this.damage = 10;
            this.defense = 100;
            this.lifeMax = 250;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.5f;
        }
        if (this.type == 0x26) {
            this.townNPC = true;
            this.friendly = true;
            this.name = "Demolitionist";
            this.width = 0x12;
            this.height = 40;
            this.aiStyle = 7;
            this.damage = 10;
            this.defense = 15;
            this.lifeMax = 250;
            this.soundHit = 1;
            this.soundKilled = 1;
            this.knockBackResist = 0.5f;
        }
        if (this.type == 0x27) {
            this.name = "Bone Serpent Head";
            this.width = 0x16;
            this.height = 0x16;
            this.aiStyle = 6;
            this.damage = 40;
            this.defense = 10;
            this.lifeMax = 120;
            this.soundHit = 2;
            this.soundKilled = 2;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 1000f;
        }
        if (this.type == 40) {
            this.name = "Bone Serpent Body";
            this.width = 0x16;
            this.height = 0x16;
            this.aiStyle = 6;
            this.damage = 30;
            this.defense = 12;
            this.lifeMax = 150;
            this.soundHit = 2;
            this.soundKilled = 2;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 1000f;
        }
        if (this.type == 0x29) {
            this.name = "Bone Serpent Tail";
            this.width = 0x16;
            this.height = 0x16;
            this.aiStyle = 6;
            this.damage = 20;
            this.defense = 0x12;
            this.lifeMax = 200;
            this.soundHit = 2;
            this.soundKilled = 2;
            this.noGravity = true;
            this.noTileCollide = true;
            this.knockBackResist = 0f;
            this.behindTiles = true;
            this.value = 1000f;
        }
        if (this.type == 0x2a) {
            this.name = "Hornet";
            this.width = 0x22;
            this.height = 0x20;
            this.aiStyle = 2;
            this.damage = 40;
            this.defense = 14;
            this.lifeMax = 100;
            this.soundHit = 1;
            this.knockBackResist = 0.8f;
            this.soundKilled = 1;
            this.value = 750f;
        }
        if (this.type == 0x2b) {
            this.noGravity = true;
            this.name = "Man Eater";
            this.width = 30;
            this.height = 30;
            this.aiStyle = 13;
            this.damage = 60;
            this.defense = 0x12;
            this.lifeMax = 200;
            this.soundHit = 1;
            this.knockBackResist = 0.7f;
            this.soundKilled = 1;
            this.value = 750f;
        }
        getFrame().setRect(0, 0, /*Main.npcTexture[this.type].Width*/ 50, /*Main.npcTexture[this.type].Height / Main.npcFrameCount[this.type]*/ 50);
        this.width = (int) (this.width * this.scale);
        this.height = (int) (this.height * this.scale);
        this.life = this.lifeMax;
        if (DUMB_AI) {
            this.aiStyle = 0;
        }
    }

    /**
     * @return the oldPosition
     */
    public Point.Float getOldPosition() {
        return oldPosition;
    }

    /**
     * @param oldPosition the oldPosition to set
     */
    public void setOldPosition(Point.Float oldPosition) {
        this.oldPosition = oldPosition;
    }

    /**
     * @return the oldVelocity
     */
    public Rectangle2D.Float getOldVelocity() {
        return oldVelocity;
    }

    /**
     * @param oldVelocity the oldVelocity to set
     */
    public void setOldVelocity(Rectangle2D.Float oldVelocity) {
        this.oldVelocity = oldVelocity;
    }

    /**
     * @return the position
     */
    public Point.Float getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Point.Float position) {
        this.position = position;
    }
}
