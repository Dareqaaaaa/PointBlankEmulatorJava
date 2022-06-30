package game.network.battle.recv;

import game.network.battle.*;
import game.network.game.sent.*;
import core.config.xml.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_BATTLE_PRESTART_REQ extends BattlePacketREQ
{
	int id, channel_id, server_id, slot;
	@Override
	public void readImpl()
	{
		id = ReadD();
		channel_id = ReadD();
		server_id = ReadD();
		slot = ReadD();
	}
	@Override
	public void runImpl()
	{
		Channel ch = GameServerXML.gI().getChannel(channel_id, server_id);
		if (ch != null)
		{
			Room r = ch.getRoom(id);
			if (r != null)
			{
				Player p = r.getPlayerBySlot(slot);
				if (p != null && p.gameClient != null)
				{
					if (r.prepared1.containsKey(slot))
						r.prepared2.put(slot, p.id);
					else
					{
						r.prepared1.put(slot, p.id);
						r.prepared2.put(slot, p.id);
					}
					String time = slot % 2 == 0 ? "TR" : "CT";
					SERVER_MESSAGE_ANNOUNCE_ACK sent = new SERVER_MESSAGE_ANNOUNCE_ACK(p.name + " is prepared (slot: " + slot + "). [" + time + "] [" + r.prepared2.size() + "/" + r.prepared1.size() + "]"); sent.packingBuffer();
					for (RoomSlot s : r.SLOT)
					{
						Player m = AccountSyncer.gI().get(s.player_id);
						if (m != null && s.state.ordinal() >= 11)
							m.gameClient.sendPacketBuffer(sent.buffer);
						if (s.id == slot)
						{
							s.espectador = r.isGhostMode(p.espectador);
							s.death = s.espectador;
							s.battleAccept = true;
						}
					}
					sent.buffer = null; sent = null;
				}
			}
		}
	}
}