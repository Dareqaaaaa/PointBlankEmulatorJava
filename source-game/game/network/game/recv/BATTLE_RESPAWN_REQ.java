package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;
import java.util.concurrent.*;

import core.config.xml.*;
import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_RESPAWN_REQ extends game.network.game.GamePacketREQ
{
	Player p; Room r; RoomSlot s;
	String texto = "";
	@Override
	public void readImpl()
	{
		p = client.getPlayer();
		r = client.getRoom();
		if (r != null && p != null)
		{
			s = r.getSlot(p.slot);
			if (s != null && s.state == SlotState.BATTLE && r.rstate == RoomState.BATTLE)
			{
				if (s.armasUsadas == null)
					s.armasUsadas = new ConcurrentHashMap<Long, Player>();
				s.equipment = new PlayerEquipment();
				s.equipment.weapon_primary = item_qty(ReadD());
				s.equipment.weapon_secundary = item_qty(ReadD());
				s.equipment.weapon_melee = item_qty(ReadD());
				s.equipment.weapon_grenade = item_qty(ReadD());
				s.equipment.weapon_special = item_qty(ReadD());
				ReadD();
				if (s.id % 2 == 0)
				{
					s.equipment.char_red = item_qty(ReadD());
					s.equipment.char_blue = ReadD();
				}
				else
				{
					s.equipment.char_red = ReadD();
					s.equipment.char_blue = item_qty(ReadD());
				}
				s.equipment.char_head = item_qty(ReadD());
				s.equipment.char_beret = item_qty(ReadD());
				s.equipment.char_dino = item_qty(ReadD());
				//ReadC();
				s.killMessage = FragValues.NONE;
				s.lastKillState = 0;
				s.oneTimeKills = 0;
				s.repeatLastState = false;
				s.dinoOnLife = 0;
				s.lastItemFrag = ItemClassEnum.UNKNOWN;
				switch (r.allWeapons)
				{
					case 42:
					case 43:
					case 46:
					case 47:
					{
						if ((s.equipment.weapon_primary / 100000) != 3000)
							s.equipment.weapon_primary = 300005003;
						break;
					}
					case 74:
					case 75:
					case 78:
					case 79:
					{
						if ((s.equipment.weapon_primary / 100000) != 4000)
							s.equipment.weapon_primary = 400006001;
						break;
					}
				}
			}
		}
	}
	@Override
	public void runImpl()
	{
		if (r != null && p != null && s != null && r.rstate == RoomState.BATTLE)
		{
			List<Integer> dinos = EssencialUtil.dinos(r, r.dinossaur());
			BATTLE_RESPAWN_ACK sent = new BATTLE_RESPAWN_ACK(r, s, dinos); sent.packingBuffer();
			for (RoomSlot sM : r.SLOT)
			{
				Player m = AccountSyncer.gI().get(sM.player_id);
				if (m != null && sM.state == SlotState.BATTLE)
					m.gameClient.sendPacketBuffer(sent.buffer);
			}
			sent.buffer = null; sent = null;
			dinos = null;
			if (texto.length() > 0)
				client.sendPacket(new SERVER_MESSAGE_ANNOUNCE_ACK(texto));
		}
	}
	public int item_qty(int item_id)
	{
		try
		{
			if (!r.isSpecialMode() && p.access_level.ordinal() < 3)
			{
				if (r.block(r.name))
				{
					ConcurrentHashMap<Integer, RoomCamp> equips = CampXML.gI().equips;
					if (equips.containsKey(item_id))
					{
						int slot = s.invent.readSlot(item_id);
						if (slot >= 1 && slot <= 5)
						{
							if (texto.length() == 0)
								texto = "Restricted this equipements.\n";
						}
						switch (slot)
						{
							case 1:
							{
								texto += " " + equips.get(item_id).name + " \n";
								return s.padrao.weapon_primary;
							}
							case 2:
							{
								texto += " " + equips.get(item_id).name + " \n";
								return s.padrao.weapon_secundary;
							}
							case 3:
							{
								texto += " " + equips.get(item_id).name + " \n";
								return s.padrao.weapon_melee;
							}
							case 4:
							{
								texto += " " + equips.get(item_id).name + " \n";
								return s.padrao.weapon_grenade;
							}
							case 5:
							{
								texto += " " + equips.get(item_id).name;
								return s.padrao.weapon_special;
							}
						}
					}
				}
			}
			item_id = checkWeapon(item_id);
			PlayerInventory item = p.buscarPeloItemId(item_id);
			if (item != null)
			{
				if (item.equip == 1 && !s.armasUsadas.containsKey(item.object))
				{
					s.armasUsadas.put(item.object, p);
					item.count--;
					if (item.count > 0)
					{
						p.gameClient.sendPacket(new INVENTORY_ITEM_UPDATE_ACK(item));
						p.updateCountAndEquip(item);
					}
					else
					{
						if (!s.armasExcluir.contains(item))
							s.armasExcluir.add(item);
						else
						{
							for (PlayerInventory iv : s.armasExcluir)
							{
								if (iv.object == item.object)
								{
									iv = item;
									break;
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
		}
		return item_id;
	}
	public int checkWeapon(int item_id)
	{
		if (item_id != 0)
		{
			int slot = item_id / 100000;
			for (PlayerInventory it : p.inventario.values())
			{
				if (it.item_id == item_id)
					return item_id;
			}
			if (slot == 1000 || slot == 2000)
				return 0; //Retorna arma nenhuma
			else if (slot == 3000)
				return 300005003; //SSG669
			else if (slot == 4000)
				return 400006001; //870MCS
			else if (slot == 6010)
				return 601002003; //K-5
			else if (slot == 7020)
			{
				if (r.modoPorrada)
					return 702023001; //Barefirst
				else
					return 702001001; //M-7
			}
			else if (slot == 8030)
				return 803007001; //K-400
			else if (slot == 9040)
				return 904007002; //Smoke
			else if (slot == 10010)
			{
				if (p.slot % 2 == 0)
					return 1001001005; //Red Bulls
				else
					return 1001002006; //Acid Pol
			}
			else if (slot == 11020)
				return 1102003001; //Capacete Básico
			else if (slot == 11030)
				return 0; //Retorna boina nenhuma
			else if (slot == 10060)
				return 1006003041; //Raptor
		}
		return item_id;
	}
}