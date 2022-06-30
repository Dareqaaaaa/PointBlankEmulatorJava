package game.network.game.recv;

import game.network.game.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_CREATE_REQUESITES_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new CLAN_CREATE_REQUESITES_ACK());
	}
}