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

public class ROOM_CHANGE_PASSWD_REQ extends game.network.game.GamePacketREQ
{
	String passwd;
	@Override
	public void readImpl()
	{
		passwd = ReadS(4);
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Room r = client.getRoom();
		if (p != null && r != null)
		{
			if (p.id == r.LIDER && (r.rstate == RoomState.READY || r.rstate == RoomState.COUNTDOWN) && (r.passwd.isEmpty() || !r.passwd.equals(passwd)))
			{
				r.passwd = passwd;
				ROOM_CHANGE_PASSWD_ACK sent = new ROOM_CHANGE_PASSWD_ACK(passwd); sent.packingBuffer();
				for (RoomSlot slot : r.SLOT)
				{
					Player m = slot.player_id > 0 ? AccountSyncer.gI().get(slot.player_id) : null;
					if (m != null)
						m.gameClient.sendPacketBuffer(sent.buffer);
				}
				sent.buffer = null; sent = null;
			}
		}
	}
}