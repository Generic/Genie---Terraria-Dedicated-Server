/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genie.world;

import genie.world.Item;

/**
 *
 * @author Jay
 */
public class Chest {
    public static final int MAX_ITEMS = 20;
    public static final int MAX_CHESTS = 0x3e8;
    private Item[] items = new Item[MAX_ITEMS];
    private int x;
    private int y;

    public void setItem(int slot, Item item) {
        items[slot] = item;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
}
