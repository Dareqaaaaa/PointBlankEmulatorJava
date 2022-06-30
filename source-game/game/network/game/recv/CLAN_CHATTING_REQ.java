package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_CHATTING_REQ extends game.network.game.GamePacketREQ
{
	ChatType chatType;
	String msg;
	int length;
	@Override
	public void readImpl()
	{
		chatType = ChatType.values()[ReadH()];
		length = ReadH();
		msg = ReadS(length);
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null && length <= 61 && chatType == ChatType.CHATTING_TYPE_CLAN)
		{
			Clan c = ck.BUSCAR_CLAN(p.clan_id);
			if (c != null)
			{
				CLAN_CHATTING_ACK sent = new CLAN_CHATTING_ACK(p, msg); sent.packingBuffer();
				for (Player m : ck.getPlayers(c))
				{
					if (m != null && m.gameClient != null)
						m.gameClient.sendPacketBuffer(sent.buffer);
				}
				sent.buffer = null; sent = null;
			}
		}
	}
}