package game.network.auth.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_PUSH_PRESENT_ITEM_ACK extends game.network.auth.AuthPacketACK
{
	int error;
	public BASE_PUSH_PRESENT_ITEM_ACK(int error)
	{
		super();
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
	}
}