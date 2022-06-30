package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class SERVER_MESSAGE_ANNOUNCE_ACK extends game.network.game.GamePacketACK
{
	String msg;
	public SERVER_MESSAGE_ANNOUNCE_ACK(String msg)
	{
		this.msg = msg;
	}
	@Override
	public void writeImpl()
	{
		WriteD(2);
		WriteH(msg.length()); //1024
		WriteSv2(msg);
	}
}