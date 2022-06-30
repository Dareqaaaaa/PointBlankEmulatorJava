package core.model;

/**
 * 
 * @author Henrique
 *
 */

public class PlayerTitles
{
    public byte[] title = new byte[45], position = new byte[8];
    public int equip1, equip2, equip3;
    public int slot = 1;
    public PlayerTitles()
    {
		title[0] = 1;
    }
}