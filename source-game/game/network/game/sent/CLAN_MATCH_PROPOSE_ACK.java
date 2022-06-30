package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_PROPOSE_ACK extends game.network.game.GamePacketACK
{
	public CLAN_MATCH_PROPOSE_ACK()
	{
		super();
	}
	@Override
	public void writeImpl()
	{
		WriteD(0);
	}
}