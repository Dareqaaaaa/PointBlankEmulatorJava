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

public class BATTLE_SENDPING_REQ extends game.network.game.GamePacketREQ
{
	Room r;
	Player leader;
	int players;
	@Override
	public void readImpl()
	{
		r = client.getRoom();
		if (r != null && r.rstate == RoomState.BATTLE)
		{
			leader = r.getLeader();
			if (leader != null)
			{
				byte[] ping = ReadB(16);
				for (RoomSlot slot : r.SLOT)
				{
					slot.ping = ping[slot.id];
					if (slot.id == leader.slot)
						r.ping = slot.ping;
				}
			}
		}
	}
	@Override
	public void runImpl()
	{
		if (leader != null)
		{
			SlotState state_lider = r.getSlotState(leader.slot);
			if (state_lider.ordinal() < 9)
			{
				client.sendPacket(new SERVER_MESSAGE_KICK_BATTLE_ACK(BattleErrorMessage.EVENT_ERROR_NO_REAL_IP, r, r.getSlotByPID(client.pId)));
				return;
			}
			BATTLE_SENDPING_ACK sent = new BATTLE_SENDPING_ACK(r); sent.packingBuffer();
			for (RoomSlot slot : r.SLOT)
			{
				Player m = AccountSyncer.gI().get(slot.player_id);
				if (m != null)
				{
					if (slot.state == SlotState.BATTLE)
						m.gameClient.sendPacketBuffer(sent.buffer);
					else
						players++;
				}
			}
			sent.buffer = null; sent = null;
			if (players == 0)
				r.startBattle(r.isSpecialMode());
			/*if (r.rstate == RoomState.BATTLE && (red > 0 || blue > 0))
			{
				r.startBattle(specialMode);
			}
			else
			{
				if (specialMode)
				{
					if (r.rstate == RoomState.PRE_BATTLE || (red + blue) == 0)
						r.startBattle(specialMode);
				}
				else
				{
					if (ch.type == ChannelServerEnum.CHANNEL_TYPE_CLAN)
					{
						if ((red + blue) == 0)
							r.startBattle(specialMode);
					}
					else
					{
						if ((preparedRed > 0 && preparedBlue > 0) || (red + blue) == 0)
							r.startBattle(specialMode);
					}
				}
			}*/
		}
	}
}