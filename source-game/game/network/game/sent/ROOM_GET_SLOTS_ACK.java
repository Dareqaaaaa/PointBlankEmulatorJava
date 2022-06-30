package game.network.game.sent;

import core.info.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_GET_SLOTS_ACK extends game.network.game.GamePacketACK
{
	Room r;
	public ROOM_GET_SLOTS_ACK(Room r)
	{
		super();
		this.r = r;
	}
	@Override
	public void writeImpl()
	{
		if (r != null)
		{
			Player leader = r.getLeader();
			if (leader != null)
			{
				WriteD(leader.slot);
				boolean epic = Software.pc.client_rev.equals("1.15.9");
				for (RoomSlot s : r.SLOT)
				{
					Player p = AccountSyncer.gI().get(s.player_id);
					Clan c = p != null ? ck.BUSCAR_CLAN(p.clan_id) : null;
					WriteC(s.state.ordinal());
					WriteC(p != null ? p.rank() : 0);
					WriteD(p != null ? p.clan_id() : 0);
					WriteD(p != null ? p.role() : 0);
				    WriteC(c != null ? c.rank : 0);
					WriteD(c != null ? c.logo : 0xFFFFFFFF);
					WriteC(p != null ? p.vip_pccafe : 0);
					WriteC(p != null ? p.tourney_level : 0);
					WriteC(p != null ? p.effect1 : 0);
					WriteC(p != null ? p.effect2 : 0);
					WriteC(p != null ? p.effect3 : 0);
					WriteC(p != null ? p.effect4 : 0);
					WriteC(p != null ? p.effect5 : 0);
					WriteS(c != null ? c.name : "", 17);
					WriteH(0);
					if (!epic)
						WriteC(0);
					WriteC(p != null ? p.country.value : 0);
				}
			}
		}
	}
}