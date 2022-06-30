package game.network.game.recv;

import game.network.game.sent.*;
import core.enums.*;
import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class ROOM_CREATE_REQ extends game.network.game.GamePacketREQ
{
	Player p;
	Room r;
	RoomSlot s;
	int error = 0x80000000, slot, slots;
	@Override
	public void readImpl()
	{
		p = client.getPlayer();
		if (p != null && p.status() > 0 && client.getRoom() == null && (setting.room_create || p.access_level == AccessLevel.MASTER_PLUS))
		{
			Channel c = client.getChannel();
			if (c != null)
			{
				if (c.SALAS.size() >= c.max_rooms)
				{
					client.sendPacket(new SERVER_MESSAGE_ANNOUNCE_ACK("Room limit has been reached, try again later. '" + c.SALAS.size() + "'"));
					return;
				}
				else if (!c.leaderRoom(p.id))
				{
					for (int i = 0; i < c.max_rooms; i++)
					{
						if (c.getRoom(i) == null)
						{
							if (p.delay == 0)
								p.delay = System.currentTimeMillis();
							else
							{
								long interval = System.currentTimeMillis() - p.delay;
								if (interval <= 500)
								{
									p.tentativa_erro++;
									if (p.tentativa_erro >= 4)
									{
										client.sendPacket(new BASE_KICK_ACCOUNT_ACK(p.gameClient, KickType.O_JOGO_SERA_FINALIZADO_POR_SOLICITAÇÃO_DO_SERVIDOR));
										client.close();
										db.updateAccountStatusAndBanExpirates(p.id, false, date.getMinutesNow(10));
									}
									break;
								}
								else
								{
									p.delay = 0;
								}
							}
							ReadD();
							r = new Room(i, c.id, client.getServerId()); //client.id
							r.name = ReadS(23);
							r.map = ReadH();
							r.stage4v4 = ReadC();
							r.type = ModesEnum.values()[ReadC()];
							if (r.type == ModesEnum.DINO)
							{
								r = null;
								return;
							}
							ReadC();
							ReadC();
							slots = ReadC(); //8-10-16
							r.setSlots(r.stage4v4 == 1 ? 8 : slots);
							ReadC();
							r.allWeapons = ReadC();
							r.randomMap = ReadC();
							r.special = ReadC();
							ReadS(33);
							r.killMask = ReadC();
							ReadC();
							ReadC();
							ReadC();
							r.limit = ReadC();
							r.seeConf = ReadC();
							r.balanceamento = ReadH();
							r.passwd = ReadS(4);
							if (r.isSpecialMode()) 
							{
								r.aiCount = ReadC();
								r.aiLevel = ReadC();
							}
							if (c.type == ChannelServerEnum.CHANNEL_TYPE_CLAN)
							{
								if (r.isSpecialMode())
								{
									error = 0x8000107D;
									return;
								}
							}
							s = r.addPlayer(p);
							if (s != null)
							{
								c.removePlayer(p.id);
								r.LIDER = p.id;
								p.gameClient.setRoomId(i);
								client.setRoomId(i);
								if (r.allWeapons == 128)
									r.modoPorrada = true;
								r.badName();
								r.badConfig();
								c.SALAS.put(i, r);
								error = 0;
							}
							else
							{
								r = null;
							}
							break;
						}
					}
				}
			}
		}
	}
	@Override
	public void runImpl()
	{
		if (r != null && s != null && error == 0)
		{
			client.sendPacket(new ROOM_CREATE_ACK(r, 0));
			client.sendPacket(new ROOM_GET_SLOT_PLAYER_ACK(p, s));
			client.sendPacket(new ROOM_GET_SLOTS_ACK(r));
		}
		else
		{
			client.sendPacket(new ROOM_CREATE_ACK(null, 0x80000000));
		}
	}
}