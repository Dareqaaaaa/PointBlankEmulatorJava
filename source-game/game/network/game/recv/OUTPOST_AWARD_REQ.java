package game.network.game.recv;

import game.network.game.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class OUTPOST_AWARD_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new OUTPOST_AWARD_ACK(0));
	}
}