/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genie.world;

/**
 *
 * @author Jay
 */
public class Controller {
    private boolean up, down, left, right, jump, useItem;
    private int direction; // Looks like always -1 or 1, consider a boolean

    /**
     * @return the up
     */
    public boolean isUp() {
        return up;
    }

    /**
     * @param up the up to set
     */
    public void setUp(boolean up) {
        this.up = up;
    }

    /**
     * @return the down
     */
    public boolean isDown() {
        return down;
    }

    /**
     * @param down the down to set
     */
    public void setDown(boolean down) {
        this.down = down;
    }

    /**
     * @return the left
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * @return the right
     */
    public boolean isRight() {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * @return the jump
     */
    public boolean isJump() {
        return jump;
    }

    /**
     * @param jump the jump to set
     */
    public void setJump(boolean jump) {
        this.jump = jump;
    }

    /**
     * @return the useItem
     */
    public boolean isUseItem() {
        return useItem;
    }

    /**
     * @param useItem the useItem to set
     */
    public void setUseItem(boolean useItem) {
        this.useItem = useItem;
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

    //private boolean up, down, left, right, jump, useItem;
    //private int direction;
    public void reset() {
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.jump = false;
        this.useItem = false;
        this.direction = -1;
    }
}
