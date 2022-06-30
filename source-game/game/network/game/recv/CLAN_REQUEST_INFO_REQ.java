package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_REQUEST_INFO_REQ extends game.network.game.GamePacketREQ
{
	long pId;
	@Override
	public void readImpl()
	{
		pId = ReadQ();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null && p.clan_id != 0)
			client.sendPacket(new CLAN_REQUEST_INFO_ACK(pId, p.clan_id));
	}
}