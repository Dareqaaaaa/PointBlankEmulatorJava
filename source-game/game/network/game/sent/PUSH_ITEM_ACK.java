package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class PUSH_ITEM_ACK extends game.network.game.GamePacketACK
{
	public PUSH_ITEM_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}