package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class MISSION_QUEST_UPDATE_CARD_ACK extends game.network.game.GamePacketACK
{
	Player p;
	int error, type;
	public MISSION_QUEST_UPDATE_CARD_ACK(Player p, int error, int type)
	{
		super();
		this.p = p;
		this.error = error;
		this.type = type;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		WriteC(type); //6
		if ((error & 1) == 1)
		{
			WriteD(p.exp);
			WriteD(p.gold);
			WriteD(p.brooch);
	        WriteD(p.insignia);
	        WriteD(p.medal);
	        WriteD(p.blue_order);
	        WriteD(p.rank);
		}
	}
}