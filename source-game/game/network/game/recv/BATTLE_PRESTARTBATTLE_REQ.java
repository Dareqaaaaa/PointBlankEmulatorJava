package game.network.game.recv;

import game.network.battle.*;
import game.network.game.sent.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_PRESTARTBATTLE_REQ extends game.network.game.GamePacketREQ
{
	int map, stage4v4;
	ModesEnum type;
	@Override
	public void readImpl()
	{
		map = ReadH();
		stage4v4 = ReadC();
		type = ModesEnum.values()[ReadC()];
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			Room r = client.getRoom();
			if (r != null && r.map == map && r.stage4v4 == stage4v4 && r.type == type)
			{
				RoomSlot s = r.getSlotByPID(p.id);
				if (s != null && s.state == SlotState.RENDEZVOUS && r.rstate.ordinal() >= 2)
				{
					Player leader = r.getLeader();
					if (leader != null && leader.gameClient != null && !(_IP(p, false).length == 0 || _IP(p, false) == new byte[4] || _IP(p, true).length == 0 || _IP(p, true) == new byte[4] || _IP(leader, false).length == 0 || _IP(leader, false) == new byte[4] || _IP(leader, true).length == 0 || _IP(leader, true) == new byte[4]))
					{
						BattleManager.gI().addPlayer(r, s.id);
						int seed = r.getSeed();
						boolean isLeader = p.id == leader.id;
						if (isLeader)
						{
							r.rstate = RoomState.PRE_BATTLE;
							r.updateBInfo();
						}
						//calculehit
						r.changeState(p, SlotState.PRESTART, true);
						client.sendPacket(new BATTLE_PRESTARTBATTLE_ACK(r, p, s.id, true, r.rstate.ordinal() >= 2 ? 1 : 0, seed));
						if (!isLeader)
							leader.gameClient.sendPacket(new BATTLE_PRESTARTBATTLE_ACK(r, p, s.id, false, r.rstate.ordinal() >= 2 ? 1 : 0, seed));
						s.espectador = r.isGhostMode(p.espectador);
						s.death = s.espectador;
					}
					else
					{
						client.sendPacket(new SERVER_MESSAGE_KICK_BATTLE_ACK(BattleErrorMessage.EVENT_ERROR_FIRST_HOLE, r, s));
					}
				}
				else
				{
					r.changeStateInfo(s, SlotState.NORMAL, true);
					r.checkBattlePlayers(p.slot);
					r.changeHost(0, p.slot);
					client.sendPacket(new BATTLE_STARTBATTLE_ACK(null, null, 0, 0, 0, 0, null));
				}
			}
			else
			{
				client.sendPacket(new SERVER_MESSAGE_KICK_BATTLE_ACK(BattleErrorMessage.EVENT_ERROR_FIRST_MAINLOAD, r, r != null ? r.getSlotByPID(client.pId) : null));
				client.sendPacket(new BATTLE_PRESTARTBATTLE_ACK(null, null, 0, false, 0, 0));
				if (r == null)
					client.sendPacket(new LOBBY_ENTER_ACK(0));
			}
		}
	}
	byte[] _IP(Player p, boolean localhost)
	{
		return localhost ? p.localhost : p.IP();
	}
}