package game.network.game.sent;

import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_GIVEUPBATTLE_ACK extends game.network.game.GamePacketACK
{
	Room r;
	int old_leader, leader;
	public BATTLE_GIVEUPBATTLE_ACK(Room r, int old_leader, int leader)
	{
		super();
		this.r = r;
		this.old_leader = old_leader;
		this.leader = leader;
	}
	@Override
	public void writeImpl()
	{
		WriteD(leader);
		for (RoomSlot slot : r.SLOT)
		{
			Player p = AccountSyncer.gI().get(slot.player_id);
			WriteB(slot.id != old_leader && p != null && p.gameClient != null ? p.IP() : new byte[4]);
			WriteH(slot.id != old_leader && p != null && p.gameClient != null ? p.gameClient.SECURITY_KEY : 0);
			WriteB(slot.id != old_leader && p != null && p.gameClient != null ? p.localhost : new byte[4]);
			WriteH(slot.id != old_leader && p != null && p.gameClient != null ? p.gameClient.SECURITY_KEY : 0);
			WriteC(0);
		}
	}
}