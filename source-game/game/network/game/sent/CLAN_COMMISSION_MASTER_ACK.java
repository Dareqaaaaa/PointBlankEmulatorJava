package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_COMMISSION_MASTER_ACK extends game.network.game.GamePacketACK
{
	int error;
	public CLAN_COMMISSION_MASTER_ACK(int error)
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