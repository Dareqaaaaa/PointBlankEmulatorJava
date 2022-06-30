package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_ITEM_DELETE_ACK extends game.network.game.GamePacketACK
{
	long object;
	int error;
	public INVENTORY_ITEM_DELETE_ACK(long object, int itemId, int error, Player p)
	{
		super();
		this.object = object;
		this.error = error;
		if (p != null && error == 1)
			p.deleteItem(object, itemId);
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		if (error == 1)
			WriteQ(object);
	}
}