package core.info;

import java.io.*;

import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class Logger
{
	final String date = DateTimeUtil.gI().toDate("yyyy-MM-dd--HH-mm-ss");
	public void info(String pasta, Throwable cause, String texto, Class<?> classe)
	{
		try
		{
			File file = new File("log/" + pasta + "//");
			if (!file.exists())
				file.mkdirs();
			if (cause != null)
			{
				cause.printStackTrace();
				texto = cause.toString() + " | " + cause.getStackTrace().toString() + " | " + cause.getLocalizedMessage();
			}
			FileWriter fw = new FileWriter("log/" + pasta + "//" + (date + "-" + classe.getSimpleName()) + ".txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(texto);
			bw.newLine();
			bw.flush();
			bw.close();
			fw.flush();
			fw.close();
		}
		catch (Throwable e)
		{
		}
	}
	static final Logger INSTANCE = new Logger();
	public static Logger gI()
	{
		return INSTANCE;
	}
}