package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_SYSTEM_MESSAGE_ACK extends game.network.game.GamePacketACK
{
	String owner, msg;
	int colored;
	public BASE_SYSTEM_MESSAGE_ACK(String owner, String msg, int colored)
	{
		super();
		this.owner = owner;
		this.msg = msg;
		this.colored = colored;
	}
	@Override
	public void writeImpl()
	{
		WriteS(owner, 33);
		WriteC(colored);
		WriteH(msg.length() + 1);
		WriteS(msg, msg.length() + 1);
	}
}