package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_TIMERSYNC_ACK extends game.network.game.GamePacketACK
{
	public BATTLE_TIMERSYNC_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}