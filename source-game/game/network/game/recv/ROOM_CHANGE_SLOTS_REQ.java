package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;

import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_CHANGE_SLOTS_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Room r = client.getRoom();
		if (p != null && r != null)
		{
			if (r.rstate == RoomState.READY && r.LIDER == p.id)
			{
				List<RoomSlot> slots = new ArrayList<RoomSlot>(8);
				int leader = (p.slot % 2 == 0) ? p.slot + 1 : p.slot - 1;
				for (int i : r.RED_TEAM)
				{
					RoomSlot o = r.getSlot(i), n = r.getSlot(i + 1);
					if (o.state == SlotState.READY) r.changeStateInfo(o, SlotState.NORMAL, false);
					if (n.state == SlotState.READY) r.changeStateInfo(n, SlotState.NORMAL, false);
					Player a = AccountSyncer.gI().get(o.player_id);
					Player b = AccountSyncer.gI().get(n.player_id);
					if (a != null) a.slot = i + 1;
					if (b != null) b.slot = i;
					o.id = i + 1;
					n.id = i;
					r.SLOT[i] = n;
					r.SLOT[i + 1] = o;
					slots.add(new RoomSlot(n, o));
				}
				if (slots.size() > 0)
				{
					ROOM_CHANGE_SLOTS_ACK sent1 = new ROOM_CHANGE_SLOTS_ACK(r, leader, 2, slots); sent1.packingBuffer();
					ROOM_GET_SLOTS_ACK sent2 = new ROOM_GET_SLOTS_ACK(r); sent2.packingBuffer();
					for (RoomSlot slot : r.SLOT)
					{
						Player m = AccountSyncer.gI().get(slot.player_id);
						if (m != null && m.gameClient != null)
						{
							m.gameClient.sendPacketBuffer(sent1.buffer);
							m.gameClient.sendPacketBuffer(sent2.buffer);
						}
					}
					sent1.buffer = null; sent1 = null;
					sent2.buffer = null; sent2 = null;
				}
				slots = null;
			}
		}
	}
}