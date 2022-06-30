package core.config.dat;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import core.enums.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class MissionsDAT extends BinaryEncrypt
{
	public List<CardAwards> cardsReward = new ArrayList<CardAwards>();
	public ConcurrentHashMap<Integer, MissionAwards> missionReward = new ConcurrentHashMap<Integer, MissionAwards>();
	public List<Card> cards = new ArrayList<Card>();
	public ConcurrentHashMap<Integer, Integer> buy = new ConcurrentHashMap<Integer, Integer>();
    public int missionList;
	public MissionsDAT()
	{
		super("data/package/cards.dat");
		try
		{ Load(); }
		catch (IOException e)
		{ e.printStackTrace(); }
	}
	void Load() throws FileNotFoundException, IOException
	{
		/*int sizeAward = br.ReadD();
		int sizeReward = br.ReadD();
		int sizeList = br.ReadD();
		int sizeMission = br.ReadD();
		for (int i = 0; i < sizeAward; i++)
			cardsReward.add(new CardAwards(br.ReadD(),
					br.ReadD(),
					br.ReadD(),
					br.ReadD(),
					br.ReadD(),
					br.ReadD(),
					br.ReadD()));
		for (int i = 0; i < sizeReward; i++)
		{
			int id = br.ReadD();
			missionReward.put(id, new MissionAwards(id,
					br.ReadD(),
					br.ReadD(),
					br.ReadD(),
					br.ReadD(),
					br.ReadD()));
		}
		br.ReadB(sizeList * 16);
		for (int i = 0; i < sizeMission; i++)
		{
			int cardIdx = br.ReadD();
			int sizeCards = br.ReadD();
			for (int j = 0; j < sizeCards; j++)
			{
				Card c = new Card();
				c.id = br.ReadD();
				c.limit = br.ReadD();
				c.mission_id = br.ReadD();
				c.type = MissionType.values()[br.ReadD()];
				c.cardIdx = cardIdx;
				cards.add(c);
			}
		}
		for (CardsEnum enums : CardsEnum.values())
		{
			buy.put(enums.id, enums.gold);
			missionList += 1 << enums.id;
		}*/
		close();
	}
	public int getCard(int mission, int card, byte[] list)
	{
	    int value = 0;
	    for (Card c : getCardsToId(mission, card))
        	if (list[getValue(c.id, c.mission_id) - 1] >= c.limit)
        		value |= (15 << 4 * c.mission_id);
	    return value;
	}
	public List<Card> getCardsToId(int cardIdx, int id)
    {
		List<Card> list = new ArrayList<Card>();
		for (Card c : cards)
			if ((c.id == id && id != -1 || id == -1) && c.cardIdx == cardIdx)
				list.add(c);
		return list;
    }
	public List<CardAwards> getCardAwards(int mission, int cartao)
    {
         List<CardAwards> list = new ArrayList<CardAwards>();       
         for (CardAwards c : cardsReward)
             if (c.id == mission && c.card == cartao)
            	 list.add(c);
         return list;
    }
	public int getValue(int cId, int cMId)
    {
        if (cId == 0 && cMId == 0) return 1;
        else if (cId == 0 && cMId == 1) return 2;
        else if (cId == 0 && cMId == 2) return 3;
        else if (cId == 0 && cMId == 3) return 4;
        else if (cId == 1 && cMId == 0) return 5;
        else if (cId == 1 && cMId == 1) return 6;
        else if (cId == 1 && cMId == 2) return 7;
        else if (cId == 1 && cMId == 3) return 8;
        else if (cId == 2 && cMId == 0) return 9;
        else if (cId == 2 && cMId == 1) return 10;
        else if (cId == 2 && cMId == 2) return 11;
        else if (cId == 2 && cMId == 3) return 12;
        else if (cId == 3 && cMId == 0) return 13;
        else if (cId == 3 && cMId == 1) return 14;
        else if (cId == 3 && cMId == 2) return 15;
        else if (cId == 3 && cMId == 3) return 16;
        else if (cId == 4 && cMId == 0) return 17;
        else if (cId == 4 && cMId == 1) return 18;
        else if (cId == 4 && cMId == 2) return 19;
        else if (cId == 4 && cMId == 3) return 20;
        else if (cId == 5 && cMId == 0) return 21;
        else if (cId == 5 && cMId == 1) return 22;
        else if (cId == 5 && cMId == 2) return 23;
        else if (cId == 5 && cMId == 3) return 24;
        else if (cId == 6 && cMId == 0) return 25;
        else if (cId == 6 && cMId == 1) return 26;
        else if (cId == 6 && cMId == 2) return 27;
        else if (cId == 6 && cMId == 3) return 28;
        else if (cId == 7 && cMId == 0) return 29;
        else if (cId == 7 && cMId == 1) return 30;
        else if (cId == 7 && cMId == 2) return 31;
        else if (cId == 7 && cMId == 3) return 32;
        else if (cId == 8 && cMId == 0) return 33;
        else if (cId == 8 && cMId == 1) return 34;
        else if (cId == 8 && cMId == 2) return 35;
        else if (cId == 8 && cMId == 3) return 36;
        else if (cId == 9 && cMId == 0) return 37;
        else if (cId == 9 && cMId == 1) return 38;
        else if (cId == 9 && cMId == 2) return 39;
        else if (cId == 9 && cMId == 3) return 40;
        return 0;
    }
	static final MissionsDAT INSTANCE = new MissionsDAT();
	public static MissionsDAT gI()
	{
		return INSTANCE;
	}
}