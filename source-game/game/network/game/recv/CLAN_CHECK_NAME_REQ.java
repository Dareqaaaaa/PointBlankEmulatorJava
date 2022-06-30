package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_CHECK_NAME_REQ extends game.network.game.GamePacketREQ
{
	String new_name;
	int error;
	@Override
    public void readImpl()
    {
		new_name = ReadS(ReadC()).replace(" ", "").trim();
    }
	@Override
    public void runImpl()
    {
		Player p = client.getPlayer();
		if (p != null)
		{
			if (p.clan_id == 0)
				error = EssencialUtil.VERIFICAR_APELIDO_DO_CLAN(p, new_name) ? 0x80001076 : 0;
			else
			{
    			Clan c = ck.BUSCAR_CLAN(p.clan_id);
    			if (c != null && p.role == ClanRole.CLAN_MEMBER_LEVEL_MASTER)
    	    		error = EssencialUtil.VERIFICAR_APELIDO_DO_CLAN(p, new_name) ? 0x80001076 : 0;
    			else
    				error = 0x8000105B;
			}
        	if (error == 0)
        		p.update_clan_nick = new_name;
		}
		else
		{
			error = 0x80000000;
		}
    	client.sendPacket(new CLAN_CHECK_DUPLICATE_ACK(error));
    }
}