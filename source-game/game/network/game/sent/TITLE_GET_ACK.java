package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class TITLE_GET_ACK extends game.network.game.GamePacketACK
{
	int error, slot;
	public TITLE_GET_ACK(int error, int slot)
	{
		super();
		this.error = error;
		this.slot = slot;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);	
		WriteD(slot);	
	}
}