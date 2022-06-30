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

public class VOTEKICK_UPDATE_REQ extends game.network.game.GamePacketREQ
{
	int voto;
	@Override
	public void readImpl()
	{
		voto = ReadC();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Room r = client.getRoom();
		if (p != null && r != null && r.rstate == RoomState.BATTLE && r.threadVOTE != null && r.votekick != null)
		{
			RoomSlot rs = r.getSlot(p.slot);
			if (rs.state == SlotState.BATTLE)
			{
				VoteKick vote = r.votekick;
				if (vote.creatorIdx != p.slot && vote.victimIdx != p.slot)
				{
					if (r.votou.containsKey(p.id))
					{
						client.sendPacket(new VOTEKICK_UPDATE_ERROR_ACK(0x800010F1));
					}
					else
					{
						if (voto == 0)
						{
							vote.kikar++;
							if (p.slot % 2 == vote.victimIdx % 2)
								vote.allies++;
							else
								vote.enemys++;
						}
						else
						{
							vote.deixar++;
						}
						r.votou.put(p.id, voto);
						if (r.votou.size() >= vote.playing())
						{
							r.votekickPlayer();
						}
						else
						{
							VOTEKICK_UPDATE_ACK sent = new VOTEKICK_UPDATE_ACK(vote); sent.packingBuffer();
							for (RoomSlot slot : r.SLOT)
							{
								Player m = slot.player_id > 0 ? AccountSyncer.gI().get(slot.player_id) : null;
								if (m != null && slot.state == SlotState.BATTLE)
									m.gameClient.sendPacketBuffer(sent.buffer);
					        }
							sent.buffer = null; sent = null;
						}
					}
				}
			}
		}
	}
}