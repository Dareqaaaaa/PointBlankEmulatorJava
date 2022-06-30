package game.network.game.recv;

import game.network.game.sent.*;
import core.config.dat.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class MISSION_QUEST_BUY_CARD_REQ extends game.network.game.GamePacketREQ
{
	Player p;
	int error = 0x8000104E, missionId;
	@Override
	public void readImpl()
	{
		missionId = ReadC();
	}
	@Override
	public void runImpl()
	{
		p = client.getPlayer();
		if (p != null)
		{
			int price = MissionsDAT.gI().buy.get(missionId);
			if ((p.gold - price) < 0)
				 error = 0x8000104D;
			else
			{
				if (p.mission1 == 0)
	            {
					p.mission1 = missionId;
					p.missions.card1 = 0;
	                p.missions.list1 = new byte[40];
	                p.missions.actual_mission = 0;
	                error = 0;
	            }
	            else if (p.mission2 == 0)
	            {
	            	p.mission2 = missionId;
	            	p.missions.card2 = 0;
	                p.missions.list2 = new byte[40];
	                p.missions.actual_mission = 1;
	                error = 0;
	            }
	            else if (p.mission3 == 0)
	            {
	            	p.mission3 = missionId;
	            	p.missions.card3 = 0;
	                p.missions.list3 = new byte[41];
	                p.missions.actual_mission = 2;
	                error = 0;
	            }
	            else
	            {
	            	error = 0x8000104C;
	            }
				if (error == 0) p.gold -= price;
			}
		}
		client.sendPacket(new MISSION_QUEST_BUY_CARD_ACK(p, error));
		if (error == 0)
		{
			db.updateActualMission(p, p.missions.actual_mission);
            db.updatePlayerMission(p, missionId, p.missions.actual_mission + 1);
            db.updateCard(p, p.missions.actual_mission + 1, 0);
			db.updateMissions(p.id, new byte[40], p.missions.actual_mission + 1);
			db.updateGold(p);
		}
	}
}