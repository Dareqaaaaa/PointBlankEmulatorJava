package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.utils.AccountSyncer;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_LEAVEP2PSERVER_REQ extends game.network.game.GamePacketREQ
{
	public boolean remove = false, escape = true;
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Room r = client.getRoom();
		Player p = client.getPlayer();
		if (r != null && p != null)
		{
			RoomSlot s = r.getSlotByPID(p.id);
			if (s != null && s.state.ordinal() > 8)
			{
				if (s.respawn == 0)
					escape = false;
				r.resetSlotPlayer(s, remove, escape);
				if (r.votekick != null && r.threadVOTE != null && r.votekick.victimIdx == s.id)
				{
					VOTEKICK_LEAVE_ACK sent = new VOTEKICK_LEAVE_ACK(); sent.packingBuffer();
					for (RoomSlot slot : r.SLOT)
					{
						Player m = AccountSyncer.gI().get(slot.player_id);
						if (m != null && slot.state == SlotState.BATTLE)
							m.gameClient.sendPacketBuffer(sent.buffer);
			        }
					sent.buffer = null; sent = null;
					r.stopTask("votekick");
				}
				client.sendPacket(new BATTLE_ENDBATTLE_ACK(p, r, 0, 0, 0, new byte[144]));
				int players = 0;
				BATTLE_LEAVEP2PSERVER_ACK sent = new BATTLE_LEAVEP2PSERVER_ACK(p, s.id, client.LEAVEP2P); sent.packingBuffer();
				for (RoomSlot slot : r.SLOT)
				{
					Player m = AccountSyncer.gI().get(slot.player_id);
					if (m != null && slot.state.ordinal() >= 11)
					{
						players++;
						m.gameClient.sendPacketBuffer(sent.buffer);
					}
				}
				sent.buffer = null; sent = null;
				r.checkBattlePlayers(s.id);
				r.changeHost(players, s.id);
				r.updateInfo();
			}
		}
	}
}