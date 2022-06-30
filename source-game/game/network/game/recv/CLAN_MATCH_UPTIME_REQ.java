package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MATCH_UPTIME_REQ extends game.network.game.GamePacketREQ
{
	int formacao;
	@Override
	public void readImpl()
	{
		formacao = ReadC();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Channel ch = client.getChannel();
		if (p != null && ch != null)
		{
			ClanFronto cf = ch.getClanFronto(p.cf_id);
			if (cf != null && cf.lider == p.id)
			{
				if (cf.sizePlayers() <= formacao)
				{
					if (formacao == cf.formacao)
						return;
					cf.formacao = formacao;
					CLAN_MATCH_UPTIME_ACK sent = new CLAN_MATCH_UPTIME_ACK(0, formacao); sent.packingBuffer();
					for (RoomSlot slot : cf.SLOT)
					{
						Player pR = slot.player_id > 0 ? AccountSyncer.gI().get(slot.player_id) : null;
						if (pR != null && pR.gameClient != null)
							pR.gameClient.sendPacketBuffer(sent.buffer);
					}
					sent.buffer = null; sent = null;
				}
				else
				{
					client.sendPacket(new CLAN_MATCH_UPTIME_ACK(0x80001095, 0));
				}
			}
			else
			{
				client.sendPacket(new CLAN_MATCH_UPTIME_ACK(0x80001095, 0));
			}
		}
		else
		{
			client.sendPacket(new CLAN_MATCH_UPTIME_ACK(0x80001095, 0));
		}
	}
}