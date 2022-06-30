package core.model;

/**
 * 
 * @author Henrique
 *
 */

public class MapLocation
{
	public int map, index;
	public String local;
	public MapLocation(int map, int index, String local)
	{
		this.map = map;
		this.index = index;
		this.local = local;
	}
}