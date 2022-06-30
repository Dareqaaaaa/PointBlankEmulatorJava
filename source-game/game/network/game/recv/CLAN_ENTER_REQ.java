package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_ENTER_REQ extends game.network.game.GamePacketREQ
{
	Clan c;
	int clan_id, role;
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			Room r = client.getRoom();
			if (r != null)
			{
				RoomSlot s = r.getSlotByPID(p.id);
				if (s != null && s.state.ordinal() <= 8)
				{
					r.changeState(s, SlotState.CLAN, true);
				}
			}
			c = ck.BUSCAR_CLAN(p.clan_id);
			clan_id = p.clan_id();
			role = p.role();
			p.update_clan_nick = "";
			p.update_nick = "";
			p.update_clan_logo = 0;
		}
		client.sendPacket(new CLAN_ENTER_ACK(clan_id, role));
		if (c != null)
			client.sendPacket(new CLAN_DETAIL_INFO_ACK(c, 0));
	}
}