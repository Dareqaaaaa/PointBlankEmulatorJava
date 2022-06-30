package game.network.auth.recv;

import game.network.auth.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_CONFIG_REQ extends game.network.auth.AuthPacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = AccountSyncer.gI().get(client.pId);
		if (p != null)
		{
			client.sendPacket(new BASE_GET_URL_LIST_ACK());
			client.sendPacket(new BASE_GET_MYBOX_ACK(p));
			client.sendPacket(new BASE_GET_RANKINFO_ACK());
			client.sendPacket(new BASE_GET_CONFIG_ACK(p, 0));
		}
	}
}