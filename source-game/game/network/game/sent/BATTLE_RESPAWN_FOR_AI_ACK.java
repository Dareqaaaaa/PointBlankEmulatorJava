package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_RESPAWN_FOR_AI_ACK extends game.network.game.GamePacketACK
{
	int slot;
	public BATTLE_RESPAWN_FOR_AI_ACK(int slot)
	{
		super();
		this.slot = slot;
	}
	@Override
	public void writeImpl()
	{
		WriteD(slot);
	}
}