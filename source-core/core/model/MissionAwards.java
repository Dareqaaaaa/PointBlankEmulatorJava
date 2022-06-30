package core.model;

/**
 * 
 * @author Henrique
 *
 */

public class MissionAwards
{
    public int id, blue_order, item_id, exp, gp, count;
    public MissionAwards(int id, int blue_order, int exp, int gp, int item_id, int count)
    {
        this.id = id;
        this.blue_order = blue_order;
        this.item_id = item_id;
        this.exp = exp;
        this.gp = gp;
        this.count = count;
    }
}