package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.utils.EssencialUtil;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_CHECK_LOGO_REQ extends game.network.game.GamePacketREQ
{
	int error, logo;
	@Override
	public void readImpl()
	{
		logo = ReadD();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null && p.clan_id > 0 && p.role == ClanRole.CLAN_MEMBER_LEVEL_MASTER)
		{
			Clan c = ck.BUSCAR_CLAN(p.clan_id);
			if (c != null)
			{
				if (logo == 0 || logo == 0xFFFFFFFF) error = 0x80001075;
				else if (ck.logoExist(logo)) error = 0x80001075;
				else if (db.logoExist(logo)) error = 0x80001075;
				else if (EssencialUtil.CHECKAR_NOVO_CLAN_LOGO_IGUAL(p.id, logo)) error = 0x80001075;
				else p.update_clan_logo = logo;
			}
			else
			{
				error = 0x8000105B;
			}
		}
		else
		{
			error = 0x80000000;
		}
		client.sendPacket(new CLAN_CHECK_LOGO_ACK(error));
	}
}