package game.network.game.recv;

import game.network.game.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_ANTIGAME_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new LOBBY_ANTIGAME_ACK(0));
	}
}