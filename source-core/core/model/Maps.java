package core.model;

/**
 * 
 * @author Henrique
 *
 */

public class Maps 
{
	public int id, mode, tag, list;
	public String enable;
	public Maps(int id, int mode, int tag, int list, String enable)
	{
		this.id = id;
		this.mode = mode;
		this.tag = tag;
		this.list = list;
		this.enable = enable;
	}
}