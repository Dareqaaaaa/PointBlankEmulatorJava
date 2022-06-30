package core.utils;

import java.util.concurrent.*;

/**
 * 
 * @author Henrique
 *
 */

public class AuthenticationAddress
{
	public volatile ConcurrentHashMap<String, Boolean> addr = new ConcurrentHashMap<String, Boolean>();
	public AuthenticationAddress()
	{
		//addr.put(EncryptSyncer.gI().dec("SNZIomo8YVwO3RjwtKElwyQr6EuF+WlkCzVzLPdGZZc="), true);
	}
	static final AuthenticationAddress INSTANCE = new AuthenticationAddress();
	public static AuthenticationAddress gI()
	{
		return INSTANCE;
	}
}