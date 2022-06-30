package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class TITLE_UPDATE_ACK extends game.network.game.GamePacketACK
{
	Player p;
	public TITLE_UPDATE_ACK(Player p)
	{
		super();
		this.p = p;
	}
	@Override
	public void writeImpl()
	{
		WriteB(p.title.position);
        WriteD(p.brooch);
        WriteD(p.insignia);
        WriteD(p.medal);
        WriteD(p.blue_order);
	}
}