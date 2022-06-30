package game.network.game.recv;

import game.network.game.sent.*;
import core.config.xml.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_CHANNEL_PASSWD_REQ extends game.network.game.GamePacketREQ
{
	GameServerInfo gameServer;
	boolean enter = false;
	@Override
	public void readImpl()
	{
		gameServer = GameServerXML.gI().servers.get(client.getServerId());
		if (gameServer != null)
		{
			String passwd = ReadS(ReadC()).trim();
			enter = passwd.equals(gameServer.senha) || gameServer.senha.isEmpty();
		}
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null && p.observing() > 0)
			enter = true;
		client.passwd_enter = enter;
		client.sendPacket(new BASE_CHANNEL_PASSWD_ACK(enter ? 1 : 0x80000000));
	}
}