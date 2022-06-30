package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_ACCOUNT_LOG_ROOM_ACK extends game.network.game.GamePacketACK
{
	long pId;
	public BASE_ACCOUNT_LOG_ROOM_ACK(long pId)
	{
		super();
		this.pId = pId;
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
		WriteQ(pId);
	}
}