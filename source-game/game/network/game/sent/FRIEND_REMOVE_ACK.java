package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class FRIEND_REMOVE_ACK extends game.network.game.GamePacketACK
{
	int error;
	public FRIEND_REMOVE_ACK(int error)
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