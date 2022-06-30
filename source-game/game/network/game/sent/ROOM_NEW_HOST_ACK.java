package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_NEW_HOST_ACK extends game.network.game.GamePacketACK
{
	int slot;
	public ROOM_NEW_HOST_ACK(int slot)
	{
		super();
		this.slot = slot;
	}
	@Override
	public void writeImpl()
	{
		WriteD(slot);
	}
}