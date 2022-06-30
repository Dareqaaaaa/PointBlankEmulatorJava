package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class VOTEKICK_UPDATE_ERROR_ACK extends game.network.game.GamePacketACK
{
	int error;
	public VOTEKICK_UPDATE_ERROR_ACK(int error)
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