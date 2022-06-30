package core.model;

/**
 * 
 * @author Henrique
 *
 */

public class RoomBattle
{
	public Room r;
	public int leader;
	public RoomBattle(Room r, int leader)
	{
		this.r = r;
		this.leader = leader;
	}
	public void addPlayer(PlayerBattle p)
	{
		if (r.playersBattle.containsKey(p.slot))
			r.playersBattle.replace(p.slot, p);
		else
			r.playersBattle.put(p.slot, p);
		System.out.println(" Jogador adicionado; Slot: " + p.slot);
	}
	public void removePlayer(int slot)
	{
		if (r.playersBattle.containsKey(slot))
			r.playersBattle.remove(slot);
		System.out.println(" Jogador removido; Slot: " + slot);
	}
	public PlayerBattle getPlayer(int slot)
	{
		return r.playersBattle.containsKey(slot) ? r.playersBattle.get(slot) : null;
	}
	public boolean isUDP(int slot, boolean lider)
	{
		return lider ? slot != leader : slot == leader;
	}
}