package game.network.game.sent;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_DEATH_ACK extends game.network.game.GamePacketACK
{
	Room r;
	FragInfos info;
	RoomSlot killer;
	public BATTLE_DEATH_ACK(Room r, FragInfos info, RoomSlot killer)
	{
		super();
		this.r = r;
		this.info = info;
		this.killer = killer;
	}
	@Override
	public void writeImpl()
	{
		WriteC(info.victimIdx);
        WriteC(info.count);
        WriteC(info.slot);
        WriteD(info.weapon);
		WriteH(info.position.posX);
		WriteH(info.position.posY);
		WriteH(info.position.posZ);
		WriteH(info.position.camX);
		WriteH(info.position.camY);
		WriteH(info.position.area);
        WriteC(info.flag);
        for (Frag frag : info.frags)
    	{
            WriteC(frag.index);
            WriteC(frag.hits);
            WriteH(killer.killMessage.value);
    		WriteH(frag.position.posX);
    		WriteH(frag.position.posY);
    		WriteH(frag.position.posZ);
    		WriteH(frag.position.camX);
    		WriteH(frag.position.camY);
    		WriteH(frag.position.area);
            WriteC(frag.flag);
		}
        WriteH(r.redKills);
        WriteH(r.blueKills);
        WriteH(r.blueKills);
        WriteH(r.redKills);
	    for (RoomSlot slot : r.SLOT)
        {
			WriteH(slot.allKills);
			WriteH(slot.allDeaths);
		}
	    int kOL = killer.oneTimeKills;
	    WriteC(kOL > 4 ? 4 : kOL > 0 ? kOL - 1 : 0);
        WriteH(info.score);
        if (r.type == ModesEnum.DINO)
        {
        	WriteH(r.redDino);
    		WriteH(r.blueDino);
        }
	}
}