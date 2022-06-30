package game.network.game.recv;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_LOADING_REQ extends game.network.game.GamePacketREQ
{
	Room r;
	@Override
	public void readImpl()
	{
		r = client.getRoom();
		if (r != null)
			r.map_name = ReadS(ReadC());
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (r != null && p != null)
		{
			if (r.map_name.isEmpty() || r.map_name.length() < 3)
				return;
			if (r.getSlotState(p.slot) != SlotState.LOAD)
				return;
			p.listClans.clear();
			if (!r.prepared1.containsKey(p.slot))
				r.prepared1.put(p.slot, p.id);
			r.changeState(p.slot, SlotState.RENDEZVOUS, true);
			if (p.id == r.LIDER)
			{
				r.prepare_room(p.slot);
				r.rstate = RoomState.RENDEZVOUS;
				r.updateBInfo();
			}
		}
	}
}