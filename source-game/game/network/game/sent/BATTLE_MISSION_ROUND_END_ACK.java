package game.network.game.sent;

import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_MISSION_ROUND_END_ACK extends game.network.game.GamePacketACK
{
	Room r;
	WinnerRound type;
	TimeEnum team;
	public BATTLE_MISSION_ROUND_END_ACK(Room r, TimeEnum team, WinnerRound type)
	{
		super();
		this.r = r;
		this.team = team;
		this.type = type;
	}
	@Override
	public void writeImpl()
	{
		WriteC(team.ordinal());
		WriteC(type.ordinal());
		WriteH(r.teamRound(0));
		WriteH(r.teamRound(1));
	}
}