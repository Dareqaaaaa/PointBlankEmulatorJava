package game.network.game.recv;

import java.awt.*;

import game.network.game.sent.*;
import core.info.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_ACCOUNT_LOG_CHANNEL_REQ extends game.network.game.GamePacketREQ
{
	int index;
	@Override
	public void readImpl()
	{
		index = ReadD();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Channel ch = client.getChannel();
		if (p != null && p.observing() == 1 && ch != null)
		{
			Player a = ch.getPlayerLobby(index);
			if (a != null)
			{
				client.sendPacket(new BASE_ACCOUNT_LOG_CHANNEL_ACK(a.id));
			}
		}
		//GClass20
		Software.LogColor("BASE_ACCOUNT_LOG_CHANNEL_REQ: pId: " + client.pId + "; Index: " + index, Color.GREEN);
		client.sendPacket(new SERVER_MESSAGE_ANNOUNCE_ACK("BASE_ACCOUNT_LOG_CHANNEL_REQ: Index: " + index));
	}
}