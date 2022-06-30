package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class OUTPOST_LEAVE_ACK extends game.network.game.GamePacketACK
{
	public OUTPOST_LEAVE_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}