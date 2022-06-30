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

public class ROOM_CHANGE_SLOT_REQ extends game.network.game.GamePacketREQ
{
	int team;
	@Override
	public void readImpl()
	{
		team = ReadD();
	}
	@Override
	public void runImpl()
	{
		Room r = client.getRoom();
		Player p = client.getPlayer();
		if (r != null && p != null)
		{
			RoomSlot o = r.getSlotByPID(p.id);
			if (o != null && o.id % 2 != team && o.state == SlotState.NORMAL)
			{
				synchronized (o)
				{
					if (!p.changeSlot)
		            {
						p.changeSlot = true;
						RoomSlot n = r.changeSlot(p, o, SlotState.NORMAL, team), l = r.getSlotByPID(r.LIDER);
						if (n != null && l != null)
						{
							ROOM_CHANGE_SLOT_ACK sent = new ROOM_CHANGE_SLOT_ACK(l, o, n); sent.packingBuffer();
							for (RoomSlot slot : r.SLOT)
							{
								Player m = AccountSyncer.gI().get(slot.player_id);
								if (m != null)
									m.gameClient.sendPacketBuffer(sent.buffer);
							}
							sent.buffer = null; sent = null;
						}
						p.changeSlot = false;
		            }
					else
					{
						p.changeSlot = false;
					}
				}
			}
		}
	}
}