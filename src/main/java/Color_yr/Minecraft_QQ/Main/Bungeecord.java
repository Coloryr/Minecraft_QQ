package Color_yr.Minecraft_QQ.Main;

import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Config.Bukkit;
import Color_yr.Minecraft_QQ.Socket.socket;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Message.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Bungeecord extends Plugin {

    public static Color_yr.Minecraft_QQ.Config.BungeeCord config_data_bungee;

    private void loadconfig() {
        config.log_b.info("§d[Minecraft_QQ]§e当前插件版本为：" + config.Version
                + "，你的配置文件版本为：" + config_data_bungee.config.getString("Version"));

        Bukkit.Join_Message = config_data_bungee.config.getString("Join.Message", "%Player%加入了服务器");
        Bukkit.Join_sendQQ = config_data_bungee.config.getBoolean("Join.sendQQ", true);

        Bukkit.Quit_Message = config_data_bungee.config.getString("Quit.Message", "%Player%退出了服务器");
        Bukkit.Quit_sendQQ = config_data_bungee.config.getBoolean("Quit.sendQQ", true);

        config_data_bungee.ChangeServer_Message = config_data_bungee.config.getString("ChangeServer.Message", "%Player%加入了子服%Server%");
        config_data_bungee.ChangeServer_sendQQ = config_data_bungee.config.getBoolean("ChangeServer.sendQQ", true);

        Bukkit.Minecraft_ServerName = config_data_bungee.config.getString("Minecraft.ServerName", "[MC服务器]");// 服务器名字
        Bukkit.Minecraft_Check = config_data_bungee.config.getString("Minecraft.Check", "群：");// 触发文本
        Bukkit.Minecraft_Message = config_data_bungee.config.getString("Minecraft.Message", "%Servername%-%Player%:%Msg%");// 发送文本
        Bukkit.Minecraft_Say = config_data_bungee.config.getString("Minecraft.Say", "&6[%Servername%]&b[群消息]&3");// 后台文本
        Bukkit.Minecraft_Mode = config_data_bungee.config.getInt("Minecraft.Mode", 1);
        Bukkit.Minecraft_SendMode = config_data_bungee.config.getBoolean("Minecraft.SendMode", true);
        config_data_bungee.Minecraft_SendOneByOne = config_data_bungee.config.getBoolean("Minecraft.SendOneByOne", true);
        config_data_bungee.Minecraft_SendOneByOneMessage = config_data_bungee.config.getString("Minecraft.SendOneByOneMessage", "\n[%Server%-%player_number%]-%player_list%");
        config_data_bungee.Minecraft_HideEmptyServer = config_data_bungee.config.getBoolean("Minecraft.HideEmptyServer", true);
        Bukkit.Minecraft_PlayerListMessage = config_data_bungee.config.getString("Minecraft.PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%");
        config_data_bungee.Minecraft_HideList = config_data_bungee.config.getBoolean("Minecraft.HideList", false);
        Bukkit.Minecraft_ServerOnlineMessage = config_data_bungee.config.getString("Minecraft.ServerOnlineMessage", "%Servername%服务器在线");

        config_data_bungee.SendAllServer_Enable = config_data_bungee.config.getBoolean("SendAllServer.Enable", true);
        config_data_bungee.SendAllServer_Message = config_data_bungee.config.getString("SendAllServer.Message", "[%Servername%]玩家：[%Player%]发送群消息：[%Message%]");
        config_data_bungee.SendAllServer_OnlySideServer = config_data_bungee.config.getBoolean("SendAllServer.OnlySideServer", false);

        Bukkit.System_IP = config_data_bungee.config.getString("System.IP", "localhost"); // 服务器地址
        Bukkit.System_PORT = config_data_bungee.config.getInt("System.Port", 25555);// 服务器端口号
        Bukkit.System_AutoConnet = config_data_bungee.config.getBoolean("System.AutoConnet", false);
        Bukkit.System_AutoConnetTime = config_data_bungee.config.getInt("System.AutoConnetTime", 10000);
        Bukkit.System_Debug = config_data_bungee.config.getBoolean("System.Debug", false);
        Bukkit.Head = config_data_bungee.config.getString("System.Head", "[Head]");
        Bukkit.End = config_data_bungee.config.getString("System.End", "[End]");
        Bukkit.System_Sleep = config_data_bungee.config.getInt("System.Sleep", 50);

        Bukkit.User_SendSucceed = config_data_bungee.config.getBoolean("User.SendSucceed", true);
        Bukkit.User_SendSucceedMessage = config_data_bungee.config.getString("User.SendSucceedMessage", "已发送消息至群内");
        Bukkit.User_NotSendCommder = config_data_bungee.config.getBoolean("User.NotSendCommder", true);

        logs.Socket_log = config_data_bungee.config.getBoolean("Logs.Socket", true);
        logs.Group_log = config_data_bungee.config.getBoolean("Logs.Group", true);
        logs.Send_log = config_data_bungee.config.getBoolean("Logs.Send", true);
        logs.Error_log = config_data_bungee.config.getBoolean("Logs.Error", true);
    }

    public void setConfig() {
        try {
            config.FileName = new File(getDataFolder(), "config.yml");
            logs.file = new File(getDataFolder(), "logs.log");
            if (!getDataFolder().exists())
                getDataFolder().mkdir();
            if (!config.FileName.exists()) {
                InputStream in = getResourceAsStream("config_bungee.yml");
                Files.copy(in, config.FileName.toPath());
            }
            if (!logs.file.exists()) {
                logs.file.createNewFile();
            }
            config_data_bungee.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(config.FileName);
            loadconfig();
        } catch (Exception e) {
            config.log_b.warning("§d[Minecraft_QQ]§c配置文件读取失败:" + e.getMessage());
        }
    }

    @Override
    public void onEnable() {
        config_data_bungee = new Color_yr.Minecraft_QQ.Config.BungeeCord();
        config.is_bungee = true;
        config.log_b = ProxyServer.getInstance().getLogger();
        config.log_b.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
        setConfig();
        config.read_thread = new BungeeCord();
        config.read_thread.start();
        ProxyServer.getInstance().getPluginManager().registerListener(this, new Color_yr.Minecraft_QQ.Event.BungeeCord());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Color_yr.Minecraft_QQ.Command.BungeeCord());
        config.log_b.info("§d[Minecraft_QQ]§e已启动-" + config.Version);
        config.log_b.info("§d[Minecraft_QQ]§eDebug模式" + Bukkit.System_Debug);
        socket socket = new socket();
        socket.socket_start();
    }

    @Override
    public void onDisable() {
        if (socket.hand.socket_runFlag == true) {
            try {
                socket.server_close();
                if(config.read_thread.isAlive()) {
                    config.read_thread.stop();
                }
            } catch(Exception e) {
                e.getMessage();
                if (logs.Error_log == true) {
                    logs logs = new logs();
                    logs.log_write("[ERROR]" + e.getMessage());
                }
            }
        }
        config.log_b.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
