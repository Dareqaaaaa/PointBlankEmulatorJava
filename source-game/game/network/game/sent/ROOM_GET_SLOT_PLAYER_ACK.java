package game.network.game.sent;

import core.info.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_GET_SLOT_PLAYER_ACK extends game.network.game.GamePacketACK
{
	RoomSlot s;
	Player p;
	Clan c;
	public ROOM_GET_SLOT_PLAYER_ACK(Player p, RoomSlot s)
	{
		super();
		this.s = s;
		this.p = p;
		c = p != null ? ck.BUSCAR_CLAN(p.clan_id) : null;
	}
	@Override
	public void writeImpl()
	{
		if (p != null && s != null)
		{
			WriteD(s.id);
			WriteC(s.state.ordinal());
			WriteC(p.rank());
			WriteD(p.clan_id());
			WriteD(p.role());
			WriteC(c != null ? c.rank : 0);
			WriteD(c != null ? c.logo : 0xFFFFFFFF);
			WriteC(p.vip_pccafe);
			WriteC(p.effect1);
			WriteC(p.effect2);
			WriteC(p.effect3);
			WriteC(p.effect4);
			WriteC(p.effect5);
			WriteS(c != null ? c.name : "", 17);
			WriteH(0);
			if (!Software.pc.client_rev.equals("1.15.9"))
				WriteC(0);
			WriteC(p.country.value);
			WriteC(p.name.length() + 1); //anular
			WriteS(p.name, p.name.length() + 1);
			WriteC(p.color);
		}
	}
}