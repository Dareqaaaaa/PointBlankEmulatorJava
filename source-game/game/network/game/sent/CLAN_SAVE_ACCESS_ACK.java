package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_SAVE_ACCESS_ACK extends game.network.game.GamePacketACK
{
	int error;
	public CLAN_SAVE_ACCESS_ACK(int error)
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