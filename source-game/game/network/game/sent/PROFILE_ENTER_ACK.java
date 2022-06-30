package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class PROFILE_ENTER_ACK extends game.network.game.GamePacketACK
{
	public PROFILE_ENTER_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}