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

public class BATTLE_DINO_DEATHBLOW_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
		ReadC();
		ReadD();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Room r = client.getRoom();
		if (p != null && r != null && r.rstate == RoomState.BATTLE)
		{
			RoomSlot slot = r.getSlot(p.slot);
			if (slot != null && slot.state == SlotState.BATTLE)
			{
				if (slot.id % 2 == 0)
					r.redDino += 5;
				else
					r.blueDino += 5;
				BATTLE_DINO_PLACAR_ACK sent = new BATTLE_DINO_PLACAR_ACK(r); sent.packingBuffer();
				for (RoomSlot s : r.SLOT)
				{
					Player m = s.player_id > 0 ? AccountSyncer.gI().get(s.player_id) : null;
					if (m != null && s.state == SlotState.BATTLE)
						m.gameClient.sendPacketBuffer(sent.buffer);
				}
				sent.buffer = null; sent = null;
			}
		}
	}
}