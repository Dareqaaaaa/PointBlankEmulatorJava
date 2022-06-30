package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_WAR_TEAM_CHATTING_REQ extends game.network.game.GamePacketREQ
{
	String msg;
	int length;
	@Override
	public void readImpl()
	{
		ReadH(); //type
		length = ReadH();
		if (length > 60) length = 60;
		msg = ReadS(length);
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Channel ch = client.getChannel();
		if (p != null && ch != null)
		{
			ClanFronto cf = ch.getClanFronto(p.cf_id);
			if (cf != null)
			{
				CLAN_WAR_TEAM_CHATTING_ACK sent = new CLAN_WAR_TEAM_CHATTING_ACK(p, msg); sent.packingBuffer();
				for (RoomSlot slot : cf.SLOT)
				{
					Player m = slot.player_id > 0 ? AccountSyncer.gI().get(slot.player_id) : null;
					if (m != null && m.gameClient != null)
						m.gameClient.sendPacketBuffer(sent.buffer);
				}
				sent.buffer = null; sent = null;
			}
		}
	}
}