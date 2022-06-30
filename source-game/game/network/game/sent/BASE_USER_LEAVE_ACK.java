package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_USER_LEAVE_ACK extends game.network.game.GamePacketACK
{
	public BASE_USER_LEAVE_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}