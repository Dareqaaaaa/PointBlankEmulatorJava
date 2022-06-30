package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class PLAYER_EXIT_GAME_ACK extends game.network.game.GamePacketACK
{
	public PLAYER_EXIT_GAME_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}