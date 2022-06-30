package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class PLAYER_COUPON_INFO_ACK extends game.network.game.GamePacketACK
{
	Player p;
	int value;
	public PLAYER_COUPON_INFO_ACK(Player p, int value)
	{
		super();
		this.p = p;
		this.value = value;
	}
	@Override
	public void writeImpl()
	{
		WriteH(value);
        WriteD(p.false_rank);
        WriteD(p.false_rank);
        WriteS(p.false_nick, 33);
		WriteH(p.cor_mira);
	}
}