package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class VOTEKICK_KICK_ACK extends game.network.game.GamePacketACK
{
	public VOTEKICK_KICK_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteC(0);
	}
}