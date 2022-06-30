package game.network.game.recv;

import core.enums.*;
import core.model.*;
import core.network.*;
import core.utils.*;
import game.network.game.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_TIMERSYNC_REQ extends game.network.game.GamePacketREQ
{
	int timeLost, ping, latency;
	@Override
	public void readImpl()
	{
		timeLost = ReadD(); //4294967285-0
		ReadF();
		ReadC();
		ping = ReadC();
		ReadC();
		latency = ReadH();
	}
	@Override
	public void runImpl()
	{
		Room room = client.getRoom();
		Player player = client.getPlayer();
		if (room != null && player != null)
		{
			RoomSlot mySlot = room.getSlot(player.slot);
			if (mySlot != null && mySlot.state == SlotState.BATTLE && room.rstate == RoomState.BATTLE)
			{
				room.timeLost = timeLost;
				if (!room.isSpecialMode())
				{
					mySlot.ping = ping;
					mySlot.latency = latency;
					BATTLE_SENDPING_ACK sent = new BATTLE_SENDPING_ACK(room); sent.packingBuffer();
					for (RoomSlot slot : room.SLOT)
					{
						Player m = AccountSyncer.gI().get(slot.player_id);
						if (m != null && slot.state == SlotState.BATTLE)
						{
							m.gameClient.sendPacketBuffer(sent.buffer);
						}
					}
					sent.buffer = null; sent = null;
				}
				if (room.timeLost < 1)
				{
					if (room.rodadas == 1 && (room.type == ModesEnum.DINO || room.type == ModesEnum.CROSSCOUNTER))
					{
						room.rodadas = 2;
						room.stopTask("crosscounter");
						BATTLE_MISSION_ROUND_END_ACK sent = new BATTLE_MISSION_ROUND_END_ACK(room, TimeEnum.values()[room.timeVencedor()], WinnerRound.TIMEOUT); sent.packingBuffer();
						for (RoomSlot slot : room.SLOT)
		                {
							Player m = AccountSyncer.gI().get(slot.player_id);
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
						try
						{
							if (room.threadCROSS == null)
							{
								room.threadCROSS = ThreadPoolManager.gI().scheduleCOUNTD(new Runnable()
								{
									@Override
									public void run()
									{
										if (room.rstate == RoomState.BATTLE)
										{
											BATTLE_MISSION_ROUND_START_ACK sent = new BATTLE_MISSION_ROUND_START_ACK(room, 0, room.getFlagSlot(true, false, false)); sent.packingBuffer();
											for (RoomSlot slot : room.SLOT)
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
										room.stopTask("crosscounter");
										try
										{
											room.threadCROSS = ThreadPoolManager.gI().scheduleCOUNTD(new Runnable()
											{
												@Override
												public void run()
												{
													BATTLE_MISSION_ROUND_START_ACK sent = new BATTLE_MISSION_ROUND_START_ACK(room, 0, room.getFlagSlot(true, false, false)); sent.packingBuffer();
													for (RoomSlot slot : room.SLOT)
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
													room.stopTask("crosscounter");
												}
											}, 5350);
										}
										catch (Throwable e)
										{
											exceptionLOG(e);
											room.stopTask("crosscounter");
										}
									}
								}, 7350);
							}
						}
						catch (Throwable e)
						{
							exceptionLOG(e);
							room.stopTask("crosscounter");
						}
					}
					else if (room.type == ModesEnum.DESTRUICAO || room.type == ModesEnum.SABOTAGEM || room.type == ModesEnum.SUPRESSAO || room.type == ModesEnum.DEFESA || room.type == ModesEnum.ESCOLTA)
					{
						TimeEnum winner = TimeEnum.TIME_AZUL;
						if (room.type != ModesEnum.SABOTAGEM)
							winner = TimeEnum.TIME_AZUL;
						else
						{
							if (room.bar1 > room.bar2)
								winner = TimeEnum.TIME_VERMELHO;
							else if (room.bar1 < room.bar2)
								winner = TimeEnum.TIME_AZUL;
						}
						room.round(winner, WinnerRound.TIMEOUT);
					}
					else
					{
						int win = room.timeVencedor();
						BATTLE_MISSION_ROUND_END_ACK sent = new BATTLE_MISSION_ROUND_END_ACK(room, TimeEnum.values()[win], WinnerRound.TIMEOUT); sent.packingBuffer();
						for (RoomSlot slot : room.SLOT)
		                {
							Player m = AccountSyncer.gI().get(slot.player_id);
							if (m != null && slot.state == SlotState.BATTLE)
								m.gameClient.sendPacketBuffer(sent.buffer);
		                }
						sent.buffer = null; sent = null;
						room.end(win);
					}
				}
			}
		}
	}
}