package core.model;

import core.enums.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class User
{
	public String client_rev, udp_rev, box;
	public String[] info, info2;
	public ClientLocale locale;
	public byte[] IP_Bytes;
	public User(String[] info, String[] info2, ClientLocale locale, String box)
	{
		//[0] Name
		//[1] Client Date
		//[2] Client Date Hour
		//[3] Client Type
		//[4] UDP PROTOCOL VER 1
		//[5] UDP PROTOCOL VER 2
		//[6] Build
		//[7] Address
		//[8] Client Ver 1
		//[9] Client Ver 2
		//[10] Client Ver 3
		//[11] Client Ver 4
		//[12] Client Rev
		this.info = info;
		this.info2 = info2;
		this.locale = locale;
		this.box = box;
		client_rev = info[8] + "." + info[9] + "." + info[10];
		udp_rev = info[4] + "." + info[5];
		IP_Bytes = NetworkUtil.parseIpToBytes(info[7]);
	}
}