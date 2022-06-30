package game.network.battle.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_REMOVE_PLAYER_ACK extends game.network.battle.BattlePacketACK
{
	Room r;
	int slot;
	public REQUEST_REMOVE_PLAYER_ACK(Room r, int slot)
	{
		super();
		this.r = r;
		this.slot = slot;
	}
	@Override
	public void writeImpl()
	{
		WriteD(r.index);
		WriteD(slot);
	}
}