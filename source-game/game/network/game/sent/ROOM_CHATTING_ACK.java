package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_CHATTING_ACK extends game.network.game.GamePacketACK
{
	int slot, type;
	boolean access;
	String message;
	public ROOM_CHATTING_ACK(int slot, int type, boolean access, String message)
	{
		super();
		this.slot = slot;
		this.type = type;
		this.access = access;
		this.message = message;
	}
	@Override
	public void writeImpl()
	{
		WriteH(type);
		WriteD(slot);
		WriteC(access ? 1 : 0);
		WriteD(message.length() + 1);
		WriteS(message, message.length() + 1);
	}
}