package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_NICK_CHECK_ACK extends game.network.game.GamePacketACK 
{
	int error;
	public INVENTORY_NICK_CHECK_ACK(int error)
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