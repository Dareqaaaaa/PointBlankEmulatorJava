package game.network.game.recv;

import game.network.game.sent.*;
import core.config.xml.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_CREATE_NICK_NAME_REQ extends game.network.game.GamePacketREQ
{
	String nick;
	@Override
	public void readImpl()
	{
		nick = ReadS(ReadC() - 1).replace(" ", "").trim();
	}
	@Override
	public void runImpl()
	{
		try
		{
			if (!EssencialUtil.VERIFICAR_APELIDO_NA_DATABASE(nick) && !EssencialUtil.CHECKAR_NOVO_NICK_IGUAL(client.pId, nick) && nick.length() >= 2 && nick.length() <= 16)
			{
				Player p = client.getPlayer();
				Channel ch = client.getChannel();
				if (p != null && ch != null && p.status() == 0 && !p.name.equals(nick) && p.firstEnter)
				{
					p.name = nick;
					ch.addPlayer(p);
					if (BONUS(p))
						client.sendPacket(new PUSH_PRESENT_ITEM_ACK(0));
					client.sendPacket(new LOBBY_CREATE_NICK_NAME_ACK(0));
					client.sendPacket(new MISSION_QUEST_GET_INFO_ACK(p));
					db.updateNick(p, nick);
					db.replaceNick(p.id, nick);
				}
				else
				{
					client.sendPacket(new LOBBY_CREATE_NICK_NAME_ACK(0x80000112)); //0x80000114
				}
			}
			else
			{
				client.sendPacket(new LOBBY_CREATE_NICK_NAME_ACK(0x80000113));
			}
		}
		catch (Exception e)
		{
			client.sendPacket(new LOBBY_CREATE_NICK_NAME_ACK(0x80000112));
		}
	}
	boolean BONUS(Player p)
	{
		int size = 0;
		for (PlayerInventory it : TemplateXML.gI().items)
		{
			if (!it.starter && p.buscarPeloItemId(it.item_id) == null)
			{
				client.sendPacket(new INVENTORY_ITEM_CREATE_ACK(p, it.item_id, it.count, it.equip, -1));
				size++;
			}
		}
		return size > 0;
	}
}