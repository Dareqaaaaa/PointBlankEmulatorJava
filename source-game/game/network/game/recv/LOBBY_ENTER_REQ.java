package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_ENTER_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Channel c = client.getChannel();
		Room r = client.getRoom();
		if (p != null && c != null)
		{
			if (r != null)
				r.removePlayer(p, p.slot, false, true, false);
			p.gameClient.setRoomId(-1);
			client.setRoomId(-1);
			p.slot = -1;
			c.addPlayer(p);
			if (p.status() > 0 && p.last_fstate != 80)
				EssencialUtil.updateFriends(p, 80, false);
		}
		client.sendPacket(new LOBBY_ENTER_ACK(0));
	}
}