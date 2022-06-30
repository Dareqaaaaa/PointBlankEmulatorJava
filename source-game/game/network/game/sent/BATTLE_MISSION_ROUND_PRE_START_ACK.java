package game.network.game.sent;

import java.util.*;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_MISSION_ROUND_PRE_START_ACK extends game.network.game.GamePacketACK
{
	Room r;
	boolean specialMode;
	int slotPlay;
	List<Integer> dinos;
	public BATTLE_MISSION_ROUND_PRE_START_ACK(Room r, boolean specialMode, int slotPlay, List<Integer> dinos)
	{
		super();
		this.r = r;
		this.specialMode = specialMode;
		this.slotPlay = slotPlay;
		this.dinos = dinos;
	}
	@Override
	public void writeImpl()
	{
		WriteH(slotPlay);
		if (r.type == ModesEnum.DINO || r.type == ModesEnum.CROSSCOUNTER)
		{
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
		}
		else
		{
			if (specialMode)
			{
				WriteB(new byte[10]);
			}
			else
			{
				WriteB(new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255});
			}
		}
	}
}