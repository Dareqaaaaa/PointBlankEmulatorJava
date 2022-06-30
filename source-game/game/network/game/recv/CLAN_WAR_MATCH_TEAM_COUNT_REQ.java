package game.network.game.recv;

import game.network.game.sent.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_WAR_MATCH_TEAM_COUNT_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Channel ch = client.getChannel();
		if (ch != null)
			client.sendPacket(new CLAN_WAR_MATCH_TEAM_COUNT_ACK(ch.CLANFRONTOS.size()));
	}
}