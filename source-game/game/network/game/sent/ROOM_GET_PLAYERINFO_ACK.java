package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_GET_PLAYERINFO_ACK extends game.network.game.GamePacketACK
{	
	Player p;
	Clan c;
	int slot;
	public ROOM_GET_PLAYERINFO_ACK(Room r, int slot)
	{
		super();
		this.slot = slot;
		if (r != null)
		{
			p = r.getPlayerBySlot(slot);
			c = ck.BUSCAR_CLAN(p != null ? p.clan_id : 0);
		}
	}
	@Override
	public void writeImpl()
	{
		WriteD(slot);
		if (p != null)
		{
			WriteS(p.name, 33);
			WriteD(p.exp);
			WriteD(p.rank());
			WriteD(p.rank);
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
			WriteD(p.equipment.char_red);
			WriteD(p.equipment.char_blue);
			WriteD(p.equipment.char_head);
			WriteD(p.equipment.char_beret);
			WriteD(p.equipment.char_dino);
			WriteD(p.equipment.weapon_primary);
			WriteD(p.equipment.weapon_secundary);
			WriteD(p.equipment.weapon_melee);
			WriteD(p.equipment.weapon_grenade);
			WriteD(p.equipment.weapon_special);
			WriteD(p.title.equip1);
			WriteD(p.title.equip2);
			WriteD(p.title.equip3);
		}
	}
}