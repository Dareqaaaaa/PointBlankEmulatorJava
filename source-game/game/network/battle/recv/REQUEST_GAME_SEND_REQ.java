package game.network.battle.recv;

import game.network.battle.*;
import game.network.game.sent.*;
import core.config.xml.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_GAME_SEND_REQ extends BattlePacketREQ
{
	int roomId,  serverId, channelId, slot;
	String aviso;
	@Override
	public void readImpl()
	{
		roomId = ReadD();
		serverId = ReadD();
		channelId = ReadD();
		slot = ReadD();
		aviso = ReadS(ReadD());
	}
	@Override
	public void runImpl()
	{
		Channel ch = GameServerXML.gI().getChannel(channelId, serverId);
		if (ch != null)
		{
			Room r = ch.getRoom(roomId);
			if (r != null)
			{
				Player p = r.getPlayerBySlot(slot);
				if (p != null && p.gameClient != null)
				{
					p.gameClient.sendPacket(new ROOM_CHATTING_ACK(slot, 1, true, aviso));
				}
			}
		}
	}
}