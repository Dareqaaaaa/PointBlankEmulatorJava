package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class AUTH_SHOP_AUTH_ITEMS_ACK extends game.network.game.GamePacketACK
{
	GoodList list;
	public AUTH_SHOP_AUTH_ITEMS_ACK(GoodList list)
	{
		super();
		this.list = list;
	}
	@Override
	public void writeImpl()
	{
		WriteD(list.total);
		WriteD(list.count);
		WriteD(list.indexof);
		WriteB(list.buffer);
		WriteD(list.value);
	}
}