package game.network.game.recv;

import game.network.game.sent.*;
import core.manager.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_REMOVE_INVITES_REQ extends game.network.game.GamePacketREQ
{
	int error;
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
			if (!ClanInviteManager.gI().deleteInvite(p) && p.clan_invited > 0)
				error = 0x8000106D;
			else
				error = 0;
		}
		else
		{
			error = 0x8000106D;
		}
		client.sendPacket(new CLAN_REMOVE_INVITES_ACK(error));
	}
}