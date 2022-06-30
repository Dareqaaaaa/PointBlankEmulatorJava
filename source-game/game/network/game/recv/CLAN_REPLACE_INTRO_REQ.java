package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_REPLACE_INTRO_REQ extends game.network.game.GamePacketREQ
{
	int error = 1;
	String info;
	@Override
	public void readImpl()
	{
		info = ReadS(ReadC());
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			Clan c = ck.BUSCAR_CLAN(p.clan_id);
			if (c != null && p.adminClan())
			{
		        c.info = info;
		        ck.updateInfo(c);
		        db.updateClanInfo(c);
			}
			else
			{
				error = 0x80001074;
			}
		}
		else
		{
			error = 0x80001074;
		}
		client.sendPacket(new CLAN_REPLACE_INTRO_ACK(error));
	}
}