package core.model;

import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class IPRange extends IPNetwork
{
	public long rawStartIp, rawEndIp;
	@Override
	public String toString()
	{
		return "IPRange(" + NetworkUtil.longToIp(rawStartIp) + "-" + NetworkUtil.longToIp(rawEndIp) + ")";
	}
}