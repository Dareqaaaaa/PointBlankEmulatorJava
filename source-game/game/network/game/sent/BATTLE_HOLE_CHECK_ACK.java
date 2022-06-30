package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_HOLE_CHECK_ACK extends game.network.game.GamePacketACK
{
	public BATTLE_HOLE_CHECK_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}