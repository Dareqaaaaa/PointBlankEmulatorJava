package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class TITLE_USE_ACK extends game.network.game.GamePacketACK
{
	int error;
	public TITLE_USE_ACK(int error)
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