package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_WAR_TEAM_CHATTING_ACK extends game.network.game.GamePacketACK 
{
	Player p;
	String msg;
	public CLAN_WAR_TEAM_CHATTING_ACK(Player p, String msg) 
	{
		super();
		this.p = p;
		this.msg = msg;
	}
	@Override
	public void writeImpl()
	{
		WriteC(0);
        WriteC(p.name.length() + 1);
        WriteS(p.name, p.name.length() + 1);
        WriteC(msg.length() + 1);
        WriteS(msg, msg.length() + 1);
	}
}