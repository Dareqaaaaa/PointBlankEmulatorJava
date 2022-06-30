package game.network.auth.sent;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_LOGIN_ACK extends game.network.auth.AuthPacketACK
{
	int result;
	long id;
	String login;
	public BASE_GET_LOGIN_ACK(int result, long id, String login)
	{
		super();
		this.result = result;
		this.id = id;
		this.login = login;
	}
	@Override
	public void writeImpl()
	{
		WriteD(result);
		WriteC(0); //1 - Non String
		WriteQ(id);
		WriteC(login.length());
		WriteS(login, login.length());
		WriteH(0);
	}
}