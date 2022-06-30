package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MEMBER_INFO_DELETE_ACK extends game.network.game.GamePacketACK
{
	long pId;
	public CLAN_MEMBER_INFO_DELETE_ACK(long pId)
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