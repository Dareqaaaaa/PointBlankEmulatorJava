package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MEMBER_CONTEXT_REQ extends game.network.game.GamePacketREQ
{
	@Override
	public void readImpl()
	{
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			Clan c = ck.BUSCAR_CLAN(p.clan_id);
			if (c != null)
				client.sendPacket(new CLAN_MEMBER_CONTEXT_ACK(0, c.membros.size()));
			else
				client.sendPacket(new CLAN_MEMBER_CONTEXT_ACK(0x8000105B, 0));
		}
		else
		{
			client.sendPacket(new CLAN_MEMBER_CONTEXT_ACK(0x8000105B, 0));
		}
	}
}