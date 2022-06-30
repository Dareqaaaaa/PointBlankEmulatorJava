package core.model;

import core.enums.*;

/**
 * 
 * @author Henrique
 *
 */

public class ClanFronto
{
	public int id, formacao, clan_id, channel_id, server_id;
	public long lider;
	public RoomSlot[] SLOT = new RoomSlot[8];
	public RoomClanState cstate = RoomClanState.CLAN_ROOM_STAY;
	public ClanFronto(int id, int formacao, int clan_id, int channel_id, int server_id, long lider)
	{
		this.id = id;
		this.formacao = formacao;
		this.clan_id = clan_id;
		this.channel_id = channel_id;
		this.server_id = server_id;
		this.lider = lider;
		for (int i = 0; i < 8; i++)
			SLOT[i] = new RoomSlot(i, 0, SlotState.EMPTY);
	}
	public RoomSlot addPlayer(long pId)
	{
		for (RoomSlot slot : SLOT)
		{
			if (slot.player_id == 0 && slot.state == SlotState.EMPTY)
			{
				slot.player_id = pId;
				slot.state = SlotState.NORMAL;
				return slot;
			}
		}
		return null;
	}
	public void removePlayer(int id)
	{
		RoomSlot slot = getSlot(id);
		if (slot != null)
		{
			slot.player_id = 0;
			slot.state = SlotState.EMPTY;
		}
	}
	public RoomSlot getSlot(int slot)
	{
		if (slot >= 0 && slot <= 15)
			return SLOT[slot];
		return null;
	}
	public int sizePlayers()
	{
		int total = 0;
		for (RoomSlot s : SLOT)
			if (s.player_id > 0)
				total++;
		return total;
	}
}