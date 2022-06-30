package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_MISSION_BOMB_UNINSTALL_ACK extends game.network.game.GamePacketACK
{
	int slot;
	public BATTLE_MISSION_BOMB_UNINSTALL_ACK(int slot)
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