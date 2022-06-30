package core.config.settings;

import java.awt.*;
import java.io.*;
import java.util.*;

import core.info.Software;

/**
 * 
 * @author Henrique
 *
 */

public abstract class SystemLoader 
{
	protected static final Properties PROPERTIES = new Properties();
	public String file;
	protected SystemLoader(String file)
	{
		this.file = file;
		LOAD();
	}
	public void LOAD()
	{
		try
		{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			PROPERTIES.load(br);
			fr.close();
			br.close();
		}
		catch (Exception e)
		{
			Software.ErrorColor(e, Color.RED);
		}
	}
	public boolean ReadL(String key)
	{
		return Boolean.valueOf(PROPERTIES.getProperty(key));
	}
	public int ReadD(String key)
	{
		String pro = PROPERTIES.getProperty(key);
		if (pro.equals("-1"))
			return -1;
		return Integer.valueOf(pro);
	}
	public long ReadQ(String key)
	{
		String pro = PROPERTIES.getProperty(key);
		if (pro.equals("-1"))
			return -1;
		return Long.valueOf(pro);
	}
	public String ReadT(String section, String var, String def)
	{
		return PROPERTIES.getProperty(section + '.' + var, def);
	}
	public String ReadS(String key)
	{
		return PROPERTIES.getProperty(key);
	}
}