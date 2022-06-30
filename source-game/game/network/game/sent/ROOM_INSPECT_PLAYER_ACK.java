package game.network.game.sent;

import java.util.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_INSPECT_PLAYER_ACK extends game.network.game.GamePacketACK
{
	PlayerEquipment e;
	List<Integer> cupons = new ArrayList<Integer>();
	public ROOM_INSPECT_PLAYER_ACK(Player p)
	{
		super();
		e = p.equipment;
		for (int id : p.todosOsItemsDoInventario(3))
			if (p.buscarEquipPeloItemId(id) == 2)
				cupons.add(id);
		if (e == null)
			e = new PlayerEquipment();
	}
	@Override
	public void writeImpl()
	{
		WriteD(e.weapon_primary);
		WriteD(e.weapon_secundary);
		WriteD(e.weapon_melee);
		WriteD(e.weapon_grenade);
		WriteD(e.weapon_special);
		WriteD(e.char_red);
		WriteD(e.char_blue);
		WriteD(e.char_head);
		WriteD(e.char_beret);
		WriteD(e.char_dino);
		WriteD(cupons.size());
		for (int item_id : cupons)
			WriteD(item_id);
		cupons = null;
	}
}