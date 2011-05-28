/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genie.net;

/**
 *
 * @author Jay
 */
public class Opcodes {
    /**
     * Netmodes: 0 = SP, 1 = client, 2= server | Credit: Shoot
     */
    public static final int RECV_CONNECT = 0x01,
            SEND_DISCONNECT = 0x02,
            SEND_CONNECT_RESPONSE = 0x03,
            RECV_PLAYER_DATA = 0x04,
            SEND_PLAYER_DATA = 0x04,
            RECV_INVENTORY_DATA = 0x05,
            SEND_INVENTORY_DATA = 0x05,
            RECV_WORLD_REQUEST = 0x06,
            SEND_WORLD_DATA = 0x07,
            RECV_TILE_REQUEST = 0x08,
            SEND_TILE_LOADING = 0x09,
            SEND_TILE_SECTION = 0x0A,
            SEND_CONFIRM_TILE = 0x0B,
            RECV_SPAWN = 0x0C,
            SEND_SPAWN = 0x0C,
            RECV_PLAYER_UPDATE = 0x0D,
            SEND_PLAYER_UPDATE = 0x0D,
            SEND_PLAYER_UPDATE2 = 0x0E, // 14
            RECV_PLAYER_HEALTH = 0x10,
            SEND_PLAYER_HEALTH = 0x10,
            RECV_MANIPULATE_TILE = 0x11,
            SEND_TILE_SQUARE = 0x14,
            SEND_ITEM_INFO = 0x15,
            SEND_OWNER_INFO = 0x16,
            SEND_NPC_INFO = 0x17,
            SEND_MESSAGE = 0x19,
            RECV_PROJECTILE = 0x1B,
            SEND_PROJECTILE = 0x1B,
            SEND_PVP_MODE = 0x1E,
            RECV_ZONE_INFO = 0x24,
            SEND_PASS_REQUEST = 0x25,
            RECV_PASS_RESPONSE = 0x26,
            RECV_NPC_TALK = 0x28,
            RECV_PLAYER_MANA = 0x2A,
            SEND_PLAYER_MANA = 0x2A,
            SEND_PVP_TEAM = 0x2D;
}
