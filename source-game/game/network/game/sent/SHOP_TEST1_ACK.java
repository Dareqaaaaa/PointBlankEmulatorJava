package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class SHOP_TEST1_ACK extends game.network.game.GamePacketACK
{
	public SHOP_TEST1_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
		WriteD(0);
		WriteD(0);
		WriteD(44);
	}
}