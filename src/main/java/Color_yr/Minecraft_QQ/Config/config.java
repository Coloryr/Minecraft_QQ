package Color_yr.Minecraft_QQ.Config;

import Color_yr.Minecraft_QQ.Log.ILog;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Message.IMessage;
import Color_yr.Minecraft_QQ.Socket.socket_hand;

import java.io.File;

public class config {
    public final static String Version = "2.1.3";
    public static File FileName;
    public static File player;
    public static ILog ilog;
    public static logs F_Log;
    public static socket_hand hand = new socket_hand();
    public static IMessage iMessage;
}
