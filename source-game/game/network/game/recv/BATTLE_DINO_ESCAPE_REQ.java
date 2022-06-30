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

public class BATTLE_DINO_ESCAPE_REQ extends game.network.game.GamePacketREQ
{
	int slot, bonus;
	@Override
	public void readImpl()
	{
		slot = ReadD();
	}
	@Override
	public void runImpl()
	{
		Room r = client.getRoom();
		if (r != null && r.rstate == RoomState.BATTLE)
		{
			RoomSlot s = r.getSlot(slot);
			if (s != null && s.state == SlotState.BATTLE)
			{
				switch (s.dinoOnLife)
				{
					case 0: bonus = 1; break;
					case 1: bonus = 2; break;
					case 2: bonus = 3; break;
					case 3: bonus = 4; break;
					default: bonus = 1; break;
				}
				if (slot % 2 == 0)
					r.redDino += (5 + bonus + s.dinoOnLife);
				else
					r.blueDino += (5 + bonus + s.dinoOnLife);
				s.exp += 5;
				s.gold += 5;
				s.objetivo++;
				s.dinoOnLife++;
				BATTLE_DINO_ESCAPE_ACK sent1 = new BATTLE_DINO_ESCAPE_ACK(r, s); sent1.packingBuffer();
				BATTLE_DINO_PLACAR_ACK sent2 = new BATTLE_DINO_PLACAR_ACK(r); sent2.packingBuffer();
				for (RoomSlot sM : r.SLOT)
				{
					Player m = sM.player_id > 0 ? AccountSyncer.gI().get(sM.player_id) : null;
					if (m != null && sM.state == SlotState.BATTLE)
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