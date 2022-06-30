package core.config.settings;

import java.io.*;

import core.udp.events.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class ConfigurationLoader extends SystemLoader
{
	public boolean login_active;
	public boolean auto_account;
	public boolean only_gm;
	public boolean outpost;
	public boolean mission_active;
	public boolean room_create;
	public boolean battle_cash;

	public int account_for_address;
	public int votekick_requirits_rank;
	public int clan_requerits_rank;
	public int clan_requerits_gold;

	public String driver;
	public String url;
	public String username;
	public String password;

	public int maxActive;
	public int maxIdle;

	public String validationQuery;
	public String charset;

	public int login_min_length = 3;
	public int login_max_length = 32;
	public int pass_min_length = 3;
	public int pass_max_length = 32;
	
	public int connection;
	public int limit_online;
	
	public long launcher_key;
	
	public String userfilelist;
	public String bad_string;
	public String has_string;
	public UDP_Type udp;

	public ConfigurationLoader()
	{
		super("data/configuration/settings.dat");
	}
	@Override
	public void LOAD()
	{
		super.LOAD();
		login_active = ReadL("login_active");
		auto_account = ReadL("auto_account");
		only_gm = ReadL("only_gm");
		outpost = ReadL("outpost");
		mission_active = ReadL("mission_active");
		room_create = ReadL("room_create");
		battle_cash = ReadL("battle_cash");
		account_for_address = ReadD("account_for_address");
		votekick_requirits_rank = ReadD("votekick_requirits_rank");
		clan_requerits_rank = ReadD("clan_requerits_rank");
		clan_requerits_gold = ReadD("clan_requerits_gold");
		driver = ReadS("driver");
		url = ReadS("url");
		username = ReadS("username");
		password = ReadS("password");
		maxActive = ReadD("maxActive");
		maxIdle = ReadD("maxIdle");
		validationQuery = ReadS("validationQuery");
		charset = ReadS("charset");

		connection = ReadD("connection");
		limit_online = ReadD("limit_online");
		launcher_key = ReadQ("launcher_key");
		bad_string = ReadS("bad_string");
		has_string = ReadS("has_string");
		udp = UDP_Type.valueOf(ReadS("udp_server"));

		File file = new File("data/configuration/UserFileList.dat");
		userfilelist = NetworkUtil.getMD5Digest(file).toUpperCase();
		file = null;

		PROPERTIES.clear();
	}
	static final ConfigurationLoader INSTANCE = new ConfigurationLoader();
	public static ConfigurationLoader gI()
	{
		return INSTANCE;
	}
}