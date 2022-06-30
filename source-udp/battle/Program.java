package battle;

import java.awt.*;

import core.config.settings.*;
import core.config.xml.*;
import core.info.*;
import core.network.*;
import core.utils.PefReader;
import battle.network.battle.*;

/**
 * 
 * @author Henrique
 *
 */

public class Program extends core.postgres.sql.InterfaceSQL
{
	public static void main(String[] args)
	{
		try
		{
			Software.printIntro("UDP", Color.CYAN, args);
			ThreadPoolManager.gI();
			//ConfigurationLoader.gI();
			
			//PefReader pef = new PefReader("Basic", "data/Basic.pef", 1);
			
			/*BattleManager.gI();
			MapsXML.gI().LOAD();
			Socket.start();*/
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			error(Program.class, e);
			System.out.println(" Erro fatal. Check log files!!! ");
			System.out.flush();
		}
	}
}