package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_LEAVE_REQ extends game.network.game.GamePacketREQ
{
	Player p;
	int type;
	@Override
	public void readImpl()
	{
		p = client.getPlayer();
		type = ReadD();
		if ((type & 1) == 1)
		{
			p.equipment.char_red = ReadD(); 
			p.equipment.char_blue = ReadD();
			p.equipment.char_head = ReadD(); 
			p.equipment.char_beret = ReadD(); 
			p.equipment.char_dino = ReadD();
		}
		if ((type & 2) == 2)
		{
			p.equipment.weapon_primary = ReadD(); 
			p.equipment.weapon_secundary = ReadD(); 
			p.equipment.weapon_melee = ReadD(); 
			p.equipment.weapon_grenade = ReadD(); 
			p.equipment.weapon_special = ReadD();
		}
	}
	@Override
	public void runImpl()
	{
		if (p != null)
		{
			Room r = client.getRoom();
			if (r != null)
			{
				RoomSlot s = r.getSlotByPID(p.id);
				if (s != null && s.state.ordinal() <= 8)
				{
					r.changeState(s, SlotState.NORMAL, true);
				}
				p.update_clan_nick = "";
				p.update_nick = "";
				p.update_clan_logo = 0;
			}
			client.sendPacket(new INVENTORY_LEAVE_ACK(type));
			if ((type & 1) == 1) db.updateChara(p, p.equipment);
			if ((type & 2) == 2) db.updateWeapons(p, p.equipment);
		}
	}
}