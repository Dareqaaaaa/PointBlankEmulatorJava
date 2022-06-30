package game;

import game.manager.*;
import game.network.auth.*;
import game.network.battle.*;
import game.network.game.sent.*;

import java.awt.*;

import core.config.dat.*;
import core.config.settings.*;
import core.config.xml.*;
import core.enums.*;
import core.info.*;
import core.manager.*;
import core.model.*;
import core.network.*;
import core.postgres.sql.*;
import core.utils.*;

/**
 * 
 * @author eoq_henrqu
 *
 */

public class Program extends core.postgres.sql.InterfaceSQL
{
	public static void main(String[] args)
	{
		try
		{
			Software.printIntro("Game", Color.YELLOW, args);
			
			//Config
			ThreadPoolManager.gI();
			ConfigurationLoader.gI();

			//DAT
			BoxDAT.gI();
			CuponsDAT.gI();
			MissionsDAT.gI();
			SetsDAT.gI();
			TitlesDAT.gI();
			
			//XML
			CampXML.gI().LOAD();
			CheckXML.gI().LOAD();
			GameServerXML.gI().LOAD(false);
			MapsXML.gI().LOAD();
			PlaytimeXML.gI().LOAD();
			RankXML.gI().LOAD();
			TemplateXML.gI().LOAD();
			UrlXML.gI().LOAD();
			
			//Manager
			ClanInviteManager.gI();
			ClanManager.gI();
			IPSystemManager.gI();
			AccountSyncer.gI();
			ShopFunction.gI().FONT();
			AuthManager.gI();
			BattleManager.gI();
			AccountSyncer.gI();
			AuthenticationAddress.gI();
			
			//Database
			AccountSQL.gI();
			FriendSQL.gI();
			PlayerSQL.gI().NICKS_AND_RANKS();

			//Network
			Socket.start();
			
			commands();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			error(Program.class, e);
			System.out.println(" Erro fatal. Check log files!!! ");
			System.out.flush();
		}
	}
	/*protected static void commands()
	{
		while (true)
		{
			try
			{
				String read = Software.console.readLine();
				if (read.startsWith("/a "))
				{
					String command = read.substring(3).trim();
					if (command.startsWith("online"))
					{
						log("Clientes online: " + AccountSyncer.gI().ACCOUNTS.size());
					}
					if (command.startsWith("recarregar_config"))
					{
						ConfigurationLoader.gI().LOAD();
						log("As configuracoes foram recarregadas.");
					}
					else if (command.startsWith("recarregar_xml"))
					{
						GameServerXML.gI().LOAD(true);
						CheckXML.gI().LOAD();
						PlaytimeXML.gI().LOAD();
						TemplateXML.gI().LOAD();
						CampXML.gI().LOAD();
						RankXML.gI().LOAD();
						log("Os XML foram recarregados.");
					}
					else if (command.startsWith("recarregar_loja"))
					{
						ShopFunction s = ShopFunction.gI();
						int last = s.shopNow;
						s.FONT();
						log("Loja (" + s.shopNow + "/" + last + ") recarregada.");
					}
					else if (command.startsWith("msg "))
					{
						SERVER_MESSAGE_ANNOUNCE_ACK sent = new SERVER_MESSAGE_ANNOUNCE_ACK(command.substring(4) + " "); sent.packingBuffer();
						for (Player pR : AccountSyncer.gI().ACCOUNTS.values())
							if (pR != null && pR.gameClient != null)
								pR.gameClient.sendPacketBuffer(sent.buffer);
						sent.buffer = null; sent = null;
						log("Enviando mensagem ('" + command.substring(2) + "') para " + AccountSyncer.gI().ACCOUNTS.size() + " jogadores");
					}
					else if (command.startsWith("kikar_todos"))
					{
						int total = 0;
						for (Player mM : AccountSyncer.gI().ACCOUNTS.values())
						{
							if (mM != null && mM.gameClient != null && mM.rank != 53 && mM.rank != 54 && mM.access_level == AccessLevel.OFF)
							{
								mM.gameClient.sendPacket(new BASE_KICK_ACCOUNT_ACK(mM.gameClient, KickType.O_JOGO_SERA_FINALIZADO_POR_SOLICITAÇÃO_DO_SERVIDOR));
								mM.gameClient.close();
								total++;
							}
						}
						log("Todos os jogadores foram desconectados: " + total + "/" + AccountSyncer.gI().ACCOUNTS.size());
					}
					else if (command.startsWith("forcado_kikar_todos"))
					{
						int total = 0;
						for (Player mM : AccountSyncer.gI().ACCOUNTS.values())
						{
							if (mM != null && mM.gameClient != null)
							{
								mM.gameClient.sendPacket(new BASE_KICK_ACCOUNT_ACK(mM.gameClient, KickType.O_JOGO_SERA_FINALIZADO_POR_SOLICITAÇÃO_DO_SERVIDOR));
								mM.gameClient.close();
								total++;
							}
						}
						log("Todos os jogadores foram desconectados (f): " + total + "/" + AccountSyncer.gI().ACCOUNTS.size());
					}
					else if (command.startsWith("kikar_jogador "))
					{
						String nome = command.substring(14);
						Player mM = AccountSyncer.gI().get(nome);
						if (mM != null && mM.gameClient != null)
						{
							mM.gameClient.sendPacket(new BASE_KICK_ACCOUNT_ACK(mM.gameClient, KickType.O_JOGO_SERA_FINALIZADO_POR_SOLICITAÇÃO_DO_SERVIDOR));
							mM.gameClient.close();
							log("Jogador desconectado: " + nome + "");
						}
						else
						{
							log("Jogador nao foi encontrado: " + nome + "");
						}
					}
					else if (command.startsWith("resetar_online"))
					{
						PlayerSQL.gI().	executeQuery("UPDATE jogador SET online='0");
						log("Todas as contas foram checkadas pelo status na database.");
					}
					else
					{
						log("Comando nao foi encontrado. (" + command + ")");
					}
				}
			}
			catch (Exception e)
			{
				log("-------------------------------------------------");
				log("Erro Fatal (Causa: " + e.getCause() + ")");
				log("-------------------------------------------------");
			}
		}
	}*/
	protected static void log(String texto)
	{
		String print = "";
		if (!texto.substring(1).equals("-"))
			print = " [Admin] ";
		System.out.println(print + texto);
		System.out.flush();
	}
}