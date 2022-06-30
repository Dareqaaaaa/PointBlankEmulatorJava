package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;

import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_NEW_HOST_REQ extends game.network.game.GamePacketREQ
{
	List<RoomSlot> list = new ArrayList<RoomSlot>(15);
	int error = 0x80000000;
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Room r = client.getRoom();
		if (p != null && r != null && r.rstate == RoomState.READY && r.LIDER == client.pId)
		{
			for (RoomSlot slot : r.SLOT)
			{
				if (slot.player_id > 0 && slot.player_id != r.LIDER)
				{
					list.add(slot);
				}
			}
			int size = list.size();
			if (size > 0)
			{
				try
				{
					for (int i = 0; i < size; i++)
					{
						RoomSlot host = list.get(IDFactory.gI().randomId(size));
						if (host != null)
						{
							r.setNewLeader(host.id, false);
							error = host.id;
							break;
						}
					}
				}
				catch (Exception e)
				{
				}
				client.sendPacket(new ROOM_NEW_HOST_ACK(error));
			}
			list = null;
		}
	}
}