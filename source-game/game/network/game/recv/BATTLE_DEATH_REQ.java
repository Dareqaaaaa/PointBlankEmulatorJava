package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;
import core.udp.events.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_DEATH_REQ extends game.network.game.GamePacketREQ
{
	FragInfos kills;
	Player player;
	Room room;
	boolean dead, specialMode;
	byte[] teamDeath = new byte[2], teamPlay = new byte[2];
	@Override
	public void readImpl()
	{
		player = client.getPlayer();
		room = client.getRoom();
		if (room != null && player != null && room.rstate == RoomState.BATTLE)
		{
			specialMode = room.isSpecialMode();
			int leader = room._getMaster();
			kills = new FragInfos();
			kills.victimIdx = ReadC();
	        kills.count = ReadC();
	        kills.slot = ReadC();
	        kills.weapon = ReadD();
			kills.position = new UDP_PosRotation(ReadUShort(), ReadUShort(), ReadUShort(), ReadUShort(), ReadUShort(), ReadUShort());
	        kills.flag = ReadC();
	        if (kills.position != null)
	        {
		        boolean playing = room.getSlotState(kills.slot) == SlotState.BATTLE;
		        if (specialMode)
		        {
		        	if (leader % 2 != kills.slot % 2)
		        	{
		        		playing = true;
		        	}
		        }
		        if (playing)
		        {
			    	for (int i = 0; i < kills.count; i++)
					{
						Frag frag = new Frag();
			            frag.index = ReadC();
			            frag.hits = ReadC();
			            ReadH();
			            frag.position = new UDP_PosRotation(ReadUShort(), ReadUShort(), ReadUShort(), ReadUShort(), ReadUShort(), ReadUShort());
			            frag.flag = ReadC();
			            if (frag.position != null)
			            {
							if (frag.getDeatSlot() == kills.slot)
								dead = true;
							playing = room.getSlotState(frag.getDeatSlot()) == SlotState.BATTLE;
							if (specialMode)
							{
					        	if (leader % 2 != frag.getDeatSlot() % 2)
					        	{
					        		playing = true;
					        	}
							}
							if (playing)
								kills.frags.add(frag);
			            }
			        }
		        }
		        kills.count = kills.frags.size();
	        }
		}
	}
	@Override
	public void runImpl()
	{
		try
		{
			if (room != null && player != null && !kills.frags.isEmpty())
			{
				RoomSlot killer = room.getSlot(kills.slot);
				if (killer != null)
				{
					ItemClassEnum itemClass = ItemClassEnum.values()[Integer.parseInt(String.valueOf(kills.weapon).substring(0, 1))];
					killer.killMessage = FragValues.NONE;
					boolean matouMais = kills.count > 1 && !dead;
					for (Frag frag : kills.frags)
					{
			    		RoomSlot death = room.getSlot(frag.getDeatSlot());
			    		if (death != null)
			    		{
			    			CharaDeathEnum charaDeath = CharaDeathEnum.values()[frag.hits >> 4];
			    			if (kills.count - (dead ? 1 : 0) <= 1)
			    			{
                                int type = 0;
                                if (charaDeath == CharaDeathEnum.HEADSHOT)
                                {
                                    type = 4;
                                }
                                else if (charaDeath == CharaDeathEnum.DEFAULT && itemClass == ItemClassEnum.KNIFE)
                                {
                                    type = 6;
                                }
                                if (type == 0)
                                {
                                    if (matouMais && !kills.massKill())
									{
                                        killer.killMessage = FragValues.PIERCING_SHOT;
                                    }
                                    else
                                    {
                                        killer.lastKillState = 0;
                                        killer.repeatLastState = false;
                                    }
                                }
                                else
                                {
                                	 if ((killer.lastKillState >> 12) != type)
                                		 killer.repeatLastState = false;
                                     killer.lastKillState = type << 12 | killer.oneTimeKills + 1;
                                     if (type == 4)
                                     {
                                         if (killer.repeatLastState)
                                         {
                                             killer.killMessage = (killer.lastKillState & 16383) <= 1 ? FragValues.HEADSHOT : FragValues.CHAIN_HEADSHOT;
                                         }
                                         else
                                         {
                                             killer.killMessage = FragValues.HEADSHOT;
                                             killer.repeatLastState = true;
                                         }
                                         if (matouMais && !kills.massKill() && itemClass == ItemClassEnum.SNIPER)
                                         {
                                             if (killer.killMessage == FragValues.HEADSHOT)
                                             {
                                                 killer.killMessage = FragValues.PIERCING_HEADSHOT;
                                             }
                                             else if (killer.killMessage == FragValues.CHAIN_HEADSHOT)
                                             {
                                                 killer.killMessage = FragValues.PIERCING_CHAIN_HEADSHOT;
                                             }
                                             killer.repeatLastState = true;
                                         }
                                         killer.allHeadshots++;
                                     }
                                     else if (type == 6)
                                     {
                                         if (!killer.repeatLastState || (killer.lastKillState & 16383) <= 1)
                                        	 killer.repeatLastState = true;
                                         else
                                        	 killer.killMessage = FragValues.CHAIN_SLUGGER;
                                     }
                                }
			    			}
			    			else
                            {
			    				killer.killMessage = charaDeath == CharaDeathEnum.BOOM || charaDeath == CharaDeathEnum.OBJECT_EXPLOSION || charaDeath == CharaDeathEnum.POISON || charaDeath == CharaDeathEnum.HOWL || charaDeath == CharaDeathEnum.TRAMPLED || itemClass == ItemClassEnum.SHOTGUN ? FragValues.MASS_KILL : FragValues.PIERCING_SHOT;
                            }
                            if (death.oneTimeKills > 3)
                            {
                                killer.killMessage = FragValues.CHAIN_STOPPER;
                            }
                            if ((kills.acidDino() && frag.getDeatSlot() != kills.slot) || (frag.getDeatSlot() == kills.slot && dead) || player.espectador == 0)
                            {
                                death.allDeaths++;
                            }
                            if (killer.id % 2 != death.id % 2)
                            {
                                killer.allKills++;
                                killer.oneTimeKills++;
                                if (death.id % 2 == 0)
                                {
                                    room.blueKills++;
                                    if (room.type == ModesEnum.DINO)
                                    {
                                        room.redDino += 4;
                                    }
                                }
                                else
								{
                                    room.redKills++;
                                    if (room.type == ModesEnum.DINO)
                                    {
                                        room.blueDino += 4;
                                    }
                                }
                                int score = room.calculeScore(killer.killMessage);
                                if (specialMode)
                                {
                                    killer.score += killer.oneTimeKills + room.aiLevel + score;
                                    if (killer.score > 65535)
                                    	killer.score = 65535;
                                    kills.score = killer.score;
                                }
                                else
                                {
                                    killer.score += score;
                                    kills.score = score;
                                    if (killer.killMessage == FragValues.PIERCING_SHOT)
                                        killer.piercing_shot++;
                                    killer.exp += 5;
                                    killer.gold += 5;
                                	death.death = room.isGhostMode(player.espectador);
                                	EssencialUtil.missionComplete(room, killer, kills, specialMode, false, false, false, false);
                					EssencialUtil.missionComplete(room, death, new FragInfos(), specialMode, true, false, false, false);
                                }
                            }
			    		}
					}
					killer.lastItemFrag = itemClass;
				}
				int index = kills.slot % 2;
				int ROUND_ATUAL = room.rodadas;
				BATTLE_DEATH_ACK sent = new BATTLE_DEATH_ACK(room, kills, killer); sent.packingBuffer();
		    	for (RoomSlot slot : room.SLOT)
		    	{
					Player m = AccountSyncer.gI().get(slot.player_id);
		    		if (m != null && slot.state == SlotState.BATTLE)
		    		{
		    			m.gameClient.sendPacketBuffer(sent.buffer);
		    			if (!slot.espectador && slot.respawn > 0)
						{
							if (slot.death)
								teamDeath[index]++;
							teamPlay[index]++;
						}
		    		}
		    	}
		    	sent.buffer = null; sent = null;
				if (room.isGhostMode(0))
				{
					checking();
				}
				else
				{
					if (!specialMode && !room.isRoundMap() && room.type != ModesEnum.DINO && room.type != ModesEnum.CROSSCOUNTER)
					{
						for (int i : room.KILLS)
						{
					        if (room.getKillsByMask() == i && (room.redKills >= i || room.blueKills >= i))
							{
					        	room.end(-1);
								return;
							}
						}
					}
				}
				if (room.isGhostMode(0) && ROUND_ATUAL == room.rodadas) //Se o round não acabar
					checking();
			}
		}
		finally
		{
			kills = null;
		}
	}
	public void checking()
	{
		if (kills.slot % 2 == 0)
		{
			if (teamDeath[1] >= teamPlay[1])
			{
				room.round(TimeEnum.TIME_VERMELHO, WinnerRound.ALLDEATH);
				return;
			}
			if (teamDeath[0] >= teamPlay[0])
			{
				if (room.threadBOMB == null)
					room.round(TimeEnum.TIME_AZUL, WinnerRound.ALLDEATH);
				return;
			}
		}
		else
		{
			if (teamDeath[0] >= teamPlay[0])
			{
				if (room.threadBOMB == null)
					room.round(TimeEnum.TIME_AZUL, WinnerRound.ALLDEATH);
				return;
			}
			if (teamDeath[1] >= teamPlay[1])
			{
				room.round(TimeEnum.TIME_VERMELHO, WinnerRound.ALLDEATH);
				return;
			}
		}
	}
    int getScore(int message, int kills)
    {
        if (dead)
            return 0;
        if (message == 1) return kills * 5;
        else if (message == 2) return kills * 5;
        else if (message == 4) return 6;
        else if (message == 5) return 7;
        else return 5;
    }
}