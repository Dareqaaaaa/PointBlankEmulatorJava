package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_REPLACE_NOTICE_REQ extends game.network.game.GamePacketREQ
{
	int error = 1;
	String notice;
	@Override
	public void readImpl()
	{
		notice = ReadS(ReadC());
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
		        c.notice = notice;
		        ck.updateInfo(c);
		        db.updateClanNotice(c);
			}
			else
			{
				error = 0x80001073;
			}
		}
		else
		{
			error = 0x80001073;
		}
		client.sendPacket(new CLAN_REPLACE_NOTICE_ACK(error));
	}
}