package game.network.auth.recv;

import game.network.auth.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_ENTER_SERVER_REQ extends game.network.auth.AuthPacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		if (!client.packets.containsKey(2678) || !client.packets.containsKey(2565) || !client.packets.containsKey(2567) || !client.packets.containsKey(2577))
			client.close();
		client.sendPacket(new BASE_GET_ENTER_SERVER_ACK());
	}
}