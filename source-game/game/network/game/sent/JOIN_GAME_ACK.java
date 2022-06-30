package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class JOIN_GAME_ACK extends game.network.game.GamePacketACK
{
	public JOIN_GAME_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}