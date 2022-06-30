package game.network.game.sent;

import game.network.game.*;
import core.info.*;
import core.model.*;

public class BATTLE_READYBATTLE_ACK extends GamePacketACK
{
	Room r;
	int count;
	public BATTLE_READYBATTLE_ACK(Room r)
	{
		super();
		if (r != null)
		{
			for (RoomSlot slot : r.SLOT)
				if (slot.state.ordinal() > 7)
					count++;
		}
		this.r = r;
	}
	@Override
	public void writeImpl()
	{
		WriteH(r.map);
		WriteC(r.stage4v4);
		WriteC(r.type.ordinal());
		WriteC(count);
		for (RoomSlot slot : r.SLOT)
		{
			Player p = r.getPlayerBySlot(slot.id);
			if (p != null && slot.state.ordinal() >= 8)
			{
				WriteC(slot.id);
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
	}
}