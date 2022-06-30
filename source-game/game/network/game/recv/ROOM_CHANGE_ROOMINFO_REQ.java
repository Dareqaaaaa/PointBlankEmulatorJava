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

public class ROOM_CHANGE_ROOMINFO_REQ extends game.network.game.GamePacketREQ
{
	Room r;
	ModesEnum type;
	int map, weapon;
	boolean autorized, camp;
	@Override
	public void readImpl()
	{
		r = client.getRoom();
		if (r != null && r.rstate == RoomState.READY && r.LIDER == client.pId)
		{
			String name = r.name;
			ReadD();
			r.name = ReadS(23);
			map = r.map;
			type = r.type;
			weapon = r.allWeapons;
			r.map = ReadH();
			r.stage4v4 = ReadC();
			r.type = ModesEnum.values()[ReadC()];
			ReadC();
			ReadC();
			ReadC();
			r.ping = ReadC();
			if (!r.modoPorrada)
			{
				r.allWeapons = ReadC();
			}
			else
			{
				ReadC();
			}
			r.randomMap = ReadC(); 
			r.special = ReadC(); 
			r.aiCount = ReadC();
			r.aiLevel = ReadC();
			r.badName();
			r.badConfig();
			autorized = true;
			if (!(r.block(name)))
			{
				if (r.block(r.name))
					camp = true;
			}
		}
		else
		{
			autorized = false;
		}
	}
	@Override
	public void runImpl()
	{
		if (autorized)
		{
			int update = 0;
			if (r.rstate == RoomState.READY)
			{
				if (map != r.map || type != r.type || weapon != r.allWeapons || camp)
				{
					for (RoomSlot slot : r.SLOT)
					{
						if (slot.state == SlotState.READY)
						{
							r.changeStateInfo(slot, SlotState.NORMAL, false);
							update++;
						}
					}
				}
			}
			ROOM_CHANGE_ROOMINFO_ACK sent1 = new ROOM_CHANGE_ROOMINFO_ACK(r, "", r.isSpecialMode()); sent1.packingBuffer();
			ROOM_GET_SLOTS_ACK sent2 = new ROOM_GET_SLOTS_ACK(r); sent2.packingBuffer();
			for (RoomSlot slot : r.SLOT)
			{
				Player m = AccountSyncer.gI().get(slot.player_id);
				if (m != null)
				{
					m.gameClient.sendPacketBuffer(sent1.buffer);
					if (update > 0)
						m.gameClient.sendPacketBuffer(sent2.buffer);
				}
			}
			sent1.buffer = null; sent1 = null;
			sent2.buffer = null; sent2 = null;
		}
	}
}