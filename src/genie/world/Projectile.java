/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genie.world;

import java.awt.geom.Point2D;

/**
 *
 * @author Jay
 */
public class Projectile {
    public static final int MAX_AI = 2;
    private boolean active;
    private float[] ai = new float[MAX_AI];
    private int aiStyle;
    private int alpha;
    private int damage;
    private int direction;
    private boolean friendly;
    private int height;
    private boolean hostile;
    private int identity;
    private boolean ignoreWater;
    private float knockBack;
    private boolean lavaWet;
    private float light;
    private int maxUpdates;
    private String name = "";
    private boolean netUpdate;
    private int numUpdates;
    private int owner = 8;
    private int penetrate = 1;
    private int[] playerImmune = new int[8];
    private Point2D.Float position;
    private int restrikeDelay;
    private float rotation;
    private float scale = 1f;
    private int soundDelay;
    private boolean tileCollide;
    private int timeLeft;
    private int type;
    private Point2D.Float velocity;
    private boolean wet;
    private byte wetCount;
    private int whoAmI;
    private int width;

    public void setDefaults(int type) {
        for (int i = 0; i < MAX_AI; i++) {
            this.getAi()[i] = 0f;
        }
        for (int j = 0; j < 8; j++) {
            this.getPlayerImmune()[j] = 0;
        }
        this.setLavaWet(false);
        this.setWetCount((byte) 0);
        this.setWet(false);
        this.setIgnoreWater(false);
        this.setHostile(false);
        this.setNetUpdate(false);
        this.setNumUpdates(0);
        this.setMaxUpdates(0);
        this.setIdentity(0);
        this.setRestrikeDelay(0);
        this.setLight(0f);
        this.setPenetrate(1);
        this.setTileCollide(true);
        this.setPosition(new Point2D.Float());
        this.setVelocity(new Point2D.Float());
        this.setAiStyle(0);
        this.setAlpha(0);
        this.setType(type);
        this.setActive(true);
        this.setRotation(0f);
        this.setScale(1f);
        this.setOwner(8);
        this.setTimeLeft(0xe10);
        this.setName("");
        this.setFriendly(false);
        this.setDamage(0);
        this.setKnockBack(0f);
        if (this.getType() == 1) {
            this.setName("Wooden Arrow");
            this.setWidth(10);
            this.setHeight(10);
            this.setAiStyle(1);
            this.setFriendly(true);
        } else if (this.getType() == 2) {
            this.setName("Fire Arrow");
            this.setWidth(10);
            this.setHeight(10);
            this.setAiStyle(1);
            this.setFriendly(true);
            this.setLight(1f);
        } else if (this.getType() == 3) {
            this.setName("Shuriken");
            this.setWidth(0x16);
            this.setHeight(0x16);
            this.setAiStyle(2);
            this.setFriendly(true);
            this.setPenetrate(4);
        } else if (this.getType() == 4) {
            this.setName("Unholy Arrow");
            this.setWidth(10);
            this.setHeight(10);
            this.setAiStyle(1);
            this.setFriendly(true);
            this.setLight(0.2f);
            this.setPenetrate(3);
        } else if (this.getType() == 5) {
            this.setName("Jester's Arrow");
            this.setWidth(10);
            this.setHeight(10);
            this.setAiStyle(1);
            this.setFriendly(true);
            this.setLight(0.4f);
            this.setPenetrate(-1);
            this.setTimeLeft(40);
            this.setAlpha(100);
            this.setIgnoreWater(true);
        } else if (this.getType() == 6) {
            this.setName("Enchanted Boomerang");
            this.setWidth(0x16);
            this.setHeight(0x16);
            this.setAiStyle(3);
            this.setFriendly(true);
            this.setPenetrate(-1);
        } else if ((this.getType() == 7) || (this.getType() == 8)) {
            this.setName("Vilethorn");
            this.setWidth(0x1c);
            this.setHeight(0x1c);
            this.setAiStyle(4);
            this.setFriendly(true);
            this.setPenetrate(-1);
            this.setTileCollide(false);
            this.setAlpha(0xff);
            this.setIgnoreWater(true);
        } else if (this.getType() == 9) {
            this.setName("Starfury");
            this.setWidth(0x18);
            this.setHeight(0x18);
            this.setAiStyle(5);
            this.setFriendly(true);
            this.setPenetrate(2);
            this.setAlpha(50);
            this.setScale(0.8f);
            this.setLight(1f);
        } else if (this.getType() == 10) {
            this.setName("Purification Powder");
            this.setWidth(0x40);
            this.setHeight(0x40);
            this.setAiStyle(6);
            this.setFriendly(true);
            this.setTileCollide(false);
            this.setPenetrate(-1);
            this.setAlpha(0xff);
            this.setIgnoreWater(true);
        } else if (this.getType() == 11) {
            this.setName("Vile Powder");
            this.setWidth(0x30);
            this.setHeight(0x30);
            this.setAiStyle(6);
            this.setFriendly(true);
            this.setTileCollide(false);
            this.setPenetrate(-1);
            this.setAlpha(0xff);
            this.setIgnoreWater(true);
        } else if (this.getType() == 12) {
            this.setName("Fallen Star");
            this.setWidth(0x10);
            this.setHeight(0x10);
            this.setAiStyle(5);
            this.setFriendly(true);
            this.setPenetrate(-1);
            this.setAlpha(50);
            this.setLight(1f);
        } else if (this.getType() == 13) {
            this.setName("Hook");
            this.setWidth(0x12);
            this.setHeight(0x12);
            this.setAiStyle(7);
            this.setFriendly(true);
            this.setPenetrate(-1);
            this.setTileCollide(false);
        } else if (this.getType() == 14) {
            this.setName("Musket Ball");
            this.setWidth(4);
            this.setHeight(4);
            this.setAiStyle(1);
            this.setFriendly(true);
            this.setPenetrate(1);
            this.setLight(0.5f);
            this.setAlpha(0xff);
            this.setMaxUpdates(1);
            this.setScale(1.2f);
            this.setTimeLeft(600);
        } else if (this.getType() == 15) {
            this.setName("Ball of Fire");
            this.setWidth(0x10);
            this.setHeight(0x10);
            this.setAiStyle(8);
            this.setFriendly(true);
            this.setLight(0.8f);
            this.setAlpha(100);
        } else if (this.getType() == 0x10) {
            this.setName("Magic Missile");
            this.setWidth(10);
            this.setHeight(10);
            this.setAiStyle(9);
            this.setFriendly(true);
            this.setLight(0.8f);
            this.setAlpha(100);
        } else if (this.getType() == 0x11) {
            this.setName("Dirt Ball");
            this.setWidth(10);
            this.setHeight(10);
            this.setAiStyle(10);
            this.setFriendly(true);
        } else if (this.getType() == 0x12) {
            this.setName("Orb of Light");
            this.setWidth(0x20);
            this.setHeight(0x20);
            this.setAiStyle(11);
            this.setFriendly(true);
            this.setLight(1f);
            this.setAlpha(150);
            this.setTileCollide(false);
            this.setPenetrate(-1);
            this.setTimeLeft(this.getTimeLeft() * 5);
            this.setIgnoreWater(true);
        } else if (this.getType() == 0x13) {
            this.setName("Flamarang");
            this.setWidth(0x16);
            this.setHeight(0x16);
            this.setAiStyle(3);
            this.setFriendly(true);
            this.setPenetrate(-1);
            this.setLight(1f);
        } else if (this.getType() == 20) {
            this.setName("Green Laser");
            this.setWidth(4);
            this.setHeight(4);
            this.setAiStyle(1);
            this.setFriendly(true);
            this.setPenetrate(-1);
            this.setLight(0.75f);
            this.setAlpha(0xff);
            this.setMaxUpdates(2);
            this.setScale(1.4f);
            this.setTimeLeft(600);
        } else if (this.getType() == 0x15) {
            this.setName("Bone");
            this.setWidth(0x10);
            this.setHeight(0x10);
            this.setAiStyle(2);
            this.setScale(1.2f);
            this.setFriendly(true);
        } else if (this.getType() == 0x16) {
            this.setName("Water Stream");
            this.setWidth(12);
            this.setHeight(12);
            this.setAiStyle(12);
            this.setFriendly(true);
            this.setAlpha(0xff);
            this.setPenetrate(-1);
            this.setMaxUpdates(1);
            this.setIgnoreWater(true);
        } else if (this.getType() == 0x17) {
            this.setName("Harpoon");
            this.setWidth(4);
            this.setHeight(4);
            this.setAiStyle(13);
            this.setFriendly(true);
            this.setPenetrate(-1);
            this.setAlpha(0xff);
        } else if (this.getType() == 0x18) {
            this.setName("Spiky Ball");
            this.setWidth(14);
            this.setHeight(14);
            this.setAiStyle(14);
            this.setFriendly(true);
            this.setPenetrate(3);
        } else if (this.getType() == 0x19) {
            this.setName("Ball 'O Hurt");
            this.setWidth(0x16);
            this.setHeight(0x16);
            this.setAiStyle(15);
            this.setFriendly(true);
            this.setPenetrate(-1);
        } else if (this.getType() == 0x1a) {
            this.setName("Blue Moon");
            this.setWidth(0x16);
            this.setHeight(0x16);
            this.setAiStyle(15);
            this.setFriendly(true);
            this.setPenetrate(-1);
        } else if (this.getType() == 0x1b) {
            this.setName("Water Bolt");
            this.setWidth(0x10);
            this.setHeight(0x10);
            this.setAiStyle(8);
            this.setFriendly(true);
            this.setLight(0.8f);
            this.setAlpha(200);
            this.setTimeLeft(this.getTimeLeft() / 2);
            this.setPenetrate(10);
        } else if (this.getType() == 0x1c) {
            this.setName("Bomb");
            this.setWidth(0x16);
            this.setHeight(0x16);
            this.setAiStyle(0x10);
            this.setFriendly(true);
            this.setPenetrate(-1);
        } else if (this.getType() == 0x1d) {
            this.setName("Dynamite");
            this.setWidth(10);
            this.setHeight(10);
            this.setAiStyle(0x10);
            this.setFriendly(true);
            this.setPenetrate(-1);
        } else if (this.getType() == 30) {
            this.setName("Grenade");
            this.setWidth(14);
            this.setHeight(14);
            this.setAiStyle(0x10);
            this.setFriendly(true);
            this.setPenetrate(-1);
        } else if (this.getType() == 0x1f) {
            this.setName("Sand Ball");
            this.setKnockBack(6f);
            this.setWidth(10);
            this.setHeight(10);
            this.setAiStyle(10);
            this.setFriendly(true);
            this.setHostile(true);
            this.setPenetrate(-1);
        } else if (this.getType() == 0x20) {
            this.setName("Ivy Whip");
            this.setWidth(0x12);
            this.setHeight(0x12);
            this.setAiStyle(7);
            this.setFriendly(true);
            this.setPenetrate(-1);
            this.setTileCollide(false);
        } else if (this.getType() == 0x21) {
            this.setName("Thorn Chakrum");
            this.setWidth(0x1c);
            this.setHeight(0x1c);
            this.setAiStyle(3);
            this.setFriendly(true);
            this.setScale(0.9f);
            this.setPenetrate(-1);
        } else if (this.getType() == 0x22) {
            this.setName("Flamelash");
            this.setWidth(14);
            this.setHeight(14);
            this.setAiStyle(9);
            this.setFriendly(true);
            this.setLight(0.8f);
            this.setAlpha(100);
            this.setPenetrate(2);
        } else if (this.getType() == 0x23) {
            this.setName("Sunfury");
            this.setWidth(0x16);
            this.setHeight(0x16);
            this.setAiStyle(15);
            this.setFriendly(true);
            this.setPenetrate(-1);
        } else if (this.getType() == 0x24) {
            this.setName("Meteor Shot");
            this.setWidth(4);
            this.setHeight(4);
            this.setAiStyle(1);
            this.setFriendly(true);
            this.setPenetrate(2);
            this.setLight(0.6f);
            this.setAlpha(0xff);
            this.setMaxUpdates(1);
            this.setScale(1.4f);
            this.setTimeLeft(600);
        } else {
            this.setActive(false);
        }
        this.setWidth((int) (this.getWidth() * this.getScale()));
        this.setHeight((int) (this.getHeight() * this.getScale()));
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

    public void setAi(int index, float ai) {
        this.ai[index] = ai;
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
     * @return the hostile
     */
    public boolean isHostile() {
        return hostile;
    }

    /**
     * @param hostile the hostile to set
     */
    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }

    /**
     * @return the identity
     */
    public int getIdentity() {
        return identity;
    }

    /**
     * @param identity the identity to set
     */
    public void setIdentity(int identity) {
        this.identity = identity;
    }

    /**
     * @return the ignoreWater
     */
    public boolean isIgnoreWater() {
        return ignoreWater;
    }

    /**
     * @param ignoreWater the ignoreWater to set
     */
    public void setIgnoreWater(boolean ignoreWater) {
        this.ignoreWater = ignoreWater;
    }

    /**
     * @return the knockBack
     */
    public float getKnockBack() {
        return knockBack;
    }

    /**
     * @param knockBack the knockBack to set
     */
    public void setKnockBack(float knockBack) {
        this.knockBack = knockBack;
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
     * @return the light
     */
    public float getLight() {
        return light;
    }

    /**
     * @param light the light to set
     */
    public void setLight(float light) {
        this.light = light;
    }

    /**
     * @return the maxUpdates
     */
    public int getMaxUpdates() {
        return maxUpdates;
    }

    /**
     * @param maxUpdates the maxUpdates to set
     */
    public void setMaxUpdates(int maxUpdates) {
        this.maxUpdates = maxUpdates;
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
     * @return the numUpdates
     */
    public int getNumUpdates() {
        return numUpdates;
    }

    /**
     * @param numUpdates the numUpdates to set
     */
    public void setNumUpdates(int numUpdates) {
        this.numUpdates = numUpdates;
    }

    /**
     * @return the owner
     */
    public int getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(int owner) {
        this.owner = owner;
    }

    /**
     * @return the penetrate
     */
    public int getPenetrate() {
        return penetrate;
    }

    /**
     * @param penetrate the penetrate to set
     */
    public void setPenetrate(int penetrate) {
        this.penetrate = penetrate;
    }

    /**
     * @return the playerImmune
     */
    public int[] getPlayerImmune() {
        return playerImmune;
    }

    /**
     * @param playerImmune the playerImmune to set
     */
    public void setPlayerImmune(int[] playerImmune) {
        this.playerImmune = playerImmune;
    }

    /**
     * @return the position
     */
    public Point2D.Float getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Point2D.Float position) {
        this.position = position;
    }

    /**
     * @return the restrikeDelay
     */
    public int getRestrikeDelay() {
        return restrikeDelay;
    }

    /**
     * @param restrikeDelay the restrikeDelay to set
     */
    public void setRestrikeDelay(int restrikeDelay) {
        this.restrikeDelay = restrikeDelay;
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
     * @return the tileCollide
     */
    public boolean isTileCollide() {
        return tileCollide;
    }

    /**
     * @param tileCollide the tileCollide to set
     */
    public void setTileCollide(boolean tileCollide) {
        this.tileCollide = tileCollide;
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
     * @return the velocity
     */
    public Point2D.Float getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the velocity to set
     */
    public void setVelocity(Point2D.Float velocity) {
        this.velocity = velocity;
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
}
