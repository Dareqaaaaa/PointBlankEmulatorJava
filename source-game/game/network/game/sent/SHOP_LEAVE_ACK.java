package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class SHOP_LEAVE_ACK extends game.network.game.GamePacketACK
{
	int type;
	public SHOP_LEAVE_ACK(int type)
	{
		super();
		this.type = type;
	}
	@Override
	public void writeImpl()
	{
		WriteD(type);
		WriteD(0);
	}
}