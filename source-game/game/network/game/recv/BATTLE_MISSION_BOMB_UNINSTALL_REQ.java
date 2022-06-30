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

public class BATTLE_MISSION_BOMB_UNINSTALL_REQ extends game.network.game.GamePacketREQ
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
		if (r != null)
		{
			RoomSlot s = r.getSlot(slot);
			if (s != null && r.threadBOMB != null && slot % 2 == 1 && s.state == SlotState.BATTLE && r.rstate == RoomState.BATTLE)
			{
				BATTLE_MISSION_BOMB_UNINSTALL_ACK sent = new BATTLE_MISSION_BOMB_UNINSTALL_ACK(slot); sent.packingBuffer();
				for (RoomSlot sM : r.SLOT)
				{
					Player m = sM.player_id > 0 ? AccountSyncer.gI().get(sM.player_id) : null;
					if (m != null && sM.state == SlotState.BATTLE)
						m.gameClient.sendPacketBuffer(sent.buffer);
				}
				sent.buffer = null; sent = null;
				if (r.map != 44)
				{
					s.objetivo++;
					r.round(TimeEnum.TIME_AZUL, WinnerRound.UNINSTALL);
				}
			}
		}
	}
}