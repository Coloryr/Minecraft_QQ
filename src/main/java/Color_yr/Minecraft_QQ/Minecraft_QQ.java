package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.Config.ConfigOBJ;
import Color_yr.Minecraft_QQ.Side.IMinecraft_QQ;
import Color_yr.Minecraft_QQ.Socket.SocketHand;

import java.io.File;

public class Minecraft_QQ {
    public final static String Version = "2.3.0";

    public static File player;
    public static SocketHand hand = new SocketHand();
    public static IMinecraft_QQ MinecraftQQ;

    public static File FileName;
    public static ConfigOBJ Config;
}
