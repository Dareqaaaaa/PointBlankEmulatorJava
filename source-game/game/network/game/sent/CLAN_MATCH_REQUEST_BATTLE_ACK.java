package game.network.game.sent;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_REQUEST_BATTLE_ACK extends game.network.game.GamePacketACK
{
	ClanFronto cf;
	Clan clan;
	public CLAN_MATCH_REQUEST_BATTLE_ACK(ClanFronto cf)
	{
		super();
		this.cf = cf;
		if (cf != null)
			clan = ck.BUSCAR_CLAN(cf.clan_id);
	}
	@Override
	public void writeImpl()
	{
		if (cf != null && clan != null)
		{
			WriteH(cf.id);
			WriteH(cf.channel_id + 10);
			WriteH(cf.channel_id + 10);
			WriteH(cf.server_id);
			WriteC(cf.formacao);
			WriteC(cf.sizePlayers());
			WriteD(0);
			WriteC(0);
			WriteD(clan.id);
			WriteC(clan.rank);
			WriteD(clan.logo);
			WriteS(clan.name, 17);
		    WriteC(EssencialUtil.getRank(cf.lider));
		    WriteS(EssencialUtil.getNick(cf.lider), 33);
			WriteQ(clan.owner);
			WriteC(1); //t
		}
	}
}