package Color_yr.Minecraft_QQ;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static Color_yr.Minecraft_QQ.config.*;

public class Minecraft_QQ_bukkit extends JavaPlugin {

    public static FileConfiguration config;

    public static void loadconfig() {
        config_bukkit.log.info("§d[Minecraft_QQ]§e当前插件版本为：" + Version
                + "，你的配置文件版本为：" + config.getString("Version"));

        config_bukkit.Join_Message = config.getString("Join.Message", "%Player%加入了服务器");
        config_bukkit.Join_sendQQ = config.getBoolean("Join.sendQQ", true);

        config_bukkit.Quit_Message = config.getString("Quit.Message", "%Player%退出了服务器");
        config_bukkit.Quit_sendQQ = config.getBoolean("Quit.sendQQ", true);

        config_bukkit.Minecraft_ServerName = config.getString("Minecraft.ServerName", "[MC服务器]");// 服务器名字
        config_bukkit.Minecraft_Check = config.getString("Minecraft.Check", "群：");// 触发文本
        config_bukkit.Minecraft_Message = config.getString("Minecraft.Message", "%Servername%-%Player%:%Msg%");// 发送文本
        config_bukkit.Minecraft_Say = config.getString("Minecraft.Say", "&6[%Servername%]&b[群消息]&3");// 后台文本
        config_bukkit.Minecraft_Mode = config.getInt("Minecraft.Mode", 1);
        config_bukkit.Minecraft_SendMode = config.getBoolean("Minecraft.SendMode", true);
        config_bukkit.Minecraft_PlayerListMessage = config.getString("Minecraft.PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%");
        config_bukkit.Minecraft_ServerOnlineMessage = config.getString("Minecraft.ServerOnlineMessage", "%Servername%服务器在线");

        config_bukkit.System_IP = config.getString("System.IP", "localhost"); // 服务器地址
        config_bukkit.System_PORT = config.getInt("System.Port", 25555);// 服务器端口号
        config_bukkit.System_AutoConnet = config.getBoolean("System.AutoConnet", false);
        config_bukkit.System_AutoConnetTime = config.getInt("System.AutoConnetTime", 10000);
        config_bukkit.System_Debug = config.getBoolean("System.Debug", false);
        config_bukkit.Head = config.getString("System.Head", "[Head]");
        config_bukkit.End = config.getString("System.End", "[End]");

        config_bukkit.User_SendSucceed = config.getBoolean("User.SendSucceed", true);
        config_bukkit.User_SendSucceedMessage = config.getString("User.SendSucceedMessage", "已发送消息至群内");
        config_bukkit.User_NotSendCommder = config.getBoolean("User.NotSendCommder", true);

        logs.Socket_log = config.getBoolean("Logs.Socket", true);
        logs.Group_log = config.getBoolean("Logs.Group", true);
        logs.Send_log = config.getBoolean("Logs.Send", true);
        logs.Error_log = config.getBoolean("Logs.Error", false);
    }

    public static void Config_reload() {
        try {
            config = YamlConfiguration.loadConfiguration(FileName);
            loadconfig();
        } catch (Exception arg0) {
            config_bukkit.log.warning("§d[Minecraft_QQ]§c配置文件读取失败:" + arg0);
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig() {
        FileName = new File(getDataFolder(), "config.yml");
        logs.file = new File(getDataFolder(), "logs.log");
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        if (!FileName.exists()) {
            try (InputStream in = getResource("config_bukkit.yml")) {
                Files.copy(in, FileName.toPath());
            } catch (IOException e) {
                config_bukkit.log.warning("§d[Minecraft_QQ]§c配置文件创建失败：" + e);
            }
        }
        try {
            if (!logs.file.exists()) {
                logs.file.createNewFile();
            }
        } catch (IOException e) {
            config_bukkit.log.warning("§d[Minecraft_QQ]§c日志文件错误：" + e);
        }
    }

    @Override
    public void onEnable() {
        config_bukkit.log = Bukkit.getLogger();
        Color_yr.Minecraft_QQ.config.message_a = new message_bukkit();
        config_bukkit.log.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
        setConfig();
        Config_reload();
        Bukkit.getPluginManager().registerEvents(new Event_bukkit(), this);
        Bukkit.getPluginCommand("qq").setExecutor(new commder_bukkit());
        config_bukkit.log.info("§d[Minecraft_QQ]§e已启动-" + Version);
        config_bukkit.log.info("§d[Minecraft_QQ]§eDebug模式" + config_bukkit.System_Debug);
        socket socket = new socket();
        socket.socket_start();
    }

    @Override
    public void onDisable() {
        if (socket.socket_runFlag == true) {
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
        config_bukkit.log.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
