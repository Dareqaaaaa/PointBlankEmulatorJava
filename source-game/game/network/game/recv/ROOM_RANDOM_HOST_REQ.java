package game.network.game.recv;

import java.awt.Color;
import java.util.*;

import core.enums.*;
import core.info.Software;
import core.model.*;
import core.utils.*;
import game.network.game.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_RANDOM_HOST_REQ extends game.network.game.GamePacketREQ
{
	List<RoomSlot> list = new ArrayList<RoomSlot>(15);
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Software.LogColor("ROOM_RANDOM_HOST_REQ: pId: " + client.pId, Color.GREEN);
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
					RoomSlot host = list.get(IDFactory.gI().randomId(size));
					if (host != null)
					{
						r.setNewLeader(host.id, false);
						ROOM_RANDOM_HOST_ACK sent1 = new ROOM_RANDOM_HOST_ACK(host.id); sent1.packingBuffer();
						ROOM_GET_SLOTS_ACK sent2 = new ROOM_GET_SLOTS_ACK(r); sent2.packingBuffer();
						for (RoomSlot slot : r.SLOT)
						{
							Player m = AccountSyncer.gI().get(slot.player_id);
							if (m != null)
							{
								m.gameClient.sendPacketBuffer(sent1.buffer);
								m.gameClient.sendPacketBuffer(sent2.buffer);
							}
						}
					}
					else
					{
						client.sendPacket(new ROOM_RANDOM_HOST_ACK(0x80000000));
					}
				}
				catch (Exception e)
				{
					client.sendPacket(new ROOM_RANDOM_HOST_ACK(0x80000000));
				}
			}
			list = null;
		}
	}
}