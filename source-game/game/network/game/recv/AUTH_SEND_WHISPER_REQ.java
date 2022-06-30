package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class AUTH_SEND_WHISPER_REQ extends game.network.game.GamePacketREQ
{
	Player c, p;
	String owner, message;
	int length;
	long pId;
	@Override
	public void readImpl()
	{
		if (opcode == 292)
			pId = ReadQ();
		owner = ReadS(33);
		if (pId > 0)
			c = AccountSyncer.gI().get(pId);
		else
			c = AccountSyncer.gI().get(owner);
		length = ReadH();
		message = ReadS(length);
	}
	@Override
	public void runImpl()
	{
		if (length > 62)
			return;
		p = client.getPlayer();
		if (p != null && c != null && c.gameClient != null && c.online == 1)
		{
			client.sendPacket(new AUTH_SEND_WHISPER_ACK(owner, message, 0));
			c.gameClient.sendPacket(new AUTH_RECV_WHISPER_ACK(p, message));
		}
		else
		{
			client.sendPacket(new AUTH_SEND_WHISPER_ACK(owner, message, 0x80000000));
		}
	}
}