package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class PUSH_PRESENT_ITEM_ACK extends game.network.game.GamePacketACK
{
	int error;
	public PUSH_PRESENT_ITEM_ACK(int error)
	{
		super(); //516
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
	}
}