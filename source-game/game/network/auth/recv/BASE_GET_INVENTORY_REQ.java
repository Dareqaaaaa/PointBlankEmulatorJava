package game.network.auth.recv;

import game.network.auth.sent.BASE_GET_INVENTORY_ACK;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_INVENTORY_REQ extends game.network.auth.AuthPacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new BASE_GET_INVENTORY_ACK());
	}
}