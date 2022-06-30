package game.network.auth.sent;

import java.util.*;

import core.config.settings.*;
import core.config.xml.*;
import core.enums.*;
import core.info.*;
import core.manager.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_MYINFO_ACK extends game.network.auth.AuthPacketACK
{
	Player p; Clan c; EventVerification ev; ConfigurationLoader srv; MapsXML mapas;
	int error, christmas;
	List<PlayerInventory> weapon, chara, coupon;
	public BASE_GET_MYINFO_ACK(Player pl)
	{
		super();
		p = pl;
		if (p != null)
		{
			srv = ConfigurationLoader.gI();
			c = ClanManager.gI().BUSCAR_CLAN(p.clan_id);
			mapas = MapsXML.gI();
			if (p.buscarPeloItemId(1103003016) == null && (p.rank > 46 || p.access_level == AccessLevel.MASTER_PLUS))
				db.adicionarItem(p, new PlayerInventory(0, 1103003016, 1, 3)); //Boina de General
			weapon = p.getItemByType(1);
			chara = p.getItemByType(2);
			coupon = p.getItemByType(3);
			EventVerification clone = CheckXML.gI().VERIFICATION_NOW();
			if (clone != null)
			{
				if (clone.id != p.event.checks_id && clone.checks > p.event.last_check1 && p.event.check_day <= date.getYearMouthDay(0))
				{
					ev = clone;
				}
			}
		}
		else
		{
			error = 0x80000000;
		}
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		if (error == 0 && p != null)
		{
			WriteC(18); //Idade
			WriteS(p.name, 33);
			WriteD(p.exp);
			WriteD(p.rank);
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
			WriteH(0);
	        WriteD(p.false_rank);
	        WriteD(p.false_rank);
	        WriteS(p.false_nick, 33);       
			WriteH(p.cor_mira);
			WriteC(p.country.value);
			if (Software.pc.client_rev.equals("1.15.37"))
			{
				WriteC(1);
				WriteD(chara.size());
				WriteD(weapon.size());
				WriteD(coupon.size());
				WriteD(0);
				for (PlayerInventory item : chara)
				{
					WriteQ(item.object);
					WriteD(item.item_id);
					WriteC(item.equip);
					WriteD(item.count);
				}
				for (PlayerInventory item : weapon)
				{
					WriteQ(item.object);
					WriteD(item.item_id);
					WriteC(item.equip);
					WriteD(item.count);
				}
				for (PlayerInventory item : coupon)
				{
					WriteQ(item.object);
					WriteD(item.item_id);
					WriteC(item.equip);
					WriteD(item.count);
				}
			}
			WriteC(srv.outpost ? 1 : 0);
			WriteD(p.brooch);
			WriteD(p.insignia);
			WriteD(p.medal);
			WriteD(p.blue_order);
			WriteC(p.missions.actual_mission);
			WriteC(p.missions.card1);
			WriteC(p.missions.card2);
			WriteC(p.missions.card3);
			WriteC(p.missions.card4);
			for (int i = 0; i < 10; i++) WriteH(missao.getCard(p.mission1, i, p.missions.list1));
			for (int i = 0; i < 10; i++) WriteH(missao.getCard(p.mission2, i, p.missions.list2));
			for (int i = 0; i < 10; i++) WriteH(missao.getCard(p.mission3, i, p.missions.list3));
			for (int i = 0; i < 10; i++) WriteH(missao.getCard(p.mission4, i, p.missions.list4));
			WriteC(p.mission1);
			WriteC(p.mission2);
			WriteC(p.mission3);
			WriteC(p.mission4);
	        WriteB(p.missions.list1);
	        WriteB(p.missions.list2);
	        WriteB(p.missions.list3);
	        WriteB(p.missions.list4);
	        WriteB(p.title.position);
			WriteC(p.title.equip1);
			WriteC(p.title.equip2);
			WriteC(p.title.equip3);
			WriteD(p.title.slot);
			WriteB(mapas.modes);
			WriteC(mapas.maps.size());
			WriteC(mapas.position.length);
			WriteD(mapas.position[0]);
			WriteD(mapas.position[1]);
			WriteD(mapas.position[2]);
			WriteD(mapas.position[3]);
			for (Maps m : mapas.maps)
				WriteH(m.mode);
			for (Maps m : mapas.maps)
				WriteC(m.tag);
	        WriteC(srv.mission_active ? 1 : 0);
	        WriteD(missao.missionList);
	        WriteD(50);
	        WriteD(75);
	        WriteC(1);
	        WriteH(20);
	        WriteB(new byte[20]);
	        WriteC(0);
	        WriteC(p.observing());
	        WriteC(0);
	        WriteC(0);
	        WriteC(christmas);
	        WriteD(0);
			WriteD(ev != null ? ev.id : 0);
			WriteC(ev != null ? p.event.last_check1 : 0);
			WriteC(ev != null ? p.event.last_check2 : 0);
			WriteH(0);
			WriteD(ev != null ? ev.start : 0);
			WriteS(ev != null ? ev.announce : "", 60);
			WriteC(ev != null ? 2 : 0);
			WriteC(ev != null ? ev.checks : 0);
			WriteH(0);
			WriteD(ev != null ? ev.id : 0);
			WriteD(ev != null ? ev.start : 0);
			WriteD(ev != null ? ev.end : 0);
			for (int i = 1; i < (mapas.support_client > 39 ? 9 : 8); i++)
			{
				EventGifts gf = ev != null ? ev.getGift(i) : null;
				if (i == 8)
					WriteC(gf != null ? gf.size : 1);
				else
					WriteD(gf != null ? gf.size : 1);
				WriteD(gf != null ? gf.gift1 : 0);
				WriteD(gf != null ? gf.gift2 : 0);
			}
	        WriteD(date.getDateTime());
	        WriteS("10.120.1.44", 256);
	        WriteD(8085);
	        WriteC(1); //Gift System
	        WriteH(0);
	        WriteH(ShopTag.NEW.ordinal());
	        WriteC(ShopTag.NEW.ordinal());
	        WriteC(ShopTag.VIP_BASIC.ordinal());
	        WriteH(ShopTag.VIP_PLUS.ordinal());
	        WriteC(ShopTag.NEW.ordinal());
	        WriteC(ShopTag.HOT.ordinal());
	        WriteC(ShopTag.SALE.ordinal());
	        WriteC(ShopTag.EVENT.ordinal());
		}
		chara = null;
		weapon = null;
		coupon = null;
	}
}