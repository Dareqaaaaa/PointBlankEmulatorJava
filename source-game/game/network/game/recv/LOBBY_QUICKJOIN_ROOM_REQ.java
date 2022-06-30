package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_QUICKJOIN_ROOM_REQ extends game.network.game.GamePacketREQ
{
	Player p;
	Channel ch;
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		try
		{
			p = client.getPlayer();
			ch = client.getChannel();
			if (p != null && ch != null && client.getRoom() == null)
			{
				BUSCAR_SALA();
			}
			else
			{
				client.sendPacket(new LOBBY_QUICKJOIN_ACK(0x80000000));
			}
		}
		catch (Exception e)
		{
			client.sendPacket(new LOBBY_QUICKJOIN_ACK(0x80000000));
		}
	}
	public void BUSCAR_SALA() throws Exception
	{
		if (ch.SALAS.size() > 0)
		{
			Room r = ch.getRoom(IDFactory.gI().randomId(ch.SALAS.size()));
			if (r != null && !r.kikados.contains(p.id) && r.limit == 0 && r.passwd.isEmpty())
			{
				RoomSlot slot = r.addPlayer(p);
				if (slot != null)
				{
					ch.removePlayer(p.id);
					ROOM_GET_SLOT_PLAYER_ACK sent = new ROOM_GET_SLOT_PLAYER_ACK(p, slot); sent.packingBuffer();
					for (RoomSlot s : r.SLOT)
					{
						Player m = AccountSyncer.gI().get(s.player_id);
						if (m != null && s.id != slot.id)
							m.gameClient.sendPacketBuffer(sent.buffer);
					}
					sent.buffer = null; sent = null;
					client.sendPacket(new ROOM_JOIN_ACK(r, slot.id, 0));
				}
				else
				{
					client.sendPacket(new ROOM_JOIN_ACK(null, -1, 0x80001003));
				}
			}
			else
			{
				client.sendPacket(new LOBBY_QUICKJOIN_ACK(0x80000000));
			}
		}
		else
		{
			client.sendPacket(new LOBBY_QUICKJOIN_ACK(0x80000000));
		}
	}
}