package game.network.game.recv;

import game.network.game.sent.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class MISSION_QUEST_DELETE_CARD_REQ extends game.network.game.GamePacketREQ
{
	Player p;
	int cardId, error = 0x80001050;
	@Override
	public void readImpl()
	{
		cardId = ReadC();
	}
	@Override
	public void runImpl()
	{
		p = client.getPlayer();
		if (p != null)
		{
			if (!(cardId >= 4 || (cardId == 0 && p.mission1 == 0) || (cardId == 1 && p.mission2 == 0) || (cardId == 2 && p.mission3 == 0) || (cardId == 3 && p.mission4 == 0)))
			{
				if (cardId == 0)
				{
					p.mission1 = 0;
					p.missions.card1 = 0;
	                p.missions.list1 = new byte[40];
	                error = 0;
				}
				else if (cardId == 1)
				{
					p.mission2 = 0;
					p.missions.card2 = 0;
	                p.missions.list2 = new byte[40];
	                error = 0;
				}
				else if (cardId == 2)
				{
					p.mission3 = 0;
					p.missions.card3 = 0;
	                p.missions.list3 = new byte[40];
	                error = 0;
				}
				else if (cardId == 3)
				{
					p.mission4 = 0;
					p.missions.card4 = 0;
	                p.missions.list4 = new byte[40];
	                error = 0;
				}
				else
				{
					error = 0x8000104F;
				}
			}
		}
        client.sendPacket(new MISSION_QUEST_DELETE_CARD_ACK(p, error, cardId));
		if (error == 0)
		{
			db.updatePlayerMission(p, 0, cardId + 1);
			db.updateCard(p, cardId + 1, 0);
			db.updateMissions(p.id, new byte[40], cardId + 1);
		}
	}
}