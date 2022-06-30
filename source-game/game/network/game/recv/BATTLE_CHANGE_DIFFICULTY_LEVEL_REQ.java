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

public class BATTLE_CHANGE_DIFFICULTY_LEVEL_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Room r = client.getRoom();
		if (r != null && r.aiLevel < 10 && r.isSpecialMode() && r.getSlotState(client.pId) == SlotState.BATTLE && r.rstate == RoomState.BATTLE)
		{
            if (r.aiLevel < 10)
            	r.aiLevel++;
            BATTLE_CHANGE_DIFFICULTY_LEVEL_ACK sent = new BATTLE_CHANGE_DIFFICULTY_LEVEL_ACK(r);  sent.packingBuffer();
            for (RoomSlot slot : r.SLOT)
			{
				Player m = AccountSyncer.gI().get(slot.player_id);
				if (m != null && slot.state.ordinal() >= 8)
					m.gameClient.sendPacketBuffer(sent.buffer);
			}
            sent.buffer = null; sent = null;
		}
	}
}