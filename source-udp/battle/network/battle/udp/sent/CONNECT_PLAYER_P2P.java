package battle.network.battle.udp.sent;

import java.awt.*;

import core.enums.*;
import core.info.*;
import core.model.*;
import core.udp.events.*;
import io.netty.channel.*;
import io.netty.channel.socket.*;
import battle.network.battle.*;
import battle.network.battle.sent.*;
import battle.network.battle.udp.*;

/**
 * 
 * @author Henrique
 *
 */

public class CONNECT_PLAYER_P2P
{
	ChannelHandlerContext ctx;
	DatagramPacket msg;
	UDP_Model model;
	public CONNECT_PLAYER_P2P(ChannelHandlerContext ctx, DatagramPacket msg, UDP_Model model)
	{
		this.ctx = ctx;
		this.msg = msg;
		this.model = model;
	}
	public void write() throws Throwable
	{
		BattleUdpBuffer send = new BattleUdpBuffer();
		RoomBattle r = model.getRoom(true);
        if (r != null)
        {
            PlayerBattle p = r.getPlayer(model.slot);
            if (p != null && p.sender == null)
            {
            	send.buffer.writeByte(66);
            	send.buffer.writeByte(0);
            	send.buffer.writeFloat(15);
            	send.buffer.writeByte(0);
            	send.buffer.writeShort(13);
            	send.buffer.writeInt(0);
                ctx.writeAndFlush(new DatagramPacket(send.buffer, (p.sender = msg.sender())));
        		p.state = SlotState.BATTLE;
                BattleManager.gI().sendPacket(new REQUEST_BATTLE_PRESTART_ACK(r.r, p.slot));
            	Software.LogColor(" Connection established. [" + p.sender.toString() + "]", Color.YELLOW);
            }
        }
        send.close();
        super.finalize();
	}
}