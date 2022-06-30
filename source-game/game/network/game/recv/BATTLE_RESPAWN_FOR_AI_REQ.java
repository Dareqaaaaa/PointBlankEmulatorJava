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

public class BATTLE_RESPAWN_FOR_AI_REQ extends game.network.game.GamePacketREQ
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
		Room r = client.getRoom();
		if (r != null && r.LIDER == client.pId && r.isSpecialMode() && r.rstate == RoomState.BATTLE)
		{
			RoomSlot s = r.getSlot(slot);
			if (s != null)
			{
				s.aiLevel = r.aiLevel;
				s.killMessage = FragValues.NONE;
				s.lastKillState = 0;
				s.oneTimeKills = 0;
				s.repeatLastState = false;
				s.dinoOnLife = 0;
				s.respawn++;
				r.respawn++;
				BATTLE_RESPAWN_FOR_AI_ACK sent = new BATTLE_RESPAWN_FOR_AI_ACK(slot); sent.packingBuffer();
				for (RoomSlot sM : r.SLOT)
				{
					Player m = AccountSyncer.gI().get(sM.player_id);
					if (m != null && sM.state == SlotState.BATTLE)
						m.gameClient.sendPacketBuffer(sent.buffer);
				}
				sent.buffer = null; sent = null;
			}
		}
	}
}