package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class SERVER_MESSAGE_KICK_PLAYER_ACK extends game.network.game.GamePacketACK
{
	public SERVER_MESSAGE_KICK_PLAYER_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteC(0);
	}
}