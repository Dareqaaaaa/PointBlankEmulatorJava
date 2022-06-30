package battle.network.battle.udp;

import io.netty.channel.*;
import io.netty.channel.socket.*;
import io.netty.util.*;

import java.io.*;
import java.nio.*;

import core.info.*;
import core.udp.events.*;
import battle.network.battle.udp.sent.*;

/**
 * 
 * @author Henrique
 *
 */

public class BattleUdpReceive extends SimpleChannelInboundHandler<DatagramPacket>
{
	@Override
	public void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws IOException, Exception
	{
		synchronized (this)
		{
			UDP_Model model = new UDP_Model();
			try
			{
				if (model.receive(msg.content().order(ByteOrder.LITTLE_ENDIAN), this))
				{
					int opcode = model.opcode;
					if (opcode == 65)
					{
						CONNECT_PLAYER_P2P packet = new CONNECT_PLAYER_P2P(ctx, msg, model);
						packet.write(); packet = null;
					}
					else if (opcode == 67)
					{
						DESCONNECT_PLAYER_P2P packet = new DESCONNECT_PLAYER_P2P(model);
						packet.write(); packet = null;
					}
					else
					{
						if (opcode == 3 || opcode == 4 || opcode == 84 || opcode == 97 || opcode == 131 || opcode == 132)
						{
							BATTLE_PARTX2_P2P packet = new BATTLE_PARTX2_P2P(ctx, model);
							packet.write(); packet = null;
						}
					}
				}
				else
				{
					System.out.println(" Error length calc ");
				}
			}
		    catch (Throwable e)
		    {
		    	exceptionCaught(null, e);
		    }
		    finally
		    {
		    	model.close();
		    	model = null;
		        ReferenceCountUtil.release(msg);
		    }
		}
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception 
	{
		ctx.flush();
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception 
	{
		if (cause instanceof IllegalReferenceCountException)
			return;
		Logger.gI().info("errorUDP", cause, "", getClass());
	}
	/*public void asyncEvent(ByteBuf read, Room r, Player p, UDP_Model udp, ByteBuf sender) throws Throwable
	{
		byte opc = read.readByte();

		UDP_P2P subHead = UDP_SubHead.getSubHead(opc);
		subHead.slot = read.readShort();
		subHead.length = read.readShort();
		subHead.flag = read.readInt();

		BattleUdpBuffer ss = new BattleUdpBuffer();

		ss.buffer.writeByte(opc);
		ss.buffer.writeShort(subHead.slot);
		ss.buffer.writeShort(subHead.length);
		ss.buffer.writeInt(subHead.flag);
		
		if ((subHead.flag & 0x04) > 0) //Pos Rotate
		{
			UDP_PosRotation half = new UDP_PosRotation(read.readUnsignedShort(), read.readUnsignedShort(), read.readUnsignedShort(), read.readUnsignedShort(), read.readUnsignedShort(), read.readUnsignedShort());
			p.pos = half;
			ss.buffer.writeShort(half.posX);
			ss.buffer.writeShort(half.posY);
			ss.buffer.writeShort(half.posZ);
			ss.buffer.writeShort(half.camX);
			ss.buffer.writeShort(half.camY);
			ss.buffer.writeShort(half.area);

			Software.setColor(Color.GREEN, Color.BLACK);
			core.model.MapLocation map = MapsXML.gI().get(r.map, half.area);
			if (map != null)
			{
				BattleManager.gI().sendPacket(new REQUEST_GAME_SEND_ACK(r.roomId, r.serverId, r.channelId, p.slot, String.valueOf(map.local)));
				System.out.println(" [" + map.index + "] " + map.local);
				System.out.flush();
			}
			else
			{
				//RoomController.gI().sendPacket(new REQUEST_GAME_SEND_ACK(r.roomId, r.serverId, r.channelId, p.slot, String.valueOf(half.area)));
			}
			Software.setColor(Color.WHITE, Color.BLACK);
            Software.setColor(Color.CYAN, Color.BLACK);
			//logging("[Burning Hall] Slot " + udp.slot + "; " + local, true);
            Software.setColor(Color.WHITE, Color.BLACK);
			subHead.flag ^= 0x04;
		}
		ss.buffer.writeBytes(Unpooled.copiedBuffer(read));
		BitShift.encrypt(ss.buffer, udp.length % 6 + 1);
		sender.writeBytes(Unpooled.copiedBuffer(ss.buffer));
		ss.close();
		
		int k = 0;
		if (k == 0)
			return;
		
		if ((subHead.flag & 0x01) > 0) //ActionState
		{
			int acao = read.readInt();
			UDP_Action action = UDP_Action.valueOf(acao);
			if (action.value > 0)
				BattleManager.gI().sendPacket(new REQUEST_GAME_SEND_ACK(r.roomId, r.serverId, r.channelId, p.slot, String.valueOf(action)));
			ss.buffer.writeInt(acao);
			subHead.flag ^= 0x01;
		}
		if ((subHead.flag & 0x02) > 0) //Animation
		{
			short value = read.readShort();
			ss.buffer.writeShort(value);
			subHead.flag ^= 0x02;
		}
		if ((subHead.flag & 0x08) > 0) //Use Object
		{
			short value = read.readShort();
			ss.buffer.writeShort(value);
			subHead.flag ^= 0x08;
		}
		if ((subHead.flag & 0x20) > 0) //Radio Chat
		{
			UDP_Chat radio = UDP_Chat.valueOf(read.readByte());
			byte area = read.readByte();
            Software.setColor(Color.YELLOW, Color.BLACK);
            logging(" Slot " + udp.slot + " is release a radio chat: radId, localId [" + radio.value + ";" + area + "]", true);
            Software.setColor(Color.WHITE, Color.BLACK);
			ss.buffer.writeByte(radio.value);
			ss.buffer.writeByte(area);
			subHead.flag ^= 0x20;
		}
		if ((subHead.flag & 0x40) > 0) //HP Sync
		{
			UDP_HPSync hp = new UDP_HPSync(read.readShort(), false);
			if (hp.life < 1)
				hp.death = true;
			logging(" HP Sync slot " + p.slot + " >> hp " + hp + " death: " + hp.death, true);
			BattleManager.gI().sendPacket(new REQUEST_GAME_SEND_ACK(r.roomId, r.serverId, r.channelId, p.slot, String.valueOf("[HP] Life " + hp + " death: " + hp.death)));
			ss.buffer.writeShort(hp.life);
			subHead.flag ^= 0x40;
		}
		if ((subHead.flag & 0x100) > 0) //Object Status
		{
			ss.buffer.writeShort(read.readShort()); //unk
	        while (read.isReadable())
	        {
	           int type = read.readByte();
	           if (type == 8) //Jogador
               {
                   int objid = read.readShort();
                   int unk1 = read.readShort();
                   byte byte1 = read.readByte();
                   int unk2 = read.readShort();
                   byte byte2 = read.readByte();
                   int hp = read.readShort();
                   ss.buffer.writeByte(type);
                   ss.buffer.writeShort(objid);
                   ss.buffer.writeShort(unk1);
                   ss.buffer.writeByte(byte1);
                   ss.buffer.writeShort(unk2);
                   ss.buffer.writeByte(byte2);
                   ss.buffer.writeShort(hp);
                   Software.setColor(Color.GREEN, Color.BLACK);
                   logging("hitPlayer: objIdx: " + objid + "; unk1: " + unk1 + "; unk2: " + unk2 + "; hp: " + hp, true);
                   Software.setColor(Color.WHITE, Color.BLACK);
               }
               else if (type == 3)
               {
                   int objId = read.readShort();
                   int unk1 = read.readShort();
                   int partIdx = read.readShort();
                   boolean destroyed = read.readBoolean();
                   Software.setColor(Color.YELLOW, Color.BLACK);
                   logging("hitObject: objIdx: " + objId + "; partIdx: " + partIdx + "; unk: " + unk1 + "; destroyed: " + destroyed, true);
                   Software.setColor(Color.WHITE, Color.BLACK);
                   ss.buffer.writeByte(type);
                   ss.buffer.writeShort(objId);
                   ss.buffer.writeShort(unk1);
                   ss.buffer.writeShort(partIdx);
                   ss.buffer.writeBoolean(destroyed);
               }
               read.discardReadBytes(); 
	        }
			subHead.flag ^= 0x100;
		}
		ss.close();
	}
	public void log()
	{
		if (udp.buffer.isReadable() && udp.opcode > 0)
	    {
			BitShift.onlyDecrypt(udp.buffer, udp.length);
			int remainValue = udp.sub_event;
			String barraInicio = "========================================================" + NetworkUtil.NEWLINE;
			String opcode = "Opcode: " + udp.opcode + NetworkUtil.NEWLINE;
			String slot = "Slot: " + udp.slot + NetworkUtil.NEWLINE;
			String time = "Time: " + udp.time + NetworkUtil.NEWLINE;
			String session = "Session: " + udp.session + NetworkUtil.NEWLINE;
			String length = "Length: " + udp.length + NetworkUtil.NEWLINE;
			String event = "Event: " + udp.event + NetworkUtil.NEWLINE;
			String subOpcode = "SubOpcode: " + udp.sub_opcode + NetworkUtil.NEWLINE;
			String subSlot = "SubSlot: " + udp.subHead.slot + NetworkUtil.NEWLINE;
			String subLength = "SubLength: " + udp.sub_length + NetworkUtil.NEWLINE;
			String subEvent = "SubEvent: " + udp.sub_event + NetworkUtil.NEWLINE;
			String flag = "Flag: " + udp.flag.toString() + NetworkUtil.NEWLINE;
			String roomId = "RoomID: " + udp.roomId + NetworkUtil.NEWLINE;
			String team = "TEAM: " + udp.team + NetworkUtil.NEWLINE;
			String roomSeed = "RoomSeed: " + udp.roomSeed + NetworkUtil.NEWLINE;
			String mask = "RemainMask: " + (udp.sub_event - remainValue) + NetworkUtil.NEWLINE;
			String reads = "Restates: " + udp.read.readableBytes() + NetworkUtil.NEWLINE;
			String barraFim = barraInicio;
			String more = "";
			if (udp.flag == UdpEvent.MissionDataForHost)
				more = "BOMB INSTALL";
			String dain = barraInicio + opcode + slot + team + time + session + length + event + subOpcode + subSlot + subLength + subEvent + flag + roomId + roomSeed + mask + reads + barraFim + more;
			
			String data = NetworkUtil.printData(dain, udp.buffer);
	    
			Logger.gI().info("eventUDP", data, "-BattleUdpReceive");
	    
			if (udp.opcode != 4)
			{
				logging(data);
				System.out.flush();
			}
	    }
	}*/
}