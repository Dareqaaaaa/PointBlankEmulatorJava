package game.network.game.recv;

import java.util.*;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_GET_HOST_REQ extends game.network.game.GamePacketREQ
{
	boolean change;
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Room r = client.getRoom();
		if (p != null && r != null)
		{
			change = p.rank == 53 || p.access_level == AccessLevel.GM_NORMAL || p.access_level == AccessLevel.MASTER_PLUS;
			if (r.LIDER != p.id && (r.rstate == RoomState.READY || change))
			{
				List<Player> jogadores = r.getPlayers();
				if (jogadores.size() > 0 && r.last_host != p.slot)
				{
					r.last_host = p.slot;
					if (!r.listHost.containsKey(p.id))
						r.listHost.put(p.id, p.slot);
					if (r.listHost.size() >= jogadores.size() / 2 + 1)
						change = true;
					if (change)
						change = r.setNewLeader(p.slot, false);
					ROOM_GET_HOST_ACK sent1 = new ROOM_GET_HOST_ACK(p.slot); sent1.packingBuffer();
					ROOM_CHANGE_HOST_ACK sent2 = new ROOM_CHANGE_HOST_ACK(p.slot); sent2.packingBuffer();
					ROOM_GET_SLOTS_ACK sent3 = new ROOM_GET_SLOTS_ACK(r); sent3.packingBuffer();
					for (Player m : jogadores)
					{
						m.gameClient.sendPacketBuffer(sent1.buffer);
						if (change)
						{
							m.gameClient.sendPacketBuffer(sent2.buffer);
							m.gameClient.sendPacketBuffer(sent3.buffer);
						}
					}
					sent1.buffer = null; sent1 = null;
					sent2.buffer = null; sent2 = null;
					sent3.buffer = null; sent3 = null;
				}
				jogadores = null;
			}
		}
		else
		{
			client.sendPacket(new ROOM_GET_HOST_ACK(0x80000000));
		}
	}
}