package game.network.game.sent;

import java.util.*;

import core.enums.*;
import core.info.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_JOIN_ACK extends game.network.game.GamePacketACK
{
	Room r;
	Player leader;
	int slot_id, error;
	List<Player> jogadores;
	public ROOM_JOIN_ACK(Room r, int slot_id, int error)
	{
		super();
		this.r = r;
		this.slot_id = slot_id;
		this.error = error;
		if (error == 0 && r != null && slot_id >= 0)
		{
			jogadores = r.getPlayers();
			leader = r.getLeader();
			if (leader == null)
				error = 0x80001004;
		}
	}
	@Override
	public void writeImpl()
	{
		if (error == 0 && r != null && slot_id >= 0 && leader != null)
		{
			WriteD(r.id);
	        WriteD(slot_id);
	        WriteD(r.id);
	        WriteS(r.name, 23);
	        WriteH(r.map);
	        WriteC(r.stage4v4);
	        WriteC(r.type.ordinal());
	        WriteC(RoomState.READY.ordinal());
	        WriteC(jogadores.size());
			WriteC(r.getSlots());
			WriteC(r.ping);
	        WriteC(r.allWeapons);
	        WriteC(r.randomMap);
	        WriteC(r.special);
	        WriteS(leader.name, 33);
			WriteC(r.killMask);
			WriteC(r.redRounds + r.blueRounds);
			WriteH(r.getKillTime() * 60 - r.timeLost);
	        WriteC(r.limit);
	        WriteC(r.seeConf);
	        WriteH(r.balanceamento);
	        WriteS(r.passwd, 4);
	        WriteC(r.threadCOUNTDOWN != null ? 1 : 0); //timerstate countdown
	    	WriteD(leader.slot);
			boolean epic = Software.pc.client_rev.equals("1.15.9");
			for (RoomSlot s : r.SLOT)
			{
				Player p = AccountSyncer.gI().get(s.player_id);
				Clan c = p != null ? ck.BUSCAR_CLAN(p.clan_id) : null;
				WriteC(s.state.ordinal());
				WriteC(p != null ? p.rank() : 0);
				WriteD(p != null ? p.clan_id() : 0);
				WriteD(p != null ? p.role() : 0);
			    WriteC(c != null ? c.rank : 0);
				WriteD(c != null ? c.logo : 0xFFFFFFFF);
				WriteC(p != null ? p.vip_pccafe : 0);
				WriteC(p != null ? p.tourney_level : 0);
				WriteC(p != null ? p.effect1 : 0);
				WriteC(p != null ? p.effect2 : 0);
				WriteC(p != null ? p.effect3 : 0);
				WriteC(p != null ? p.effect4 : 0);
				WriteC(p != null ? p.effect5 : 0);
				WriteS(c != null ? c.name : "", 17);
				WriteH(0);
				if (!epic)
					WriteC(0);
				WriteC(p != null ? p.country.value : 0);
			}
			WriteC(jogadores.size());
			for (Player p : jogadores)
			{
				WriteC(p.slot);
				WriteC(p.name.length() + 1); //33
				WriteS(p.name, p.name.length() + 1);
				WriteC(p.color);
			}
			if (r.isSpecialMode())
			{
				WriteC(r.aiCount);
				WriteC(r.aiLevel);
			}
			jogadores = null;
		}
        else if (error == 0x80001005)
        {
        	WriteB(new byte[] { 4, 0, 10, 12 });
            WriteD(error);
        }
        else
        {
        	WriteD(error);
        }
	}
}