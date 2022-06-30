package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_READYBATTLE_ERROR_ACK extends game.network.game.GamePacketACK
{
	int error;
	public BATTLE_READYBATTLE_ERROR_ACK(int error)
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