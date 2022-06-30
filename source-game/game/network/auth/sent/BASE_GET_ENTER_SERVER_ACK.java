package game.network.auth.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_ENTER_SERVER_ACK extends game.network.auth.AuthPacketACK
{
	public BASE_GET_ENTER_SERVER_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}