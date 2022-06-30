package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class ALARM_COIN_ACK extends game.network.game.GamePacketACK
{
	public ALARM_COIN_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}