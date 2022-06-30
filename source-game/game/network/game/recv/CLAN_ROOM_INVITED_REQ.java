package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_ROOM_INVITED_REQ  extends game.network.game.GamePacketREQ
{
	Player p;
	@Override
	public void readImpl()
	{
		p = AccountSyncer.gI().get(ReadQ());
	}
	@Override
	public void runImpl()
	{
		if (p != null && p.gameClient != null)
			p.gameClient.sendPacket(new CLAN_ROOM_INVITED_RESULT_ACK(client.pId));
	}
}