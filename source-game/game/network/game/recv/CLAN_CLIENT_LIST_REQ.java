package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_CLIENT_LIST_REQ extends game.network.game.GamePacketREQ
{
	List<Clan> clans = new ArrayList<Clan>(170);
	int error, clan_id;
	@Override
	public void readImpl()
	{
		error = ReadD();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			clan_id = p.clan_id;
			if (error == 0)
			{
				p.listClans.clear();
				int clanSize = (int)Math.ceil(ck.clans.size() / 170);
				if (clanSize == 0 && ck.clans.size() > 0)
					clanSize = 1;
				for (int i = 0; i < clanSize; i++)
					p.listClans.put(i, new ArrayList<Clan>(170));
				for (Clan c : ck.clans.values())
				{
					for (List<Clan> list : p.listClans.values())
					{
						if (list.size() + 1 <= 170)
						{
							list.add(c);
							break;
						}
					}
				}
			}
			if (p.listClans.containsKey(error))
				clans = p.listClans.get(error);
		}
		client.sendPacket(new CLAN_CLIENT_LIST_ACK(clans, error, clan_id));
	}
}