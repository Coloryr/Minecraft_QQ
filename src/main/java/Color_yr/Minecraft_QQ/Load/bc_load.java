package Color_yr.Minecraft_QQ.Load;

import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.BungeeCord;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
import Color_yr.Minecraft_QQ.logs;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class bc_load {
    private void loadconfig() throws IOException {
        BungeeCord.log_b.info("§d[Minecraft_QQ]§e当前插件版本为：" + use.Version
                + "，你的配置文件版本为：" + BungeeCord.config_data_bungee.config.getString("Version"));

        BaseConfig.JoinMessage = BungeeCord.config_data_bungee.config.getString("Join.Message", "%Player%加入了服务器");
        BaseConfig.JoinsendQQ = BungeeCord.config_data_bungee.config.getBoolean("Join.sendQQ", true);

        BaseConfig.QuitMessage = BungeeCord.config_data_bungee.config.getString("Quit.Message", "%Player%退出了服务器");
        BaseConfig.QuitsendQQ = BungeeCord.config_data_bungee.config.getBoolean("Quit.sendQQ", true);

        BungeeCord.config_data_bungee.ChangeServerMessage = BungeeCord.config_data_bungee.config.getString("ChangeServer.Message", "%Player%加入了子服%Server%");
        BungeeCord.config_data_bungee.ChangeServersendQQ = BungeeCord.config_data_bungee.config.getBoolean("ChangeServer.sendQQ", true);

        BaseConfig.MinecraftServerName = BungeeCord.config_data_bungee.config.getString("Minecraft.ServerName", "[MC服务器]");// 服务器名字
        BaseConfig.MinecraftCheck = BungeeCord.config_data_bungee.config.getString("Minecraft.Check", "群：");// 触发文本
        BaseConfig.MinecraftMessage = BungeeCord.config_data_bungee.config.getString("Minecraft.Message", "%Servername%-%Player%:%Msg%");// 发送文本
        BaseConfig.MinecraftSay = BungeeCord.config_data_bungee.config.getString("Minecraft.Say", "&6[%Servername%]&b[群消息]&3");// 后台文本
        BaseConfig.MinecraftMode = BungeeCord.config_data_bungee.config.getInt("Minecraft.Mode", 1);
        BaseConfig.MinecraftSendMode = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.SendMode", true);
        BungeeCord.config_data_bungee.MinecraftSendOneByOne = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.SendOneByOne", true);
        BungeeCord.config_data_bungee.MinecraftSendOneByOneMessage = BungeeCord.config_data_bungee.config.getString("Minecraft.SendOneByOneMessage", "\n[%Server%-%player_number%]-%player_list%");
        BungeeCord.config_data_bungee.MinecraftHideEmptyServer = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.HideEmptyServer", true);
        BaseConfig.MinecraftPlayerListMessage = BungeeCord.config_data_bungee.config.getString("Minecraft.PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%");
        BungeeCord.config_data_bungee.MinecraftHideList = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.HideList", false);
        BaseConfig.MinecraftServerOnlineMessage = BungeeCord.config_data_bungee.config.getString("Minecraft.ServerOnlineMessage", "%Servername%服务器在线");

        BungeeCord.config_data_bungee.SendAllServerEnable = BungeeCord.config_data_bungee.config.getBoolean("SendAllServer.Enable", true);
        BungeeCord.config_data_bungee.SendAllServerMessage = BungeeCord.config_data_bungee.config.getString("SendAllServer.Message", "[%Servername%]玩家：[%Player%]发送群消息：[%Message%]");
        BungeeCord.config_data_bungee.SendAllServerOnlySideServer = BungeeCord.config_data_bungee.config.getBoolean("SendAllServer.OnlySideServer", false);

        BaseConfig.SystemIP = BungeeCord.config_data_bungee.config.getString("System.IP", "localhost"); // 服务器地址
        BaseConfig.SystemPORT = BungeeCord.config_data_bungee.config.getInt("System.Port", 25555);// 服务器端口号
        BaseConfig.SystemAutoConnet = BungeeCord.config_data_bungee.config.getBoolean("System.AutoConnet", false);
        BaseConfig.SystemAutoConnetTime = BungeeCord.config_data_bungee.config.getInt("System.AutoConnetTime", 10000);
        BaseConfig.SystemDebug = BungeeCord.config_data_bungee.config.getBoolean("System.Debug", false);
        BaseConfig.Head = BungeeCord.config_data_bungee.config.getString("System.Head", "[Head]");
        BaseConfig.End = BungeeCord.config_data_bungee.config.getString("System.End", "[End]");
        BaseConfig.SystemSleep = BungeeCord.config_data_bungee.config.getInt("System.Sleep", 50);

        BaseConfig.UserSendSucceed = BungeeCord.config_data_bungee.config.getBoolean("User.SendSucceed", true);
        BaseConfig.UserSendSucceedMessage = BungeeCord.config_data_bungee.config.getString("User.SendSucceedMessage", "已发送消息至群内");
        BaseConfig.UserNotSendCommder = BungeeCord.config_data_bungee.config.getBoolean("User.NotSendCommder", true);

        BaseConfig.MuteList = ConfigurationProvider.getProvider(YamlConfiguration.class).load(use.player).getStringList("player");

        logs.Group_log = BungeeCord.config_data_bungee.config.getBoolean("Logs.Group", true);
        logs.Send_log = BungeeCord.config_data_bungee.config.getBoolean("Logs.Send", true);

    }

    public void setConfig() {
        try {
            use.FileName = new File(BungeeCord.Plugin.getDataFolder(), "config.yml");
            logs.file = new File(BungeeCord.Plugin.getDataFolder(), "logs.log");
            use.player = new File(BungeeCord.Plugin.getDataFolder(), "mute.yml");
            if (!BungeeCord.Plugin.getDataFolder().exists())
                BungeeCord.Plugin.getDataFolder().mkdir();
            if (!use.FileName.exists()) {
                InputStream in = BungeeCord.Plugin.getResourceAsStream("config_bungee.yml");
                Files.copy(in, use.FileName.toPath());
            }
            if (!use.player.exists()) {
                InputStream in = BungeeCord.Plugin.getResourceAsStream("mute.yml");
                Files.copy(in, use.player.toPath());
            }
            if (!logs.file.exists()) {
                logs.file.createNewFile();
            }
            BungeeCord.config_data_bungee.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(use.FileName);
            loadconfig();
        } catch (Exception e) {
            BungeeCord.log_b.warning("§d[Minecraft_QQ]§c配置文件读取失败:");
            e.printStackTrace();
        }
    }
}
