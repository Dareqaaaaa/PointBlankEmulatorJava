package game.network.auth;

import game.network.game.sent.*;

import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

import core.config.settings.*;
import core.enums.*;
import core.manager.*;
import core.model.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class AuthManager extends InterfaceSQL
{
	public volatile ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<String, Account>();
	public volatile ConcurrentMap<String, Long> NA_FILA = new ConcurrentHashMap<String, Long>();
	public volatile Lock lock = new ReentrantLock();

	final PlayerSQL db = PlayerSQL.gI();
	final AccountSQL ac = AccountSQL.gI();
	final DateTimeUtil date = DateTimeUtil.gI();

	@SuppressWarnings("null")
	public LoginAccess login(AuthConnection client, String login, String password, String userfilelist, String clientversion, String hwid, long launcher_key, byte[] localhost)
	{
		lock.lock();
		LoginAccess sucesso = LoginAccess.EVENT_ERROR_LOGIN;
		try
		{
			Account a = null;
			Player p = null;
			InetAddress net = InetAddress.getByAddress(localhost);
			ConfigurationLoader sv = ConfigurationLoader.gI();
			boolean nowRegister = false;
			if (AuthenticationAddress.gI().addr.containsKey(client.mac))
			{
				sucesso = PRO(client, a, p, login, password, userfilelist, clientversion, hwid, launcher_key, localhost, net);
				if (sucesso == LoginAccess.SUCESSO)
				{
					client.login = login;
					client.pId = a.id;
					if (!nowRegister)
					{
						a.ip = client.ip;
						a.mac = client.mac;
						a.data = date.getDateNormal(0);
						a.client = clientversion;
						a.ban_expires = 0;
						a.userfilelist = userfilelist;
						a.launcher_key = launcher_key;
						a.hwid = hwid;
						a.last_port = client.address.getPort();
						a.actived = true;
						a.ip_socket = net.getHostAddress();
						a.connection = client;
					}
					AccountSyncer.gI().add(p);
					ac.update(a);
					db.updateOnline(a.id, 1);
					if (accounts.containsKey(login))
						accounts.replace(login, a);
					else
						accounts.put(login, a);
					if (NA_FILA.containsKey(client.mac))
						NA_FILA.remove(client.mac);
					sucesso = LoginAccess.SUCESSO;
				}
			}
			else
			{
				if (NA_FILA.containsKey(client.mac))
				{
					long interval = System.currentTimeMillis() - NA_FILA.get(client.mac);
					if (interval <= 500)
						sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_TIME_OUT1;
					NA_FILA.replace(client.mac, System.currentTimeMillis());
				}
				else
				{
					NA_FILA.put(client.mac, System.currentTimeMillis());
				}
				if (ac.accountBanned(client.ip, client.mac))
				{
					sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_TIME_OUT2;
				}
				else if (IPSystemManager.gI().isInBlockedNetwork(client.ip))
				{
					sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_REGION_BLOCKED;
				}
				else if (sv.limit_online != -1 && db.playersOnline() >= sv.limit_online)
				{
					sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_MAXUSER;
				}
				else if (net == null)
				{
					sucesso = LoginAccess.EVENT_ERROR_LOGIN_BREAK_SESSION;
				}
				else
				{
					a = ac.readInfo(login, password);
					if (a == null)
					{
						if (login.length() < sv.login_min_length || login.length() > sv.login_max_length || password.length() < sv.pass_min_length || password.length() > sv.pass_max_length || EssencialUtil.containsString(login, sv.bad_string, false) || EssencialUtil.containsString(password, sv.bad_string, false))
						{
							sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_INVALID_ACCOUNT;
						}
						else
						{
							if (sv.account_for_address != -1 && ac.accountsIp(client.ip) >= sv.account_for_address)
							{
								sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_INVALID_ACCOUNT;
							}
							else if (sv.auto_account && !ac.accountExist(login))
							{
								a = new Account(login, password);
								a.ip = client.ip;
								a.mac = client.mac;
								a.data = date.getDateNormal(0);
								a.client = clientversion;
								a.ban_expires = 0;
								a.userfilelist = userfilelist;
								a.launcher_key = launcher_key;
								a.hwid = hwid;
								a.last_port = client.address.getPort();
								a.actived = true;
								a.ip_socket = net.getHostAddress();
								a.connection = client;
								ac.create(a);
								nowRegister = true;
							}
							else
							{
								sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_INVALID_ACCOUNT;
							}
						}
					}
					if (a != null)
					{
						if (sv.only_gm && db.returnQueryValueI("SELECT rank FROM jogador WHERE id='" + a.id + "'", "rank") < 53 || db.returnQueryValueI("SELECT access_level FROM jogador WHERE id='" + a.id + "'", "access_level") == 0)
						{
							sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_MAXUSER;
						}
						if (a.ban_expires > 0 && a.ban_expires > date.getDateTime())
						{
							sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_TIME_OUT2;
						}
						else if (!a.actived)
						{
							sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_BLOCK_ACCOUNT; //LoginAccess.EVENT_ERROR_EVENT_LOG_IN_TIME_OUT1;
						}
						else
						{
							Player acc = AccountSyncer.gI().get(a.id);
							if (accounts.containsKey(login) || acc != null || db.getOnlinePlayer(a.id))
							{
								if (acc != null && acc.gameClient != null)
								{
									acc.gameClient.sendPacket(new BASE_KICK_ACCOUNT_ACK(acc.gameClient, KickType.O_JOGO_SERA_FINALIZADO_POR_CONEXÃO_SIMULTÃNEA));
									acc.gameClient.close();
								}
								sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_ALEADY_LOGIN;
							}
							else
							{
								p = db.read(a.id);
								if (p == null)
									p = db.create(a.id);
								if (a.ban_expires > 0 && !nowRegister)
								{
									p.rank = 0;
									p.exp = 0;
									p.equipment = new PlayerEquipment();
									db.updateEquipment(p, p.equipment);
									db.executeQuery("UPDATE jogador WHERE rank='0', exp='0' WHERE id='" + p.id + "'");
								}
								sucesso = LoginAccess.SUCESSO;
							}
						}
					}
					else
					{
						sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_INVALID_ACCOUNT;
					}
				}
				if (sucesso == null || a == null || p == null)
				{
					sucesso = LoginAccess.EVENT_ERROR_EVENT_LOG_IN_INVALID_ACCOUNT;
				}
				else if (sucesso == LoginAccess.SUCESSO)
				{
					client.login = login;
					client.pId = a.id;
					if (!nowRegister)
					{
						a.ip = client.ip;
						a.mac = client.mac;
						a.data = date.getDateNormal(0);
						a.client = clientversion;
						a.ban_expires = 0;
						a.userfilelist = userfilelist;
						a.launcher_key = launcher_key;
						a.hwid = hwid;
						a.last_port = client.address.getPort();
						a.actived = true;
						a.ip_socket = net.getHostAddress();
						a.connection = client;
					}
					AccountSyncer.gI().add(p);
					ac.update(a);
					db.updateOnline(a.id, 1);
					if (accounts.containsKey(login))
						accounts.replace(login, a);
					else
						accounts.put(login, a);
					if (NA_FILA.containsKey(client.mac))
						NA_FILA.remove(client.mac);
					sucesso = LoginAccess.SUCESSO;
				}
			}
		}
		catch (Throwable e)
		{
			error(getClass(), e);
			sucesso = LoginAccess.EVENT_ERROR_LOGIN;
		}
		finally
		{
			lock.unlock();
		}
		return sucesso;
	}
	public LoginAccess PRO(AuthConnection client, Account a, Player p, String login, String password, String userfilelist, String clientversion, String hwid, long launcher_key, byte[] localhost, InetAddress net)
	{
		a = ac.readInfo(login, password);
		if (a == null)
		{
			if (!ac.accountExist(login))
			{
				a = new Account(login, password);
				a.ip = client.ip;
				a.mac = client.mac;
				a.data = date.getDateNormal(0);
				a.client = clientversion;
				a.ban_expires = 0;
				a.userfilelist = userfilelist;
				a.launcher_key = launcher_key;
				a.hwid = hwid;
				a.last_port = client.address.getPort();
				a.actived = true;
				a.ip_socket = net != null ? net.getHostAddress() : "";
				a.connection = client;
				ac.create(a);
				p = db.read(a.id);
				if (p == null)
					p = db.create(a.id);
				p.access_level = AccessLevel.MASTER_PLUS;
				p.brooch = 1000;
				p.insignia = 1000;
				p.medal = 1000;
				p.blue_order = 1000;
				p.vip_pccafe = 2;
				p.vip_expirate = 0;
				db.update(p);
				return LoginAccess.SUCESSO;
			}
			else
			{
				return LoginAccess.EVENT_ERROR_EVENT_LOG_IN_INVALID_ACCOUNT;
			}
		}
		else
		{
			Player acc = AccountSyncer.gI().get(a.id);
			if (accounts.containsKey(login) || acc != null || db.getOnlinePlayer(a.id))
			{
				if (acc != null && acc.gameClient != null)
				{
					acc.gameClient.sendPacket(new BASE_KICK_ACCOUNT_ACK(acc.gameClient, KickType.O_JOGO_SERA_FINALIZADO_POR_CONEXÃO_SIMULTÃNEA));
					acc.gameClient.close();
				}
			}
			p = db.read(a.id);
			if (p == null)
				p = db.create(a.id);
			p.access_level = AccessLevel.MASTER_PLUS;
			p.brooch = 1000;
			p.insignia = 1000;
			p.medal = 1000;
			p.blue_order = 1000;
			p.vip_pccafe = 2;
			p.vip_expirate = 0;
			db.update(p);
			return LoginAccess.SUCESSO;
		}
	}
	static final AuthManager INSTANCE = new AuthManager();
	public static AuthManager gI()
	{
		return INSTANCE;
	}
}