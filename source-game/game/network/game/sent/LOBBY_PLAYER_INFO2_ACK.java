package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_PLAYER_INFO2_ACK extends game.network.game.GamePacketACK
{
	PlayerEquipment e;
	boolean clear = false;
	public LOBBY_PLAYER_INFO2_ACK(PlayerEquipment e)
	{
		super();
		if (e == null)
		{
			e = new PlayerEquipment();
			clear = true;
		}
		this.e = e;
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
		if (clear)
			e = null;
	}
}