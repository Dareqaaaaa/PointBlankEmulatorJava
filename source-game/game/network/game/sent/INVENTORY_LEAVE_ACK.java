package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_LEAVE_ACK extends game.network.game.GamePacketACK
{
	int type;
	public INVENTORY_LEAVE_ACK(int type)
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