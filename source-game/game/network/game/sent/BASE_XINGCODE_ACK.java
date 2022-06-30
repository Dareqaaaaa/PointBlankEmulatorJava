package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_XINGCODE_ACK extends game.network.game.GamePacketACK
{
	public BASE_XINGCODE_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteB(new byte[512]);
	}
}