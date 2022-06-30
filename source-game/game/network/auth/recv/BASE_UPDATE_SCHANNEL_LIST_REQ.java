package game.network.auth.recv;

import game.network.auth.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_UPDATE_SCHANNEL_LIST_REQ extends game.network.auth.AuthPacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new BASE_UPDATE_SCHANNEL_LIST_ACK(null));
	}
}