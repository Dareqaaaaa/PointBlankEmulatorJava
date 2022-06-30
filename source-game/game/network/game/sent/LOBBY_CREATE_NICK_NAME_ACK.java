package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_CREATE_NICK_NAME_ACK extends game.network.game.GamePacketACK
{
	int error;
	public LOBBY_CREATE_NICK_NAME_ACK(int error)
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