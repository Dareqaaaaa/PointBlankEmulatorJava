package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class AUTH_GET_REMAIN_MONEY_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			//p.gold = db.returnQueryValueI("SELECT gold FROM jogador WHERE id='" + p.id + "'", "gold");
			//p.cash = db.returnQueryValueI("SELECT cash FROM jogador WHERE id='" + p.id + "'", "cash");
			client.sendPacket(new AUTH_GET_REMAIN_MONEY_ACK(p));
		}
	}
}