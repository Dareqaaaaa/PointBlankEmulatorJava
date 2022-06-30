package game.network.auth.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_EXIT_GAME_ACK extends game.network.auth.AuthPacketACK
{
	public BASE_EXIT_GAME_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}