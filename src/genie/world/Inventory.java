/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genie.world;

/**
 *
 * @author Jay
 */
public class Inventory {
    public static final int INVENTORY_SLOTS = 0x34;

    private Item[] items;
    private int selectedItem;
    public Inventory() {
        items = new Item[INVENTORY_SLOTS];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item();
        }
    }

    /**
     * If is out of bounds slot is set to 0.
     * @param slot A slot from 0-51
     * @return The item located at the specified slot
     */
    public Item getItem(int slot) {
        if (slot < 0 || slot > 51) {
            slot = 0;
        }
        return items[slot];
    }

    /**
     * If item is < 0 slot is set to 0 if item is > 0 slot is set to 51
     * @param slot A slot from 0-51
     * @param item The item to be put in the specified slot
     */
    public void setItem(int slot, Item item) {
        // TODO: if slot > 0x2c handle armor
        if (slot < 0) {
            slot = 0;
        } else if (slot > 51) {
            slot = 51;
        }
        items[slot] = item;
    }

    /**
     *
     * @param item The item to add in the next free slot.
     * @return Whether or not the item was added successfully.
     */
    public boolean addItem(Item item) {
        for (int i = 0; i < 0x2c; i++) {
            if (!items[i].isActive()) {
                items[i] = item;
                return true;
            }
        }
        return false;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }
}
