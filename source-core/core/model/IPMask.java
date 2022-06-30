package core.model;

import java.net.*;

/**
 * 
 * @author Henrique
 *
 */

public class IPMask extends IPNetwork
{
	public Inet4Address i4addr;
	public byte maskCtr;
	public int addrInt, maskInt;
	@Override
	public String toString()
	{
		return "IPMask(" + start + "/" + end + ")";
	}
}