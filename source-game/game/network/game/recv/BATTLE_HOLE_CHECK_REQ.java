package game.network.game.recv;

import game.network.game.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_HOLE_CHECK_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new BATTLE_HOLE_CHECK_ACK());
	}
}