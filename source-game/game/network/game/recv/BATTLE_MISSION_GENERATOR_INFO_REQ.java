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

public class BATTLE_MISSION_GENERATOR_INFO_REQ extends game.network.game.GamePacketREQ
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
			}
		}
	}
	@Override
	public void runImpl()
	{
		if (r != null && playing)
		{
			BATTLE_MISSION_GENERATOR_INFO_ACK sent = new BATTLE_MISSION_GENERATOR_INFO_ACK(r); sent.packingBuffer();
			for (RoomSlot slot : r.SLOT)
			{
				Player m = AccountSyncer.gI().get(slot.player_id);
				if (m != null && slot.state == SlotState.BATTLE)
					m.gameClient.sendPacketBuffer(sent.buffer);
			}
			sent.buffer = null;	sent = null;
	        if (r.bar1 < 1)
	        {
	        	r.round(TimeEnum.TIME_AZUL, WinnerRound.GENERATORINFO);
	        }
	        else if (r.bar2 < 1)
	        {
	        	r.round(TimeEnum.TIME_VERMELHO, WinnerRound.GENERATORINFO);
	        }
		}
	}
}