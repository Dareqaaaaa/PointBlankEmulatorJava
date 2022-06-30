package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_RECORD_INFO_DB_REQ extends game.network.game.GamePacketREQ
{
	PlayerStats s;
	@Override
	public void readImpl()
	{
		long pId = ReadQ();
		Player p = AccountSyncer.gI().get(pId);
		if (p != null && p.stats != null)
			s = p.stats;
		else
			s = db.READ_STATS(pId);
	}
	@Override
	public void runImpl()
	{
		client.sendPacket(new BASE_GET_RECORD_INFO_DB_ACK(s));
	}
}