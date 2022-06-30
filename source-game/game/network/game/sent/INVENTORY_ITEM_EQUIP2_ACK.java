package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_ITEM_EQUIP2_ACK extends game.network.game.GamePacketACK
{
	long object;
	int error;
	public INVENTORY_ITEM_EQUIP2_ACK(long object, int error)
	{
		super();
		this.object = object;
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
        WriteD(error);
        if (error == 1)
        {
            WriteD(date.getDateTime());
            WriteQ(object);
        }
	}
}