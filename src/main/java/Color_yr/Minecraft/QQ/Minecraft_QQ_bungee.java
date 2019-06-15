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

import static Color_yr.Minecraft.QQ.Minecraft_QQ.Version;
import static Color_yr.Minecraft.QQ.Minecraft_QQ.config_data;

public class Minecraft_QQ_bungee extends Plugin {

    public static config_bungee config_data_bungee;
    public static void loadconfig() {
        ProxyServer.getInstance().getLogger().info("§d[Minecraft_QQ]§e当前插件版本为：" + Version
                + "，你的配置文件版本为：" + config_data.config.getString("Version"));

        config_data.Join_Message = config_data.config.getString("Join.Message", "%Player%加入了服务器");
        config_data.Join_sendQQ = config_data.config.getBoolean("Join.sendQQ", true);

        config_data.Quit_Message = config_data.config.getString("Quit.Message", "%Player%退出了服务器");
        config_data.Quit_sendQQ = config_data.config.getBoolean("Quit.sendQQ", true);

        config_data_bungee.ChangeServer_Message = config_data.config.getString("ChangeServer.Message", "%Player%加入了子服%Server%");
        config_data_bungee.ChangeServer_sendQQ = config_data.config.getBoolean("ChangeServer.sendQQ", true);

        config_data.Minecraft_ServerName = config_data.config.getString("Minecraft.ServerName", "[MC服务器]");// 服务器名字
        config_data.Minecraft_Check = config_data.config.getString("Minecraft.Check", "群：");// 触发文本
        config_data.Minecraft_Message = config_data.config.getString("Minecraft.Message", "%Servername%-%Player%:%Msg%");// 发送文本
        config_data.Minecraft_Say = config_data.config.getString("Minecraft.Say", "&6[%Servername%]&b[群消息]&3");// 后台文本
        config_data.Minecraft_Mode = config_data.config.getInt("Minecraft.Mode", 1);
        config_data.Minecraft_SendMode = config_data.config.getBoolean("Minecraft.SendMode", true);
        config_data_bungee.Minecraft_SendOneByOne = config_data.config.getBoolean("Minecraft.SendOneByOne", true);
        config_data_bungee.Minecraft_SendOneByOneMessage = config_data.config.getString("Minecraft.SendOneByOneMessage", "\n[%Server%-%player_number%]-%player_list%");
        config_data_bungee.Minecraft_HideEmptyServer = config_data.config.getBoolean("Minecraft.HideEmptyServer", true);
        config_data.Minecraft_PlayerListMessage = config_data.config.getString("Minecraft.PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%");
        config_data.Minecraft_ServerOnlineMessage = config_data.config.getString("Minecraft.ServerOnlineMessage", "%Servername%服务器在线");

        config_data_bungee.SendAllServer_Enable = config_data.config.getBoolean("SendAllServer.Enable", true);
        config_data_bungee.SendAllServer_Message = config_data.config.getString("SendAllServer.Message", "[%Servername%]玩家：[%Player%]发送群消息：[%Message%]");
        config_data_bungee.SendAllServer_OnlySideServer = config_data.config.getBoolean("SendAllServer.OnlySideServer", false);

        config_data.System_IP = config_data.config.getString("System.IP", "localhost"); // 服务器地址
        config_data.System_PORT = config_data.config.getInt("System.Port", 25555);// 服务器端口号
        config_data.System_AutoConnet = config_data.config.getBoolean("System.AutoConnet", false);
        config_data.System_AutoConnetTime = config_data.config.getInt("System.AutoConnetTime", 10000);
        config_data.System_Debug = config_data.config.getBoolean("System.Debug", false);
        message_bungee.Head = config_data.config.getString("System.Head", "[Head]");
        message_bungee.End = config_data.config.getString("System.End", "[End]");

        config_data.User_SendSucceed = config_data.config.getBoolean("User.SendSucceed", true);
        config_data.User_SendSucceedMessage = config_data.config.getString("User.SendSucceedMessage", "已发送消息至群内");
        config_data.User_NotSendCommder = config_data.config.getBoolean("User.NotSendCommder", true);

        logs.Socket_log = config_data.config.getBoolean("Logs.Socket", true);
        logs.Group_log = config_data.config.getBoolean("Logs.Group", true);
        logs.Send_log = config_data.config.getBoolean("Logs.Send", true);
        logs.Error_log = config_data.config.getBoolean("Logs.Error", false);
    }

    public static void reloadConfig() {
        try {
            config_data_bungee.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Minecraft_QQ.FileName);
            loadconfig();
        } catch (Exception arg0) {
            config_data.log.warning("§d[Minecraft_QQ]§c配置文件读取失败:" + arg0);
        }
    }

    public static Configuration getConfig() {
        return config_data_bungee.config;
    }

    public void setConfig() {
        Minecraft_QQ.FileName = new File(getDataFolder(), "config.yml");
        logs.file = new File(getDataFolder(), "logs.log");
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        if (!Minecraft_QQ.FileName.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, Minecraft_QQ.FileName.toPath());
            } catch (IOException e) {
                config_data.log.warning("§d[Minecraft_QQ]§c配置文件创建失败：" + e);
            }
        }
        try {
            if (!logs.file.exists()) {
                logs.file.createNewFile();
            }
        } catch (IOException e) {
            config_data.log.warning("§d[Minecraft_QQ]§c日志文件错误：" + e);
        }
    }

    @Override
    public void onEnable() {
        config_data_bungee = new config_bungee();
        config_data.log = ProxyServer.getInstance().getLogger();
        config_data.log.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
        setConfig();
        reloadConfig();
        ProxyServer.getInstance().getPluginManager().registerListener(this, new Event_bungee());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new command_bungee());
        config_data.log.info("§d[Minecraft_QQ]§e已启动-" + Version);
        config_data.log.info("§d[Minecraft_QQ]§eDebug模式" + config_data.System_Debug);
        socket socket = new socket();
        socket.socket_start();
    }

    @Override
    public void onDisable() {
        if (socket.socket_runFlag == true) {
            socket.socket_send("[群消息]" + config_data.Minecraft_ServerName + "已关闭");
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
        config_data.log.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
