package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class FRIEND_INVITE_ACK extends game.network.game.GamePacketACK
{
	int error;
	public FRIEND_INVITE_ACK(int error)
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