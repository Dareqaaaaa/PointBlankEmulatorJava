package game.network.game.recv;

import game.network.game.sent.*;

import java.util.*;

import core.config.dat.*;
import core.config.xml.*;
import core.info.*;
import core.model.*;
import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class INVENTORY_ITEM_EQUIP_REQ extends game.network.game.GamePacketREQ
{
	PlayerInventory invent;	
	Player player;
	Room room;
	RandomBox box;
	Coupon cup;
	int dias;
    public void readImpl()
    {
    	player = client.getPlayer();
    	room = client.getRoom();
    	if (player != null)
    	{
    		invent = player.buscarPeloObjeto(ReadQ());
    		if (invent != null)
    		{
    			if (invent.slot == 11)
				{
					dias = Integer.parseInt(String.valueOf(invent.item_id).substring(7, 10));
	    			invent.cupon_id = (invent.item_id - (100000000 + dias));
				}
    			if (opcode == 536)
    			{
					invent.effect_length = ReadC();
	    			invent.effect = ReadC();
    			}
    			cup = CuponsDAT.gI().get(invent.cupon_id);
    			BoxDAT rb = BoxDAT.gI();
    			if (rb.randomBox.containsKey(invent.item_id))
    				box = rb.randomBox.get(invent.item_id);
    		}
    	}
    }
    public void runImpl()
    {
    	try
    	{
    		if (invent != null)
    		{
    			if (box != null)
    			{
    				randomBox();
    			}
    			else if (cup != null)
    			{
    				if (invent.cupon_id == 1200009000 && invent.effect > 50 && cup.value == 0)
        			{
        				client.sendPacket(new INVENTORY_ITEM_EQUIP_ACK(null, null, 0x80000000, false));
        				return;
        			}
        			client.sendPacket(new INVENTORY_ITEM_EQUIP_ACK(invent, player, 1, true));
        			client.sendPacket(new INVENTORY_ITEM_CREATE_ACK(player, invent.cupon_id, date.getDateTime(dias), 2, dias));
        			duraveis();
        		}
    	    	else
    	    	{
    	    		if (invent.slot == 11)
        			{
    	    			int error = consumiveis();
    	    			client.sendPacket(new INVENTORY_ITEM_EQUIP_ACK(invent, player, error, true));
    	                if (opcode == 536 && error == 1)
    	                	client.sendPacket(new INVENTORY_ITEM_EQUIP2_ACK(invent.object, error));
        			}
        			else
        			{
        				invent.count = date.getDateTime(invent.count / 86400);
        				invent.equip = 2;
    	        		client.sendPacket(new INVENTORY_ITEM_EQUIP_ACK(invent, player, 1, false));
    	        		player.updateCountAndEquip(invent);
        			}
    			}
    		}
    		else
	    	{
	    		client.sendPacket(new INVENTORY_ITEM_EQUIP_ACK(null, null, 0x80000000, false));
	    	}
    	}
    	catch (Throwable e)
    	{
    		exceptionLOG(e);
    		client.sendPacket(new INVENTORY_ITEM_EQUIP_ACK(null, null, 0x80000000, false));
    	}
    }
    void failedEquip(Throwable e, int error)
    {
    	if (e != null)
    		exceptionLOG(e);
    	error = 0x80000000;
    }
    void randomBox() throws Throwable
    {
    	if (invent != null)
    	{
    		boolean jackpot = false;
	    	Random random = new Random();
	    	if (box != null)
	    	{
		    	int resultado = random.nextInt(box.total + 1);
	        	if (randomizar(box, resultado)) resultado = random.nextInt(box.total - 1);
	        	if (randomizar(box, resultado)) resultado = random.nextInt(box.total + 2);
	        	if (randomizar(box, resultado)) resultado = random.nextInt(box.total - 1);
	        	if (randomizar(box, resultado)) resultado = random.nextInt(box.total);
	        	if (resultado > box.total) resultado = box.total;
	        	client.sendPacket(new INVENTORY_RANDOMBOX_ACK(invent.item_id, resultado));
	        	client.sendPacket(new INVENTORY_ITEM_EQUIP_ACK(invent, player, 1, true));
		    	for (RandomBoxReward r : box.items)
		    	{
					if (r.random == resultado)
					{
						if (r.equip == 1 && r.item_id > 0)
						{
							client.sendPacket(new INVENTORY_ITEM_CREATE_ACK(player, r.item_id, r.count, 1, -1));
						}
						else if (r.equip == 21 && r.count > 0)
						{
							player.gold += r.count;
							client.sendPacket(new AUTH_GET_REMAIN_MONEY_ACK(player));
							db.updateGold(player);
						}
						if (r.jackpot)
							jackpot = true;
					}
		    	}
		    	if (jackpot)
		    	{
		    		for (Player pR : AccountSyncer.gI().ACCOUNTS.values())
						if (pR != null && pR.gameClient != null)
							pR.gameClient.sendPacket((new INVENTORY_ITEM_JACKPOT_ACK(player.name, box.id, resultado)));
		    	}
				random = null;
	    	}
	    	else
	    	{
	    		throw new Throwable("Randombox nao sincronizada.");
	    	}
    	}
    }
    void duraveis()
    {
		switch (invent.cupon_id)
		{
			case 1200006000: //APELIDO COLORIDO
			{
				if (player.color != invent.effect)
				{
					player.color = invent.effect;
	    			if (room != null)
	                {
	    				ROOM_GET_NICKNAME_ACK sent = new ROOM_GET_NICKNAME_ACK(player); sent.packingBuffer();
		    			for (RoomSlot s : room.SLOT)
    					{
    						Player m = s.player_id > 0 ? AccountSyncer.gI().get(s.player_id) : null;
    						if (m != null)
    							m.gameClient.sendPacketBuffer(sent.buffer);
    					}
		    			sent.buffer = null; sent = null;
	                }
	                client.sendPacket(new PLAYER_UPDATE_NICK_ACK(player));
	                db.updateColor(player, player.color);
				}
				break;
			}
			case 1200009000: //PATENTE FALSA
			{
				if (player.false_rank != invent.effect)
				{
					player.false_rank = invent.effect;
	                client.sendPacket(new PLAYER_COUPON_INFO_ACK(player, invent.effect_length));
	                db.updateFRank(player, player.false_rank);
	                if (room != null)
	                	room.updateInfo();
				}
				break;
			}
			case 1200014000: //COR DA MIRA
			{
				if (player.cor_mira != invent.effect)
				{
					player.cor_mira = invent.effect;
					client.sendPacket(new PLAYER_COUPON_INFO_ACK(player, invent.effect_length));
	                db.updateCorMira(player, player.cor_mira);
				}
				break;
			}
		}
        if (cup.value > 0)
        {
         	player.effect1 = CuponsDAT.gI().couponCalcule(player, 1);
        	player.effect2 = CuponsDAT.gI().couponCalcule(player, 2);
        	player.effect3 = CuponsDAT.gI().couponCalcule(player, 3);
        	player.effect4 = CuponsDAT.gI().couponCalcule(player, 4);
        	player.effect5 = CuponsDAT.gI().couponCalcule(player, 5);
        }
    }
    int consumiveis()
    {
    	int error = 1;
		switch (invent.item_id)
		{
    		case 1301047000: //ALTERAR NICK
    		{
    			try
    			{
    				if (player.update_nick.length() >= 2)
    				{
	    				String oldname = player.name;
		    			player.name = player.update_nick;
		    			player.update_nick = "";
		    			if (room != null)
		                {
		    				ROOM_GET_NICKNAME_ACK sent = new ROOM_GET_NICKNAME_ACK(player); sent.packingBuffer();
			    			for (RoomSlot s : room.SLOT)
	    					{
	    						Player m = s.player_id > 0 ? AccountSyncer.gI().get(s.player_id) : null;
	    						if (m != null)
	    							m.gameClient.sendPacketBuffer(sent.buffer);
	    					}
			    			sent.buffer = null; sent = null;
		                }
		                client.sendPacket(new PLAYER_UPDATE_NICK_ACK(player));
		    			db.updateNick(player, player.name);
		    			db.replaceNick(player.id, player.name);
		    			EssencialUtil.updateFRC(player, false, true, true);
		    			Logger.gI().info("nicks", null, "Conta '" + oldname + "' trocou o apelido para '" + player.name + "' [" + player.id + "]", getClass());
    				}
    				else
    				{
    					error = 0x80000000;
    				}
    			}
    			catch (Throwable e)
    			{
    				failedEquip(e, error);
    			}
    			break;
    		}
    		case 1301048000: //RESETAR P/V/D
    		{
    			player.stats.partidas = 0;
    			player.stats.ganhou = 0;
    			player.stats.perdeu = 0;
    			client.sendPacket(new PLAYER_UPDATE_STATS_ACK(player.stats));
    			db.executeQuery("UPDATE jogador_estatisticas SET partidas='0', ganhou='0', perdeu='0' WHERE player_id='" + player.id + "'");
    			break;
    		}
    		case 1301049000: //RESETAR K/D
    		{
    			player.stats.matou = 0;
    			player.stats.morreu = 0;
    			player.stats.headshots = 0;
    			client.sendPacket(new PLAYER_UPDATE_STATS_ACK(player.stats));
    			db.executeQuery("UPDATE jogador_estatisticas SET matou='0', morreu='0', headshots='0' WHERE player_id='" + player.id + "'");
    			break;
    		}
    		case 1301050000: //RESETAR D
    		{
    			if (player.stats.kitou != 0)
    			{
	    			player.stats.kitou = 0;
	    			client.sendPacket(new PLAYER_UPDATE_STATS_ACK(player.stats));
	    			db.executeQuery("UPDATE jogador_estatisticas SET kitou='0' WHERE player_id='" + player.id + "'");
    			}
    			break;
    		}
    		case 1301114000: //500 DE GOLD
    		case 1301115000: //1K DE GOLD
    		case 1301116000: //5K DE GOLD
    		case 1301117000: //10K DE GOLD
    		case 1301118000: //30K DE GOLD
    		{
				int gold = invent.item_id == 1301118000 ? 30000 : invent.item_id == 1301117000 ? 10000 : invent.item_id == 1301116000 ? 5000 : invent.item_id == 1301115000 ? 1000 : invent.item_id == 1301114000 ? 500 : 0; 
    			player.gold += gold;
    			client.sendPacket(new PLAYER_UPDATE_GOLD_ACK(player, gold));
    			db.updateGold(player);
    			break;
    		}
    		case 1200005000: //COR DO NOME DO CLAN
    		{
    			try
    			{
	                Clan c = ck.BUSCAR_CLAN(player.clan_id);
	                if (c != null && c.owner == player.id)
    				{
	                	if (c.color != invent.effect)
	                	{
    						c.color = invent.effect;
    						ck.updateInfo(c);
    						db.updateClanColor(c.id, c.color);
	                	}
    				}
	    			else
	    			{
	    				error = 0x80000000;
	    			}
    			}
    			catch (Throwable e)
    			{
    				failedEquip(e, error);
    			}
    			break;
    		}
    		case 1301052000: //EMBLEMA DO CLAN
    		{
    			try
    			{
	                Clan c = ck.BUSCAR_CLAN(player.clan_id);
	                if (c != null && c.owner == player.id)
		    		{
		    			c.logo = player.update_clan_logo;
		    			for (Player j : ck.getPlayers(c))
		    			{
		    				if (j != null && j.gameClient != null)
		    				{
		    					j.gameClient.sendPacket(new CLAN_CREATE_ACK(c, 0, j.gold));
		    					Channel ch = GameServerXML.gI().getChannel(j.gameClient.getChannelId(), j.gameClient.getServerId());
		    					if (ch != null)
		    					{
			    					Room r = ch.getRoom(j.gameClient.getRoomId());
			    					if (r != null)
			    						r.updateInfo();
		    					}
		    				}
		    			}
		    			db.updateClanLogo(c);
		    		}
	    			else
	    			{
	    				error = 0x80000000;
	    			}
    			}
    			catch (Throwable e)
    			{
    				failedEquip(e, error);
    			}
    			break;
    		}
    		case 1301056000: //REINICIAR PONTOS DO CLAN
    		{
    			try
    			{
	                Clan c = ck.BUSCAR_CLAN(player.clan_id);
	                if (c != null && c.owner == player.id)
		    		{
	                	if (c.pontos != 1000)
	                	{
			    			c.pontos = 1000;
    						ck.updateInfo(c);
			    			db.updateClanPontos(c);
	                	}
		    		}
	    			else
	    			{
	    				error = 0x80000000;
	    			}
    			}
    			catch (Throwable e)
    			{
    				failedEquip(e, error);
    			}
    			break;
    		}
    		case 1301051000: //ALTERAR NOME DO CLAN
    		{
    			try
    			{
	                Clan c = ck.BUSCAR_CLAN(player.clan_id);
	                if (c != null && c.owner == player.id)
	    			{
	    				if (!EssencialUtil.VERIFICAR_APELIDO_DO_CLAN(player, player.update_clan_nick) && !EssencialUtil.CHECKAR_NOVO_CLAN_NOME_IGUAL(player.id, player.update_clan_nick))
	    				{
		    				String oldname = c.name;
			    			c.name = player.update_clan_nick;
			    			for (Player j : ck.getPlayers(c))
			    			{
			    				if (j != null && j.gameClient != null)
			    				{
			    					j.gameClient.sendPacket(new CLAN_CREATE_ACK(c, 0, j.gold));
			    					Channel ch = GameServerXML.gI().getChannel(j.gameClient.getChannelId(), j.gameClient.getServerId());
			    					if (ch != null)
			    					{
				    					Room r = ch.getRoom(j.gameClient.getRoomId());
				    					if (r != null)
				    						r.updateInfo();
			    					}
			    				}
			    			}
			    			db.updateClanName(c);
			    			Logger.gI().info("clans", null, "Clan '" + oldname + "' trocou o nome para '" + c.name + "' [" + c.id + "]", getClass());
	    				}
	    				else
	    				{
	    					error = 0x80000000;
	    				}
	    			}
	    			else
	    			{
	    				error = 0x80000000;
	    			}
    			}
    			catch (Throwable e)
    			{
    				failedEquip(e, error);
    			}
    			break;
    		}
    		case 1301053000: //RESETAR V/D CLAN
    		{
    			try
    			{
	                Clan c = ck.BUSCAR_CLAN(player.clan_id);
	                if (c != null && c.owner == player.id)
	    			{
		    			c.vitorias = 0;
		    			c.derrotas = 0;
						ck.updateInfo(c);
		    			db.updateVDclan(c);
	    			}
	    			else
	    			{
	    				error = 0x80000000;
	    			}
    			}
    			catch (Throwable e)
    			{
    				failedEquip(e, error);
    			}
    			break;
    		}
    		case 1301055000: //+50 MEMBROS NO CLAN
    		{
    			try
    			{
	                Clan c = ck.BUSCAR_CLAN(player.clan_id);
	                if (c != null && c.owner == player.id)
	    			{
	    				if ((c.vagas + 50) <= 250)
	    				{
			    			c.vagas += 50;
			    			client.sendPacket(new CLAN_ITEM_MAX_PLAYERS_ACK(c.vagas));
    						ck.updateInfo(c);
			    			db.updateVagasClan(c);
	    				}
	    			}
	    			else
	    			{
	    				error = 0x80000000;
	    			}
    			}
    			catch (Throwable e)
    			{
    				failedEquip(e, error);
    			}
    			break;
    		}
    		case 1301085000: //INSPECIONAR USUARIO
    		{
	    		if (room != null)
	    		{
		    		Player pR = room.getPlayerBySlot(invent.effect);
		    		if (pR != null)
		    			client.sendPacket(new ROOM_INSPECT_PLAYER_ACK(pR));
	    		}
    			break;
    		}
    		default:
    		{
    			error = 0x80000000;
    			break;
    		}
		}
		return error;
    }
    boolean randomizar(RandomBox box, int valor)
    {
    	for (RandomBoxReward r : box.items)
    	{
			if (r.random == valor)
			{
				if (r.equip == 1 && r.item_id > 0)
					if (r.count >= 2592000)
						return true;
				else if (r.equip == 21 && r.count > 0)
					if (r.count >= 50000)
						return true;
			}
		}
    	return false;
    }
}