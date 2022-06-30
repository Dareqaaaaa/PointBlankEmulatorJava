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

public class CLAN_MESSAGE_INVITE_RESP_REQ extends game.network.game.GamePacketREQ
{
	int clan_id, response, error;
	@Override
	public void readImpl()
	{
		clan_id = ReadD();
		ReadD(); //0
		response = ReadC();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			for (PlayerMessage msg : p.mensagens)
			{
				if (msg.owner_id == clan_id && msg.readed == ReadType.VISUALIZADO && msg.type == NoteType.CLAN && msg.response == 0)
				{
					msg.response = 1;
					db.executeQuery("UPDATE jogador_mensagem SET response='1' WHERE object='" + msg.object + "'");
					break;
				}
			}
			Clan c = ck.BUSCAR_CLAN(clan_id);
			if (c == null)
			{
				client.sendPacket(new CLAN_MESSAGE_INVITE_RESP_ACK(0x8000105B));
				return;
			}
			else if (p.clan_id > 0)
			{
				client.sendPacket(new CLAN_MESSAGE_INVITE_RESP_ACK(0x80001058));
				return;
			}
			else if (response == 1)
			{
				if ((c.membros.size() + 1) > c.vagas)
					client.sendPacket(new CLAN_MESSAGE_INVITE_RESP_ACK(0x80001056));
				else
					error = ck.addPlayer(p, true, c.id);
			}
			sendMessage(c, p);
		}
		else
		{
			client.sendPacket(new CLAN_MESSAGE_INVITE_RESP_ACK(0x80000000));
		}
	}
	public void sendMessage(Clan c, Player p)
	{
		if (c != null && p != null && error == 0)
		{
			PlayerMessage msg = new PlayerMessage(p.id, p.id, 15, c.id, NoteType.WEB, response == 1 ? NoteReceive.CONVITE_ACEITO : NoteReceive.CONVITE_REJEITADO, false);
			msg.name = c.name;
			msg.owner_name = p.name;
			msg.texto = "";
			MESSENGER_NOTE_RECEIVE_ACK sent = new MESSENGER_NOTE_RECEIVE_ACK(msg); sent.packingBuffer();
			for (Player m : ck.getPlayers(c))
			{
				if (msg != null && (m != null && m.adminClan()))
				{
					msg.player_id = m.id;
					db.addMessage(msg);
					if (msg != null && m.gameClient != null)
					{
						Player t = AccountSyncer.gI().get(m.id);
						if (t != null)
							t.mensagens.add(msg);
						m.gameClient.sendPacketBuffer(sent.buffer);
					}
				}
			}
			sent.buffer = null; sent = null;
			msg = null;
		}
		client.sendPacket(new MESSENGER_NOTE_ACCEPT_ACK(error)); //0 - Mensagem enviada | 1 - Falha ao enviar mensagem
	}
}