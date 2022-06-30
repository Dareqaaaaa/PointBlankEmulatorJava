package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_UNREADY_4VS4_ACK extends game.network.game.GamePacketACK
{
	int error;
	public ROOM_UNREADY_4VS4_ACK(int error)
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