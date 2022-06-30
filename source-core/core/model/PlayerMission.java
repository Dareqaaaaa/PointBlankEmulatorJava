package core.model;

/**
 * 
 * @author Henrique
 *
 */

public class PlayerMission
{
	public byte[] list1 = new byte[40], list2 = new byte[40], list3 = new byte[40], list4 = new byte[40];
	public int card1, card2, card3, card4, actual_mission;
	public boolean selectedCard = false;
}