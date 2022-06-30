package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class FRIEND_ROOM_INVITE_ACK extends game.network.game.GamePacketACK
{
	int indexOf;
	public FRIEND_ROOM_INVITE_ACK(int indexOf)
	{
		super();
		this.indexOf = indexOf;
	}
	@Override
	public void writeImpl()
	{
	    WriteC(indexOf);
	}
}