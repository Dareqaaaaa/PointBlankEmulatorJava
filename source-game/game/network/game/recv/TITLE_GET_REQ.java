package game.network.game.recv;

import game.network.game.sent.*;
import core.config.dat.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class TITLE_GET_REQ extends game.network.game.GamePacketREQ
{
	int titleIdx, error, slot;
	@Override
	public void readImpl()
	{
		titleIdx = ReadC();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		TitleV t = TitlesDAT.gI().titles.get(titleIdx);
		if (p != null && t != null)
		{
			if (p.rank >= t.rank && p.title.title[t.reqT1] == 1 && p.title.title[t.reqT2] == 1 && p.brooch >= t.brooch && p.medal >= t.medals && p.blue_order >= t.blue_order && p.insignia >= t.insignia)
			{
				if (p.title.title[t.id] == 0)
				{
					slot = t.slot;
					p.title.position[t.pos - 1] += t.pos_v;
					p.title.title[t.id] = 1;
		            p.brooch -= t.brooch;
		            p.medal -= t.medals;
		            p.blue_order -= t.blue_order;
		            p.insignia -= t.insignia;
		            if (p.brooch < 0) p.brooch = 0;
		            if (p.medal < 0) p.medal = 0;
		            if (p.blue_order < 0) p.blue_order = 0;
		            if (p.insignia < 0) p.insignia = 0;
		            if (p.title.slot > slot)
		            	slot = p.title.slot;
		            else
		            {
		            	p.title.slot = slot;
		            	db.executeQuery("UPDATE jogador_titulos SET slot='" + p.title.slot + "' WHERE player_id='" + p.id + "'");
		            }
		            for (PlayerInventory it : t.rewards)
		            	client.sendPacket(new INVENTORY_ITEM_CREATE_ACK(p, it.item_id, it.count, it.equip, -1));
		            if (t.brooch > 0) db.updateBrooch(p);
		            if (t.medals > 0) db.updateMedal(p);
		            if (t.insignia > 0) db.updateInsignia(p);
		            if (t.blue_order > 0) db.updateBlueOrder(p);
		            db.updateTitle(p, p.title.title);
				}
				else
				{
					slot = p.title.slot;
				}
			}
			else
			{
				error = 0x80001083;
			}
		}
		else
		{
			error = 0x80000000;
		}
		client.sendPacket(new TITLE_GET_ACK(error, slot)); //0x80001084 - Titulo já foi adquirido.
		if (p != null && error == 0)
			client.sendPacket(new TITLE_UPDATE_ACK(p));
	}
}