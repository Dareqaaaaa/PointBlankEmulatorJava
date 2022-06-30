package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_CHANGE_OPTIONINFO_REQ extends game.network.game.GamePacketREQ
{
	Room r;
	String leader_name;
	boolean autorized;
	@Override
	public void readImpl()
	{
		r = client.getRoom();
		if (r != null && r.rstate == RoomState.READY && r.LIDER == client.pId)
		{
			leader_name = ReadS(33);
			r.killMask = ReadD();
			r.limit = ReadC();
			r.seeConf = ReadC();
			r.balanceamento = ReadH();
			autorized = true;
		}
		else
		{
			autorized = false;
		}
	}
	@Override
	public void runImpl()
	{
		if (autorized)
		{
			ROOM_CHANGE_OPTIONINFO_ACK sent1 = new ROOM_CHANGE_OPTIONINFO_ACK(r, leader_name); sent1.packingBuffer();
			ROOM_CHANGE_ROOMINFO_ACK sent2 = new ROOM_CHANGE_ROOMINFO_ACK(r, leader_name, r.isSpecialMode()); sent2.packingBuffer();
			for (RoomSlot slot : r.SLOT)
			{
				Player m = AccountSyncer.gI().get(slot.player_id);
				if (m != null)
				{
					m.gameClient.sendPacketBuffer(sent1.buffer);
					m.gameClient.sendPacketBuffer(sent2.buffer);
				}
			}
			sent1.buffer = null; sent1 = null;
			sent2.buffer = null; sent2 = null;
		}
	}
}