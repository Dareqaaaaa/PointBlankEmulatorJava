package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class TITLE_DETACH_REQ extends game.network.game.GamePacketREQ
{
	int slot, error;
	@Override
	public void readImpl()
	{
		slot = ReadH();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			int equip = -1;
			if (slot == 0)
			{
				equip = p.title.equip1;
				p.title.equip1 = 0;
			}
			else if (slot == 1)
			{
				equip = p.title.equip2;
				p.title.equip2 = 0;
			}
			else if (slot == 2)
			{
				equip = p.title.equip3;
				p.title.equip3 = 0;
			}
			if (equip == -1 || p.title.title[equip] == 0)
				error = 0x80000000;
			else
			{
				if (equip == 13 && p.equipment.char_beret == 1103003001 || equip == 19 && p.equipment.char_beret == 1103003003 || equip == 25 && p.equipment.char_beret == 1103003002 || equip == 34 && p.equipment.char_beret == 1103003005 || equip == 39 && p.equipment.char_beret == 1103003004)
				{
					p.equipment.char_beret = 0;
					db.executeQuery("UPDATE jogador_equipamento SET char_beret='0' WHERE player_id='" + p.id + "'");
				}
				db.executeQuery("UPDATE jogador_titulos SET equip" + (slot + 1) + "='0' WHERE player_id = '" + p.id + "'");
				error = 0;
			}
		}
		else
		{
			error = 0x80000000;
		}
		client.sendPacket(new TITLE_DETACH_ACK(error));
	}
}