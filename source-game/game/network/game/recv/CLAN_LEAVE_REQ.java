package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_LEAVE_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Room r = client.getRoom();
		if (p != null)
		{
			if (r != null)
			{
				RoomSlot s = r.getSlotByPID(p.id);
				if (s != null && s.state.ordinal() <= 8)
				{
					r.changeState(s, SlotState.NORMAL, true);
				}
			}
			p.update_clan_nick = "";
			p.update_nick = "";
			p.listClans.clear();
			p.listClanFrontos.clear();
		}
		client.sendPacket(new CLAN_LEAVE_ACK());
	}
}