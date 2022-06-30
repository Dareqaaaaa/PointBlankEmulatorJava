package game.network.auth.recv;

import core.model.*;
import core.utils.*;
import game.network.auth.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_MYINFO_REQ extends game.network.auth.AuthPacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = AccountSyncer.gI().get(client.pId);
		client.sendPacket(new BASE_GET_MYINFO_ACK(p));
		client.sendPacket(new BASE_UPDATE_SCHANNEL_LIST_ACK(p));
	}
}