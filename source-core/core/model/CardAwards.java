package core.model;

/**
 * 
 * @author Henrique
 *
 */

public class CardAwards
{
    public int id, card, insignia, medal, brooch, exp, gp;
    public CardAwards(int id, int card, int insignia, int medal, int brooch, int exp, int gp)
    {
        this.id = id;
        this.card = card;
        this.insignia = insignia;
        this.medal = medal;
        this.brooch = brooch;
        this.exp = exp;
        this.gp = gp;
    }
}