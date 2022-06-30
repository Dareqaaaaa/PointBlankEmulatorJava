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

public class BASE_ACCOUNT_LOG_ROOM_REQ extends game.network.game.GamePacketREQ
{
	int slot;
	@Override
	public void readImpl()
	{
		slot = ReadD();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		Room r = client.getRoom();
		if (p != null && p.observing() == 1 && r != null)
		{
			Player a = r.getPlayerBySlot(slot);
			if (a != null)
				client.sendPacket(new BASE_ACCOUNT_LOG_CHANNEL_ACK(a.id));
		}
		//GClass21
		Software.LogColor("BASE_ACCOUNT_LOG_ROOM_REQ: pId: " + client.pId + "; Slot: " + slot, Color.GREEN);
		client.sendPacket(new SERVER_MESSAGE_ANNOUNCE_ACK("BASE_ACCOUNT_LOG_ROOM_REQ: Slot: " + slot));
	}
}