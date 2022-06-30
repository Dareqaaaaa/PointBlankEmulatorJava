package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class PLAYER_UPDATE_RANK_ACK extends game.network.game.GamePacketACK
{
	Player p; 	
	public PLAYER_UPDATE_RANK_ACK(Player p)
	{
		super();
		this.p = p;
	}
	@Override
	public void writeImpl()
	{
		WriteH(0);
        WriteD(p.rank);
        WriteD(p.rank);
        WriteS(p.false_nick, 33);
		WriteH(p.cor_mira);
	}
}