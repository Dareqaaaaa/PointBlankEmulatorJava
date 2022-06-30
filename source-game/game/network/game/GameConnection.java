package game.network.game;

import game.network.battle.*;
import game.network.game.recv.*;
import game.network.game.sent.*;
import io.netty.buffer.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.*;

import java.awt.Color;
import java.nio.*;
import java.util.*;

import core.config.xml.*;
import core.info.*;
import core.model.*;
import core.network.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class GameConnection extends Connection
{
	final ProtocolProcessor<GamePacketREQ> processor = new ProtocolProcessor<GamePacketREQ>(1, 8);
	public long pId;
	public int shopId;
	public boolean passwd_enter = false, first_enter = false;
	int server_id = 0, channel_id = -1, room_id = -1;
	public GameConnection(SocketChannel socket, int server_id)
	{
		super(socket);
		this.server_id = server_id;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		super.channelActive(ctx);
		sendPacket(new BASE_SCHANNEL_LIST_ACK(this));
		sentDiposable();
	}
	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
	{
		ByteBuf buffer = null;
		try
		{
			buffer = in.order(ByteOrder.LITTLE_ENDIAN);
			int length = buffer.readableBytes();
			if (readHeader)
			{
				if (buffer.readableBytes() >= 4)
				{
					length = buffer.readUnsignedShort();
					if (length > 8908)
					{
						length &= 32767;
						decrypt = true;
					}
					readHeader = false;
				}
			}
			if (buffer.readableBytes() >= length + 2)
			{
				ByteBuf data = buffer.readBytes(length + 2);
				if (decrypt)
					BitShift.decryptCOD(data, CRYPT_KEY);
				receivePacket(data);
				readHeader = true;
				decrypt = false;
			}
		}
		catch (Throwable ex)
		{
			exceptionCaught(ctx, ex);
		}
		finally
		{
			buffer = null;
		}
	}
	public void receivePacket(ByteBuf buffer) throws Exception
	{
		Integer opcode = buffer.readUnsignedShort();
		if (buffer.readableBytes() > 0)
		{
			if (FIRST_PACKET == 0)
			{
				closeSent();
				FIRST_PACKET = opcode;
				if (opcode != 2577 && opcode != 2579 && opcode != 2644 && opcode != 2654 && opcode != 2654)
				{
					close();
					return;
				}
			}
			else
			{
				if (!first_enter)
				{
					Player p = getPlayer();
					if (p != null)
					{
						long status = p.status();
						if (status == 0 && p.firstEnter)
						{
							if (opcode != 3101 && opcode != 3073 && opcode != 3079 && opcode != 2821 && opcode != 2571 && opcode != 2573 && opcode != 2579 && opcode != 2584 && opcode != 2654)
							{
								close();
								return;
							}
						}
						if (status == 1)
							first_enter = true;
					}
				}
			}
			GamePacketREQ packet = null;
			int seed_receive = buffer.readUnsignedShort();
			boolean reced = seed_receive == calculeSeed(opcode); 
			if (true)
			{
				switch (opcode)
				{
					case 275: packet = new FRIEND_ROOM_INVITE_REQ(); break;
					case 280: packet = new FRIEND_ACCEPT_REQ(); break;
					case 282: packet = new FRIEND_INVITE_REQ(); break;
					case 284: packet = new FRIEND_REMOVE_REQ(); break;
					case 290:
					case 292: packet = new AUTH_SEND_WHISPER_REQ(); break;
					case 297: packet = new BASE_FIND_PLAYER_REQ(); break;
	
					case 417: packet = new MESSENGER_NOTE_SEND_REQ(); break;
					case 419: packet = new MESSENGER_NOTE_RESPONDE_REQ(); break;
					case 422: packet = new MESSENGER_NOTE_CHECK_READED_REQ(); break;
					case 424: packet = new MESSENGER_NOTE_DELETE_REQ(); break;
	
					case 528: packet = new BASE_USER_GIFTLIST_REQ(); break;
					case 530: packet = new AUTH_SHOP_GOODS_BUY_REQ(); break;
					case 534:
					case 536: packet = new INVENTORY_ITEM_EQUIP_REQ(); break;
					case 540: packet = new MESSENGER_NOTE_GIFT_TAKE_REQ(); break;
					case 542: packet = new INVENTORY_ITEM_DELETE_REQ(); break;
					case 544: packet = new AUTH_GET_REMAIN_MONEY_REQ(); break;
					case 548: packet = new INVENTORY_NICK_CHECK_REQ(); break;
	
					case 1304: packet = new CLAN_DETAIL_INFO_REQ(); break;
					case 1306: packet = new CLAN_MEMBER_CONTEXT_REQ(); break;
					case 1308: packet = new CLAN_MEMBER_LIST_REQ(); break;
					case 1310: packet = new CLAN_CREATE_REQ(); break;
					case 1312: packet = new CLAN_CLOSE_CLAN_REQ(); break;
					case 1314: packet = new CLAN_CREATE_PLAYER_INVITE_REQ(); break;
					case 1316: packet = new CLAN_CREATE_INVITE_REQ(); break;
					case 1318: packet = new CLAN_REMOVE_INVITES_REQ(); break;
					case 1320: packet = new CLAN_REQUEST_CONTEXT_REQ(); break;
					case 1322: packet = new CLAN_REQUEST_LIST_REQ(); break;
					case 1324: packet = new CLAN_REQUEST_INFO_REQ(); break;
					case 1326: packet = new CLAN_ACCEPT_REQUEST_REQ(); break;
					case 1329: packet = new CLAN_DENIAL_REQUEST_REQ(); break;
					case 1332: packet = new CLAN_PLAYER_LEAVE_REQ(); break;
					case 1334: packet = new CLAN_DEMOTE_KICK_REQ(); break;
					case 1337: packet = new CLAN_PROMOTE_MASTER_REQ(); break;
					case 1340: packet = new CLAN_COMMISSION_STAFF_REQ(); break;
					case 1343: packet = new CLAN_COMMISSION_REGULAR_REQ(); break;
					case 1358: packet = new CLAN_CHATTING_REQ(); break;
					case 1360: packet = new CLAN_CHECK_LOGO_REQ(); break;
					case 1362: packet = new CLAN_REPLACE_NOTICE_REQ(); break;
					case 1364: packet = new CLAN_REPLACE_INTRO_REQ(); break;
					case 1372: packet = new CLAN_SAVE_ACCESS_REQ(); break;
					case 1381: packet = new CLAN_ROOM_INVITED_REQ(); break;
					case 1392: packet = new CLAN_MESSAGE_INVITE_REQ(); break;
					case 1394: packet = new CLAN_MESSAGE_INVITE_RESP_REQ(); break;
					case 1396: packet = new CLAN_MSG_FOR_PLAYERS_REQ(); break;
	
					case 1416: packet = new CLAN_CREATE_REQUESITES_REQ(); break;
					case 1441: packet = new CLAN_ENTER_REQ(); break;
					case 1443: packet = new CLAN_LEAVE_REQ(); break;
					case 1445: packet = new CLAN_CLIENT_LIST_REQ(); break;
					case 1447: packet = new CLAN_CHECK_NAME_REQ(); break;
					case 1451: packet = new CLAN_IN_CS_LIST_REQ(); break;
	
					case 1538: packet = new CLAN_MATCH_CLAN_PART2_REQ(); break;
					case 1540: packet = new CLAN_MATCH_CLAN_PART_REQ(); break;
					case 1542: packet = new CLAN_WAR_MATCH_TEAM_COUNT_REQ(); break;
					case 1544: packet = new CLAN_WAR_MATCH_TEAM_LIST_REQ(); break;
					case 1546: packet = new CLAN_WAR_CREATE_TEAM_REQ(); break;
					case 1550: packet = new CLAN_MATCH_REMOVE_CLAN_REQ(); break;
					case 1558: packet = new CLAN_MATCH_ACCEPTED_BATTLE_REQ(); break;
					case 1571: packet = new CLAN_MATCH_UPTIME_REQ(); break;
					case 1576: packet = new CLAN_WAR_TEAM_CHATTING_REQ(); break;
	
					case 2571: packet = new BASE_CHANNEL_LIST_REQ(); break;
					case 2573: packet = new BASE_CHANNEL_ANNOUNCE_REQ(); break;
					case 2577: packet = new BASE_USER_LEAVE_REQ(); break;
					case 2579: packet = new BASE_USER_ENTER_REQ(); break;
					case 2581: packet = new PLAYER_CONFIG_UPDATE_REQ(); break;
					case 2584: packet = new BASE_XINGCODE_REQ(); break; //2582
					case 2591: packet = new BASE_GET_RECORD_INFO_DB_REQ(); break;
	
					case 2601: packet = new MISSION_QUEST_UPDATE_CARD_REQ(); break;
					case 2605: packet = new MISSION_QUEST_BUY_CARD_REQ(); break;
					case 2607: packet = new MISSION_QUEST_DELETE_CARD_REQ(); break;
					case 2619: packet = new TITLE_GET_REQ(); break;
					case 2621: packet = new TITLE_USE_REQ(); break;
					case 2623: packet = new TITLE_DETACH_REQ(); break;
					case 1390:
					case 2627: packet = new LOBBY_CHATTING_REQ(); break;
					case 2635: packet = new MISSION_QUEST_COMPLETE_REQ(); break;
					case 2639: packet = new LOBBY_PLAYER_INFO_REQ(); break;
					case 2642: packet = new BASE_SCHANNEL_UPDATE_REQ(); break;
					case 2644: packet = new BASE_CHANNEL_PASSWD_REQ(); break;
					case 2654: packet = new PLAYER_EXIT_GAME_REQ(); break;
					case 2661: packet = new ATTENDANCE_CHECK_REQ(); break;
					case 2663: packet = new ATTENDANCE_REWARD_REQ(); break;
					case 2684: packet = new BASE_ACCOUNT_LOG_CHANNEL_REQ(); break;
					case 2686: packet = new BASE_ACCOUNT_LOG_ROOM_REQ(); break;
					
					case 2817: packet = new SHOP_LEAVE_REQ(); break;
					case 2819: packet = new SHOP_ENTER_REQ(); break;
					case 2821: packet = new LOBBY_SHOP_LIST_REQ(); break;
					case 2897: packet = new OUTPOST_ENTER_REQ(); break;
					case 2899: packet = new OUTPOST_LEAVE_REQ(); break;
					case 2901: packet = new OUTPOST_AWARD_REQ(); break;
	
					case 3073: packet = new LOBBY_GET_ROOMLIST_REQ(); break;
					case 3077: packet = new LOBBY_QUICKJOIN_ROOM_REQ(); break;
					case 3079: packet = new LOBBY_ENTER_REQ(); break;
					case 3081: packet = new ROOM_JOIN_REQ(); break;
					case 3083: packet = new LOBBY_LEAVE_REQ(); break;
					case 3087: packet = new LOBBY_GET_ROOMINFOADD_REQ(); break;
					case 3089: packet = new ROOM_CREATE_REQ(); break;
					case 3096: packet = new LOBBY_ANTIGAME_REQ(); break;
					case 3094: break; //GClass6
					case 3099: packet = new LOBBY_PLAYER_INFO2_REQ(); break;
	
					case 3101: packet = new LOBBY_CREATE_NICK_NAME_REQ(); break;
	
					case 3329: packet = new BATTLE_HOLE_CHECK_REQ(); break;
					case 3331: packet = new BATTLE_READYBATTLE_REQ(); break;
					case 3333: packet = new BATTLE_STARTBATTLE_REQ(); break;
					case 3337: packet = new BATTLE_RESPAWN_REQ(); break;
					case 3343: packet = new BATTLE_NETWORK_ERROR_REQ(); break;
					case 3344: packet = new BATTLE_SENDPING_REQ(); break;
					case 3348: packet = new BATTLE_PRESTARTBATTLE_REQ(); break;
					case 3354: packet = new BATTLE_DEATH_REQ(); break;
					case 3356: packet = new BATTLE_MISSION_BOMB_INSTALL_REQ(); break; 
					case 3358: packet = new BATTLE_MISSION_BOMB_UNINSTALL_REQ(); break; 
					case 3368: packet = new BATTLE_MISSION_GENERATOR_INFO_REQ(); break;
					case 3372: packet = new BATTLE_TIMERSYNC_REQ(); break;
					case 3376: packet = new BATTLE_CHANGE_DIFFICULTY_LEVEL_REQ(); break;
					case 3378: packet = new BATTLE_RESPAWN_FOR_AI_REQ(); break;
					case 3382: packet = new BATTLE_DINO_ESCAPE_REQ(); break;	
					case 3384: packet = new BATTLE_LEAVEP2PSERVER_REQ(); break; 
					case 3386: packet = new BATTLE_MISSION_DEFENCE_INFO_REQ(); break;
					case 3390: packet = new BATTLE_DINO_DEATHBLOW_REQ(); break;
					case 3394: packet = new BATTLE_TUTORIAL_END_REQ(); break;
					case 3396: packet = new VOTEKICK_START_REQ(); break;
	
					case 3400: packet = new VOTEKICK_UPDATE_REQ(); break;
					case 3428: packet = new BATTLE_TIMER_INFO_REQ(); break;
	
					case 3585: packet = new INVENTORY_ENTER_REQ(); break;
					case 3589: packet = new INVENTORY_LEAVE_REQ(); break;
	
					case 3841: packet = new ROOM_GET_PLAYERINFO_REQ(); break;
					case 3845: packet = new ROOM_CHANGE_SLOT_REQ(); break;
					case 3847: packet = new ROOM_CHANGE_ROOMINFO_REQ(); break;
					case 3849: packet = new ROOM_CLOSE_SLOT_REQ(); break;
					case 3854: packet = new ROOM_GET_LOBBY_USER_LIST_REQ(); break;
					case 3858: packet = new ROOM_CHANGE_OPTIONINFO_REQ(); break;
					case 3862: packet = new PROFILE_ENTER_REQ(); break;
					case 3864: packet = new PROFILE_LEAVE_REQ(); break;
					case 3866: packet = new ROOM_GET_HOST_REQ(); break;
					case 3868: packet = new ROOM_RANDOM_HOST_REQ(); break;
					case 3870: packet = new ROOM_CHANGE_HOST_REQ(); break;
					case 3872: packet = new ROOM_NEW_HOST_REQ(); break;
					case 3874: packet = new ROOM_CHANGE_SLOTS_REQ(); break;
					case 3884: packet = new ROOM_INVITES_PLAYERS_REQ(); break;
					case 3886: packet = new ROOM_CHANGE_INFO_REQ(); break;
					case 3890: packet = new BASE_KICK_ACCOUNT_ROOM_REQ(); break;
					case 3894: packet = new BASE_KICK_ACCOUNT_REQ(); break;
					
					case 3900: packet = new ROOM_SLOT_REASON_REQ(); break;
					case 3902: break;
					case 3904: packet = new BATTLE_LOADING_REQ(); break;
					case 3906: packet = new ROOM_CHANGE_PASSWD_REQ(); break;
					case 3910: packet = new BATTLE_AWARD_TIME_REQ(); break;
					case 2575:
					case 2694:
					case 2818:
					case 2820: break;
					default:
					{
						if (opcode.toString().length() == 3 || opcode.toString().length() == 4)
						{
							Software.LogColor(" Opcode error listed. ", Color.YELLOW);
							Logger.gI().info("error", null, (this + NetworkUtil.printData(String.format(" [GC] OPCODE NOT FOUNDED : 0x%02X [int: %d]", opcode, opcode), buffer)), getClass());
							close();
							return;
						}
					}
				}
				if (packet != null && packet.readPacket(buffer, this, opcode))
				{
					processor.newThread(packet);
					return;
				}
				else
				{
					buffer = null;
				}
			}
			else
			{
				Software.LogColor(" esperava-se: " + SEED + " - recebido: " + seed_receive, Color.RED);
				sendPacket(new SERVER_MESSAGE_ANNOUNCE_ACK("[Seed] Esperava-se: " + SEED + "; Recebido: " + seed_receive));
			}
			if (!reced)
			{
				Software.LogColor(" esperava-se: " + SEED + " - recebido: " + seed_receive, Color.RED);
				sendPacket(new SERVER_MESSAGE_ANNOUNCE_ACK("[Seed] Esperava-se: " + SEED + "; Recebido: " + seed_receive));
			}
		}
	}
	@Override
	public void sendPacket(PacketACK packet)
	{
		try
		{
			if (packet != null && packet instanceof GamePacketACK)
			{
				((GamePacketACK) packet).writeImpl(this);
				packet.finalize();
			}
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		packet = null;
	}
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception
	{
		close();
		super.channelUnregistered(ctx);
	}
	@Override
	public void close()
	{
		boolean changeServer = false;
		try
		{
			PlayerSQL db = PlayerSQL.gI();
			Player p = getPlayer();
			if (p != null)
			{
				changeServer = p.changeServer;
				try
				{
					Room r = getRoom();
					if (r != null)
					{
						BATTLE_LEAVEP2PSERVER_REQ packet = new BATTLE_LEAVEP2PSERVER_REQ();
						packet.remove = true;
						packet.escape = false;
						packet.client = this;
						packet.buffer = null;
						packet.opcode = 3384;
						processor.newThread(packet);
						packet.finalize();
						packet = null;
						BattleManager.gI().removePlayer(r, p.slot);
						r.removePlayer(p, p.slot, false, true, false);
					}
					Channel ch = getChannel();
					if (ch != null)
					{
						ch.removePlayer(pId);
						ch.removeEmptyRooms();
					}
				}
				catch (Exception e)
				{
				}
				p.cf_id = -1;
				p.cf_slot = -1;
				p.slot = -1;
				channel_id = -1;
				room_id = -1;
				p.gameClient = this;
				if (!changeServer)
				{
					p.online = 0;
					db.update(p);
					db.replaceNick(p.id, p.name);
					db.replaceRank(p.id, p.rank);
					EssencialUtil.updateFriends(p, 48, false);
				}
				EssencialUtil.updateFRC(p, false, false, true);
				if (p != null)
					p.finalize();
				p = null;
			}
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
		finally
		{
			if (!changeServer)
				checkOnlyAccount();
			super.close();
		}
	}
	public void checkOnlyAccount()
	{
		try
		{
			PlayerSQL db = PlayerSQL.gI();
			Player p = getPlayer();
			if (p != null && p.online == 1)
			{
				p.online = 0;
				db.updateOnline(pId, 0);
			}
			else if (db.getOnlinePlayer(pId))
			{
				db.updateOnline(pId, 0);
			}
			AccountSyncer.gI().remove(pId);
		}
		catch (Throwable ex)
		{
			exceptionLOG(ex);
		}
	}
	public Player getPlayer()
	{
		Player p = AccountSyncer.gI().get(pId);
		if (p != null && p.gameClient != null)
			p.gameClient = this;
		return p;
	}
	public Room getRoom()
	{
		Channel ch = getChannel();
		if (ch != null)
			return ch.getRoom(room_id);
		return null;
	}
	public Channel getChannel()
	{
		return GameServerXML.gI().getChannel(channel_id, server_id);
	}
	public int getServerId()
	{
		return server_id;
	}
	public void setServerId(int server_id)
	{
		this.server_id = server_id;
	}
	public int getChannelId()
	{
		return channel_id;
	}
	public void setChannelId(int channel_id)
	{
		this.channel_id = channel_id;
	}
	public int getRoomId()
	{
		return room_id;
	}
	public void setRoomId(int room_id)
	{
		this.room_id = room_id;
	}
}