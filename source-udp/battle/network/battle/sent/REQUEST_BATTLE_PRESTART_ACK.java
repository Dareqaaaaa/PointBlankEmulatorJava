package battle.network.battle.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_BATTLE_PRESTART_ACK extends battle.network.battle.BattlePacketACK
{
	Room r;
	int slot;
	public REQUEST_BATTLE_PRESTART_ACK(Room r, int slot)
	{
		super();
		this.r = r;
		this.slot = slot;
	}
	@Override
	public void writeImpl()
	{
		WriteD(r.id);
		WriteD(r.channel_id);
		WriteD(r.server_id);
		WriteD(slot);
	}
}