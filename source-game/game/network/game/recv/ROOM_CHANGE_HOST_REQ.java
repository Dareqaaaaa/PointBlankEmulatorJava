package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.utils.AccountSyncer;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_CHANGE_HOST_REQ extends game.network.game.GamePacketREQ
{
	int slot;
	@Override
	public void readImpl()
	{
		slot = ReadD();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Room r = client.getRoom();
		if (p != null && r != null && r.LIDER == p.id && p.slot != slot && r.rstate == RoomState.READY)
		{
			if (r.setNewLeader(slot, false))
			{
				ROOM_CHANGE_HOST_ACK sent1 = new ROOM_CHANGE_HOST_ACK(slot); sent1.packingBuffer();
				ROOM_GET_SLOTS_ACK sent2 = new ROOM_GET_SLOTS_ACK(r); sent2.packingBuffer();
				for (RoomSlot s : r.SLOT)
				{
					Player m = AccountSyncer.gI().get(s.player_id);
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
}