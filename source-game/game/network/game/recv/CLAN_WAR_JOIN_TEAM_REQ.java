package game.network.game.recv;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_WAR_JOIN_TEAM_REQ  extends game.network.game.GamePacketREQ
{
	protected int indexOf, channel;
	@Override
	public void readImpl()
	{
		ReadC();
		indexOf = ReadC();
		channel = ReadH();
		ReadC();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Channel c = client.getChannel();
		if (p != null && c != null)
		{
		}
	}
}