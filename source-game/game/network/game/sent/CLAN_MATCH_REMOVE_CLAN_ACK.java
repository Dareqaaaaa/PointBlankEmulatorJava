package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_REMOVE_CLAN_ACK extends game.network.game.GamePacketACK
{
	int error;
	public CLAN_MATCH_REMOVE_CLAN_ACK(int error)
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