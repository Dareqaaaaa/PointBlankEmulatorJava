package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class AUTH_GET_REMAIN_MONEY_ACK extends game.network.game.GamePacketACK
{
	Player p;
	public AUTH_GET_REMAIN_MONEY_ACK(Player p)
	{
		super();
		this.p = p;
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
        WriteD(p.gold);
        WriteD(p.cash);
	}
}