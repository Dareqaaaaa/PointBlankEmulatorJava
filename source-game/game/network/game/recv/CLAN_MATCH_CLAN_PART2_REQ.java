package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_CLAN_PART2_REQ extends game.network.game.GamePacketREQ
{
	int partidas;
	@Override
	public void readImpl()
	{
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
	        		partidas++;
		}
		client.sendPacket(new CLAN_MATCH_CLAN_PART2_ACK(partidas));
	}
}