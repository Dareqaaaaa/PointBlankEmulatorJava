package game.network.auth.sent;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class BASE_GET_CONFIG_ACK extends game.network.auth.AuthPacketACK
{
	Player p;
	int error;
	public BASE_GET_CONFIG_ACK(Player p, int error)
	{
		super();
		this.p = p;
		this.error = error;
	}
	@Override
	public void writeImpl()
	{
		WriteD(error);
		if (error == 0)
		{
			WriteC(p.name.isEmpty() ? 1 : 0);
	        WriteH(p.config.sangue);
	        WriteC(p.config.mira);
	        WriteC(p.config.mao);
	        WriteD(p.config.config); 
	        WriteD(p.config.audio_enable);
	        WriteH(0);
	        WriteC(p.config.audio1);
	        WriteC(p.config.audio2);
	        WriteH(p.config.visao);       
	        WriteC(p.config.sensibilidade);
	        WriteC(p.config.mouse_invertido);
	        WriteH(0);
	        WriteC(p.config.msgConvite);
	        WriteC(p.config.chatSusurro);        
	        WriteC(p.config.macro);
	        WriteH(0);
	        WriteC(0);
	        WriteB(p.config.keys);
	        WriteC(p.config.macro1.length() + 1);
	        WriteS(p.config.macro1, p.config.macro1.length() + 1);
	        WriteC(p.config.macro2.length() + 1);
	        WriteS(p.config.macro2, p.config.macro2.length() + 1);
	        WriteC(p.config.macro3.length() + 1);
	        WriteS(p.config.macro3, p.config.macro3.length() + 1);
	        WriteC(p.config.macro4.length() + 1);
	        WriteS(p.config.macro4, p.config.macro4.length() + 1);
	        WriteC(p.config.macro5.length() + 1);
	        WriteS(p.config.macro5, p.config.macro5.length() + 1);
		}
	}
}