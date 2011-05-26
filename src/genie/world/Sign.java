/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genie.world;

/**
 *
 * @author Jay
 */
public class Sign {
    public static final int MAX_SIGNS = 0x3e8;
    private String text;
    private int x;
    private int y;

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
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
