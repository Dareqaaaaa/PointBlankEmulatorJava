package game.network.game.sent;

import java.util.*;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_RESPAWN_ACK extends game.network.game.GamePacketACK
{
	Room r; RoomSlot s;
	List<Integer> dinos;
	public BATTLE_RESPAWN_ACK(Room r, RoomSlot s, List<Integer> dinos)
	{
		super();
		this.r = r;
		this.s = s;
		this.dinos = dinos;
	}
	@Override
	public void writeImpl()
	{
		WriteD(s.id);
		WriteD(r.respawn++);
		WriteD(s.respawn++);
		WriteD(s.equipment.weapon_primary);
		WriteD(s.equipment.weapon_secundary);
		WriteD(s.equipment.weapon_melee);
		WriteD(s.equipment.weapon_grenade);
		WriteD(s.equipment.weapon_special);
		WriteD(0);
		WriteB(new byte[] { 100, 100, 100, 100, 100, 1 });
		WriteD(s.equipment.char_red);
		WriteD(s.equipment.char_blue);
		WriteD(s.equipment.char_head);
		WriteD(s.equipment.char_beret);
		WriteD(s.equipment.char_dino);	
		if (r.type == ModesEnum.DINO || r.type == ModesEnum.CROSSCOUNTER)
	    {
			int trex = (dinos.size() == 1 || r.type == ModesEnum.CROSSCOUNTER) ? 255 : r.rexDino;
			WriteC(trex);
			for (int slot : dinos)
				if (slot != r.rexDino && r.type == ModesEnum.DINO || r.type == ModesEnum.CROSSCOUNTER)
					WriteC(slot);
			int maskSlot = 8 - dinos.size() - (trex == 255 ? 1 : 0);
			for (int i = 0; i < maskSlot; i++)
				WriteC(255);
			WriteC(255);
			WriteC(255);
		}
	}
}