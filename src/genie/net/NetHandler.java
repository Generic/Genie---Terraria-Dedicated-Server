/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genie.net;

import com.sun.corba.se.spi.activation.Server;
import java.io.IOException;
import java.util.Collection;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelLocal;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import genie.PlayerLocal;

/**
 *
 * @author Jay
 */
public class NetHandler extends IdleStateAwareChannelUpstreamHandler {
    public static final int VERSION = 3;
    public static final String PASSWORD = "test";
    private static PlayerLocal<Player> players = new PlayerLocal();

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        ChannelBuffer message = (ChannelBuffer) e.getMessage();
        do {
            handle(message.readInt(), (Player)players.get(e.getChannel()), message, message.readByte());
        } while(message.readerIndex() < message.capacity());
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

    public static void handle(int length, Player player, ChannelBuffer buffer, int op) {
        switch(op) {
            case Opcodes.RECV_CONNECT: NetResponse.connect(player, buffer, length); break;
            case Opcodes.RECV_PLAYER_DATA: NetResponse.recvData(player, buffer, length); break;
            case Opcodes.RECV_INVENTORY_DATA: NetResponse.recvInventory(player, buffer, length); break;
            case Opcodes.RECV_WORLD_REQUEST: NetResponse.recvWorldRequest(player, buffer, length); break;
            case Opcodes.RECV_SPAWN: NetResponse.recvSpawn(player, buffer, length); break;
            case Opcodes.RECV_PLAYER_HEALTH: NetResponse.recvPlayerHealth(player, buffer, length); break;
            case Opcodes.RECV_PASS_RESPONSE: NetResponse.recvPassword(player, buffer, length); break;
            case Opcodes.RECV_PLAYER_MANA: NetResponse.recvPlayerMana(player, buffer, length); break;
            default: {
                System.out.println("Unknown OP: " + op + " \nData: " + ChannelBuffers.hexDump(buffer, buffer.readerIndex(), length));
            } break;
        }
    }

    public static Collection<Player> getPlayers() {
        return players.getValues();
    }
}
