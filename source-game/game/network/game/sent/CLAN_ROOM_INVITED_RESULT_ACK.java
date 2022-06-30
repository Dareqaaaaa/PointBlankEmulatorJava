package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_ROOM_INVITED_RESULT_ACK extends game.network.game.GamePacketACK
{
	long pId;
	public CLAN_ROOM_INVITED_RESULT_ACK(long pId)
	{
		super();
		this.pId = pId;
	}
	@Override
	public void writeImpl()
	{
		WriteQ(pId);
	}
}