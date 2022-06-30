package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_PLAYER_INFO_REQ extends game.network.game.GamePacketREQ
{
	PlayerStats s;
	@Override
	public void readImpl()
	{
		try
		{
			Channel ch = client.getChannel();
			if (ch != null)
			{
				Player p = ch.getPlayerLobby(ReadD());
				if (p != null)
					s = p.stats;
			}
		}
		catch (Exception e)
		{
		}
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new LOBBY_PLAYER_INFO_ACK(s));
	}
}