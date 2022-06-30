package core.model;

import game.network.battle.*;
import game.network.game.sent.*;
import io.netty.buffer.*;

import java.nio.*;
import java.util.*;
import java.util.concurrent.*;

import core.config.dat.*;
import core.config.settings.*;
import core.config.xml.*;
import core.enums.*;
import core.info.*;
import core.network.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class Room extends core.postgres.sql.InterfaceSQL
{
    public int[] TIMES = new int[] { 3, 5, 7, 5, 10, 15, 20, 25, 30 };
    public int[] KILLS = new int[] { 60, 80, 100, 120, 140, 160 };
    public int[] ROUNDS = new int[] { 0, 3, 5, 7, 9 };
    public int[] RED_TEAM = new int[] { 0, 2, 4, 6, 8, 10, 12, 14 };
    public int[] BLUE_TEAM = new int[] { 1, 3, 5, 7, 9, 11, 13, 15 };

    public volatile RoomSlot[] SLOT = new RoomSlot[16];
    public volatile ConcurrentHashMap<Integer, PlayerBattle> playersBattle = new ConcurrentHashMap<Integer, PlayerBattle>(16);
	public volatile ConcurrentHashMap<Integer, Long> prepared1 = new ConcurrentHashMap<Integer, Long>(16);
	public volatile ConcurrentHashMap<Integer, Long> prepared2 = new ConcurrentHashMap<Integer, Long>(16);
	public volatile ConcurrentHashMap<Long, Integer> votou = new ConcurrentHashMap<Long, Integer>();
	public volatile ConcurrentHashMap<Long, Integer> listHost = new ConcurrentHashMap<Long, Integer>();

	public ModesEnum type;
	public RoomState rstate = RoomState.READY;

	public String name = "", map_name = "", passwd = "";

	public int id, index, channel_id, server_id;
	public int map, stage4v4, allWeapons, randomMap, limit, seeConf, balanceamento, special, killMask, timeLost, ping;
	public int redKills, blueKills, redRounds, blueRounds, redDino,  blueDino,  rexDino, bar1, bar2;
	public int rodadas, respawn, aiCount, aiLevel, last_host;

	public boolean comand = false, modoPorrada = false;

	public volatile long LIDER;
	public volatile List<Long> kikados = new ArrayList<Long>();
	public volatile ScheduledFuture<?> threadBOMB = null;
	public volatile ScheduledFuture<?> threadCOUNTDOWN = null;
	public volatile ScheduledFuture<?> threadVOTE = null;
	public volatile ScheduledFuture<?> threadROUND = null;
	public volatile ScheduledFuture<?> threadCROSS = null;
	public volatile VoteKick votekick = null;

	final PlayerSQL db = PlayerSQL.gI();
	final DateTimeUtil date = DateTimeUtil.gI();
	final Logger logger = new Logger();

	public Room(int id, int channel_id, int server_id)
	{
		this.id = id;
		this.channel_id = channel_id;
		this.server_id = server_id;
		index = (server_id & 255) << 20 | (channel_id & 255) << 12 | (id & 4095);
		type = ModesEnum.DEATHMACH;
		rodadas = 1;
		aiCount = 1;
		last_host = -1;
		ping = 5;
		for (int i = 0; i < 16; i++)
			SLOT[i] = new RoomSlot(i, 0, SlotState.EMPTY);
	}
	public Channel getChannel()
	{
		return GameServerXML.gI().getChannel(channel_id, server_id);
	}
	public Room getRoom()
	{
		return this;
	}
	public Player getLeader()
	{
		return AccountSyncer.gI().get(LIDER);
	}
	public List<Player> getPlayers()
	{
		List<Player> list = new ArrayList<Player>(16);
		for (RoomSlot slot : SLOT)
		{
			Player p = AccountSyncer.gI().get(slot.player_id);
			if (p != null && p.slot >= 0)
			{
				list.add(p);
			}
		}
		return list;
	}
	public RoomSlot getSlot(int id)
	{
		if (id >= 0 && id <= 15)
			return SLOT[id];
		return null;
	}
	public int sizePlayers()
	{
		int size = 0;
		for (RoomSlot slot : SLOT)
		{
			if (slot.player_id > 0 && AccountSyncer.gI().ACCOUNTS.containsKey(slot.player_id))
			{
				size++;
			}
		}
		return size;
	}
	public void badName()
	{
		if (name == null || name == "")
			name = named(IDFactory.gI().randomId(4));
	}
	public void badConfig()
	{
		if (stage4v4 > 1) stage4v4 = 1;
		if (type.ordinal() < 1) type = ModesEnum.values()[1];
		if (type.ordinal() > 14) type = ModesEnum.values()[14];
		if (isSpecialMode())
		{
			if (aiCount < 1) aiCount = 1;
			if (aiCount > 8) aiCount = 8;
			if (aiLevel < 1) aiLevel = 1;
			if (aiLevel > 10) aiLevel = 10;
		}
	}
	public String named(int random)
	{
		switch (random)
		{
			case 1: return "Entra aqui noob!";
			case 2: return "Lista de frag inside";
			case 3: return "Go Go Go PB!";
		}
		return "Point Blank";
	}
	public int calculeScore(FragValues killingMessage)
    {
		if (killingMessage != FragValues.MASS_KILL && killingMessage != FragValues.PIERCING_SHOT)
		{
			if (killingMessage == FragValues.CHAIN_STOPPER) return 8;
            if (killingMessage == FragValues.HEADSHOT) return 10;
            if (killingMessage == FragValues.CHAIN_HEADSHOT) return 14;
            if (killingMessage == FragValues.CHAIN_SLUGGER) return 6;
            if (killingMessage == FragValues.OBJECT_DEFENCE) return 7;
            if (killingMessage != FragValues.SUICIDE) return 5;
		}
		return 6;
    }
	public int getSeed()
	{
		return map * 16 + type.ordinal();
	}
	public int restrictions()
	{
		int restricoes = 0;
		if (randomMap > 0) restricoes += 2;
		if (passwd.length() > 0) restricoes += 4;
		//if (match)  restricoes += 32;
		//if (special == 6 || special == 9) restricoes += 64;
		if (limit > 0 && rstate.ordinal() > 1) restricoes += 128;
		return restricoes;
	}
	public int dinossaur()
	{
		return rodadas == 2 ? 1 : 0;
	}
	public void checkBattlePlayers(int id)
	{
		byte[] team = new byte[2], teamNotGhost = new byte[2], teamDeath = new byte[2], teamPlay = new byte[2];
		int index = id % 2;
		for (RoomSlot slot : SLOT)
		{
			if (slot.state.ordinal() >= 12)
			{
				team[index]++;
				if (!slot.espectador)
				{
					teamNotGhost[index]++;
					if (slot.state == SlotState.BATTLE && slot.respawn > 0)
						teamPlay[index]++;
				}
				if (slot.death) teamDeath[index]++;
			}
		}
		if (isSpecialMode())
		{
			if (team[index] == 0 && _getMaster() == index)
				end(-1);
		}
		else
		{
			if (team[0] > 0 && team[1] > 0)
			{
				if (isGhostMode(0))
				{
					if (index == 0)
					{
						if (teamDeath[0] >= teamPlay[0] || teamNotGhost[0] == 0)
						{
							if (threadBOMB == null)
							{
								round(TimeEnum.TIME_AZUL, WinnerRound.ALLDEATH);
							}
							else if (teamNotGhost[0] == 0 && teamNotGhost[1] == 0)
							{
								round(TimeEnum.TIME_VERMELHO, WinnerRound.ALLDEATH);
							}
						}
	                    else if (teamDeath[1] >= teamPlay[1] || teamNotGhost[1] == 0)
	                    {
	                    	round(TimeEnum.TIME_VERMELHO, WinnerRound.ALLDEATH);
	                    }
					}
					else
					{
						if (teamDeath[1] >= teamPlay[1] || teamNotGhost[1] == 0)
	                    {
	                    	round(TimeEnum.TIME_VERMELHO, WinnerRound.ALLDEATH);
	                    }
						else if (teamDeath[0] >= teamPlay[0] || teamNotGhost[0] == 0)
						{
							if (threadBOMB == null)
							{
								round(TimeEnum.TIME_AZUL, WinnerRound.ALLDEATH);
							}
							else if (teamNotGhost[0] == 0 && teamNotGhost[1] == 0)
							{
								round(TimeEnum.TIME_VERMELHO, WinnerRound.ALLDEATH);
							}
						}
					}
				}
			}
			else
			{
				end(team[0] == 0 ? 1 : team[1] == 0 ? 0 : -1);
			}
		}
	}
	public int teamRound(int team)
	{
		if (isRoundMap())
		{
			return team == 0 ? redRounds : blueRounds;
		}
		else if (type == ModesEnum.DINO)
		{
			return team == 0 ? redDino : blueDino;
		}
		else
		{
			return team == 0 ? redKills : blueKills;
		}
	}
	public int timeVencedor()
	{
		if (isRoundMap())
		{
            if (blueRounds == redRounds) return 2;
            else if (blueRounds > redRounds) return 1;
            else if (blueRounds < redRounds) return 0;
		}
		else if (type == ModesEnum.DINO)
		{
            if (blueDino == redDino) return 2;
            else if (blueDino > redDino) return 1;
            else if (blueDino < redDino) return 0;
		}
		else
		{
            if (blueKills == redKills) return 2;
            else if (blueKills > redKills) return 1;
            else if (blueKills < redKills) return 0;
		}
		return 2;
	}
	public synchronized void startBattle(boolean specialMode)
	{
		boolean isDino = type == ModesEnum.DINO || type == ModesEnum.CROSSCOUNTER;
		int slotPlay = 0, slotGhost = 0;
		for (RoomSlot slot : SLOT)
		{
			Player p = AccountSyncer.gI().get(slot.player_id);
			if (p != null && slot.state == SlotState.BATTLE_READY && slot.battleAccept && slot.isPlaying == 0 && rstate.ordinal() >= 4)
			{
				boolean ghost = rstate == RoomState.BATTLE ? isGhostMode(p.espectador) : false;
				changeState(slot, SlotState.BATTLE, false);
				slot.espectador = ghost;
				slot.death = ghost;
				slot.isPlaying = 1;
				slotPlay += 1 << slot.id;
				if (p.espectador == 1 || !slot.espectador)
					slotGhost += 1 << slot.id;
			}
		}
		BATTLE_RECORD_ACK sent3 = new BATTLE_RECORD_ACK(this); sent3.packingBuffer();
		List<Integer> dinos = EssencialUtil.dinos(this, dinossaur());
		for (RoomSlot slot : SLOT)
		{
			Player p = AccountSyncer.gI().get(slot.player_id);
			if (p != null && slot.state == SlotState.BATTLE && slot.isPlaying == 1)
			{
				slot.isPlaying = 2;
				boolean ghost = rstate == RoomState.BATTLE ? isGhostMode(p.espectador) : false;
				BATTLE_STARTBATTLE_ACK sent1 = new BATTLE_STARTBATTLE_ACK(this, slot, rstate == RoomState.BATTLE ? 1 : 0, 1, slotPlay, slotGhost, dinos); sent1.packingBuffer();
				for (RoomSlot rs : SLOT)
				{
					Player m = AccountSyncer.gI().get(slot.player_id);
					if (m != null && rs.state.ordinal() >= 11)
						m.gameClient.sendPacketBuffer(sent1.buffer);
				}	
				if (rstate == RoomState.PRE_BATTLE || !ghost)
				{
					p.gameClient.sendPacket(new BATTLE_MISSION_ROUND_PRE_START_ACK(this, specialMode, slotPlay, dinos));
				}
				if (!isDino)
				{
					p.gameClient.sendPacket(new BATTLE_MISSION_ROUND_START_ACK(this, rstate == RoomState.BATTLE ? 1 : 0, slotGhost));
				}
				if (rstate == RoomState.BATTLE)
				{
					p.gameClient.sendPacketBuffer(sent3.buffer);
				}
				sent1.buffer = null; sent1 = null;
			}
		}
		sent3.buffer = null; sent3 = null;
		dinos = null;
		if (rstate == RoomState.PRE_BATTLE && getSlotState(LIDER) == SlotState.BATTLE)
		{
			rstate = RoomState.BATTLE;
			updateBInfo();
			if (isDino && threadCROSS == null)
			{
				try
				{
					threadCROSS = ThreadPoolManager.gI().scheduleCOUNTD(new Runnable()
					{
						@Override
						public void run()
						{
							if (rstate == RoomState.BATTLE)
							{
								BATTLE_MISSION_ROUND_START_ACK sent = new BATTLE_MISSION_ROUND_START_ACK(getRoom(), 0, getFlagSlot(true, false, false)); sent.packingBuffer();
								for (RoomSlot slot : SLOT)
								{
									Player p = AccountSyncer.gI().get(slot.player_id);
									if (p != null && slot.state == SlotState.BATTLE)
									{
										slot.espectador = false;
										slot.death = false;
										p.gameClient.sendPacketBuffer(sent.buffer);
									}
								}
								sent.buffer = null; sent = null;
							}
							stopTask("crosscounter");
						}
					}, 5350);
				}
				catch (Exception e)
				{
					error(getClass(), e);
					stopTask("crosscounter");
				}
			}
		}
		updateInfo();
	}
	public int slotFix(int time)
	{
		for (int i : time == 0 ? RED_TEAM : BLUE_TEAM)
		{
			RoomSlot slot = getSlot(i);
			if (slot.player_id > 0 && slot.state != SlotState.EMPTY && slot.state.ordinal() < 8)
				return i;
		}
		return -1;
	}
	public int slotClear(int time)
	{
		for (int i : time == 0 ? RED_TEAM : BLUE_TEAM)
		{
			RoomSlot slot = getSlot(i);
			if (slot.player_id == 0 && slot.state == SlotState.EMPTY)
				return i;
		}
		return -1;
	}
	public SlotState getSlotState(RoomSlot slot)
	{
		if (slot == null)
			return SlotState.EMPTY;
		else
			return slot.state;
	}
	public SlotState getSlotState(long pId)
	{
		return getSlotState(getSlotByPID(pId));
	}
	public SlotState getSlotState(int slot)
	{
		return getSlotState(getSlot(slot));
	}
	public synchronized void stopTask(String idx) 
	{
		if (idx.equals("bomb"))
		{
			if (threadBOMB != null)
				threadBOMB.cancel(false);
			threadBOMB = null;
			return;
		}
		else if (idx.equals("countdown"))
		{
			if (threadCOUNTDOWN != null)
				threadCOUNTDOWN.cancel(false);
			threadCOUNTDOWN = null;
			return;
		}
		else if (idx.equals("votekick"))
		{
			if (threadVOTE != null)
				threadVOTE.cancel(false);
			threadVOTE = null;
			votekick = null;
			votou.clear();
			return;
		}
		else if (idx.equals("round"))
		{
			if (threadROUND != null)
				threadROUND.cancel(false);
			threadROUND = null;
			return;
		}
		else if (idx.equals("crosscounter"))
		{
			if (threadCROSS != null)
				threadCROSS.cancel(false);
			threadCROSS = null;
			return;
		}
	}
	public void stopAllThread() 
	{
		stopTask("bomb");
		stopTask("countdown");
		stopTask("votekick");
		stopTask("round");
		stopTask("crosscounter");
	}
	public boolean kickPlayer(int slot)
	{
		try
		{
			Player f = getPlayerBySlot(slot);
			if (f != null)
			{
				if (!kikados.contains(f.id))
					kikados.add(f.id);
				f.gameClient.sendPacket(new VOTEKICK_KICK_ACK());
				BATTLE_LEAVEP2PSERVER_ACK sent = new BATTLE_LEAVEP2PSERVER_ACK(f, slot, 2); sent.packingBuffer();
				for (RoomSlot s : SLOT)
				{
					Player m = AccountSyncer.gI().get(s.player_id);
					if (m != null && s.state == SlotState.BATTLE)
						m.gameClient.sendPacketBuffer(sent.buffer);
				}
				sent.buffer = null; sent = null;
				resetSlotPlayer(getSlot(slot), false, false);
				changeHost(0, slot);
				removePlayer(f, slot, true, true, true);
				checkBattlePlayers(slot);
				return true;
			}
		}
		catch (Exception e)
		{
			error(getClass(), e);
		}
		return false;
	}
	public void checkPlayersInReadyCountdown(boolean upInfo)
	{
		if (isRoundMap() || type == ModesEnum.DINO || type == ModesEnum.CROSSCOUNTER)
		{
			RoomSlot slotLeader = getSlotByPID(LIDER); 
			if (slotLeader != null)
			{
				int time = presPorTime(TimeEnum.values()[slotLeader.id % 2 == 0 ? 1 : 0]);
				if (slotLeader.state == SlotState.READY && time == 0 && rstate == RoomState.COUNTDOWN)
				{
					stopTask("countdown");
					rstate = RoomState.READY;
					changeStateInfo(slotLeader, SlotState.NORMAL, !upInfo);
					BATTLE_COUNTDOWN_ACK sent1 = new BATTLE_COUNTDOWN_ACK(RoomError.CONTAGEM_PREPARANDO); sent1.packingBuffer();
					ROOM_CHANGE_ROOMINFO_ACK sent2 = new ROOM_CHANGE_ROOMINFO_ACK(this, "", isSpecialMode()); sent2.packingBuffer();
					ROOM_GET_SLOTS_ACK sent3 = new ROOM_GET_SLOTS_ACK(this); sent3.packingBuffer();
					for (RoomSlot s : SLOT)
					{
						Player m = AccountSyncer.gI().get(s.player_id);
						if (m != null)
						{
							m.gameClient.sendPacketBuffer(sent1.buffer);
							m.gameClient.sendPacketBuffer(sent2.buffer);
							m.gameClient.sendPacketBuffer(sent3.buffer);
						}
					}
					sent1.buffer = null; sent1 = null;
					sent2.buffer = null; sent2 = null;
					sent3.buffer = null; sent3 = null;
				}
			}
		}
	}
	public int presPorTime(TimeEnum time)
	{
		int go = 0;
		int master = _getMaster();
		for (int i : time == TimeEnum.TIME_VERMELHO ? RED_TEAM : BLUE_TEAM)
			if (getSlotState(i) == SlotState.READY && master != i)
				go++;
		return go;
	}
	public int count4vs4team(TimeEnum time)
	{
		int go = 0;
		for (int i : time == TimeEnum.TIME_VERMELHO ? RED_TEAM : BLUE_TEAM)
			if (getSlotState(i).ordinal() >= 8)
				go++;
		return go;
	}
	public void setSlots(int slots)
    {
        if (slots == 0)
        	slots++;
        for (RoomSlot slot : SLOT)
            if (slot.id >= slots)
            	changeStateInfo(slot, SlotState.CLOSE, false);
    }
    public int getSlots()
    {
        int count = 0;
        for (RoomSlot slot : SLOT)
            if (slot.state != SlotState.CLOSE)
            	count++;
        return count;
    }
    public synchronized void changeHost(int players, int host)
	{
    	int lider = _getMaster();
    	if (host != -1 && host == lider && rstate.ordinal() > 1)
    	{
    		List<Integer> slots = new ArrayList<Integer>();
			for (int i = 13; i > 8; i--)
			{
				for (RoomSlot s : SLOT)
				{
					if (s.state.ordinal() == i)
					{
						slots.add(s.id);
					}
				}
			}
			for (int id : slots)
			{
				if (id != host)
				{
					if (setNewLeader(lider = id, true))
						break;
				}
			}
			BATTLE_GIVEUPBATTLE_ACK sent = new BATTLE_GIVEUPBATTLE_ACK(this, host, lider); sent.packingBuffer();
			for (RoomSlot slot : SLOT)
			{
				Player m = AccountSyncer.gI().get(slot.player_id);
				if (m != null && slot.state.ordinal() >= 10)
					m.gameClient.sendPacketBuffer(sent.buffer);
			}
			sent.buffer = null; sent = null;
			slots = null;
		}
		else
		{
			if (players != 1)
			{
				setNewLeader(true);
			}
		}
	}
    public Player getPlayerBySlot(int slotId)
    {
    	RoomSlot slot = getSlot(slotId);
    	return slot != null && slot.player_id > 0 ? AccountSyncer.gI().get(slot.player_id) : null;
    }
    public RoomSlot getSlotByPID(long id)
    {
    	for (RoomSlot slot : SLOT)
    		if (slot.player_id == id)
    			return slot;
    	return null;
    }    
    public void setNewLeader(boolean upInfo)
	{
    	for (RoomSlot slot : SLOT)
		{
			int index = -1;
			Player p = getPlayerBySlot(last_host);
			if (p != null)
			{
				index = last_host;
			}
			else
			{
				index = slot.id;
			}
			if (setNewLeader(index, upInfo))
			{
				break;
			}
		}
	}
	public synchronized boolean setNewLeader(int id, boolean upInfo) 
	{
		RoomSlot slot = getSlot(id);
		if (slot != null && slot.player_id > 0 && slot.player_id != LIDER)
		{
			if (slot.state == SlotState.READY)
				changeStateInfo(slot, SlotState.NORMAL, false);
			if (rstate == RoomState.COUNTDOWN)
			{
				stopTask("countdown");
				rstate = RoomState.READY;
				BATTLE_COUNTDOWN_ACK sent1 = new BATTLE_COUNTDOWN_ACK(RoomError.CONTAGEM_PREPARANDO); sent1.packingBuffer();
				ROOM_CHANGE_ROOMINFO_ACK sent2 = new ROOM_CHANGE_ROOMINFO_ACK(this, "", isSpecialMode()); sent2.packingBuffer();
				for (RoomSlot s : SLOT)
				{
					Player m = AccountSyncer.gI().get(s.player_id);
					if (m != null)
					{
						if (s.state == SlotState.READY)
							changeStateInfo(s, SlotState.NORMAL, false);
						m.gameClient.sendPacketBuffer(sent1.buffer);
						m.gameClient.sendPacketBuffer(sent2.buffer);
					}
				}
				sent1.buffer = null; sent1 = null;
				sent2.buffer = null; sent2 = null;
			}
			last_host = -1;
			LIDER = slot.player_id;
			listHost.clear();
			BattleManager.gI().changeHost(this, slot.id);
			if (rstate.ordinal() > 3) updateBInfo();
			if (upInfo) updateInfo();
			return true;
		}
		return false;
	}
	public void removePlayer(Player p, int slotId, boolean upFriends, boolean changeHost, boolean addLobby)
	{
		Channel ch = getChannel();
		if (p != null && ch != null)
		{
			if (prepared1.containsKey(slotId)) prepared1.remove(slotId);
			if (prepared2.containsKey(slotId)) prepared2.remove(slotId);
			if (listHost.containsKey(p.id)) listHost.remove(p.id);
			if (votou.containsKey(p.id)) votou.remove(p.id);
			RoomSlot slot = getSlot(slotId);
			if (slot != null)
			{
				if (last_host == slotId)
					last_host = -1;
				slot.player_id = 0;
				changeState(slot, SlotState.EMPTY, false);
			}
			p.minutosJogados = null;
			p.slot = -1;
			p.gameClient.setRoomId(-1);
			if (upFriends)
				EssencialUtil.updateFriends(p, 80, false);
			if (LIDER == p.id && sizePlayers() > 0 && changeHost)
				setNewLeader(false);
			updateInfo();
			if (addLobby)
				ch.addPlayer(p);
			ch.removeEmptyRooms();
		}
	}
	public RoomSlot addPlayer(Player p)
	{
		if (p != null && p.status() > 0)
		{
			for (RoomSlot s : SLOT)
			{
				if (s.player_id == 0 && s.state == SlotState.EMPTY)
				{
					SLOT[s.id] = (s = new RoomSlot(s.id, p.id, SlotState.NORMAL));
					p.slot = s.id;
					p.gameClient.setRoomId(id);
					EssencialUtil.updateFriends(p, 96, false);
					return s;
				}
			}
		}
		return null;
	}
	public synchronized void votekickPlayer()
	{
		try
		{
			synchronized (votekick)
			{
				if (votekick != null && threadVOTE != null)
				{
					int play = votekick.playing();
					if (votekick.kikar > votekick.deixar && votekick.enemys > 0 && votekick.allies > 0 && votou.size() >= play / 2)
					{
						kickPlayer(votekick.victimIdx);
					}
					int error = 0;
					if (votekick.allies == 0)
					{
						error = 0x80001101;
					}
					else if (votekick.enemys == 0)
					{
						error = 0x80001102;
					}
					else if (votekick.deixar < votekick.kikar || votou.size() < play / 2)
					{
						error = 0x80001100;
					}
					VOTEKICK_RESULT_ACK sent = new VOTEKICK_RESULT_ACK(votekick, error); sent.packingBuffer();
					for (RoomSlot s : SLOT)
					{
						Player m = AccountSyncer.gI().get(s.player_id);
						if (m != null && s.state == SlotState.BATTLE)
							m.gameClient.sendPacketBuffer(sent.buffer);
					}
					sent.buffer = null; sent = null;
				}
			}
		}
		finally
		{
			stopTask("votekick");
		}
	}
	public void zombieSync()
	{
		for (int i : BLUE_TEAM)
			changeStateInfo(i, SlotState.CLOSE, false);
	}
	public void updateInfo()
	{
		ROOM_GET_SLOTS_ACK sent = new ROOM_GET_SLOTS_ACK(this); sent.packingBuffer();
		for (RoomSlot slot : SLOT)
		{
			Player m = AccountSyncer.gI().get(slot.player_id);
			if (m != null)
				m.gameClient.sendPacketBuffer(sent.buffer);
		}
		sent.buffer = null; sent = null;
	}
	public void updateBInfo()
	{
		ROOM_CHANGE_ROOMINFO_ACK sent = new ROOM_CHANGE_ROOMINFO_ACK(this, "", isSpecialMode()); sent.packingBuffer();
		for (RoomSlot slot : SLOT)
		{
			Player m = AccountSyncer.gI().get(slot.player_id);
			if (m != null)
				m.gameClient.sendPacketBuffer(sent.buffer);
		}
		sent.buffer = null; sent = null;
	}
    public int getPlayingPlayers(int time, boolean inBattle, int resp)
    {
        int players = 0;
        for (RoomSlot slot : SLOT)
        {
            if (((slot.id % 2 == time || time == 2) && (slot.state == SlotState.BATTLE && (resp == -1 ? true : slot.respawn > resp) && inBattle && !slot.espectador || slot.state.ordinal() >= 11 && !inBattle)))
                players++;
		}
        return players;
    }
    public int getFlagSlot(boolean spectators, boolean missionSuccess, boolean endBattle)
    {
    	int resultado = 0;
        for (RoomSlot slot : SLOT)
        {
        	Player p = AccountSyncer.gI().get(slot.player_id) ;
    		if (p != null && (missionSuccess && slot.missionClear && slot.state == SlotState.BATTLE || !missionSuccess && slot.state.ordinal() > (endBattle ? 10 : 12) && (!spectators || (p.espectador == 1 || spectators && !slot.espectador))))
    			resultado += 1 << slot.id;
        }
        return resultado;
    }
	public RoomSlot changeSlot(Player p, RoomSlot o, SlotState state, int team)
	{
		if (team == -1)
			return null;
		for (int i : team == 0 ? RED_TEAM : BLUE_TEAM)
		{
			RoomSlot n = getSlot(i);
			if (n != null && n.player_id == 0 && n.state == SlotState.EMPTY)
			{
				n.player_id = p.id;
				changeStateInfo(n, state, false);
				o.player_id = 0;
				changeStateInfo(o, SlotState.EMPTY, false);
				p.changeSlot = false;
				p.slot = i;
				return n;
			}
		}
		return null;
	}
	public int getKillTime()
	{
		return TIMES[killMask >> 4];
	}
	public int getKillsByMask()
	{
		return isRoundMap() ? ROUNDS[killMask - 1 & 15] : KILLS[killMask & 15];
	}
	public boolean isRoundMap()
	{
		return killMask >> 4 < 3;
	}
	public boolean isRoundMap(int type)
	{
		return killMask >> 4 < 3 && this.type.ordinal() != type;
	}
	public boolean isCaos()
	{
		return type == ModesEnum.CAOS;
	}
	public synchronized void end(int twinner)
	{
		int slot_player = 0, slot_mission = 0;
		try
		{
			BattleManager.gI().removeRoom(index);
			double expPercent = 0, goldPercent = 0, cashPercent = 0;
			Channel ch = getChannel();
			if (ch != null)
			{
				expPercent = ch.bonusExp;
				goldPercent = ch.bonusGold;
				cashPercent = ch.bonusCash;
			}
			boolean win_cash = ConfigurationLoader.gI().battle_cash, specialMode = isSpecialMode(), roundMap = isRoundMap();
			rstate = RoomState.BATTLE_END;
			stopTask("bomb");
			ROOM_CHANGE_ROOMINFO_ACK sent1 = new ROOM_CHANGE_ROOMINFO_ACK(this, "", specialMode); sent1.packingBuffer();
			VOTEKICK_RESULT_ACK sent2 = null;
			if (votekick != null && threadVOTE != null)
			{
				sent2 = new VOTEKICK_RESULT_ACK(votekick, 0); sent2.packingBuffer();
			}
			stopAllThread();
			int win = timeVencedor();
			if (twinner == -1)
				twinner = win;
			for (RoomSlot slot : SLOT)
			{
				try
				{
					Player m = AccountSyncer.gI().get(slot.player_id);
					if (m != null)
					{
						m.gameClient.sendPacketBuffer(sent1.buffer);
						if (slot.state.ordinal() >= 9)
						{
							if (slot.state.ordinal() >= 11)
								slot_player += 1 << slot.id;
							checkVip(m, false);
							if (slot.state == SlotState.BATTLE)
							{
								if (sent2 != null)
									m.gameClient.sendPacketBuffer(sent2.buffer);
								if (!slot.checked)
								{
									slot.checked = true;
									m.stats.partidas++;
									if (specialMode)
									{
										slot.exp++;
										slot.gold++;
										if (win_cash)
											slot.cash++;
									}
									else
									{
										m.stats.matou += slot.allKills;
										m.stats.morreu += slot.allDeaths;
										m.stats.headshots += slot.allHeadshots;
										if (win == 2)
										{
											m.stats.empatou++;
											slot.gold += 50;
											slot.exp += 50;
											if (win_cash) slot.cash += 50;
										}
										else if (win == (slot.id % 2))
										{
											m.stats.ganhou++;
											slot.gold += 90;
											slot.exp += 90;
											if (win_cash) slot.cash += 90;
										}
										else if (win != (slot.id % 2))
										{
											m.stats.perdeu++;
											slot.gold += 10;
											slot.exp += 10;
											if (win_cash) slot.cash += 10;
										}
										if (roundMap || type == ModesEnum.DINO || type == ModesEnum.CROSSCOUNTER)
										{
											slot.gold += (slot.allKills + slot.allHeadshots * 32 + slot.allDeaths + (slot.objetivo * 50)) - slot.allDeaths + (slot.score / 3);
											slot.exp += (slot.allKills + slot.allHeadshots * 32 + slot.allDeaths + (slot.objetivo * 50)) - slot.allDeaths + (slot.score / 3);
											if (win_cash)
												slot.cash += (slot.allKills + slot.allHeadshots * 27 + slot.allDeaths + (slot.objetivo * 50)) - slot.allDeaths + (slot.score / 3);
										}
										else
										{
											slot.gold += (slot.allKills + slot.allHeadshots * 24 + (slot.objetivo * 48)) - slot.allDeaths + (slot.score / 3);
											slot.exp += (slot.allKills + slot.allHeadshots * 23 + (slot.objetivo * 48)) - slot.allDeaths + (slot.score / 3);
											if (win_cash)
												slot.cash += (slot.allKills + slot.allHeadshots * 17 + (slot.objetivo * 48)) - slot.allDeaths + (slot.score / 3);
										}
										if (m.vip_pccafe == 1 || m.vip_pccafe == 2)
										{
											slot.exp_bonus += (int)Math.ceil(slot.exp / (m.vip_pccafe == 2 ? 1.65 : 3.3)) + 30;
											slot.gold_bonus += (slot.gold / (m.vip_pccafe == 2 ? 2 : 4)) + 30;
											if (win_cash)
												slot.cash += m.vip_pccafe * 30;
										}
										if (slot.piercing_shot > 0)
										{
											slot.exp += (40 * slot.piercing_shot);
											slot.gold += (43 * slot.piercing_shot);
										}
										if (expPercent > 0) slot.exp_bonus += (int)Math.round(expPercent * slot.exp);
										if (goldPercent > 0) slot.gold_bonus += (int)Math.round(goldPercent * slot.gold);
										if (cashPercent > 0) slot.cash += (int)Math.round(cashPercent * slot.cash);
										slot.exp += (slot.util * 5);
				                        if (m.bonus100 > 0 || m.bonus50 > 0 || m.bonus30 > 0)
				                        {
				                            if (m.bonus100 > 0)
				                            {
				                                if (m.bonus100 == 1 || m.bonus100 == 3) slot.exp_bonus += slot.exp;
				                                if (m.bonus100 == 2 || m.bonus100 == 3) slot.gold_bonus += slot.gold;
				                            }
				                            if (m.bonus50 > 0)
				                            {
				                                if (m.bonus50 == 1 || m.bonus50 == 3) slot.exp_bonus += slot.exp / 2;
				                                if (m.bonus50 == 2 || m.bonus50 == 3) slot.gold_bonus += slot.gold / 2;
				                            }
				                            if (m.bonus30 > 0)
				                            {
				                                if (m.bonus30 == 1 || m.bonus30 == 3) slot.exp_bonus += (int)Math.ceil(slot.exp / 3.3);
				                                if (m.bonus30 == 2 || m.bonus30 == 3) slot.gold_bonus += (int)Math.ceil(slot.gold / 3.3);
				                            }
				                            slot.tag += 4;
				                        }
									}
									m.gold += slot.gold + slot.gold_bonus;
									m.exp += slot.exp + slot.exp_bonus;
									if (win_cash) m.cash += slot.cash;
									slot.tag += (expPercent > 0 || goldPercent > 0) ? 8 : 0;
									slot.tag += m.vip_pccafe;
									if (slot.tag > 14) slot.tag = 14;
									EssencialUtil.missionEnd(this, slot, m, win, specialMode);
									if (slot.missionClear)
										slot_mission += 1 << slot.id;
									RankInfo rank = RankXML.gI().ranks.get(m.rank);
									if (rank != null)
									{
										int expObtido = rank.onNextLevel + rank.onAllExp;
						                if (m.exp >= expObtido && m.rank < 51)
						                {
						                    for (PlayerInventory r : rank.rewards)
						                    	m.gameClient.sendPacket(new INVENTORY_ITEM_CREATE_ACK(m, r.item_id, r.count, 1, -1));
						                	int EXP = (m.exp - expObtido) + RankXML.gI().ranks.get(m.rank + 1).onAllExp;
						                    m.rank++;
						                    m.gold += rank.onGPUp;
						                    m.cash += rank.onCPUP;
						                    m.exp = EXP;
						                    m.last_up = date.getDateTime();
						                    m.gameClient.sendPacket(new PLAYER_LEVEL_UP_ACK(m.rank, rank.onNextLevel));
						                    slot.isUp = true;
						                }
									}
								}
							}
							checkInventory(m, slot);
						}
					}
				}
				catch (Exception e)
				{
				}
			}
			sent1.buffer = null; sent1 = null;
			if (sent2 != null)
			{
				sent2.buffer = null;
				sent2 = null;
			}
		}
		catch (Exception e)
		{
			error(getClass(), e);
		}
		finally
		{
			finalizeEnd(slot_player, slot_mission, twinner);
		}
	}
	public synchronized void finalizeEnd(int slot_player, int slot_mission, int winner)
	{
		try
		{
			EventPlaytime e = PlaytimeXML.gI().playtimeNow();
			ByteBuf buffer = Unpooled.buffer(144).order(ByteOrder.LITTLE_ENDIAN);
			for (RoomSlot slot : SLOT) buffer.writeShort(slot.exp);
		    for (RoomSlot slot : SLOT) buffer.writeShort(slot.gold);
		    for (RoomSlot slot : SLOT) buffer.writeShort(slot.exp_bonus);
		    for (RoomSlot slot : SLOT) buffer.writeShort(slot.gold_bonus);
		    for (RoomSlot slot : SLOT) buffer.writeByte(slot.tag);
			for (RoomSlot s : SLOT)
			{
				Player m = AccountSyncer.gI().get(s.player_id);
				if (m != null && s.state == SlotState.BATTLE)
				{
					m.gameClient.sendPacket(new BATTLE_ENDBATTLE_ACK(m, this, slot_player, slot_mission, winner, buffer.array()));
					if (e != null && m.event.play_day != e.id && m.minutosJogados != null)
					{
						try
						{
							if (!isSpecialMode())
							{
								if (m.event.event_playtime != e.id)
									m.event.played = e.time; //Resetar minutos para um novo evento que se iniciou
								m.event.event_playtime = e.id;
								Date atual = date.getTimeHasPlay();
								long diferencaMinutos = (atual.getTime() - m.minutosJogados.getTime()) / (1000 * 60);
								int minutosJogados = (m.event.played -= diferencaMinutos);
								if (minutosJogados > 0)
								{
									m.gameClient.sendPacket(new BATTLE_AWARD_TIME_ACK(e, 1, minutosJogados));
									db.executeQuery("UPDATE jogador_evento SET played='" + minutosJogados + "' WHERE player_id='" + m.id + "'");
								}
								else
								{
									m.gameClient.sendPacket(new BATTLE_AWARD_TIME_ACK(e, 0, minutosJogados));
									m.event.played = 0;
									m.event.play_day = e.id;
									m.event.event_playtime = -1;
									db.executeQuery("UPDATE jogador_evento SET played='0', play_day='" + e.id + "' WHERE player_id='" + m.id + "'");
								}
							}
							else
							{
								m.gameClient.sendPacket(new BATTLE_AWARD_TIME_ACK(e, 1, m.event.played));
							}
						}
						catch (Exception ex)
						{
						}
					}
				}
			}
			buffer = null;
			for (RoomSlot slot : SLOT)
			{
				Player m = AccountSyncer.gI().get(slot.player_id);
				if (m != null)
				{
					if (slot.state.ordinal() >= 9)
						changeStateInfo(slot, SlotState.NORMAL, false);
					db.updateStats(m);
					db.executeQuery("UPDATE jogador SET gold='" + m.gold + "', exp='" + m.exp + "', cash='" + m.cash + "' WHERE id='" + m.id + "'");
					if (slot.isUp)
					{
						db.executeQuery("UPDATE jogador SET rank='" + m.rank + "', last_up='" + m.last_up + "' WHERE id='" + m.id + "'");
						EssencialUtil.updateFRC(m, false, true, true);
					}
					if (slot.updateBrooch) db.updateBrooch(m);
					if (slot.updateInsignia) db.updateInsignia(m);
					if (slot.updateMedal) db.updateMedal(m);
					if (slot.updateBlueOrder) db.updateBlueOrder(m);
					m.bonus100 = 0;
					m.bonus50 = 0;
					m.bonus30 = 0;
					m.freepass = 0;
					m.changeSlot = false;
					db.replaceNick(m.id, m.name);
					db.replaceRank(m.id, m.rank);
				}
				int cardId = slot.missionActiveCompleted;
				if (cardId == -1)
				{
					if (slot.missions.size() > 0)
					{
						int actual = m.missions.actual_mission + 1;
						db.updateMissions(m.id, actual == 1 ? m.missions.list1 : actual == 2 ? m.missions.list2 : actual == 3 ? m.missions.list3 : actual == 4 ? m.missions.list4 : new byte[40], actual);
					}
				}
				else
				{
					if (cardId == 0)
					{
						m.mission1 = 0;
						m.missions.card1 = 0;
		                m.missions.list1 = new byte[40];
					}
					else if (cardId == 1)
					{
						m.mission2 = 0;
						m.missions.card2 = 0;
		                m.missions.list2 = new byte[40];
					}
					else if (cardId == 2)
					{
						m.mission3 = 0;
						m.missions.card3 = 0;
		                m.missions.list3 = new byte[40];
					}
					else if (cardId == 3)
					{
						m.mission4 = 0;
						m.missions.card4 = 0;
		                m.missions.list4 = new byte[40];
					}
					db.updatePlayerMission(m, 0, cardId + 1);
					db.updateCard(m, cardId + 1, 0);
					db.updateMissions(m.id, new byte[40], cardId + 1);
				}
				slot.missions.clear();
				SLOT[slot.id] = (slot = new RoomSlot(slot.id, slot.player_id, slot.state));
	        }
			votou.clear();
			prepared1.clear();
			prepared2.clear();
			listHost.clear();
			votekick = null;
			comand = false;
			redRounds = 0;
			blueRounds = 0;
			rodadas = 1;
			timeLost = 0;
			redKills = 0;
			blueKills = 0;
			redDino = 0;
			blueDino = 0;
			rexDino = -1;
			respawn = 0;
			last_host = -1;
			rstate = RoomState.READY;
			resetDefenceGenerator();
			stopAllThread();
			updateInfo();
			updateBInfo();
		}
		catch (Exception e)
		{
			error(getClass(), e);
		}
	}
	public void checkInventory(Player player, RoomSlot s)
	{
		if (player != null && s != null)
		{
			player.readItemsToDelete(player.inventario.values(), s.armasExcluir, false);
			if (player.gameClient != null)
			{
				for (PlayerInventory item : s.armasExcluir)
					player.gameClient.sendPacket(new INVENTORY_ITEM_DELETE_ACK(item.object, item.item_id, 1, player));
			}
			player.resetEquipment(s.armasExcluir);
			EssencialUtil.resetCoupons(s.armasExcluir, player, this);
			if (s.armasUsadas != null) s.armasUsadas.clear();
			if (s.armasExcluir != null) s.armasExcluir.clear();
			s.armasUsadas = new ConcurrentHashMap<Long, Player>(); 
			s.armasExcluir = new ArrayList<PlayerInventory>();
			if (player.gameClient != null)
			{
				player.gameClient.sendPacket(new BASE_GET_EQUIPMENT_INFO_ACK(player));
				player.gameClient.sendPacket(new SERVER_MESSAGE_CHANGE_INVENTORY_ACK(player, 3));
			}
		}
	}
	public void checkVip(Player player, boolean upInfo)
	{
		if (player != null)
		{
			if (player.vip_pccafe > 0 && player.vip_expirate != 0 && player.vip_expirate <= date.getDateTime())
			{
				player.vip_pccafe = 0;
				player.vip_expirate = 0;
				if (upInfo) updateInfo();
				db.executeQuery("UPDATE jogador SET vip_pccafe='0', vip_expirate='0' WHERE id='" + player.id + "'");
			}
		}
	}
	public void prepareReady(Player player, RoomSlot s)
	{
		if (player != null && s != null)
		{
			player.readItemsToDelete(player.getItemByType(3), s.armasExcluir, true);
			for (PlayerInventory item : s.armasExcluir)
				player.gameClient.sendPacket(new INVENTORY_ITEM_DELETE_ACK(item.object, item.item_id, 1, player));
			EssencialUtil.resetCoupons(s.armasExcluir, player, this);
			s.armasUsadas = new ConcurrentHashMap<Long, Player>(); 
			s.armasExcluir = new ArrayList<PlayerInventory>();
			player.minutosJogados = null;
			player.effect1 = CuponsDAT.gI().couponCalcule(player, 1);
			player.effect2 = CuponsDAT.gI().couponCalcule(player, 2);
			player.effect3 = CuponsDAT.gI().couponCalcule(player, 3);
			player.effect4 = CuponsDAT.gI().couponCalcule(player, 4);
			player.effect5 = CuponsDAT.gI().couponCalcule(player, 5);
			player.bonus100 = 0;
			player.bonus50 = 0;
			player.bonus30 = 0;
			player.freepass = 0;
			if (player.buscarEquipPeloItemId(1200002000) == 2) player.bonus30++; //EXP 30%
			if (player.buscarEquipPeloItemId(1200003000) == 2) player.bonus50++; //EXP 50%
			if (player.buscarEquipPeloItemId(1200037000) == 2) player.bonus100++; //EXP 100%
			if (player.buscarEquipPeloItemId(1200004000) == 2) player.bonus30 += 2; //GOLD 30%
			if (player.buscarEquipPeloItemId(1200119000) == 2) player.bonus50 += 2; //GOLD 50%
			if (player.buscarEquipPeloItemId(1200038000) == 2) player.bonus100 += 2; //GOLD 100%
			if (player.buscarEquipPeloItemId(1200011000) == 2) player.freepass++; //FREE PASS 
			SLOT[s.id] = (s = new RoomSlot(s.id, s.player_id, s.state));
		}
	}
	public synchronized void round(TimeEnum time, WinnerRound wr)
	{
		if (rstate == RoomState.BATTLE && type != ModesEnum.DINO && type != ModesEnum.CROSSCOUNTER && threadROUND == null)
		{
			if (time == TimeEnum.TIME_VERMELHO)
				redRounds++;
			else if (time == TimeEnum.TIME_AZUL)
				blueRounds++;
			stopTask("bomb");
			rodadas++;
			resetDefenceGenerator();
			BATTLE_MISSION_ROUND_END_ACK sent = new BATTLE_MISSION_ROUND_END_ACK(this, time, wr); sent.packingBuffer();
			for (RoomSlot slot : SLOT)
            {
				Player m = AccountSyncer.gI().get(slot.player_id);
				if (slot.oneTimeKills >= 3 && type == ModesEnum.SUPRESSAO)
					slot.objetivo++;
				slot.killMessage = FragValues.NONE;
				slot.lastKillState = 0;
				slot.oneTimeKills = 0;
				slot.dinoOnLife = 0;
				slot.util = 0;
				slot.bar1 = 0;
				slot.bar2 = 0;
				slot.repeatLastState = false;
				slot.espectador = false;
				slot.death = false;
				if (m != null && slot.state == SlotState.BATTLE)
					m.gameClient.sendPacketBuffer(sent.buffer);
			}
			sent.buffer = null; sent = null;
			if (redRounds >= getKillsByMask() || blueRounds >= getKillsByMask())
			{
				end(-1);
			}
			else
			{
				redKills = 0;
				blueKills = 0;
				try
				{
					threadROUND = ThreadPoolManager.gI().scheduleCOUNTD(new Runnable()
					{
						@Override
						public void run()
						{
							stopTask("bomb");
							if (rstate.ordinal() > 1)
							{
								List<Integer> dinos = EssencialUtil.dinos(getRoom(), dinossaur());
								int slotPlay = getFlagSlot(false, false, false), slotGhost = getFlagSlot(true, false, false);
								Room r = getRoom();
								BATTLE_MISSION_ROUND_PRE_START_ACK sent1 = new BATTLE_MISSION_ROUND_PRE_START_ACK(r, false, slotPlay, dinos); sent1.packingBuffer();
								BATTLE_MISSION_ROUND_START_ACK sent2 = new BATTLE_MISSION_ROUND_START_ACK(r, 0, slotGhost); sent2.packingBuffer();;
								for (RoomSlot slot : SLOT)
								{
									Player p = AccountSyncer.gI().get(slot.player_id);
									if (p != null && slot.state == SlotState.BATTLE)
									{
										slot.espectador = false;
										slot.death = false;
										p.gameClient.sendPacketBuffer(sent1.buffer);
										p.gameClient.sendPacketBuffer(sent2.buffer);
									}
								}
								dinos = null;
								sent1.buffer = null; sent1 = null;
								sent2.buffer = null; sent2 = null;
							}
							 stopTask("round");
						}
					}, 8001);
				}
				catch (Exception e)
				{
					error(getClass(), e);
					stopTask("round");
				}
			}
		}
	}
	public void resetSlotPlayer(RoomSlot sM, boolean remove, boolean escape)
	{
		if (sM != null)
		{
			if (prepared1.containsKey(sM.id)) prepared1.remove(sM.id);
			if (prepared2.containsKey(sM.id)) prepared2.remove(sM.id);
			if (votekick != null)
				votekick.play[sM.id] = false;
			int state = sM.state.ordinal();
			if (state > 8)
			{
				changeStateInfo(sM, SlotState.NORMAL, false);
				BattleManager.gI().removePlayer(this, sM.id);
			}
			Player p = getPlayerBySlot(sM.id);
			if (p != null)
			{
				checkVip(p, true);
				if (votou.containsKey(p.id))
					votou.remove(p.id);
				if (sM.missions.size() > 0)
				{
					for (MissionUpdate mu : sM.missions.values())
					{
						if (p.missions.actual_mission == 0) p.missions.list1[mu.card - 1] -= mu.value;
						else if (p.missions.actual_mission == 1) p.missions.list2[mu.card - 1] -= mu.value;
						else if (p.missions.actual_mission == 2) p.missions.list3[mu.card - 1] -= mu.value;
						else if (p.missions.actual_mission == 3) p.missions.list4[mu.card - 1] -= mu.value;
					}
				}
				boolean specialMode = isSpecialMode();
				if (p.freepass != 1 || specialMode)
				{
					if (remove && state > 8)
						db.executeQuery("UPDATE jogador_estatisticas SET partidas='" + (p.stats.partidas++) + "', perdeu='" + (p.stats.perdeu++) + "' WHERE player_id='" + p.id + "'");
					if (escape)
					{
						db.executeQuery("UPDATE jogador_estatisticas SET kitou='" + (p.stats.kitou++) + "' WHERE player_id='" + p.id + "'");
						if (state > 11 && !specialMode)
						{
							p.gold -= 100;
							if (p.gold < 0)
								p.gold = 0;
							db.updateGold(p);
						}
					}
				}
				checkInventory(p, sM);
				p.bonus100 = 0;
				p.bonus50 = 0;
				p.bonus30 = 0;
				p.freepass = 0;
				p.changeSlot = false;
			}
			SLOT[sM.id] = (sM = new RoomSlot(sM.id, sM.player_id, sM.state));
		}
	}
	public void changeState(RoomSlot slot, SlotState state, boolean upInfo)
	{
		if (slot != null)
		{
			SlotState oldState = slot.state;
			slot.state = state;
			SLOT[slot.id].state = state;
			if (rstate == RoomState.COUNTDOWN && oldState == SlotState.READY && state.ordinal() < 8)
			{
				if (slot.player_id == LIDER)
				{
					stopTask("countdown");
					rstate = RoomState.READY;
					changeStateInfo(slot, state, !upInfo);
					BATTLE_COUNTDOWN_ACK sent1 = new BATTLE_COUNTDOWN_ACK(RoomError.CONTAGEM_PREPARANDO); sent1.packingBuffer();
					ROOM_CHANGE_ROOMINFO_ACK sent2 = new ROOM_CHANGE_ROOMINFO_ACK(this, "", isSpecialMode()); sent2.packingBuffer();
					for (RoomSlot s : SLOT)
					{
						Player m = AccountSyncer.gI().get(s.player_id);
						if (m != null)
						{
							m.gameClient.sendPacketBuffer(sent1.buffer);
							m.gameClient.sendPacketBuffer(sent2.buffer);
						}
					}
					sent1.buffer = null; sent1 = null;
					sent2.buffer = null; sent2 = null;
				}
				else
				{
					checkPlayersInReadyCountdown(false);
				}
			}
			if (state == SlotState.CLOSE || state == SlotState.EMPTY)
				SLOT[slot.id] = (slot = new RoomSlot(slot.id, 0, state));
			if (upInfo)
				updateInfo();
		}
	}
	public void changeStateInfo(RoomSlot slot, SlotState state, boolean upInfo)
	{
		if (slot != null)
		{
			slot.state = state;
			if (state == SlotState.CLOSE || state == SlotState.EMPTY)
				SLOT[slot.id] = (slot = new RoomSlot(slot.id, 0, state));
			if (upInfo)
				updateInfo();
		}
	}
	public void changeStateInfo(int slot, SlotState state, boolean upInfo)
	{
		changeStateInfo(getSlot(slot), state, upInfo);
	}
	public void changeStateInfo(long pId, SlotState state, boolean upInfo)
	{
		changeStateInfo(getSlotByPID(pId), state, upInfo);
	}
	public void changeStateInfo(Player player, SlotState state, boolean upInfo)
	{
		changeStateInfo(getSlotByPID(player != null ? player.id : 0), state, upInfo);
	}
	public void changeState(int slot, SlotState state, boolean upInfo)
	{
		changeState(getSlot(slot), state, upInfo);
	}
	public void changeState(Player player, SlotState state, boolean upInfo)
	{
		changeState(getSlotByPID(player != null ? player.id : 0), state, upInfo);
	}
	public void changeState(long pId, SlotState state, boolean upInfo)
	{
		changeState(getSlotByPID(pId), state, upInfo);
	}
	public void resetDefenceGenerator()
	{
		if (type == ModesEnum.DEFESA)
		{
			if (map == 39)
			{
				bar1 = 6000;
				bar2 = 9000;
			}
		}
		else if (type == ModesEnum.SABOTAGEM)
		{
			if (map == 38)
			{
				bar1 = 12000;
				bar2 = 12000;
			}
			else if (map == 35)
			{
				bar1 = 6000;
				bar2 = 6000;
			}
		}
	}
	public synchronized void prepare_room(int slot)
	{
		resetDefenceGenerator();
		prepared1.clear();
		prepared2.clear();
		listHost.clear();
		votekick = null;
		rodadas = 1;
		redKills = 0;
		blueKills = 0;
		redRounds = 0;
		blueRounds = 0;
		redDino = 0;
		blueDino = 0;
		rexDino = -1;
		respawn = 0;
		if (special == 9)
			aiLevel = 1;
		votekick = null;
		for (RoomSlot s : SLOT)
		{
			Player p = AccountSyncer.gI().get(s.player_id);
			if (p != null) p.minutosJogados = null;
			if (s.checked)
			{
				s.aiLevel = aiLevel;
				s.checked = false;
			}
		}
		BattleManager.gI().addRoom(this, slot);
		stopAllThread();
	}
	public int _getMaster()
	{
		Player p = AccountSyncer.gI().get(LIDER);
		return p != null ? p.slot : 0; //-1
	}
	public synchronized RoomSlot balanceamentoDeSlot(Player p, RoomSlot o, SlotState state, List<RoomSlot> _slots, boolean position, int haveBalance)
	{
		if (isSpecialMode() || balanceamento == 0)
			return null;
		int time = definirPosicionamento(o.id, position);
		if (time != o.id % 2)
			haveBalance = 1;
		RoomSlot n = corretorDeSlot(p, o, state, time, -1, -1);
		if (n != null)
		{
			_slots.add(new RoomSlot(n, o));
			return n;
		}
		/*int pref = -1; //Nenhum time definido
		int slotLivre = slotVazio(time);
		int slotUsado = slotFix(time);
		if (slotLivre != -1)
		{
			if (slotLivre != -1) //Colocar todos os jogadores com Ready no começo
				if (slotUsado < slotLivre)
					pref = slotUsado;
		}*/
		return null;
	}
	public RoomSlot corretorDeSlot(Player p, RoomSlot o, SlotState state, int team, int pref, int oId)
	{
		if (team == -1)
			return null;
		if (pref >= 0)
		{
			RoomSlot n = getSlot(pref);
			n.state = state;
			Player a = getPlayerBySlot(oId);
			Player b = getPlayerBySlot(pref);
			if (a != null) a.slot = pref;
			if (b != null) b.slot = oId;
			o.id = pref;
			n.id = oId;
			SLOT[oId] = n;
			SLOT[pref] = o;
			return n;
		}
		return changeSlot(p, o, state, team);
	}
	public int definirPosicionamento(int id, boolean position)
	{
		int myTeam = id % 2;
		int red = count4vs4team(TimeEnum.TIME_VERMELHO) - (myTeam == 0 ? 1 : 0), blue = count4vs4team(TimeEnum.TIME_AZUL) - (myTeam == 1 ? 1 : 0);
		int slotVazio = slotClear(myTeam);
		if (position || id == LIDER)
		{
			if (slotVazio >= 0 && id > slotVazio)
				return myTeam;
		}
		else
		{
			if (red == blue)
			{
				if (slotVazio >= 0 && id > slotVazio)
					return myTeam;
			}
			else
			{
				if (myTeam == 0 && red > blue)
					return myTeam + 1; //Azul
				else if (myTeam == 1 && blue > red)
					return myTeam - 1; //Vermelho
				else
				{
					if (slotVazio >= 0 && id > slotVazio)
						return myTeam;
				}
			}
		}
		return -1;
	}
	public boolean isGhostMode(int modo_gm)
	{
		return modo_gm == 1 || type == ModesEnum.DESTRUICAO || type == ModesEnum.SUPRESSAO || type == ModesEnum.ESCOLTA;
	}
	public boolean isSpecialMode()
	{
		return special == 6 || special == 8 || special == 9;
	}
	public boolean block(String name)
	{
		return name.contains("@Camp") || name.contains("@camp") || name.contains("@CAMP");
	}
	public void finalize()
	{
		try
		{
			super.finalize();
		}
		catch (Throwable e)
		{
		}
	}
	/*class HitPart
	{
		public int value;
		public boolean method_0(byte byte_0)
		{
			return (int)byte_0 <= value;
		}
	}
	public void getHitPart()
	{
		byte[] trintaecinco = new byte[35];
		byte[] hitpart = new byte[35];
		Arrays.sort(trintaecinco);
		HitPart c = new HitPart();
		c.value = IDFactory.gI().randomId(34);
		byte[] array = Arrays.
		/*Delegate358.smethod_0(Delegate325.smethod_0(new object[]
		{
			"By: ",
			c.int_0,
			"/ Hits: ",
			Delegate553.smethod_0(array)
		}));
		
		this.byte_10 = array;
		byte[] hit = new byte[35];
		for (int i = 0; i < 35; i++)
		{
			byte b = array[i];
			hitpart[(i + 8) % 35] = b;
		}
	}*/
}