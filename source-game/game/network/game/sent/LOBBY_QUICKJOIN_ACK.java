package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_QUICKJOIN_ACK extends game.network.game.GamePacketACK
{
	int error;
	public LOBBY_QUICKJOIN_ACK(int error)
	{
		super();
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
	}
}