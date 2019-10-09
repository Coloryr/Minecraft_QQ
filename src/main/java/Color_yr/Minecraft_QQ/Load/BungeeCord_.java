package Color_yr.Minecraft_QQ.Load;

import Color_yr.Minecraft_QQ.Config.Bukkit_;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Main.BungeeCord;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BungeeCord_ {
    private void loadconfig() throws IOException {
        BungeeCord.log_b.info("§d[Minecraft_QQ]§e当前插件版本为：" + config.Version
                + "，你的配置文件版本为：" + BungeeCord.config_data_bungee.config.getString("Version"));

        Bukkit_.Join_Message = BungeeCord.config_data_bungee.config.getString("Join.Message", "%Player%加入了服务器");
        Bukkit_.Join_sendQQ = BungeeCord.config_data_bungee.config.getBoolean("Join.sendQQ", true);

        Bukkit_.Quit_Message = BungeeCord.config_data_bungee.config.getString("Quit.Message", "%Player%退出了服务器");
        Bukkit_.Quit_sendQQ = BungeeCord.config_data_bungee.config.getBoolean("Quit.sendQQ", true);

        BungeeCord.config_data_bungee.ChangeServer_Message = BungeeCord.config_data_bungee.config.getString("ChangeServer.Message", "%Player%加入了子服%Server%");
        BungeeCord.config_data_bungee.ChangeServer_sendQQ = BungeeCord.config_data_bungee.config.getBoolean("ChangeServer.sendQQ", true);

        Bukkit_.Minecraft_ServerName = BungeeCord.config_data_bungee.config.getString("Minecraft.ServerName", "[MC服务器]");// 服务器名字
        Bukkit_.Minecraft_Check = BungeeCord.config_data_bungee.config.getString("Minecraft.Check", "群：");// 触发文本
        Bukkit_.Minecraft_Message = BungeeCord.config_data_bungee.config.getString("Minecraft.Message", "%Servername%-%Player%:%Msg%");// 发送文本
        Bukkit_.Minecraft_Say = BungeeCord.config_data_bungee.config.getString("Minecraft.Say", "&6[%Servername%]&b[群消息]&3");// 后台文本
        Bukkit_.Minecraft_Mode = BungeeCord.config_data_bungee.config.getInt("Minecraft.Mode", 1);
        Bukkit_.Minecraft_SendMode = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.SendMode", true);
        BungeeCord.config_data_bungee.Minecraft_SendOneByOne = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.SendOneByOne", true);
        BungeeCord.config_data_bungee.Minecraft_SendOneByOneMessage = BungeeCord.config_data_bungee.config.getString("Minecraft.SendOneByOneMessage", "\n[%Server%-%player_number%]-%player_list%");
        BungeeCord.config_data_bungee.Minecraft_HideEmptyServer = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.HideEmptyServer", true);
        Bukkit_.Minecraft_PlayerListMessage = BungeeCord.config_data_bungee.config.getString("Minecraft.PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%");
        BungeeCord.config_data_bungee.Minecraft_HideList = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.HideList", false);
        Bukkit_.Minecraft_ServerOnlineMessage = BungeeCord.config_data_bungee.config.getString("Minecraft.ServerOnlineMessage", "%Servername%服务器在线");

        BungeeCord.config_data_bungee.SendAllServer_Enable = BungeeCord.config_data_bungee.config.getBoolean("SendAllServer.Enable", true);
        BungeeCord.config_data_bungee.SendAllServer_Message = BungeeCord.config_data_bungee.config.getString("SendAllServer.Message", "[%Servername%]玩家：[%Player%]发送群消息：[%Message%]");
        BungeeCord.config_data_bungee.SendAllServer_OnlySideServer = BungeeCord.config_data_bungee.config.getBoolean("SendAllServer.OnlySideServer", false);

        Bukkit_.System_IP = BungeeCord.config_data_bungee.config.getString("System.IP", "localhost"); // 服务器地址
        Bukkit_.System_PORT = BungeeCord.config_data_bungee.config.getInt("System.Port", 25555);// 服务器端口号
        Bukkit_.System_AutoConnet = BungeeCord.config_data_bungee.config.getBoolean("System.AutoConnet", false);
        Bukkit_.System_AutoConnetTime = BungeeCord.config_data_bungee.config.getInt("System.AutoConnetTime", 10000);
        Bukkit_.System_Debug = BungeeCord.config_data_bungee.config.getBoolean("System.Debug", false);
        Bukkit_.Head = BungeeCord.config_data_bungee.config.getString("System.Head", "[Head]");
        Bukkit_.End = BungeeCord.config_data_bungee.config.getString("System.End", "[End]");
        Bukkit_.System_Sleep = BungeeCord.config_data_bungee.config.getInt("System.Sleep", 50);

        Bukkit_.User_SendSucceed = BungeeCord.config_data_bungee.config.getBoolean("User.SendSucceed", true);
        Bukkit_.User_SendSucceedMessage = BungeeCord.config_data_bungee.config.getString("User.SendSucceedMessage", "已发送消息至群内");
        Bukkit_.User_NotSendCommder = BungeeCord.config_data_bungee.config.getBoolean("User.NotSendCommder", true);

        Bukkit_.Mute_List = ConfigurationProvider.getProvider(YamlConfiguration.class).load(config.player).getStringList("player");

        logs.Socket_log = BungeeCord.config_data_bungee.config.getBoolean("Logs.Socket", true);
        logs.Group_log = BungeeCord.config_data_bungee.config.getBoolean("Logs.Group", true);
        logs.Send_log = BungeeCord.config_data_bungee.config.getBoolean("Logs.Send", true);
        logs.Error_log = BungeeCord.config_data_bungee.config.getBoolean("Logs.Error", true);
    }

    public void setConfig() {
        try {
            config.FileName = new File(BungeeCord.Plugin.getDataFolder(), "config.yml");
            logs.file = new File(BungeeCord.Plugin.getDataFolder(), "logs.log");
            config.player = new File(BungeeCord.Plugin.getDataFolder(), "mute.yml");
            if (!BungeeCord.Plugin.getDataFolder().exists())
                BungeeCord.Plugin.getDataFolder().mkdir();
            if (!config.FileName.exists()) {
                InputStream in = BungeeCord.Plugin.getResourceAsStream("config_bungee.yml");
                Files.copy(in, config.FileName.toPath());
            }
            if (!config.player.exists()) {
                InputStream in = BungeeCord.Plugin.getResourceAsStream("mute.yml");
                Files.copy(in, config.player.toPath());
            }
            if (!logs.file.exists()) {
                logs.file.createNewFile();
            }
            BungeeCord.config_data_bungee.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(config.FileName);
            loadconfig();
        } catch (Exception e) {
            BungeeCord.log_b.warning("§d[Minecraft_QQ]§c配置文件读取失败:" + e.getMessage());
        }
    }
}
