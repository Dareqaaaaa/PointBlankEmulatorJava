package game.network.game.recv;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_XINGCODE_REQ extends game.network.game.GamePacketREQ
{
	byte[] bytes;
	@Override
	public void readImpl()
	{
		bytes = ReadB(buffer.readableBytes());
	}
	@Override
	public void runImpl()
	{
	}
}