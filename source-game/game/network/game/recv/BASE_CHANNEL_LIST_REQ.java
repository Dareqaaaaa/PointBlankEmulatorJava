package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_CHANNEL_LIST_REQ extends game.network.game.GamePacketREQ
{
	int error, observing;
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
			observing = p.observing();
		client.sendPacket(new BASE_CHANNEL_LIST_ACK(client.getServerId()));
		client.sendPacket(new BASE_SCHANNEL_UPDATE_ACK(observing));
	}
}