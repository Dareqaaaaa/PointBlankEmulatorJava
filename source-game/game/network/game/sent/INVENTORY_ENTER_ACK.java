package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_ENTER_ACK extends game.network.game.GamePacketACK
{
	public INVENTORY_ENTER_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(date.getDateTime()); 
	}
}