package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class SERVER_MESSAGE_GAMEGUARD_ERROR extends game.network.game.GamePacketACK
{
	public SERVER_MESSAGE_GAMEGUARD_ERROR()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}