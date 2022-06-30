package game.network.game.sent;

import java.util.*;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_CLAN_PART_ACK extends game.network.game.GamePacketACK
{
	List<ClanFronto> match;
	int error;
	public CLAN_MATCH_CLAN_PART_ACK(List<ClanFronto> match, int error)
	{
		super();
		this.match = match;
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteC(match.size());
		WriteH(error); //1
		WriteH(match.size());
		for (ClanFronto cf : match)
		{
			Clan clan = ck.BUSCAR_CLAN(cf.clan_id);
			WriteC(match.indexOf(cf));
			WriteH(cf.channel_id + 10);
			WriteH(cf.channel_id + 10);
			WriteH(cf.server_id);
			WriteC(cf.formacao);
			WriteC(cf.sizePlayers());
			WriteD(0); //state?
			WriteC(0);
		    WriteC(EssencialUtil.getRank(cf.lider));
		    WriteS(EssencialUtil.getNick(cf.lider), 33);
            WriteC(0x55); //Skill
            WriteS(clan.name, 17);
            WriteQ(cf.lider);
            WriteC(1);
		}
	}
}