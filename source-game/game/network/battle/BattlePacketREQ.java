package game.network.battle;

import core.network.*;

/**
 * 
 * @author Henrique
 *
 */

public abstract class BattlePacketREQ extends PacketREQ<BattleConnection>
{
	@Override
	public void run()
	{
		try
		{
			runImpl();
		}
		catch (Exception ex)
		{
			exceptionLOG(ex);
		}
	}
}