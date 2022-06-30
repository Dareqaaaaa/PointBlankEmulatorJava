package game.network.game.sent;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_GET_ROOMLIST_ACK extends game.network.game.GamePacketACK
{
	Channel ch;
	public LOBBY_GET_ROOMLIST_ACK(Channel ch)
	{
		super();
		this.ch = ch;
	}
	@Override
	public void writeImpl()
	{
		if (ch != null)
		{
			WriteD(ch.SALAS.size());
			WriteD(0);
			WriteD(ch.SALAS.size());
			for (Room r : ch.SALAS.values())
			{
				WriteD(r.id);
				WriteS(r.name, 23);
				WriteH(r.map);
				WriteC(r.stage4v4);
				WriteC(r.type.ordinal());
				WriteC(r.rstate.ordinal() > 1 ? 1 : 0);
				WriteC(r.sizePlayers());
				WriteC(r.getSlots());
				WriteC(r.ping);
				WriteC(r.allWeapons);
				WriteC(r.restrictions());
				WriteC(r.special);
			}
			WriteD(ch.JOGADORES.size());
			WriteD(0);
			WriteD(ch.JOGADORES.size());
			for (long id : ch.JOGADORES)
			{
				Player p = AccountSyncer.gI().get(id);
				if (p != null && p.status() > 0)
				{
					Clan c = ck.BUSCAR_CLAN(p.clan_id);
					WriteD(p.channel_index = ch.JOGADORES.indexOf(id));
					WriteD(c != null ? c.logo : 0xFFFFFFFF);
					WriteS(c != null ? c.name : "", 17);
		    		WriteH(p.rank());
					WriteS(p.name, 33);
					WriteC(p.color);
					WriteC(p.country.value);
				}
			}
		}
	}
}