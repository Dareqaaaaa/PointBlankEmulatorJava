package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_REMOVE_INVITES_ACK extends game.network.game.GamePacketACK
{
	int error;
	public CLAN_REMOVE_INVITES_ACK(int error)
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