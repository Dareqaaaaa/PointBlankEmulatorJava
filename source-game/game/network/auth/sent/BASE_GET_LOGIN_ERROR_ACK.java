package game.network.auth.sent;

import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_LOGIN_ERROR_ACK extends game.network.auth.AuthPacketACK
{
	protected int error;
	protected boolean chan;
	public BASE_GET_LOGIN_ERROR_ACK(int error, boolean chan)
	{
		super();
		this.error = error;
		this.chan = chan;
	}
	@Override
	public void writeImpl()
	{
		WriteD(DateTimeUtil.gI().MMddHHmmss(0));
		WriteD(error);
		WriteD(chan ? 1 : 0);
		if (chan)
			WriteC(0);
	}
}