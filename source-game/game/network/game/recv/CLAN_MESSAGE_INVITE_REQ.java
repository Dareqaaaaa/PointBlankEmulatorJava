package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MESSAGE_INVITE_REQ extends game.network.game.GamePacketREQ
{
	int type;
	long pId;
	@Override
	public void readImpl()
	{
		type = ReadC();
		if (type == 0) pId = ReadQ();
		else if (type == 1) pId = ReadD();
		else if (type == 2) pId = ReadD();
	}
	@Override
	public void runImpl() 
	{
		Player p = client.getPlayer();
		Channel ch = client.getChannel();
		if (p != null && ch != null)
		{
			Player f = null;
			if (type == 0)
			{
				f = EssencialUtil.getPlayerId(pId, false, false, false, false);
			}
			else if (type == 1)
			{
				Room r = client.getRoom();
				if (r != null)
					f = r.getPlayerBySlot((int)pId);
			}
			else if (type == 2)
			{
				f = ch.getPlayerLobby((int)pId);
			}
			if (f != null && f.clan_id == 0)
			{
				Clan c = ck.BUSCAR_CLAN(p.clan_id);
				if (c != null)
				{
					for (PlayerMessage msg : f.mensagens)
					{
						if (msg.type == NoteType.CLAN && msg.readed == ReadType.VISUALIZADO && msg.response == 1 && msg.clan_id == c.id)
						{
							return;
						}
					}
					if (db.returnQueryValueI("SELECT object FROM jogador_mensagem WHERE type='5' AND readed='1' AND response='0' AND clan_id='" + c.id + "' AND player_id='" + f.id + "'", "object") == 0)
					{
						PlayerMessage msg = new PlayerMessage(f.id, p.id, 15, c.id, NoteType.CLAN, NoteReceive.CONVITE, false);
						msg.name = c.name;
						msg.owner_name = p.name;
						msg.texto = "";
						db.addMessage(msg);
						if (msg != null && f.gameClient != null)
						{
							f.mensagens.add(msg);
							f.gameClient.sendPacket(new MESSENGER_NOTE_RECEIVE_ACK(msg));
						}
						client.sendPacket(new CLAN_INVITE_ACK(0));
						msg = null;
					}
				}
			}
			else
			{
				client.sendPacket(new CLAN_INVITE_ACK(0x8000105B));
			}		
		}
		else
		{
			client.sendPacket(new CLAN_INVITE_ACK(0x8000105B));
		}
	}
}