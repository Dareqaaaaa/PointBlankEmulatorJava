package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_WAR_MATCH_TEAM_LIST_REQ extends game.network.game.GamePacketREQ
{
	List<ClanFronto> match = new ArrayList<ClanFronto>();
	short error;
	@Override
	public void readImpl()
	{
		error = ReadH();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Channel ch = client.getChannel();
		if (p != null && ch != null)
		{
			for (ClanFronto cf : ch.CLANFRONTOS.values())
			{
				if (cf.id != p.cf_id)
				{
					if (cf.sizePlayers() == 0)
					{
						ch.CLANFRONTOS.remove(cf.id);
						continue;
					}
					match.add(cf);
				}
			}
			client.sendPacket(new CLAN_WAR_MATCH_TEAM_LIST_ACK(match, error));
		}
		match = null;
	}
}