package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class MISSION_QUEST_BUY_CARD_ACK extends game.network.game.GamePacketACK
{
	Player p;
	int error;
	public MISSION_QUEST_BUY_CARD_ACK(Player p, int error)
	{
		super();
		this.p = p;
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		if (error == 0 && p != null)
		{
			WriteD(p.gold);
			WriteC(p.missions.actual_mission);
			WriteC(p.missions.actual_mission);
			WriteC(p.missions.card1);
			WriteC(p.missions.card2);
			WriteC(p.missions.card3);
			WriteC(p.missions.card4);
			for (int i = 0; i < 10; i++) WriteH(missao.getCard(p.mission1, i, p.missions.list1));
			for (int i = 0; i < 10; i++) WriteH(missao.getCard(p.mission2, i, p.missions.list2));
			for (int i = 0; i < 10; i++) WriteH(missao.getCard(p.mission3, i, p.missions.list3));
			for (int i = 0; i < 10; i++) WriteH(missao.getCard(p.mission4, i, p.missions.list4));		
			WriteC(p.mission1);
			WriteC(p.mission2);
			WriteC(p.mission3);
			WriteC(p.mission4);
		}
	}
}