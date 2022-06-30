package game.network.game.sent;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_CHANGE_PASSWD_ACK extends game.network.game.GamePacketACK
{
	String passwd;
	public ROOM_CHANGE_PASSWD_ACK(String passwd)
	{
		super();
		this.passwd = passwd;
	}
	@Override
	public void writeImpl()
	{
		WriteS(passwd, 4);
	}
}