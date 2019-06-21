package Color_yr.Minecraft_QQ;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Minecraft_QQ_bukkit extends JavaPlugin {

    public static FileConfiguration config_data_bukkit;
    public static Plugin Minecraft_QQ;

    public static void loadconfig() {
        config.log.info("§d[Minecraft_QQ]§e当前插件版本为：" + config.Version
                + "，你的配置文件版本为：" + config_data_bukkit.getString("Version"));

        config_bukkit.Join_Message = config_data_bukkit.getString("Join.Message", "%Player%加入了服务器");
        config_bukkit.Join_sendQQ = config_data_bukkit.getBoolean("Join.sendQQ", true);

        config_bukkit.Quit_Message = config_data_bukkit.getString("Quit.Message", "%Player%退出了服务器");
        config_bukkit.Quit_sendQQ = config_data_bukkit.getBoolean("Quit.sendQQ", true);

        config_bukkit.Minecraft_ServerName = config_data_bukkit.getString("Minecraft.ServerName", "[MC服务器]");// 服务器名字
        config_bukkit.Minecraft_Check = config_data_bukkit.getString("Minecraft.Check", "群：");// 触发文本
        config_bukkit.Minecraft_Message = config_data_bukkit.getString("Minecraft.Message", "%Servername%-%Player%:%Msg%");// 发送文本
        config_bukkit.Minecraft_Say = config_data_bukkit.getString("Minecraft.Say", "&6[%Servername%]&b[群消息]&3");// 后台文本
        config_bukkit.Minecraft_Mode = config_data_bukkit.getInt("Minecraft.Mode", 1);
        config_bukkit.Minecraft_SendMode = config_data_bukkit.getBoolean("Minecraft.SendMode", true);
        config_bukkit.Minecraft_PlayerListMessage = config_data_bukkit.getString("Minecraft.PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%");
        config_bukkit.Minecraft_ServerOnlineMessage = config_data_bukkit.getString("Minecraft.ServerOnlineMessage", "%Servername%服务器在线");

        config_bukkit.System_IP = config_data_bukkit.getString("System.IP", "localhost"); // 服务器地址
        config_bukkit.System_PORT = config_data_bukkit.getInt("System.Port", 25555);// 服务器端口号
        config_bukkit.System_AutoConnet = config_data_bukkit.getBoolean("System.AutoConnet", false);
        config_bukkit.System_AutoConnetTime = config_data_bukkit.getInt("System.AutoConnetTime", 10000);
        config_bukkit.System_Debug = config_data_bukkit.getBoolean("System.Debug", false);
        config_bukkit.Head = config_data_bukkit.getString("System.Head", "[Head]");
        config_bukkit.End = config_data_bukkit.getString("System.End", "[End]");

        config_bukkit.User_SendSucceed = config_data_bukkit.getBoolean("User.SendSucceed", true);
        config_bukkit.User_SendSucceedMessage = config_data_bukkit.getString("User.SendSucceedMessage", "已发送消息至群内");
        config_bukkit.User_NotSendCommder = config_data_bukkit.getBoolean("User.NotSendCommder", true);

        logs.Socket_log = config_data_bukkit.getBoolean("Logs.Socket", true);
        logs.Group_log = config_data_bukkit.getBoolean("Logs.Group", true);
        logs.Send_log = config_data_bukkit.getBoolean("Logs.Send", true);
        logs.Error_log = config_data_bukkit.getBoolean("Logs.Error", false);
    }

    public static void Config_reload() {
        try {
            config_data_bukkit = YamlConfiguration.loadConfiguration(config.FileName);
            loadconfig();
        } catch (Exception arg0) {
            config.log.warning("§d[Minecraft_QQ]§c配置文件读取失败:" + arg0);
        }
    }

    public void setConfig() {
        config.FileName = new File(getDataFolder(), "config.yml");
        logs.file = new File(getDataFolder(), "logs.log");
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        if (!config.FileName.exists()) {
            try (InputStream in = getResource("config_bukkit.yml")) {
                Files.copy(in, config.FileName.toPath());
            } catch (IOException e) {
                config.log.warning("§d[Minecraft_QQ]§c配置文件创建失败：" + e);
            }
        }
        try {
            if (!logs.file.exists()) {
                logs.file.createNewFile();
            }
        } catch (IOException e) {
            config.log.warning("§d[Minecraft_QQ]§c日志文件错误：" + e);
        }
    }

    @Override
    public void onEnable() {
        config.log = Bukkit.getLogger();
        config.message_a = new message_bukkit();
        config.is_bungee = false;
        config.log.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
        setConfig();
        Config_reload();
        Bukkit.getPluginManager().registerEvents(new Event_bukkit(), this);
        Bukkit.getPluginCommand("qq").setExecutor(new commder_bukkit(this));
        Minecraft_QQ = this;
        config.log.info("§d[Minecraft_QQ]§e已启动-" + config.Version);
        config.log.info("§d[Minecraft_QQ]§eDebug模式" + config_bukkit.System_Debug);
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
        config.log.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
