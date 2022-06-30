package game.network.game.recv;

import core.config.settings.*;
import core.enums.*;
import core.model.*;
import core.network.*;
import core.utils.*;
import game.network.game.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class VOTEKICK_START_REQ extends game.network.game.GamePacketREQ
{
	int motivo, slot, jogadores_vermelho, jogadores_azul, error;
	@Override
	public void readImpl()
	{
		slot = ReadC();
        motivo = ReadC();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Room r = client.getRoom();
		if (p != null && r != null && r.rstate == RoomState.BATTLE)
		{
			RoomSlot rs = r.getSlot(p.slot);
			if (rs != null && rs.state == SlotState.BATTLE && p.slot != slot && r.getSlotState(slot) == SlotState.BATTLE)
			{
				if (r.threadVOTE == null && r.votekick == null)
				{
		            jogadores_vermelho = r.getPlayingPlayers(0, true, -1); 
		            jogadores_azul = r.getPlayingPlayers(1, true, -1);
					if (jogadores_vermelho < 2 && jogadores_azul < 2)
					{
						error = 0x800010E2;
					}
					else if (p.rank < ConfigurationLoader.gI().votekick_requirits_rank)
					{
						error = 0x800010E4;
					}
				}
				else
				{
					error = 0x800010E0;
				}
			}
			else
			{
				error = 0x800010E3; //0x800010E1
			}
			client.sendPacket(new BATTLE_SUGGEST_KICKVOTE_ACK(error));
			if (error == 0 && r.threadVOTE == null)
			{
				r.votekick = new VoteKick(slot, p.slot, motivo);
				for (RoomSlot s : r.SLOT)
				{
					r.votekick.play[s.id] = s.player_id > 0 && s.state == SlotState.BATTLE;
				}
				VOTEKICK_START_ACK sent = new VOTEKICK_START_ACK(r.votekick); sent.packingBuffer();
				for (RoomSlot s : r.SLOT)
				{
					Player m = s.player_id > 0 ? AccountSyncer.gI().get(s.player_id) : null;
					if (m != null && s.id != slot && s.id != p.slot)
						m.gameClient.sendPacketBuffer(sent.buffer);
				}
				sent.buffer = null; sent = null;
				try
				{
					r.threadVOTE = ThreadPoolManager.gI().scheduleCOUNTD(new Runnable()
					{
						@Override
						public void run()
						{
							r.votekickPlayer();
						}
					}, 20001);
				}
				catch (Throwable e)
				{
					exceptionLOG(e);
					r.stopTask("votekick");
				}
			}
		}
	}
}