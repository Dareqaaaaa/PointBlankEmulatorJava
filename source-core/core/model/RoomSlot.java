package core.model;

import java.util.*;
import java.util.concurrent.*;

import core.enums.*;

/**
 * 
 * @author Henrique
 *
 */

public class RoomSlot
{
    public SlotState state = SlotState.EMPTY;
    public int id, allKills, oneTimeKills, allDeaths, score, allHeadshots, lastKillState, gold, exp, cash, gold_bonus,
    		exp_bonus, tag, objetivo, bar1, bar2, respawn, dinoOnLife, util, ping, latency, piercing_shot, missionActiveCompleted = -1, aiLevel = 1, isPlaying;
	public boolean repeatLastState = false, death = false, espectador = false, checked = false, isUp = false, updateBrooch = false,
			updateInsignia = false, updateMedal = false, updateBlueOrder = false, missionClear = false, battleAccept = false, observGM = false;
	public FragValues killMessage = FragValues.NONE;
	public ItemClassEnum lastItemFrag = ItemClassEnum.UNKNOWN;
    public long player_id;

    public PlayerEquipment equipment = new PlayerEquipment();
    public PlayerEquipment padrao = new PlayerEquipment();
    public PlayerInventory invent = new PlayerInventory();

	public ConcurrentHashMap<Integer, MissionUpdate> missions = new ConcurrentHashMap<Integer, MissionUpdate>();
	public ConcurrentHashMap<Long, Player> armasUsadas = new ConcurrentHashMap<Long, Player>(); 
	public List<PlayerInventory> armasExcluir = new ArrayList<PlayerInventory>();
	
	public RoomSlot newSlot, oldSlot;
	public RoomSlot(RoomSlot newSlot, RoomSlot oldSlot)
	{
		this.newSlot = newSlot;
		this.oldSlot = oldSlot;
	}
	public RoomSlot(int id, long player_id, SlotState state)
	{
		this.id = id;
		this.player_id = player_id;
		this.state = state;
	}
}