package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_CLAN_PART_REQ extends game.network.game.GamePacketREQ
{
	List<ClanFronto> list = new ArrayList<ClanFronto>();
	int error;
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
	        for (ClanFronto m : ch.CLANFRONTOS.values())
	        	if (m.clan_id == p.clan_id)
	        		list.add(m);
		}
		client.sendPacket(new CLAN_MATCH_CLAN_PART_ACK(list, error));
		list = null;
	}
}