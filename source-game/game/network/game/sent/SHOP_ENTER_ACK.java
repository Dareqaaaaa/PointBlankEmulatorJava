package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class SHOP_ENTER_ACK extends game.network.game.GamePacketACK
{
	public SHOP_ENTER_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(date.getDateTime());
	}
}