package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_SCHANNEL_UPDATE_REQ extends game.network.game.GamePacketREQ
{
	int observing;
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
		client.sendPacket(new BASE_SCHANNEL_UPDATE_ACK(observing));
	}
}