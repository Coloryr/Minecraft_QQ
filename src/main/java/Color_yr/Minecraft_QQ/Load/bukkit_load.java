package Color_yr.Minecraft_QQ.Load;

import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Bukkit;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
import Color_yr.Minecraft_QQ.logs;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class bukkit_load {

    public FileConfiguration config_data_bukkit;

    private void loadconfig() {
        Bukkit.log_b.info("§d[Minecraft_QQ]§e当前插件版本为：" + use.Version
                + "，你的配置文件版本为：" + config_data_bukkit.getString("Version"));

        BaseConfig.JoinMessage = config_data_bukkit.getString("Join.Message", "%Player%加入了服务器");
        BaseConfig.JoinsendQQ = config_data_bukkit.getBoolean("Join.sendQQ", true);

        BaseConfig.QuitMessage = config_data_bukkit.getString("Quit.Message", "%Player%退出了服务器");
        BaseConfig.QuitsendQQ = config_data_bukkit.getBoolean("Quit.sendQQ", true);

        BaseConfig.MinecraftServerName = config_data_bukkit.getString("Minecraft.ServerName", "[MC服务器]");// 服务器名字
        BaseConfig.MinecraftCheck = config_data_bukkit.getString("Minecraft.Check", "群：");// 触发文本
        BaseConfig.MinecraftMessage = config_data_bukkit.getString("Minecraft.Message", "%Servername%-%Player%:%Msg%");// 发送文本
        BaseConfig.MinecraftSay = config_data_bukkit.getString("Minecraft.Say", "&6[%Servername%]&b[群消息]&3");// 后台文本
        BaseConfig.MinecraftMode = config_data_bukkit.getInt("Minecraft.Mode", 1);
        BaseConfig.MinecraftSendMode = config_data_bukkit.getBoolean("Minecraft.SendMode", true);
        BaseConfig.MinecraftPlayerListMessage = config_data_bukkit.getString("Minecraft.PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%");
        BaseConfig.MinecraftServerOnlineMessage = config_data_bukkit.getString("Minecraft.ServerOnlineMessage", "%Servername%服务器在线");

        BaseConfig.SystemIP = config_data_bukkit.getString("System.IP", "localhost"); // 服务器地址
        BaseConfig.SystemPORT = config_data_bukkit.getInt("System.Port", 25555);// 服务器端口号
        BaseConfig.SystemAutoConnet = config_data_bukkit.getBoolean("System.AutoConnet", false);
        BaseConfig.SystemAutoConnetTime = config_data_bukkit.getInt("System.AutoConnetTime", 10000);
        BaseConfig.SystemDebug = config_data_bukkit.getBoolean("System.Debug", false);
        BaseConfig.Head = config_data_bukkit.getString("System.Head", "[Head]");
        BaseConfig.End = config_data_bukkit.getString("System.End", "[End]");
        BaseConfig.SystemSleep = config_data_bukkit.getInt("System.Sleep", 50);

        BaseConfig.UserSendSucceed = config_data_bukkit.getBoolean("User.SendSucceed", true);
        BaseConfig.UserSendSucceedMessage = config_data_bukkit.getString("User.SendSucceedMessage", "已发送消息至群内");
        BaseConfig.UserNotSendCommder = config_data_bukkit.getBoolean("User.NotSendCommder", true);

        BaseConfig.MuteList = YamlConfiguration.loadConfiguration(
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
            Bukkit.log_b.info("§d[Minecraft_QQ]§c配置文件读取失败:");
            e.printStackTrace();
        }
    }
}
