package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.udp.events.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_STARTBATTLE_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Room r = client.getRoom();
		Player p = client.getPlayer();
		if (p != null && r != null && r.rstate.ordinal() >= 2)
		{
			RoomSlot s = r.getSlotByPID(p.id);
			if (s != null && s.state == SlotState.PRESTART)
			{
				boolean specialMode = r.isSpecialMode(); 
				if (!specialMode)
				{
					try
					{
						p.minutosJogados = date.getTimeHasPlay();
					}
					catch (Exception e)
					{
						p.minutosJogados = null;
					}
				}
				client.sendPacket(new ROOM_CHANGE_ROOMINFO_ACK(r, r.LIDER == p.id ? p.name : "", specialMode));
				r.changeState(s, SlotState.BATTLE_READY, false);
				if (setting.udp == UDP_Type.SERVER_UDP_STATE_RENDEZVOUS)
					s.battleAccept = true;
				Player leader = null;
				ROOM_GET_SLOTS_ACK sent = new ROOM_GET_SLOTS_ACK(r); sent.packingBuffer();
				SlotState state_lider = SlotState.RENDEZVOUS;
				int red = 0, blue = 0, redBattle = 0, blueBattle = 0;
				for (RoomSlot slot : r.SLOT)
				{
					Player m = AccountSyncer.gI().get(slot.player_id);
					if (m != null)
					{
						m.gameClient.sendPacketBuffer(sent.buffer);
						if (m.id == r.LIDER)
						{
							leader = m;
							state_lider = slot.state;
						}
						if (slot.id % 2 == 0)
							red++;
						else
							blue++;
						if (slot.state == SlotState.BATTLE)
						{
							if (slot.id % 2 == 0)
								redBattle++;
							else
								blueBattle++;
						}
					}
				}
				sent.buffer = null; sent = null;
				if (r.rstate == RoomState.BATTLE || ((state_lider == SlotState.BATTLE & specialMode) && ((leader.slot % 2 == 0 && redBattle > red / 2) || (leader.slot % 2 == 1 && blueBattle > blue / 2))) || (state_lider == SlotState.BATTLE && ((blueBattle > blue / 2 && redBattle > red / 2))))
					r.startBattle(specialMode);
			}
			else
			{
				client.sendPacket(new SERVER_MESSAGE_KICK_BATTLE_ACK(BattleErrorMessage.EVENT_ERROR_FIRST_HOLE, r, s));
			}
		}
		else
		{
			client.sendPacket(new SERVER_MESSAGE_KICK_BATTLE_ACK(BattleErrorMessage.EVENT_ERROR_FIRST_HOLE, r, r != null ? r.getSlotByPID(client.pId) : null));
			client.sendPacket(new BATTLE_STARTBATTLE_ACK(null, null, 0, 0, 0, 0, null));
			if (r == null)
				client.sendPacket(new LOBBY_ENTER_ACK(0));
		}
	}
}