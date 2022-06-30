package game.network.game.recv;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_KICK_ACCOUNT_REQ extends game.network.game.GamePacketREQ
{
	int slot;
	@Override
	public void readImpl()
	{
		slot = ReadD();
	}
	@Override
	public void runImpl()
	{
	}
}