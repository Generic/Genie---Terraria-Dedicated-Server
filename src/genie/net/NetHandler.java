/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genie.net;

import java.io.IOException;
import java.util.Collection;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import genie.PlayerLocal;
import org.jboss.netty.channel.WriteCompletionEvent;

/**
 *
 * @author Jay
 */
public class NetHandler extends IdleStateAwareChannelUpstreamHandler {

    public static final int VERSION = 3;
    public static final String PASSWORD = "";
    private static PlayerLocal<Player> players = new PlayerLocal();

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

        ChannelBuffer message = (ChannelBuffer) e.getMessage();
        Player player = players.get(e.getChannel());
        if (player != null && player.getId() > 0) {
            System.out.println("Received " + ChannelBuffers.hexDump(message) + " from id: " + player.getId());
        }
        do {
            handle(message.readInt(), (Player) players.get(e.getChannel()), message, message.readByte());
        } while (message.readerIndex() < message.capacity() && message.readableBytes() > 4);
    }

    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        players.set(e.getChannel(), new Player(e.getChannel()));
        System.out.println(e.getChannel().getRemoteAddress() + " has connected");
    }

    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        players.remove(e.getChannel());
        System.out.println(e.getChannel().getRemoteAddress() + " has disconnected");
    }

    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        if (!(e.getCause() instanceof IOException)) {
            e.getCause().printStackTrace();
        }
    }

    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
        if (e.getState() == IdleState.READER_IDLE) {
            //ctx.getChannel().close();
        }
    }

    public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) throws Exception {
    }

    public static void handle(int length, Player player, ChannelBuffer buffer, int op) {
        System.out.println("OP: " + op);
        switch (op) {
            case Opcodes.RECV_CONNECT:
                NetResponse.connect(player, buffer, length);
                break;
            case Opcodes.RECV_PLAYER_DATA:
                NetResponse.recvData(player, buffer, length);
                break;
            case Opcodes.RECV_INVENTORY_DATA:
                NetResponse.recvInventory(player, buffer, length);
                break;
            case Opcodes.RECV_WORLD_REQUEST:
                NetResponse.recvWorldRequest(player, buffer, length);
                break;
            case Opcodes.RECV_TILE_REQUEST:
                NetResponse.recvTiles(player, buffer, length);
                break;
            case Opcodes.RECV_SPAWN:
                NetResponse.recvSpawn(player, buffer, length);
                break;
            case Opcodes.RECV_PLAYER_UPDATE:
                NetResponse.recvPlayerUpdate(player, buffer, length);
                break;
            case Opcodes.RECV_PLAYER_HEALTH:
                NetResponse.recvPlayerHealth(player, buffer, length);
                break;
            case Opcodes.RECV_MANIPULATE_TILE:
                NetResponse.recvManipulateTile(player, buffer, length);
                break;
            case Opcodes.RECV_PROJECTILE:
                NetResponse.recvProjectile(player, buffer, length);
                break;
            case Opcodes.RECV_ZONE_INFO:
                NetResponse.recvZoneInfo(player, buffer, length);
                break;
            case Opcodes.RECV_PASS_RESPONSE:
                NetResponse.recvPassword(player, buffer, length);
                break;
            case Opcodes.RECV_NPC_TALK:
                NetResponse.recvNpcTalk(player, buffer, length);
                break;
            case Opcodes.RECV_PLAYER_MANA:
                NetResponse.recvPlayerMana(player, buffer, length);
                break;
            default: {
                try {
                    System.out.println("Lengthof: " + length);
                    System.out.println("Unknown OP: " + op);
                    System.out.println("Data: " + ChannelBuffers.hexDump(buffer, buffer.readerIndex(), length - 1));
                    buffer.skipBytes(length - 1);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("CORRECT LAST PACKET");
                }
            }
            break;
        }
    }

    public static Collection<Player> getPlayers() {
        return players.getValues();
    }
}
