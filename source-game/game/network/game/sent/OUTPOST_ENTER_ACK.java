package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class OUTPOST_ENTER_ACK extends game.network.game.GamePacketACK
{
	public OUTPOST_ENTER_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
		WriteC(0);
		WriteC(0);
		WriteC(0);
		WriteB(new byte[340]);
	}
}