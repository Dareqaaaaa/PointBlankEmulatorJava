package game.network.game.recv;

import game.network.game.sent.*;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_CREATE_REQ extends game.network.game.GamePacketREQ
{
	Clan c;
	int error, gold;
	@Override
	public void readImpl()
	{
		try
		{
			Player p = client.getPlayer();
			if (p != null)
			{
				if (ck.clans.size() >= Integer.MAX_VALUE)
				{
					error = 0x80001055;
				}
				else
				{
					if (p.clan_id == 0 && p.rank >= setting.clan_requerits_rank && (p.gold - setting.clan_requerits_gold) >= 0)
					{
						c = new Clan(0);
						int lengthName = ReadC();
						int lengthInfo = ReadC();
						int lengthNotice = ReadC();
						c.name = ReadS(lengthName);
						c.info = ReadS(lengthInfo);
						c.notice = ReadS(lengthNotice);
						c.logo = 0;	
						c.data = date.getClanTime();
						c.owner = p.id;
						if (db.createClan(c, ck))
						{
							ck.clans.put(c.id, c);
							p.clan_id = c.id;
							p.clan_date = c.data;
							p.role = ClanRole.CLAN_MEMBER_LEVEL_MASTER;
							p.gold -= setting.clan_requerits_gold;
							if (p.gold < 0)
								p.gold = 0;
							gold = p.gold;
							p.update_clan_nick = "";
							p.update_nick = "";
							p.update_clan_logo = 0;
							c.membros.add(p);
							db.executeQuery("UPDATE jogador SET gold='" + p.gold + "', clan_id='" + p.clan_id + "', clan_date='" + p.clan_date + "', role='1' WHERE id = '" + p.id + "'");
							Room r = client.getRoom();
							if (r != null) r.updateInfo();
							error = 0;
						}
						else
						{
							error = 0x80001048;
						}
					}
					else
					{
						error = 0x8000104A;
					}
				}
			}
		}
		catch (Exception e)
		{
			error = 0x80001048;
		}
	}
	@Override
	public void runImpl()
	{
		if (error != 0)
			c = null;
		client.sendPacket(new CLAN_CREATE_ACK(c, error, gold));
	}
}