package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_USER_ENTER_ACK extends game.network.game.GamePacketACK
{
	int error;
	public BASE_USER_ENTER_ACK(int error)
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