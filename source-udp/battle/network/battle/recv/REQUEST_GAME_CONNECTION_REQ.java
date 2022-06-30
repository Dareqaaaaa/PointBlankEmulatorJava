package battle.network.battle.recv;

import java.awt.*;

import core.info.*;
import battle.network.battle.*;

/**
 * 
 * @author Henrique
 *
 */

public class REQUEST_GAME_CONNECTION_REQ extends battle.network.battle.BattlePacketREQ
{
	int value;
	String key;
	@Override
	public void readImpl()
	{
		value = ReadC();
		key = ReadS(ReadH());
	}
	@Override
	public void runImpl()
	{
		if (key.equals("131f636e03f0192dd35700f0fe102a1cc03d3d"))
		{
			switch (value)
			{
				case 0: Software.LogColor(" BattleServerManager denial connection", Color.MAGENTA); break;
				case 1: Software.LogColor(" BattleServerManager accepted connection", Color.MAGENTA); break;
			}
			BattleManager bm = BattleManager.gI();
			if (bm.client == null)
				bm.client = client;
		}
		else
		{
			Software.LogColor(" BattleServerManager wrong key (" + key + ")", Color.YELLOW);
		}
	}
}