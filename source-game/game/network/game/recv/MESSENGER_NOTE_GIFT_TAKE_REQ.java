package game.network.game.recv;

import java.awt.*;

import core.info.*;

/**
 * 
 * @author Henrique
 *
 */

public class MESSENGER_NOTE_GIFT_TAKE_REQ extends game.network.game.GamePacketREQ
{
	int object;
	@Override
	public void readImpl()
	{
		object = ReadD();
	}
	@Override
	public void runImpl()
	{
		//GClass16
		Software.LogColor("MESSENGER_NOTE_GIFT_TAKE_REQ: pId: " + client.pId + "; Object: " + object, Color.GREEN);
	}
}