package core.model;

/**
 * 
 * @author Henrique
 *
 */

public class SocketComplement
{
	public int port, boss = 1, work = 1;
	public String addr;
	public SocketComplement(String addr, int port, int thread) throws Exception
	{
		this.addr = addr;
		this.port = port;
		work = thread;
	}
	public String getAddr()
	{
		return addr + ":" + port;
	}
}