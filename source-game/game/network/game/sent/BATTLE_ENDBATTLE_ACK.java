package game.network.game.sent;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_ENDBATTLE_ACK extends game.network.game.GamePacketACK
{
	Player p; Room r; Clan c;
	int slot_player, slot_mission, winner;
	byte[] array;
	public BATTLE_ENDBATTLE_ACK(Player p, Room r, int slot_player, int slot_mission, int winner, byte[] array)
	{
		super();
		this.p = p;
		this.r = r;
		this.slot_player = slot_player;
		this.slot_mission = slot_mission;
		this.winner = winner;
		this.array = array;
		c = ck.BUSCAR_CLAN(p != null ? p.clan_id : 0);
	}
	@Override
	public void writeImpl()
	{
		WriteC(winner);
		WriteH(slot_player);
		WriteH(slot_mission);
		WriteB(array);
		WriteS(p.name, 33);
		WriteD(p.exp);
		WriteD(p.rank);
		WriteD(p.rank());
		WriteD(p.gold);
		WriteD(p.cash);
		WriteD(p.clan_id());
		WriteD(p.role());
		WriteQ(p.status());
		WriteC(p.vip_pccafe);
		WriteC(p.tourney_level);
		WriteC(p.color);
		WriteS(c != null ? c.name : "", 17);
		WriteC(c != null ? c.rank : 0);
		WriteC(c != null ? c.nivel().ordinal() : 0);
		WriteD(c != null ? c.logo : 0xFFFFFFFF);
		WriteC(c != null ? c.color : 0);
		WriteD(10000);
		WriteC(0);
		WriteD(p.remaining());
		WriteD(p.lastup());
		WriteD(p.stats.partidas);
		WriteD(p.stats.ganhou);
		WriteD(p.stats.perdeu);
		WriteD(p.stats.empatou);
		WriteD(p.stats.matou);
		WriteD(p.stats.headshots);
		WriteD(p.stats.morreu);
		WriteD(p.stats.partidas);
		WriteD(p.stats.matou);
		WriteD(p.stats.kitou);
		WriteD(p.stats.partidas);
		WriteD(p.stats.ganhou);
		WriteD(p.stats.perdeu);
		WriteD(p.stats.empatou);
		WriteD(p.stats.matou);
		WriteD(p.stats.headshots);
		WriteD(p.stats.morreu);
		WriteD(p.stats.partidas);
		WriteD(p.stats.matou);
		WriteD(p.stats.kitou);
		if (r.isSpecialMode())
		{
			for (RoomSlot slot : r.SLOT)
		    	WriteH(slot.score);
		}
		else if (r.isRoundMap() || r.type == ModesEnum.DINO || r.type == ModesEnum.CROSSCOUNTER)
		{
			WriteH(r.teamRound(0));
			WriteH(r.teamRound(1));
			for (RoomSlot slot : r.SLOT)
		    	WriteC(slot.objetivo);
		}
		WriteC(0);
		WriteD(0);
		WriteB(new byte[16]);
	}
}