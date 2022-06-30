package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class ATTENDANCE_REWARD_ACK extends game.network.game.GamePacketACK
{
	int error;
	public ATTENDANCE_REWARD_ACK(int error)
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