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

public class BATTLE_MISSION_DEFENCE_INFO_REQ extends game.network.game.GamePacketREQ
{
	Room r;
	boolean playing = false;
	@Override
	public void readImpl()
	{
		r = client.getRoom();
		if (r != null)
		{
			playing = r.getSlotState(client.pId).ordinal() == 13 && r.rstate == RoomState.BATTLE;
			if (playing)
			{
				r.bar1 = ReadH();
				r.bar2 = ReadH();
			    for (RoomSlot slot : r.SLOT)
			    {
			       	short bar = ReadH();
			       	if (slot.state == SlotState.BATTLE)
			       	{
				       	slot.bar1 = bar;
				       	slot.util = slot.bar1 / 600;
			       	}
			    }
			    for (RoomSlot slot : r.SLOT)
			    {
			       	short bar = ReadH();
			       	if (slot.state == SlotState.BATTLE)
			       	{
				       	slot.bar2 = bar;
				       	slot.util = slot.bar2 / 600;
			       	}
			    }
			}
		}
	}
	@Override
	public void runImpl()
	{
		if (r != null && playing)
		{
			BATTLE_MISSION_DEFENCE_INFO_ACK sent = new BATTLE_MISSION_DEFENCE_INFO_ACK(r); sent.packingBuffer();
			for (RoomSlot sM : r.SLOT)
			{
				Player m = sM.player_id > 0 ? AccountSyncer.gI().get(sM.player_id) : null;
				if (m != null && sM.state == SlotState.BATTLE)
					m.gameClient.sendPacketBuffer(sent.buffer);
			}
			sent.buffer = null; sent = null;
		    if (r.bar1 < 1 && r.bar2 < 1)
		    	r.round(TimeEnum.TIME_VERMELHO, WinnerRound.DEFENCE_INFO);
		}
	}
}