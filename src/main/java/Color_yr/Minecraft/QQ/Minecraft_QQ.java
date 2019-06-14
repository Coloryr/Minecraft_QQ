package Color_yr.Minecraft.QQ;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

public class Minecraft_QQ extends Plugin {
    public static String Join_Message;
    public static Boolean Join_sendQQ;

    public static String Quit_Message;
    public static Boolean Quit_sendQQ;

    public static String ChangeServer_Message;
    public static Boolean ChangeServer_sendQQ;

    public static String Minecraft_ServerName;
    public static String Minecraft_Check;
    public static String Minecraft_Message;
    public static String Minecraft_Say;
    public static int Minecraft_Mode;
    public static Boolean Minecraft_SendMode;
    public static Boolean Minecraft_SendOneByOne;
    public static String Minecraft_SendOneByOneMessage;
    public static Boolean Minecraft_HideEmptyServer;
    public static String Minecraft_PlayerListMessage;
    public static String Minecraft_ServerOnlineMessage;

    public static Boolean SendAllServer_Enable;
    public static String SendAllServer_Message;
    public static Boolean SendAllServer_OnlySideServer;

    public static String System_IP;
    public static int System_PORT;
    public static Boolean System_AutoConnet;
    public static int System_AutoConnetTime;
    public static Boolean System_Debug;

    public static Boolean User_SendSucceed;
    public static String User_SendSucceedMessage;
    public static Boolean User_NotSendCommder;

    public static String Version = "2.0.0";

    public static Configuration config;
    private static File FileName;

    public static Logger log = ProxyServer.getInstance().getLogger();

    public static void loadconfig() {
        ProxyServer.getInstance().getLogger().info("§d[Minecraft_QQ]§e当前插件版本为：" + Version
                + "，你的配置文件版本为：" + config.getString("Version"));

        Join_Message = config.getString("Join.Message", "%Player%加入了服务器");
        Join_sendQQ = config.getBoolean("Join.sendQQ", true);

        Quit_Message = config.getString("Quit.Message", "%Player%退出了服务器");
        Quit_sendQQ = config.getBoolean("Quit.sendQQ", true);

        ChangeServer_Message = config.getString("ChangeServer.Message", "%Player%加入了子服%Server%");
        ChangeServer_sendQQ = config.getBoolean("ChangeServer.sendQQ", true);

        Minecraft_ServerName = config.getString("Minecraft.ServerName", "[MC服务器]");// 服务器名字
        Minecraft_Check = config.getString("Minecraft.Check", "群：");// 触发文本
        Minecraft_Message = config.getString("Minecraft.Message", "%Servername%-%Player%:%Msg%");// 发送文本
        Minecraft_Say = config.getString("Minecraft.Say", "&6[%Servername%]&b[群消息]&3");// 后台文本
        Minecraft_Mode = config.getInt("Minecraft.Mode", 1);
        Minecraft_SendMode = config.getBoolean("Minecraft.SendMode", true);
        Minecraft_SendOneByOne = config.getBoolean("Minecraft.SendOneByOne", true);
        Minecraft_SendOneByOneMessage = config.getString("Minecraft.SendOneByOneMessage", "\n[%Server%-%player_number%]-%player_list%");
        Minecraft_HideEmptyServer = config.getBoolean("Minecraft.HideEmptyServer", true);
        Minecraft_PlayerListMessage = config.getString("Minecraft.PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%");
        Minecraft_ServerOnlineMessage = config.getString("Minecraft.ServerOnlineMessage", "%Servername%服务器在线");

        SendAllServer_Enable = config.getBoolean("SendAllServer.Enable", true);
        SendAllServer_Message = config.getString("SendAllServer.Message", "[%Servername%]玩家：[%Player%]发送群消息：[%Message%]");
        SendAllServer_OnlySideServer = config.getBoolean("SendAllServer.OnlySideServer", false);

        System_IP = config.getString("System.IP", "localhost"); // 服务器地址
        System_PORT = config.getInt("System.Port", 25555);// 服务器端口号
        System_AutoConnet = config.getBoolean("System.AutoConnet", false);
        System_AutoConnetTime = config.getInt("System.AutoConnetTime", 10000);
        System_Debug = config.getBoolean("System.Debug", false);
        message.Head = config.getString("System.Head", "[Head]");
        message.End = config.getString("System.End", "[End]");

        User_SendSucceed = config.getBoolean("User.SendSucceed", true);
        User_SendSucceedMessage = config.getString("User.SendSucceedMessage", "已发送消息至群内");
        User_NotSendCommder = config.getBoolean("User.NotSendCommder", true);

        logs.Socket_log = config.getBoolean("Logs.Socket", true);
        logs.Group_log = config.getBoolean("Logs.Group", true);
        logs.Send_log = config.getBoolean("Logs.Send", true);
        logs.Error_log = config.getBoolean("Logs.Error", false);
    }

    public static void reloadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(FileName);
            loadconfig();
        } catch (Exception arg0) {
            log.warning("§d[Minecraft_QQ]§c配置文件读取失败:" + arg0);
        }
    }

    public static Configuration getConfig() {
        return config;
    }

    public void setConfig() {
        FileName = new File(getDataFolder(), "config.yml");
        logs.file = new File(getDataFolder(), "logs.log");
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        if (!FileName.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, FileName.toPath());
            } catch (IOException e) {
                log.warning("§d[Minecraft_QQ]§c配置文件创建失败：" + e);
            }
        }
        try {
            if (!logs.file.exists()) {
                logs.file.createNewFile();
            }
        } catch (IOException e) {
            log.warning("§d[Minecraft_QQ]§c日志文件错误：" + e);
        }
    }

    @Override
    public void onEnable() {
        log.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
        setConfig();
        reloadConfig();
        ProxyServer.getInstance().getPluginManager().registerListener(this, new Event());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new command());
        log.info("§d[Minecraft_QQ]§e已启动-" + Version);
        log.info("§d[Minecraft_QQ]§eDebug模式" + System_Debug);
        socket socket = new socket();
        socket.socket_start();
    }

    @Override
    public void onDisable() {
        if (socket.socket_runFlag == true) {
            socket.socket_send("[群消息]" + Minecraft_ServerName + "已关闭");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
                if (logs.Error_log == true) {
                    logs logs = new logs();
                    logs.log_write("[ERROR]" + e.getMessage());
                }
            }
        }
        socket.server_close();
        log.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
