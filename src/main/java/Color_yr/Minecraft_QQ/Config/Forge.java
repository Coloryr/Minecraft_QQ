package Color_yr.Minecraft_QQ.Config;

import Color_yr.Minecraft_QQ.Log.logs;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Config(modid = "minecraft_qq")
public class Forge {
    public static Configuration config;

    public static String Vision;

    public Forge(FMLPreInitializationEvent event)
    {
        Color_yr.Minecraft_QQ.Main.Forge.logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        load();
    }

    public static void load()
    {
        Color_yr.Minecraft_QQ.Main.Forge.logger.info("Started loading config. ");

        Bukkit.Minecraft_ServerName = config.get(Configuration.CATEGORY_GENERAL,
                "ServerName", "[MC服务器]",
                "发送群的服务器名字").getString();
        Bukkit.Minecraft_Check = config.getString(Configuration.CATEGORY_GENERAL,
                "Check", "群：",
                "触发文本");
        Bukkit.Minecraft_Message = config.getString(Configuration.CATEGORY_GENERAL,
                "Message", "群：",
                "发送至QQ群的文本\n" +
                        "#变量：%Servername%服务器名字，%Server%子服名字，%Player%玩家名字，%Message%玩家说的话");
        Bukkit.Minecraft_Say = config.getString(Configuration.CATEGORY_GENERAL,
                "Say", "[%Servername%][群消息]%Message%",
                "发送至玩家消息窗口的文本");
        Bukkit.Minecraft_Mode = config.getInt(Configuration.CATEGORY_GENERAL,
                "Mode", 1,0,2,
                "机器人模式\n" +
                        "#0:不发送消息至QQ群，1:检测是否有触发文本，如果有就发送，2:无论玩家说什么，都发送到QQ群");
        Bukkit.Minecraft_SendMode = config.getBoolean(Configuration.CATEGORY_GENERAL,
                "SendMode", true,
                "是否开启在线人数显示");
        Bukkit.Minecraft_PlayerListMessage = config.getString(Configuration.CATEGORY_GENERAL,
                "PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%",
                "在线人数文本\n" +
                        "#变量：%Servername%服务器名字，%player_number%玩家数量，%player_list%所有玩家列表");
        Bukkit.Minecraft_ServerOnlineMessage = config.getString(Configuration.CATEGORY_GENERAL,
                "ServerOnlineMessage", "%Servername%服务器在线",
                "触发文本");

        Bukkit.System_IP = config.getString(Configuration.CATEGORY_GENERAL,
                "IP", "localhost",
                "机器人链接IP");
        Bukkit.System_PORT = config.getInt(Configuration.CATEGORY_GENERAL,
                "Port", 25555,0,65536,
                "机器人链接端口");
        Bukkit.System_AutoConnet = config.getBoolean(Configuration.CATEGORY_GENERAL,
                "AutoConnet", false,
                "是否启用自动重连");
        Bukkit.System_AutoConnetTime = config.getInt(Configuration.CATEGORY_GENERAL,
                "AutoConnetTime", 10000,0,1000000,
                "自动重连时间间隔");
        Bukkit.System_Debug = config.getBoolean(Configuration.CATEGORY_GENERAL,
                "Debug", false,
                "是否启用Debug模式");
        Bukkit.Head = config.getString(Configuration.CATEGORY_GENERAL,
                "Head", "[Head]",
                "数据包头");
        Bukkit.End = config.getString(Configuration.CATEGORY_GENERAL,
                "End", "[End]",
                "数据包尾");
        Bukkit.System_Sleep = config.getInt(Configuration.CATEGORY_GENERAL,
                "Sleep", 50,0,1000000,
                "线程休眠，默认即可，如果你聊天的数量较多，请改小，单位毫秒");

        Bukkit.User_SendSucceed = config.getBoolean(Configuration.CATEGORY_GENERAL,
                "SendSucceed", false,
                "完成发送后是否提醒");
        Bukkit.User_SendSucceedMessage = config.getString(Configuration.CATEGORY_GENERAL,
                "SendSucceedMessage", "已发送消息至群内",
                "完成发送后显示的文本");
        Bukkit.User_NotSendCommder = config.getBoolean(Configuration.CATEGORY_GENERAL,
                "NotSendCommder", false,
                "是否屏蔽玩家输入指令");

        logs.Socket_log = config.getBoolean(Configuration.CATEGORY_GENERAL,
                "Socket", true,
                "是否记录链接情况");
        logs.Group_log = config.getBoolean(Configuration.CATEGORY_GENERAL,
                "Group", true,
                "是否记录群发来的消息");
        logs.Send_log = config.getBoolean(Configuration.CATEGORY_GENERAL,
                "Send", true,
                "是否记录发送至群的消息");
        logs.Error_log = config.getBoolean(Configuration.CATEGORY_GENERAL,
                "Error", true,
                "是否记录错误内容");

        Vision = config.getString("Version",
                "Version", "2.1.0-beta1",
                "配置文件版本号");

        config.save();
        Color_yr.Minecraft_QQ.Main.Forge.logger.info("Finished loading config.");
    }
}
