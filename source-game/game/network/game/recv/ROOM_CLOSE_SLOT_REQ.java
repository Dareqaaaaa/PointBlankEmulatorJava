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

public class ROOM_CLOSE_SLOT_REQ extends game.network.game.GamePacketREQ
{
	int index, error;
	@Override
	public void readImpl()
	{
		index = ReadD();
	}
	@Override
	public void runImpl()
	{
		Room r = client.getRoom();
		Player p = client.getPlayer();
		if (r != null && p != null && p.id == r.LIDER)
		{
			RoomSlot slot = r.getSlot(index & 268435455);
			if (slot != null)
			{
				if ((index & 268435456) == 268435456)
				{
					if (slot.state == SlotState.CLOSE)
					{
						setSlot1(r, slot);
					}
				}
				else
				{
					setSlot2(r, p, slot);
				}
			}
			else
			{
				return;
			}
		}
		else
		{
			error = 0x80000401;
		}
		client.sendPacket(new ROOM_CLOSE_SLOT_ACK(error));
	}
	public void setSlot1(Room r, RoomSlot slot)
	{
		boolean specialMode = r.isSpecialMode();
		if (r.stage4v4 == 1 || specialMode)
		{
			int slots = 0;
			for (int i : slot.id % 2 == 0 ? r.RED_TEAM : r.BLUE_TEAM)
				if (r.getSlotState(i).ordinal() != 1)
					slots++;
			if ((specialMode && slot.id < 8 && slots < 4) || slots < 4)
				r.changeState(slot, SlotState.EMPTY, true);
			else
				error = 0x80000401;
		}
		else
		{
			r.changeState(slot, SlotState.EMPTY, true);
		}
	}
	public void setSlot2(Room r, Player p, RoomSlot slot)
	{
		if (slot.state == SlotState.EMPTY)
		{
			r.changeState(slot, SlotState.CLOSE, true);
			return;
		}
		if ((slot.state.ordinal() - SlotState.SHOP.ordinal()) > 6)
		{
			return;
		}
		Player m = AccountSyncer.gI().get(slot.player_id);
		if (m != null && m.gameClient != null && m.gameClient.getRoomId() == r.id)
		{
			if (p.rank == 53 || p.access_level == AccessLevel.MASTER_PLUS || m.rank < 53)
			{
				r.removePlayer(m, slot.id, true, true, true); 
				m.gameClient.sendPacket(new SERVER_MESSAGE_KICK_PLAYER_ACK());
			}
			else
			{
				error = 0x80000401;
			}
		}
	}
}