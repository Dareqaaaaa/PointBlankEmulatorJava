package game.network.game.recv;

import core.model.*;

/**
 * 
 * @author Henrique
 *
 */

public class PLAYER_CONFIG_UPDATE_REQ extends game.network.game.GamePacketREQ
{
	Player player;
	boolean update = false;
	@Override
	public void readImpl()
	{
		player = client.getPlayer();
		if (player != null)
		{
			int type = ReadD();
			if ((type & 1) == 1)
			{
				player.config.sangue = ReadH();
				player.config.mira = ReadC();
				player.config.mao = ReadC();
				player.config.config = ReadD();
				player.config.audio_enable = ReadD();
		        ReadH();
		        player.config.audio1 = ReadC();
		        player.config.audio2 = ReadC();
		        player.config.visao = ReadH();
		        player.config.sensibilidade = ReadC();
		        player.config.mouse_invertido = ReadC();
		        ReadH();
		        player.config.msgConvite = ReadC();
		        player.config.chatSusurro = ReadC();
		        player.config.macro = ReadC();
		        ReadH();
		        ReadC();
		        update = true;
			}
			if ((type & 2) == 2)
			{
				player.config.keys = ReadB(220);
				update = true;
			}
			if ((type & 4) == 4)
			{
				player.config.macro1 = ReadS(ReadC());
				player.config.macro2 = ReadS(ReadC());
				player.config.macro3 = ReadS(ReadC());
				player.config.macro4 = ReadS(ReadC());
				player.config.macro5 = ReadS(ReadC());
				update = true;
			}
		}
	}
	@Override
	public void runImpl()
	{
		if (update) db.updateConfig(player, player.config);
	}
}