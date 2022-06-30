package core.model;

import core.utils.*;

/**
 * 
 * @author Henrique
 *
 */

public class PlayerConfig
{
	public int mira = 1;
    public int audio1 = 100;
    public int audio2 = 60;
    public int sensibilidade = 50;
    public int visao = 70;
    public int sangue = 1;
    public int mao = 0;
    public int audio_enable = 7;
    public int config = 55;
    public int mouse_invertido = 0;
    public int msgConvite = 0;
    public int chatSusurro = 0;
    public int macro = 0;
    public String macro1 = "", macro2 = "", macro3 = "", macro4 = "", macro5 = "";
    public byte[] keys = new byte[220];
    public PlayerConfig()
    {
    	keys = NetworkUtil.hexStringToByteArray(""
    			+ " 00 39 F8 10 00 00 0a 00 00 00 00 0D 00 00 00 00"
    			+ " 20 00 00 00 00 1C 00 00 00 00 2C 00 00 00 00 28"
    			+ " 00 00 00 00 26 00 00 00 00 0f 00 00 00 01 01 00"
    			+ " 00 00 01 02 00 00 00 00 1B 00 00 00 00 1D 00 00"
    			+ " 00 00 01 00 00 00 00 02 00 00 00 00 03 00 00 00"
    			+ " 00 04 00 00 00 00 05 00 00 00 00 06 00 00 00 00"
    			+ " 1A 00 00 00 01 00 00 00 10 01 00 00 00 20 00 10"
    			+ " 00 00 00 00 37 00 00 00 00 16 00 00 00 00 5C 00"
    			+ " 00 00 00 5B 00 00 00 00 25 00 00 00 00 40 00 00"
    			+ " 00 00 41 00 00 00 00 15 00 00 00 00 1F 00 00 00"
    			+ " 00 23 00 00 00 00 21 00 00 00 00 0C 00 00 00 00"
    			+ " 0E 00 00 00 00 31 00 00 00 00 32 00 00 00 00 46"
    			+ " 00 00 00 00 42 00 00 00 00 0b 00 00 00 00 3a 00"
    			+ " 00 00 00 FF FF FF FF 00 45 00 00 00");
    }
}