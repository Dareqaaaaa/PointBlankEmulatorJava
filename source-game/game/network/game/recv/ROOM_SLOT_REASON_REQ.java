package game.network.game.recv;

import java.awt.*;

import core.info.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_SLOT_REASON_REQ extends game.network.game.GamePacketREQ
{
	int slot;
	String reason;
	@Override
	public void readImpl()
	{
		slot = ReadD();
		reason = ReadS(ReadD());
	}
	@Override
	public void runImpl()
	{
		//GClass10
		Software.LogColor("ROOM_SLOT_REASON_REQ: pId: " + client.pId + "; Slot: " + slot + "; Reason: " + reason, Color.GREEN);
	}
}