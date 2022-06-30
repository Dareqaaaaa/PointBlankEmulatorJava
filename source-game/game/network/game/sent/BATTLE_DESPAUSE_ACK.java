package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_DESPAUSE_ACK extends game.network.game.GamePacketACK
{
	int type;
	public BATTLE_DESPAUSE_ACK(int type)
	{
		super();
		this.type = type;
	}
	@Override
	public void writeImpl()
	{
		WriteD(type);
	}
}