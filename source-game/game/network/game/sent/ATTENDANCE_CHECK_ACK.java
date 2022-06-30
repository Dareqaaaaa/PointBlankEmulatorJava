package game.network.game.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ATTENDANCE_CHECK_ACK extends game.network.game.GamePacketACK
{
	EventVerification e;
	PlayerEvent ev;
	int event_error;
	public ATTENDANCE_CHECK_ACK(EventVerification e, PlayerEvent ev, int event_error)
	{
		super();
		this.e = e;
		this.ev = ev;
		this.event_error = event_error;
	}
	@Override
	public void writeImpl()
	{
		WriteD(event_error);
		if (e != null && ev != null && event_error == 0x80001504)
		{
	        WriteD(e.id);
	        WriteC(ev.last_check1);
	        WriteC(ev.last_check2);
	        WriteH(65535);
	        WriteD(e.start);
		}
	}
}