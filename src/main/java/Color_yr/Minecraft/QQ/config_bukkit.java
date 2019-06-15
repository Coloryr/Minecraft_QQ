package Color_yr.Minecraft.QQ;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

public class config_bukkit {

    public static String Join_Message;
    public static Boolean Join_sendQQ;

    public static String Quit_Message;
    public static Boolean Quit_sendQQ;

    public static String Minecraft_ServerName;
    public static String Minecraft_Check;
    public static String Minecraft_Message;
    public static String Minecraft_Say;
    public static int Minecraft_Mode;
    public static Boolean Minecraft_SendMode;
    public static String Minecraft_PlayerListMessage;
    public static String Minecraft_ServerOnlineMessage;

    public static String System_IP;
    public static int System_PORT;
    public static Boolean System_AutoConnet;
    public static int System_AutoConnetTime;
    public static Boolean System_Debug;

    public static Boolean User_SendSucceed;
    public static String User_SendSucceedMessage;
    public static Boolean User_NotSendCommder;

    public static FileConfiguration config;

    static Logger log;
}
