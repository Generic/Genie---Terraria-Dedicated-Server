/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genie.world;

/**
 *
 * @author Jay
 */
public class Liquid {

    public static int CYCLES = 10;
    public static final int MAX_LIQUID = 0x1388;
    public static int LIQUID_COUNT;
    public static int panicCounter = 0;
    public static boolean panicMode = false;
    public static int panicY = 0;
    public static boolean quickFall = false;
    public static boolean quickSettle = false;
    public static int resLiquid = 0x1388;
    public static int skipCount = 0;
    public static boolean stuck = false;
    public static int stuckAmount = 0;
    public static int stuckCount = 0;
    
    private static int wetCounter;

    public int delay;
    public int kill;
    public int x;
    public int y;
}
