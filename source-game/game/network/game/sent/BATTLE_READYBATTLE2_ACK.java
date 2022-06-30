package game.network.game.sent;

import core.info.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_READYBATTLE2_ACK extends game.network.game.GamePacketACK
{
	Player p;	
	int slot;
	public BATTLE_READYBATTLE2_ACK(Player p, int slot)
	{
		super();
		this.p = p;
		this.slot = slot;
	}
	@Override
	public void writeImpl()
	{
		WriteC(slot);
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
		WriteD(0);
		WriteC(p.title.equip1);
		WriteC(p.title.equip2);
		WriteC(p.title.equip3);
		if (Software.pc.client_rev.equals("1.15.42"))
			WriteC(0);
	}
}