package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_PAUSE_ACK extends game.network.game.GamePacketACK
{
	int type;
	public BATTLE_PAUSE_ACK(int type)
	{
		super();
		this.type = type;
	}
	@Override
	public void writeImpl()
	{
		WriteD(type);
		if (type == 0)
			WriteD(1);
	}
}