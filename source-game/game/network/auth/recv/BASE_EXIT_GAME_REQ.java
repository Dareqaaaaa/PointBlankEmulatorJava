package game.network.auth.recv;

import game.network.auth.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_EXIT_GAME_REQ extends game.network.auth.AuthPacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new BASE_EXIT_GAME_ACK());
	}
}