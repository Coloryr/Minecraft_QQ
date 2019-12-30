package Color_yr.Minecraft_QQ.Load;

import Color_yr.Minecraft_QQ.Bukkit;
import Color_yr.Minecraft_QQ.Config.Base_config;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Log.logs;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class bukkit_load {

    public FileConfiguration config_data_bukkit;

    private void loadconfig() {
        use.ilog.Log_System("§d[Minecraft_QQ]§e当前插件版本为：" + use.Version
                + "，你的配置文件版本为：" + config_data_bukkit.getString("Version"));

        Base_config.Join_Message = config_data_bukkit.getString("Join.Message", "%Player%加入了服务器");
        Base_config.Join_sendQQ = config_data_bukkit.getBoolean("Join.sendQQ", true);

        Base_config.Quit_Message = config_data_bukkit.getString("Quit.Message", "%Player%退出了服务器");
        Base_config.Quit_sendQQ = config_data_bukkit.getBoolean("Quit.sendQQ", true);

        Base_config.Minecraft_ServerName = config_data_bukkit.getString("Minecraft.ServerName", "[MC服务器]");// 服务器名字
        Base_config.Minecraft_Check = config_data_bukkit.getString("Minecraft.Check", "群：");// 触发文本
        Base_config.Minecraft_Message = config_data_bukkit.getString("Minecraft.Message", "%Servername%-%Player%:%Msg%");// 发送文本
        Base_config.Minecraft_Say = config_data_bukkit.getString("Minecraft.Say", "&6[%Servername%]&b[群消息]&3");// 后台文本
        Base_config.Minecraft_Mode = config_data_bukkit.getInt("Minecraft.Mode", 1);
        Base_config.Minecraft_SendMode = config_data_bukkit.getBoolean("Minecraft.SendMode", true);
        Base_config.Minecraft_PlayerListMessage = config_data_bukkit.getString("Minecraft.PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%");
        Base_config.Minecraft_ServerOnlineMessage = config_data_bukkit.getString("Minecraft.ServerOnlineMessage", "%Servername%服务器在线");

        Base_config.System_IP = config_data_bukkit.getString("System.IP", "localhost"); // 服务器地址
        Base_config.System_PORT = config_data_bukkit.getInt("System.Port", 25555);// 服务器端口号
        Base_config.System_AutoConnet = config_data_bukkit.getBoolean("System.AutoConnet", false);
        Base_config.System_AutoConnetTime = config_data_bukkit.getInt("System.AutoConnetTime", 10000);
        Base_config.System_Debug = config_data_bukkit.getBoolean("System.Debug", false);
        Base_config.Head = config_data_bukkit.getString("System.Head", "[Head]");
        Base_config.End = config_data_bukkit.getString("System.End", "[End]");
        Base_config.System_Sleep = config_data_bukkit.getInt("System.Sleep", 50);

        Base_config.User_SendSucceed = config_data_bukkit.getBoolean("User.SendSucceed", true);
        Base_config.User_SendSucceedMessage = config_data_bukkit.getString("User.SendSucceedMessage", "已发送消息至群内");
        Base_config.User_NotSendCommder = config_data_bukkit.getBoolean("User.NotSendCommder", true);

        Base_config.Mute_List = YamlConfiguration.loadConfiguration(
                new File(Bukkit.Minecraft_QQ.getDataFolder(), "mute.yml"))
                .getStringList("player");

        logs.Group_log = config_data_bukkit.getBoolean("Logs.Group", true);
        logs.Send_log = config_data_bukkit.getBoolean("Logs.Send", true);
    }

    public void setConfig(Plugin Minecraft_QQ) {
        try {
            use.FileName = new File(Minecraft_QQ.getDataFolder(), "config.yml");
            logs.file = new File(Minecraft_QQ.getDataFolder(), "logs.log");
            use.player = new File(Minecraft_QQ.getDataFolder(), "mute.yml");
            if (!Minecraft_QQ.getDataFolder().exists())
                Minecraft_QQ.getDataFolder().mkdir();
            if (!use.FileName.exists()) {
                InputStream in = Minecraft_QQ.getResource("config_bukkit.yml");
                Files.copy(in, use.FileName.toPath());
            }
            if (!use.player.exists()) {
                InputStream in = Minecraft_QQ.getResource("mute.yml");
                Files.copy(in, use.player.toPath());
            }
            if (!logs.file.exists()) {
                logs.file.createNewFile();
            }
            config_data_bukkit = YamlConfiguration.loadConfiguration(use.FileName);
            loadconfig();
        } catch (Exception e) {
            use.ilog.Log_System("§d[Minecraft_QQ]§c配置文件读取失败:" + e.getMessage());
        }
    }
}
