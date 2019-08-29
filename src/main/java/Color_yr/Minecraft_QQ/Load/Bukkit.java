package Color_yr.Minecraft_QQ.Load;

import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Log.logs;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class Bukkit {

    public FileConfiguration config_data_bukkit;

    private void loadconfig() {
        config.log_b.info("§d[Minecraft_QQ]§e当前插件版本为：" + config.Version
                + "，你的配置文件版本为：" + config_data_bukkit.getString("Version"));

        Color_yr.Minecraft_QQ.Config.Bukkit.Join_Message = config_data_bukkit.getString("Join.Message", "%Player%加入了服务器");
        Color_yr.Minecraft_QQ.Config.Bukkit.Join_sendQQ = config_data_bukkit.getBoolean("Join.sendQQ", true);

        Color_yr.Minecraft_QQ.Config.Bukkit.Quit_Message = config_data_bukkit.getString("Quit.Message", "%Player%退出了服务器");
        Color_yr.Minecraft_QQ.Config.Bukkit.Quit_sendQQ = config_data_bukkit.getBoolean("Quit.sendQQ", true);

        Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_ServerName = config_data_bukkit.getString("Minecraft.ServerName", "[MC服务器]");// 服务器名字
        Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Check = config_data_bukkit.getString("Minecraft.Check", "群：");// 触发文本
        Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Message = config_data_bukkit.getString("Minecraft.Message", "%Servername%-%Player%:%Msg%");// 发送文本
        Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Say = config_data_bukkit.getString("Minecraft.Say", "&6[%Servername%]&b[群消息]&3");// 后台文本
        Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Mode = config_data_bukkit.getInt("Minecraft.Mode", 1);
        Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_SendMode = config_data_bukkit.getBoolean("Minecraft.SendMode", true);
        Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_PlayerListMessage = config_data_bukkit.getString("Minecraft.PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%");
        Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_ServerOnlineMessage = config_data_bukkit.getString("Minecraft.ServerOnlineMessage", "%Servername%服务器在线");

        Color_yr.Minecraft_QQ.Config.Bukkit.System_IP = config_data_bukkit.getString("System.IP", "localhost"); // 服务器地址
        Color_yr.Minecraft_QQ.Config.Bukkit.System_PORT = config_data_bukkit.getInt("System.Port", 25555);// 服务器端口号
        Color_yr.Minecraft_QQ.Config.Bukkit.System_AutoConnet = config_data_bukkit.getBoolean("System.AutoConnet", false);
        Color_yr.Minecraft_QQ.Config.Bukkit.System_AutoConnetTime = config_data_bukkit.getInt("System.AutoConnetTime", 10000);
        Color_yr.Minecraft_QQ.Config.Bukkit.System_Debug = config_data_bukkit.getBoolean("System.Debug", false);
        Color_yr.Minecraft_QQ.Config.Bukkit.Head = config_data_bukkit.getString("System.Head", "[Head]");
        Color_yr.Minecraft_QQ.Config.Bukkit.End = config_data_bukkit.getString("System.End", "[End]");
        Color_yr.Minecraft_QQ.Config.Bukkit.System_Sleep = config_data_bukkit.getInt("System.Sleep", 50);

        Color_yr.Minecraft_QQ.Config.Bukkit.User_SendSucceed = config_data_bukkit.getBoolean("User.SendSucceed", true);
        Color_yr.Minecraft_QQ.Config.Bukkit.User_SendSucceedMessage = config_data_bukkit.getString("User.SendSucceedMessage", "已发送消息至群内");
        Color_yr.Minecraft_QQ.Config.Bukkit.User_NotSendCommder = config_data_bukkit.getBoolean("User.NotSendCommder", true);

        logs.Socket_log = config_data_bukkit.getBoolean("Logs.Socket", true);
        logs.Group_log = config_data_bukkit.getBoolean("Logs.Group", true);
        logs.Send_log = config_data_bukkit.getBoolean("Logs.Send", true);
        logs.Error_log = config_data_bukkit.getBoolean("Logs.Error", true);
    }

    public void setConfig(Plugin Minecraft_QQ) {
        try {
            config.FileName = new File(Minecraft_QQ.getDataFolder(), "config.yml");
            logs.file = new File(Minecraft_QQ.getDataFolder(), "logs.log");
            if (!Minecraft_QQ.getDataFolder().exists())
                Minecraft_QQ.getDataFolder().mkdir();
            if (!config.FileName.exists()) {
                InputStream in = Minecraft_QQ.getResource("config_bukkit.yml");
                Files.copy(in, config.FileName.toPath());
            }
            if (!logs.file.exists()) {
                logs.file.createNewFile();
            }
            config_data_bukkit = YamlConfiguration.loadConfiguration(config.FileName);
            loadconfig();
        } catch (Exception e) {
            config.log_b.warning("§d[Minecraft_QQ]§c配置文件读取失败:" + e.getMessage());
        }
    }
}
