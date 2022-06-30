package game.network.game.recv;

import game.network.game.sent.*;

import java.net.*;

import core.config.xml.*;
import core.enums.*;
import core.manager.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_USER_ENTER_REQ extends game.network.game.GamePacketREQ
{
	Player p;
	String login;
	byte[] localhost;
	int conexao;
	@Override
	public void readImpl()
	{
		login = ReadS(ReadC());
		client.pId = ReadQ();
		conexao = ReadC();
		localhost = ReadB(4);
	}
	@Override
	public void runImpl()
	{
		try
		{
			InetAddress net = InetAddress.getByAddress(localhost);
			GameServerInfo gsi = GameServerXML.gI().servers.get(client.getServerId());
			if (net != null && gsi != null && login.length() > 0 && client.pId != 0 && conexao == setting.connection && (gsi.senha.isEmpty() || gsi.senha.length() > 0 && client.passwd_enter))
			{
				p = client.getPlayer();
				if (p != null)
				{
					p.firstEnter = p.status() == 0;
					p.address = net;
					p.localhost = localhost;
					p.gameClient = client;
					p.addrEndPoint = client.ip;
					p.login = login;
					p.changeServer = false;
					db.replaceNick(p.id, p.name);
					db.replaceRank(p.id, p.rank);
					client.mac = db.returnQueryValueS("SELECT mac FROM contas WHERE id='" + p.id + "'", "mac");
					for (Player member : AccountSyncer.gI().ACCOUNTS.values())
					{
						if (member != null && member.gameClient != null && member.id != p.id)
						{
							io.netty.channel.Channel socket = member.gameClient.channel;
							if (socket.compareTo(client.socket) == 0)
							{
								member.gameClient.sendPacket(new BASE_KICK_ACCOUNT_ACK(member.gameClient, KickType.O_JOGO_SERA_FINALIZADO_POR_CONEXÃO_SIMULTÃNEA));
								member.gameClient.close();
								break;
							}
						}
					}
					String permite = EssencialUtil.stringBad(p.name, setting.bad_string);
					if (permite.length() > 0 && !AuthenticationAddress.gI().addr.containsKey(client.mac))
					{
						client.sendPacket(new SERVER_MESSAGE_ANNOUNCE_ACK("System has blocked this string for your nickname. (" + permite + ")"));
						client.sendPacket(new BASE_USER_ENTER_ACK(0x80000000));
						return;
					}
					if (!p.changeServer)
					{
						Clan c = ck.BUSCAR_CLAN(p.clan_id);
						if (c != null)
						{
							client.sendPacket(new CLAN_READ_MEMBERS_ACK(c, p.id));
							ClanInviteManager.gI().deleteInvite(p);
						}
						client.sendPacket(new FRIEND_INFO_ACK(p));
						if (p.status() > 0)
							EssencialUtil.updateFriends(p, 64, false);
						checkBonuses();
						playTime();
					}
					AccountSyncer.gI().replace(p);
					client.sendPacket(new BASE_USER_ENTER_ACK(0));
				}
				else
				{
					client.sendPacket(new BASE_USER_ENTER_ACK(0x80000000));
				}
			}
			else
			{
				client.sendPacket(new BASE_USER_ENTER_ACK(0x80000000));
			}
		}
		catch (Exception ex)
		{
			exceptionLOG(ex);
			client.sendPacket(new BASE_USER_ENTER_ACK(0x80000000));
		}
	}
	public void checkBonuses()
	{
		if (p.event.christmas == 1)
		{
			client.sendPacket(new INVENTORY_ITEM_CREATE_ACK(p, 702001024, 100, 1, -1));
			db.updateChristmas(p, p.event.christmas = 2);
		}
		if (p.vip_pccafe == 4)
		{
			client.sendPacket(new INVENTORY_ITEM_CREATE_ACK(p, 1105003005, 100, 1, -1));
			db.executeQuery("UPDATE jogador SET pc_cafe='" + (p.vip_pccafe = 0) + "' WHERE id='" + p.id + "'");
		}
	}
	public void playTime()
	{
		EventPlaytime e = PlaytimeXML.gI().playtimeNow();
		if (e != null && p.event.play_day != e.id)
		{
			if (p.event.played == 0)
			{
				p.event.played = e.time;
				db.executeQuery("UPDATE jogador_evento SET played='" + p.event.played + "' WHERE player_id='" + p.id + "'");
			}
			p.event.event_playtime = e.id;
		}
	}
}