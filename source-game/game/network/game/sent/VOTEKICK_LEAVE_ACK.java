package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class VOTEKICK_LEAVE_ACK extends game.network.game.GamePacketACK
{
	public VOTEKICK_LEAVE_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteC(0);
	}
}