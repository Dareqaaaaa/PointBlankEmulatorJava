package core.info;

import java.awt.*;
import java.io.*;
import java.lang.management.*;
import java.security.*;

import core.enums.ClientLocale;
import core.model.*;
import core.utils.*;
import enigma.console.Console;
import enigma.console.TextAttributes;
import enigma.core.*;

/**
 * 
 * @author eoq_henrqu
 *
 */

public class Software
{
	/*public static User pc = new User(new String[] { "Henrique", "Oct 17 2016", "16:17:12", "DIST", "1012", "12", "125.180922", "25.73.139.63", "1", "15", "37", "21", "37" },
			new String[] { "40003", "29890", "39190", "29891" }, ClientLocale.BRAZIL, "Point Blank Server");*/
	public static Console console;
	static
	{
		console = Enigma.getConsole();
		System.setErr(console.getOutputStream());
		System.setOut(console.getOutputStream());
		System.setIn(console.getInputStream());
	}
	public static void printIntro(String type, Color back, String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InterruptedException
	{
		console.setTitle("Emulador " + type + " [Build: " + pc.info[6] + "] [0 KB]");
		setColor(Color.WHITE, Color.BLACK);
		try
		{
			System.out.println(" Point Blank: Copyright 2018 Zepetto Co. All rights reserved.");
			System.out.println(" Ongame Enterteiment S.A. All rights reserved in Brazil.");
			System.out.println(" GNU General Public License as published by the Free Software Foundation.");
			System.out.println(" This program is protected using data from your computer.");
			System.out.println(" User: " + pc.info[0] + "; Client Ver: " + pc.client_rev + "; UDP Protocol Ver: " + pc.udp_rev + ";");
			System.out.println(" Copyright: PISTOLA Developer.");
			System.out.println("-------------------------------------------------------------------------------");
			setColor(back, Color.BLACK);
            System.out.println("               ________  _____  __      ______ _______          ");
            System.out.println("              / ____/  |/  / / / /     / /  / / /  / /          ");
            System.out.println("             / __/ / /|_/ / / / /     / /__/_/ /__/ /           ");
            System.out.println("            / /___/ /  / / /_/ / _   / /    / /  / /            ");
            System.out.println("           /_____/_/  /_/\'____/ /_/ /_/    /_/__/_/             ");
            System.out.println();
            setColor(Color.WHITE, Color.BLACK);
			System.out.println("-------------------------------------------------------------------------------");
			System.out.flush();
			EncryptSyncer enc = EncryptSyncer.gI();
			enc.encryptionKey = args[0];
			enc.aesEncryptionAlgorithem = args[1];
		}
		catch (Exception e)
		{
			Thread.sleep(1000);
			System.exit(0);
		}
	}
	public static void updateMemory(String type)
	{
		try
		{
			console.setTitle("Emulador " + type + " [Build: " + pc.info[6] + "] [" + String.valueOf(((((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize() / 1024) / 1024)) + " KB]");
		}
		catch (Exception e)
		{
		}
	}
	public static void LogColor(String text, Color color)
	{
		try
		{
			setColor(color, Color.BLACK);
			System.out.println(text);
			System.out.flush();
			setColor(Color.WHITE, Color.BLACK);
		}
		catch (Exception ex)
		{
		}
	}
	public static void ErrorColor(Throwable ex, Color color)
	{
		try
		{
			setColor(color, Color.BLACK);
			ex.printStackTrace();
			System.err.flush();
			setColor(Color.WHITE, Color.BLACK);
		}
		catch (Exception e)
		{
		}
	}
	public static void setColor(Color fore, Color back)
	{
		try
		{
			console.setTextAttributes(new TextAttributes(fore, back));
		}
		catch (Exception e)
		{
		}
	}
	public static void deleteTree(File inFile)
	{
		if (inFile.isFile())
			inFile.delete();
		else
		{
			File files[] = inFile.listFiles();
		    for (int i = 0; i < files.length; i++)
		    	deleteTree(files[i]);
		    inFile.delete();
		}
	}
	public static double getMemoryUsagePercent()
	{
		MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		return 100.0F * (float) heapMemoryUsage.getUsed() / (float) heapMemoryUsage.getMax();
	}
	public static double getMemoryFreePercent()
	{
		return 100.0D - getMemoryUsagePercent();
	}
	public static String getMemoryMaxMb()
	{
		return getMemoryMax() / 1048576L + "MB";
	}
	public static String getMemoryUsedMb()
	{
		return getMemoryUsed() / 1048576L + "MB";
	}
	public static long getMemoryMax()
	{
		return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax();
	}
	public static long getMemoryUsed()
	{
		return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
	}
}