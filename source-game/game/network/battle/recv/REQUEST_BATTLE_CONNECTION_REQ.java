package game.network.battle.recv;

import java.awt.*;

import core.info.*;
import game.network.battle.*;
import game.network.battle.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_BATTLE_CONNECTION_REQ extends BattlePacketREQ
{
	String key;
	@Override
	public void readImpl()
	{
		key = ReadS(ReadH());
	}
	@Override
	public void runImpl()
	{
		if (key.equals("131f636e03f0192dd35700f0fe102a1cc03d3d"))
		{
			client.sendPacket(new REQUEST_BATTLE_CONNECTION_ACK(BattleManager.gI().accept(client)));
		}
		else
		{
			Software.LogColor(" BattleServerManager wrong key ", Color.YELLOW);
		}
	}
}