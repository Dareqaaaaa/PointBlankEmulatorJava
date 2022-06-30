package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_SAVE_ACCESS_REQ extends game.network.game.GamePacketREQ
{
	int error, autoridade, limite_rank, limite_idade, limite_idade2;
	@Override
	public void readImpl()
	{
		autoridade = ReadC();
	    limite_rank = ReadC();
	    limite_idade = ReadC();
	    limite_idade2 = ReadC();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			Clan c = ck.BUSCAR_CLAN(p.clan_id);
			if (c != null && c.owner == p.id)
			{
		        c.autoridade = autoridade;
		        c.limite_rank = limite_rank;
		        c.limite_idade = limite_idade;
		        c.limite_idade2 = limite_idade2;
		        ck.updateInfo(c);
		        db.updateClanAccess(c);
			}
			else
			{
				error = 0x8000106E;
			}
		}
		else
		{
			error = 0x8000106E;
		}
		client.sendPacket(new CLAN_SAVE_ACCESS_ACK(error));
	}
}