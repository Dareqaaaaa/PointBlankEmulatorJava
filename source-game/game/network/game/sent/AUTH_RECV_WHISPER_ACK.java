package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class AUTH_RECV_WHISPER_ACK extends game.network.game.GamePacketACK
{
	Player p;
	String message;
	public AUTH_RECV_WHISPER_ACK(Player p, String message)
	{
		super();
		this.p = p;
		this.message = message;
	}
	@Override
	public void writeImpl()
	{
		WriteS(p.name, 33);
		WriteC(p.rank == 53 || p.rank == 54 ? 1 : 0);
		WriteH(message.length() + 1);
		WriteS(message, message.length() + 1);
	}
}