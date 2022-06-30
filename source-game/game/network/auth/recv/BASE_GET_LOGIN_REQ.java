package game.network.auth.recv;

import game.network.auth.*;
import game.network.auth.sent.*;
import core.config.settings.*;
import core.enums.*;
import core.info.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_LOGIN_REQ extends game.network.auth.AuthPacketREQ
{
	LoginAccess state = LoginAccess.SUCESSO;
	String login, passwd, userfilelist, clientversion, hardwareId;
	int connection, locale;
	byte[] ip;
	long launcher_key;
	int error;
	@Override
	public void readImpl()
	{
		try
		{
			clientversion = String.valueOf(ReadC() + "." + ReadH() + "." + ReadH());
			locale = ReadC();
			int login_length = ReadC();
			int password_length = ReadC();
			login = ReadS(login_length);
			passwd = ReadS(password_length);
			client.mac = NetworkUtil.parseMac(ReadB(6));
			ReadH();
			connection = ReadC();
			ip = ReadB(4);
			launcher_key = ReadQ();
			userfilelist = ReadS(32).toUpperCase();
			ReadD();
			hardwareId = ReadS(32);
			ReadC();
		}
		catch (Exception e)
		{
			error = 1;
		}
	}
	@Override
	public void runImpl()
	{
		if (error == 1)
		{
			client.sendPacket(new BASE_GET_LOGIN_ACK(LoginAccess.EVENT_ERROR_LOGIN_BREAK_SESSION.value, 0, ""));
			return;
		}
		ConfigurationLoader con = ConfigurationLoader.gI();
		if ((!con.login_active) || (con.launcher_key != -1 && launcher_key != con.launcher_key) || !clientversion.equals(Software.pc.client_rev) || !userfilelist.equals(con.userfilelist) || (Software.pc.locale.value != locale) || (connection != con.connection))
		{
			client.sendPacket(new BASE_GET_LOGIN_ERROR_ACK(0x80000100, false));
		}
		else
		{
			state = AuthManager.gI().login(client, login, passwd, userfilelist, clientversion, hardwareId, launcher_key, ip);
			client.sendPacket(new BASE_GET_LOGIN_ACK(state.value, client.pId, login));
			if (state == LoginAccess.SUCESSO)
				client.sendPacket(new BASE_GET_MYCASH_ACK(0, 0));
		}
	}
}