package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class PLAYER_UPDATE_NICK_ACK extends game.network.game.GamePacketACK
{
	Player p;
	public PLAYER_UPDATE_NICK_ACK(Player p)
	{
		super();
		this.p = p;
	}
	@Override
	public void writeImpl()
	{
		WriteC(p.name.length() + 1);
		WriteS(p.name, p.name.length() + 1);
		WriteC(p.color);
	}
}