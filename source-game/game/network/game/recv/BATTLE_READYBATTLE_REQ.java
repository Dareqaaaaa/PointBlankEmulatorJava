package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;
import java.util.concurrent.*;

import core.config.xml.*;
import core.enums.*;
import core.model.*;
import core.network.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BATTLE_READYBATTLE_REQ extends game.network.game.GamePacketREQ
{
	Room r;
	Player p, leader;
	RoomSlot mySlot;
	int players, haveBalance;
	boolean specialMode, observGM;
	List<RoomSlot> _slots = new ArrayList<RoomSlot>();
	@Override
	public void readImpl()
	{
		r = client.getRoom();
		p = client.getPlayer();
		if (p != null)
		{
			observGM = ReadD() == 1;
			if (r != null)
			{
				specialMode = r.isSpecialMode();
				leader = r.getLeader();
				mySlot = r.getSlotByPID(p.id);
				if (mySlot != null && p.observing() == 1 && observGM)
					mySlot.observGM = observGM; 
			}
		}
	}
	@Override
	public void runImpl()
	{
		if (p != null && r != null && leader != null && mySlot != null)
		{
			if (checkCHARA())
				return;
			if (leader.id == p.id)
			{
				r.comand = false;
				players = r.presPorTime(TimeEnum.values()[p.slot % 2 == 0 ? 1 : 0]);
				if ((r.isRoundMap() || r.type == ModesEnum.DINO || r.type == ModesEnum.CROSSCOUNTER) && players > 0 || r.comand)
				{
					if (checkClanFronto(p.slot % 2))
					{
						if (mySlot != null)
						{
							if (mySlot.state == SlotState.READY)
							{
								r.stopTask("countdown");
								r.rstate = RoomState.READY;
								r.changeState(mySlot, SlotState.NORMAL, true);
								BATTLE_COUNTDOWN_ACK sent1 = new BATTLE_COUNTDOWN_ACK(RoomError.CONTAGEM_CANCELADA); sent1.packingBuffer();
								ROOM_CHANGE_ROOMINFO_ACK sent2 = new ROOM_CHANGE_ROOMINFO_ACK(r, "", specialMode); sent2.packingBuffer();
								for (RoomSlot slot : r.SLOT)
								{
									Player m = AccountSyncer.gI().get(slot.player_id);
									if (m != null)
									{
										m.gameClient.sendPacketBuffer(sent1.buffer);
										m.gameClient.sendPacketBuffer(sent2.buffer);
									}
								}
								sent1.buffer = null; sent1 = null;
								sent2.buffer = null; sent2 = null;
							} 
							else if (mySlot.state == SlotState.NORMAL)
							{
								if (r.threadCOUNTDOWN == null)
								{
									r.rstate = RoomState.COUNTDOWN;
									r.changeState(mySlot, SlotState.READY, true);
									BATTLE_COUNTDOWN_ACK sent1 = new BATTLE_COUNTDOWN_ACK(RoomError.CONTAGEM_TEMPO); sent1.packingBuffer();
									ROOM_CHANGE_ROOMINFO_ACK sent2 = new ROOM_CHANGE_ROOMINFO_ACK(r, "", specialMode); sent2.packingBuffer();
									for (RoomSlot slot : r.SLOT)
									{
										Player m = AccountSyncer.gI().get(slot.player_id);
										if (m != null)
										{
											m.gameClient.sendPacketBuffer(sent1.buffer);
											if (slot.state == SlotState.READY)
												m.gameClient.sendPacketBuffer(sent2.buffer);
										}
									}
									sent1.buffer = null; sent1 = null;
									sent2.buffer = null; sent2 = null;
									for (int i = 0; i < 2; i++)
									{
										for (Player m : r.getPlayers())
										{
											RoomSlot s = r.getSlotByPID(m.id);
											if (s.state == SlotState.READY)
											{
												r.balanceamentoDeSlot(m, s, SlotState.READY, _slots, i == 1, haveBalance);
											}
										}
										BALANCESLOTS(haveBalance);
									}
									if (r.threadCOUNTDOWN == null)
									{
										try
										{
											r.threadCOUNTDOWN = ThreadPoolManager.gI().scheduleCOUNTD(new Runnable()
											{
												@Override
												public void run()
												{
													players = r.presPorTime(TimeEnum.values()[p.slot % 2 == 0 ? 1 : 0]);
													if (r.getSlotState(r.LIDER) == SlotState.READY && r.rstate == RoomState.COUNTDOWN && (players > 0 || r.comand))
													{
														r.rstate = RoomState.LOADING;
														BATTLE_READYBATTLE_ACK sent1 = new BATTLE_READYBATTLE_ACK(r); sent1.packingBuffer();
														for (RoomSlot slot : r.SLOT)
														{
															Player m = AccountSyncer.gI().get(slot.player_id);
															if (m != null && slot.state == SlotState.READY)
															{
																m.gameClient.sendPacketBuffer(sent1.buffer);
																r.changeState(slot, SlotState.LOAD, false);
																r.prepareReady(m, slot);
															}
														}
														sent1.buffer = null; sent1 = null;
														ROOM_CHANGE_ROOMINFO_ACK sent2 = new ROOM_CHANGE_ROOMINFO_ACK(r, leader.name, specialMode); sent2.packingBuffer();
														ROOM_GET_SLOTS_ACK sent3 = new ROOM_GET_SLOTS_ACK(r); sent3.packingBuffer();
														for (RoomSlot slot : r.SLOT)
														{
															Player m = AccountSyncer.gI().get(slot.player_id);
															if (m != null)
															{
																m.gameClient.sendPacketBuffer(sent2.buffer);
																m.gameClient.sendPacketBuffer(sent3.buffer);
															}
														}
														sent2.buffer = null; sent2 = null;
														sent3.buffer = null; sent3 = null;
													}
													else
													{
														BATTLE_READYBATTLE_ERROR_ACK sent = new BATTLE_READYBATTLE_ERROR_ACK(0x80001008); sent.packingBuffer();
														for (RoomSlot slot : r.SLOT)
														{
															Player m = AccountSyncer.gI().get(slot.player_id);
															if (m != null && slot.state == SlotState.READY)
																m.gameClient.sendPacketBuffer(sent.buffer);
														}
														sent.buffer = null; sent = null;
													}
													r.stopTask("countdown");
												}
											}, 5350);
										}
										catch (Throwable e)
										{
											exceptionLOG(e);
											r.stopTask("countdown");
										}
									}
								}
							}
						}
					}
				}
				else if (specialMode || players > 0)
				{
					if (checkClanFronto(p.slot % 2))
					{
						r.changeState(mySlot, SlotState.READY, true);
						r.rstate = RoomState.LOADING;
						for (int i = 0; i < 2; i++)
						{
							for (Player m : r.getPlayers())
							{
								RoomSlot s = r.getSlotByPID(m.id);
								if (s.state == SlotState.READY)
								{
									r.balanceamentoDeSlot(m, s, SlotState.READY, _slots, i == 1, haveBalance);
								}
							}
							BALANCESLOTS(haveBalance);
						}
						BATTLE_READYBATTLE_ACK sent1 = new BATTLE_READYBATTLE_ACK(r); sent1.packingBuffer();
						for (RoomSlot slot : r.SLOT)
						{
							Player m = AccountSyncer.gI().get(slot.player_id);
							if (m != null && slot.state == SlotState.READY)
							{
								m.gameClient.sendPacketBuffer(sent1.buffer);
								r.changeState(slot, SlotState.LOAD, false);
								r.prepareReady(m, slot);
							}
						}
						sent1.buffer = null; sent1 = null;
						ROOM_CHANGE_ROOMINFO_ACK sent2 = new ROOM_CHANGE_ROOMINFO_ACK(r, leader.name, specialMode); sent2.packingBuffer();
						ROOM_GET_SLOTS_ACK sent3 = new ROOM_GET_SLOTS_ACK(r); sent3.packingBuffer();
						for (RoomSlot slot : r.SLOT)
						{
							Player m = AccountSyncer.gI().get(slot.player_id);
							if (m != null)
							{
								m.gameClient.sendPacketBuffer(sent2.buffer);
								m.gameClient.sendPacketBuffer(sent3.buffer);
							}
						}
						sent2.buffer = null; sent2 = null;
						sent3.buffer = null; sent3 = null;
					}
				}
				else
				{
					client.sendPacket(new BATTLE_READYBATTLE_ERROR_ACK(0x80001009));
				}
			}
			else if (r.rstate.ordinal() > 1)
			{
				if (check4vs4())
					return;
				if (r.rstate != RoomState.BATTLE_END && mySlot != null)
				{
					r.changeState(mySlot, SlotState.READY, false);
					RoomSlot newSlot = r.balanceamentoDeSlot(p, mySlot, SlotState.READY, _slots, false, haveBalance);
					if (newSlot != null)
						mySlot = newSlot;
					BALANCESLOTS(haveBalance);
					client.sendPacket(new ROOM_GET_SLOTS_ACK(r)); //state ready
					r.changeState(mySlot, SlotState.LOAD, false);
					r.prepareReady(p, mySlot);
					mySlot.espectador = r.isGhostMode(p.espectador);
					mySlot.death = mySlot.espectador;
					client.sendPacket(new BATTLE_READYBATTLE_ACK(r));
					client.sendPacket(new BATTLE_READYBATTLE_ERROR_ACK(8));
					ROOM_GET_SLOTS_ACK sent2 = new ROOM_GET_SLOTS_ACK(r); sent2.packingBuffer();
					BATTLE_READYBATTLE2_ACK sent1 = new BATTLE_READYBATTLE2_ACK(p, mySlot.id); sent1.packingBuffer();
					for (RoomSlot sM : r.SLOT)
					{
						Player m = AccountSyncer.gI().get(sM.player_id);
						if (m != null)
						{
							if (sM.state.ordinal() >= 8 && sM.id != mySlot.id)
								m.gameClient.sendPacketBuffer(sent1.buffer);
							m.gameClient.sendPacketBuffer(sent2.buffer);
						}
					}
					sent1.buffer = null; sent1 = null;
					sent2.buffer = null; sent2 = null;
				}
			}
			else
			{
				RoomSlot slot = r.getSlotByPID(p.id);
				if (slot != null)
				{
					if (slot.state == SlotState.READY)
					{
						r.changeState(slot, SlotState.NORMAL, true);
						if (r.rstate == RoomState.COUNTDOWN)
							client.sendPacket(new ROOM_CHANGE_STATE_ACK(r, RoomState.READY));
					} 
					else if (slot.state == SlotState.NORMAL)
					{
						if (check4vs4())
							return;
						r.changeState(slot, SlotState.READY, true);
						if (r.rstate == RoomState.COUNTDOWN)
						{
							client.sendPacket(new ROOM_CHANGE_STATE_ACK(r, RoomState.COUNTDOWN));
							RoomSlot newSlot = r.balanceamentoDeSlot(p, slot, SlotState.READY, _slots, true, haveBalance);
							if (newSlot != null)
								slot = newSlot;
							BALANCESLOTS(haveBalance);
						}
					}
				}
				r.checkPlayersInReadyCountdown(true);
			}
		}
	}
	boolean checkCHARA()
	{
		p.quarent.clear();
		if (specialMode)
			return false;
		if (r.block(r.name) && r.getSlotState(p.id) == SlotState.NORMAL && p.access_level.ordinal() < 3)
		{
			CampXML xml = CampXML.gI();
			ConcurrentHashMap<Integer, RoomCamp> equips = xml.equips;
			String cupon = "";
			String available = "";
			if (p.equipment.char_head != 1102003001)
				available = "Helmet/Mask";
			for (RoomCamp rc : xml.forType(1))
			{
				if (cupon.length() == 0)
					cupon = "Restricted this coupons.\n"; //O Efeito do item foi desativado
				cupon += "[" + rc.name + "] ";
				p.quarent.put(rc.id, rc.type);
			}
			int itemId = 0;
			if (p.slot % 2 == 0 && equips.containsKey(itemId = p.equipment.char_red)) available = equips.get(itemId).name;
			if (p.slot % 2 == 1 && equips.containsKey(itemId = p.equipment.char_blue)) available = equips.get(itemId).name;
			if (equips.containsKey(itemId = p.equipment.weapon_primary)) available = equips.get(itemId).name;
			if (equips.containsKey(itemId = p.equipment.weapon_secundary)) available = equips.get(itemId).name;
			if (equips.containsKey(itemId = p.equipment.weapon_melee)) available = equips.get(itemId).name;
			if (equips.containsKey(itemId = p.equipment.weapon_grenade)) available = equips.get(itemId).name;
			if (equips.containsKey(itemId = p.equipment.weapon_special)) available = equips.get(itemId).name;
			if (equips.containsKey(itemId = p.equipment.char_head)) available = equips.get(itemId).name;
			if (equips.containsKey(itemId = p.equipment.char_beret)) available = equips.get(itemId).name;
			if (equips.containsKey(itemId = p.equipment.char_dino)) available = equips.get(itemId).name;
			if (available.length() > 0)
				client.sendPacket(new SERVER_MESSAGE_ANNOUNCE_ACK("Restricted this equipements.\n[" + available + "]"));
			if (cupon.length() > 0 && available.length() == 0)
				client.sendPacket(new SERVER_MESSAGE_ANNOUNCE_ACK(cupon));
			return available.length() > 0;
		}
		return false;
	}
	boolean checkClanFronto(int team)
	{
		Channel ch = client.getChannel();
		if (ch != null)
		{
			if (ch.type == ChannelServerEnum.CHANNEL_TYPE_CLAN && !specialMode)
			{
				if ((r.count4vs4team(TimeEnum.values()[team]) + 1) >= 4 && r.count4vs4team(TimeEnum.values()[team == 0 ? 1 : 0]) >= 4)
					return true;
				return false;
			}
		}
		return true;
	}
	boolean check4vs4()
	{
		if (r.stage4v4 != 0)
		{
			int lider = leader.slot;
			int slot = p.slot;
			int liderLimit = 3;
			if (r.getSlotState(lider).ordinal() > 7)
				liderLimit++;
			if (lider % 2 == slot % 2) //Time do lider tem que deixar 1 vaga pra ele setar o PRONTO
			{
				if (lider != slot && r.count4vs4team(TimeEnum.values()[slot % 2]) >= liderLimit)
				{
					client.sendPacket(new ROOM_UNREADY_4VS4_ACK(r.stage4v4));
					return true;
				}
			}
			else
			{
				if (r.count4vs4team(TimeEnum.values()[slot % 2]) >= 4)
				{
					client.sendPacket(new ROOM_UNREADY_4VS4_ACK(r.stage4v4));
					return true;
				}
			}
		}
		return false;
	}
	void BALANCESLOTS(int type)
	{
		if (_slots.size() > 0)
		{
			ROOM_CHANGE_SLOTS_ACK sent1 = new ROOM_CHANGE_SLOTS_ACK(r, leader.slot, 1, _slots); sent1.packingBuffer();
			ROOM_GET_SLOTS_ACK sent2 = new ROOM_GET_SLOTS_ACK(r); sent2.packingBuffer();
			for (RoomSlot slot : r.SLOT)
			{
				Player m = AccountSyncer.gI().get(slot.player_id);
				if (m != null)
				{
					m.gameClient.sendPacketBuffer(sent1.buffer);
					m.gameClient.sendPacketBuffer(sent2.buffer);
				}
			}
			sent1.buffer = null; sent1 = null;
			sent2.buffer = null; sent2 = null;
		}
		_slots.clear();
	}
}