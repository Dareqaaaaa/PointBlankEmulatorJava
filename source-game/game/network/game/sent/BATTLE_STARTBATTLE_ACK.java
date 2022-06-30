package game.network.game.sent;

import java.util.*;

import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_STARTBATTLE_ACK extends game.network.game.GamePacketACK
{
	Room r; RoomSlot s;
	int enter, error, slotDino, slotPlay, slotGhost;
	List<Integer> dinos;
	public BATTLE_STARTBATTLE_ACK(Room r, RoomSlot s, int enter, int error, int slotPlay, int slotGhost, List<Integer> dinos)
	{
		super();
		this.r = r;
		this.s = s;
		this.enter = enter;
		this.error = error;
		this.slotPlay = slotPlay;
		this.slotGhost = slotGhost;
		this.dinos = dinos;
		if (r != null && s != null)
			EssencialUtil.missionComplete(r, s, new FragInfos(), r.isSpecialMode(), false, enter == 0, false, enter == 1);
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		if (error == 1)
		{
			WriteD(s.id);
			WriteC(enter);
			WriteH(slotPlay);
			if (r.isRoundMap() || r.type == ModesEnum.DINO || r.type == ModesEnum.CROSSCOUNTER)
			{
				WriteH(r.teamRound(0));
				WriteH(r.teamRound(1));
				if (r.isGhostMode(0))
					WriteH(slotGhost);
				else
				{
					if (r.type == ModesEnum.SABOTAGEM || r.type == ModesEnum.DEFESA)
					{
						WriteH(r.bar1);
						WriteH(r.bar2);
					    for (RoomSlot slot : r.SLOT)
					    	WriteH(slot.bar1);
					    if (r.type == ModesEnum.DEFESA)
					    {
					    	for (RoomSlot slot : r.SLOT)
					    	{
							   	WriteH(slot.bar2);
					    	}
					    }
					}
					else if (r.type == ModesEnum.DINO || r.type == ModesEnum.CROSSCOUNTER)
					{
						WriteC(r.dinossaur()); //0
						WriteH(slotPlay);
						int trex = (dinos.size() == 1 || r.type == ModesEnum.CROSSCOUNTER) ? 255 : r.rexDino;
						WriteC(trex);
						for (int slot : dinos)
							if (slot != r.rexDino && r.type == ModesEnum.DINO || r.type == ModesEnum.CROSSCOUNTER)
								WriteC(slot);
						int maskSlot = 8 - dinos.size() - (trex == 255 ? 1 : 0);
						for (int i = 0; i < maskSlot; i++)
							WriteC(255);
						WriteC(255);
						WriteC(255);
						WriteC(37); //89
					}
				}
			}
		}
	}
}