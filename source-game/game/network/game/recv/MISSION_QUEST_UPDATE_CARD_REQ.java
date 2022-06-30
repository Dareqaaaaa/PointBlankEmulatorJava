package game.network.game.recv;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class MISSION_QUEST_UPDATE_CARD_REQ extends game.network.game.GamePacketREQ
{
	int missionId, cardId, index;
	@Override
	public void readImpl()
	{
		missionId = ReadC();
        cardId = ReadC();
        index = ReadH();
	}
	@Override
	public void runImpl()
	{
		Player p = client.getPlayer();
		if (p != null)
		{
			if (missionId == 0) p.missions.card1 = cardId;
			else if (missionId == 1) p.missions.card2 = cardId;
			else if (missionId == 2) p.missions.card3 = cardId;
			else if (missionId == 3) p.missions.card4 = cardId;
			p.missions.selectedCard = index == 65535;
			db.updateCard(p, missionId + 1, cardId);
			if (p.missions.actual_mission != missionId)
			{
				p.missions.actual_mission = missionId;
				db.updateActualMission(p, missionId);
			}
		}
	}
}