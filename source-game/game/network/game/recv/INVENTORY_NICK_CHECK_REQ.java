 package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_NICK_CHECK_REQ extends game.network.game.GamePacketREQ
{
	Player p;
	String nome;
	int error;
	@Override
    public void readImpl()
    {
    	try
    	{
			nome = ReadS(33).replace(" ", "").trim();
		}
    	catch (Exception e)
    	{
    		error = 0x80000113;
    	}
    }
	@Override
    public void runImpl()
    {
		p = client.getPlayer();
    	try
    	{
    		if (p != null)
    			error = p.buscarPeloItemId(1301047000) == null || EssencialUtil.VERIFICAR_APELIDO_NA_DATABASE(nome) || EssencialUtil.CHECKAR_NOVO_NICK_IGUAL(p.id, nome) ? 0x80000113 : 0;
    		else
    			error = 0x80000113;
    	}
    	catch (Exception e)
    	{
    		error = 0x80000113;
    	}
    	client.sendPacket(new INVENTORY_NICK_CHECK_ACK(error));
    	if (p != null && error == 0)
    		p.update_nick = nome;
    }
}