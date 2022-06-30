package game.network.game.sent;

import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class CLAN_MEMBER_CONTEXT_ACK extends game.network.game.GamePacketACK
{
	int error, jogadores;
	public CLAN_MEMBER_CONTEXT_ACK(int error, int jogadores)
	{
		super();
		this.error = error;
		this.jogadores = jogadores;
	}
	@Override
	public void writeImpl()
	{
        WriteD(error);
        if (error == 0)
        {
	        WriteC(jogadores);
	        WriteC(14);
			WriteC((int)Math.ceil(jogadores / 14.0));
	        WriteD(DateTimeUtil.gI().MMddHHmmss(0));
        }
	}
}