package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_CLOSE_SLOT_ACK extends game.network.game.GamePacketACK
{
	int error;
	public ROOM_CLOSE_SLOT_ACK(int error)
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