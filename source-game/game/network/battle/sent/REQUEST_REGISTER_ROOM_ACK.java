package game.network.battle.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_REGISTER_ROOM_ACK extends game.network.battle.BattlePacketACK
{
	Room r;
	int slot;
	public REQUEST_REGISTER_ROOM_ACK(Room r, int slot)
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
		WriteH(r.map);
		WriteC(r.type.ordinal());
		WriteD(slot);
	}
}