package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class LOBBY_CHATTING_ACK extends game.network.game.GamePacketACK
{
	Player p;
	String message, owner;
	int color;
	public LOBBY_CHATTING_ACK(Player p, String message, String owner)
	{
		super();
		this.p = p;
		this.message = message;
		this.owner = owner;
		if (p != null && p.cor_chat != 0)
			color = p.cor_chat;
		else
			color = p.color;
	}
	@Override
	public void writeImpl()
	{
		WriteD(p.channel_index);
		WriteC(owner.length() + 1);
		WriteS(owner, owner.length() + 1);
		WriteC(color);
		WriteC(p.rank == 53 || p.rank == 54 ? 1 : 0);
		WriteH(message.length() + 1);
		WriteS(message, message.length() + 1);
	}
}