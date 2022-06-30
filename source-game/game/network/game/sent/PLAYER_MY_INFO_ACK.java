package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class PLAYER_MY_INFO_ACK extends game.network.game.GamePacketACK
{
	Player p;
	Clan c;
	public PLAYER_MY_INFO_ACK(Player p)
	{
		super();
		this.p = p;
		c = ck.BUSCAR_CLAN(p != null ? p.clan_id : 0);
	}
	@Override
	public void writeImpl()
	{
		WriteS(p.name, 33);
		WriteD(p.exp);
		WriteD(p.rank);
		WriteD(p.rank);
		WriteD(p.gold);
		WriteD(p.cash);
		WriteD(p.clan_id());
		WriteD(p.role());
		WriteQ(p.status());
		WriteC(p.vip_pccafe);
		WriteC(p.tourney_level);
		WriteC(p.color);
		WriteS(c != null ? c.name : "", 17);
		WriteC(c != null ? c.rank : 0);
		WriteC(c != null ? c.nivel().ordinal() : 0);
		WriteD(c != null ? c.logo : 0xFFFFFFFF);
		WriteC(c != null ? c.color : 0);
        WriteD(10000);
		WriteC(0);
		WriteD(p.remaining());
		WriteD(p.lastup());
	}
}