package game.network.game.sent;

import java.util.*;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_WAR_MATCH_TEAM_LIST_ACK extends game.network.game.GamePacketACK
{
	List<ClanFronto> match;
	int error;
	public CLAN_WAR_MATCH_TEAM_LIST_ACK(List<ClanFronto> match, int error)
	{
		super();
		this.match = match;
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteH(match.size()); //Quantidades de clans na lista
		WriteD(error);
		for (ClanFronto cf : match)
		{
			Clan clan = ck.BUSCAR_CLAN(cf.clan_id);
			WriteC(match.indexOf(cf)); //id?
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
            WriteH(0);
            WriteH(17513); //???
            WriteC(0);
		    WriteS(EssencialUtil.getNick(cf.lider), 33);
	        WriteC(EssencialUtil.getRank(cf.lider));
	    	for (RoomSlot sM : cf.SLOT)
			{
	    		Player pC = sM.player_id > 0 ? AccountSyncer.gI().get(sM.player_id) : null;
				if (pC != null && pC.gameClient != null)
				{
					WriteQ(sM.player_id);
					WriteC(sM.id);
					WriteC(pC.rank);
					WriteS(pC.name, 33);
				}
				else
				{
					WriteB(new byte[43]);
				}
			}
		}
	}
}