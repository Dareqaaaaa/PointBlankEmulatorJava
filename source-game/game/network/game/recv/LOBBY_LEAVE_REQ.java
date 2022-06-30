package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_LEAVE_REQ extends game.network.game.GamePacketREQ
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
			c.removePlayer(p.id);
			p.gameClient.setRoomId(-1);
			p.gameClient.setChannelId(-1);
			p.slot = -1;
			client.setRoomId(-1);
			client.setChannelId(-1);
			if (p.status() > 0 && p.last_fstate != 64)
				EssencialUtil.updateFriends(p, 64, false);
		}
		client.sendPacket(new LOBBY_LEAVE_ACK());
	}
}