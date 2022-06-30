package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class SERVER_MESSAGE_FRIEND_INVITED extends game.network.game.GamePacketACK
{
	public SERVER_MESSAGE_FRIEND_INVITED()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteC(0);
	}
}