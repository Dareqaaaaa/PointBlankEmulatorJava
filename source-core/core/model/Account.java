package core.model;

import game.network.auth.*;

/**
 * 
 * @author Henrique
 *
 */

public class Account
{
	public long id, launcher_key;
	public String login, senha,  email, ip, mac, data, client, userfilelist, hwid, ip_socket;
	public int last_port, ban_expires, bans;
	public boolean actived = true;
	public AuthConnection connection;
	public Account(String login, String senha)
	{
		this.login = login;
		this.senha = senha;
		email = String.valueOf(login + "@pointblank");
	}
}