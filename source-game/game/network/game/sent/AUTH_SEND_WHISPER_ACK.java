package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class AUTH_SEND_WHISPER_ACK extends game.network.game.GamePacketACK
{
	String sender, message;
	int error;
	public AUTH_SEND_WHISPER_ACK(String sender, String message, int error)
	{
		super();
		this.sender = sender;
		this.message = message;
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		if (error == 0)
		{
			WriteD(error);
			WriteS(sender, 33);
			WriteH(message.length() + 1);
			WriteS(message, message.length() + 1);
		}
		else
		{
			WriteC(0);
			WriteD(error);
			WriteS(sender, 33);
		}
	}
}