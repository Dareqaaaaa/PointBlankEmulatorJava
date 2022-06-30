package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_ENTER_ACK extends game.network.game.GamePacketACK
{
	int error;
	public LOBBY_ENTER_ACK(int error)
	{
		super();
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		WriteD(1);
		WriteD(0);
		WriteC(0);
		WriteD(0);
	}
}