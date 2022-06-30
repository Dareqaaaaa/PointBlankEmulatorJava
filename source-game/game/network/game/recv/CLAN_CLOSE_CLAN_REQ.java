package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_CLOSE_CLAN_REQ extends game.network.game.GamePacketREQ
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
			try
			{
				Clan c = ck.BUSCAR_CLAN(p.clan_id);
				if (c != null && p.id == c.owner && c.membros.size() == 1 && ck.clans.containsKey(c.id))
				{
					synchronized (c)
					{
						db.deleteClan(c.id);
						ck.clans.remove(c.id);
					}
					p.clan_id = 0;
					p.clan_date = 0;
					p.role = ClanRole.CLAN_MEMBER_LEVEL_UNKNOWN;
					db.executeQuery("UPDATE jogador SET clan_id='0', clan_date='0', role='0' WHERE id = '" + p.id + "'");
					client.sendPacket(new CLAN_READ_MEMBERS_ACK(null, 0));
					c.membros.clear();
					c = null;

					Room r = client.getRoom();
					if (r != null) r.updateInfo();
				}
				else
				{
					error = 0x8000106A;
				}
			}
			catch (Exception e)
			{
				error = 0x8000106A;
			}
		}
		else
		{
			error = 0x8000106A;
		}
		client.sendPacket(new CLAN_DELETE_ACK(error));
	}
}