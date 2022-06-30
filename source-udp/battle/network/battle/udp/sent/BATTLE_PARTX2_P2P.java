package battle.network.battle.udp.sent;

import core.enums.*;
import core.model.*;
import core.udp.events.*;
import io.netty.channel.*;
import io.netty.channel.socket.*;
import battle.network.battle.udp.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_PARTX2_P2P
{
	ChannelHandlerContext ctx;
	UDP_Model model;
	public BATTLE_PARTX2_P2P(ChannelHandlerContext ctx, UDP_Model model)
	{
		this.ctx = ctx;
		this.model = model;
	}
	public void write() throws Throwable
	{
		BattleUdpBuffer send = new BattleUdpBuffer();
		RoomBattle r = model.getRoom(false);
        if (r != null)
        {
        	send.buffer.writeByte(model.opcode);
        	send.buffer.writeByte(model.slot);
        	send.buffer.writeFloat(model.time);
        	send.buffer.writeByte(model.session);
        	send.buffer.writeShort(model.length);
        	send.buffer.writeInt(model.event);
        	send.buffer.writeBytes(model.bufferRead);
    		for (PlayerBattle c : r.r.playersBattle.values())
    		{
    			if (c.sender != null && c.state == SlotState.BATTLE && r.isUDP(c.slot, model.slot == r.leader))
    			{
    				ctx.writeAndFlush(new DatagramPacket(send.buffer.copy(), c.sender));
    			}
    		}
        }
        send.close();
        super.finalize();
	}
}