package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_FIND_PLAYER_REQ extends game.network.game.GamePacketREQ
{
	long pId;
	@Override
	public void readImpl()
	{
		String owner = (ReadS(33).replace(" ", "").trim());
		if (owner.length() > 0 && owner != "" && owner != " " && !owner.isEmpty())
		{
			Player p = AccountSyncer.gI().get(owner);
			if (p != null)
				pId = p.id;
			else
				pId = db.playerExist(owner);
		}
	}
	@Override
	public void runImpl()
	{
		try
		{
			client.sendPacket(new BASE_FIND_PLAYER_ACK(pId, pId > 0 ? 0 : 0x80001803)); //0x80001070 | offline: 0x80001804
		}
    	catch (Exception e)
		{
    		client.sendPacket(new BASE_FIND_PLAYER_ACK(0, 0x8000106F));
    	}
	}
}