package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class FRIEND_ROOM_INVITE_ERROR_ACK extends game.network.game.GamePacketACK
{
	int error;
	public FRIEND_ROOM_INVITE_ERROR_ACK(int error)
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