package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_LEAVE_ACK extends game.network.game.GamePacketACK
{
	public LOBBY_LEAVE_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}