package Color_yr.Minecraft_QQ.Load;

import Color_yr.Minecraft_QQ.Config.Base_config;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.BungeeCord;
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

        Base_config.Join_Message = BungeeCord.config_data_bungee.config.getString("Join.Message", "%Player%加入了服务器");
        Base_config.Join_sendQQ = BungeeCord.config_data_bungee.config.getBoolean("Join.sendQQ", true);

        Base_config.Quit_Message = BungeeCord.config_data_bungee.config.getString("Quit.Message", "%Player%退出了服务器");
        Base_config.Quit_sendQQ = BungeeCord.config_data_bungee.config.getBoolean("Quit.sendQQ", true);

        BungeeCord.config_data_bungee.ChangeServer_Message = BungeeCord.config_data_bungee.config.getString("ChangeServer.Message", "%Player%加入了子服%Server%");
        BungeeCord.config_data_bungee.ChangeServer_sendQQ = BungeeCord.config_data_bungee.config.getBoolean("ChangeServer.sendQQ", true);

        Base_config.Minecraft_ServerName = BungeeCord.config_data_bungee.config.getString("Minecraft.ServerName", "[MC服务器]");// 服务器名字
        Base_config.Minecraft_Check = BungeeCord.config_data_bungee.config.getString("Minecraft.Check", "群：");// 触发文本
        Base_config.Minecraft_Message = BungeeCord.config_data_bungee.config.getString("Minecraft.Message", "%Servername%-%Player%:%Msg%");// 发送文本
        Base_config.Minecraft_Say = BungeeCord.config_data_bungee.config.getString("Minecraft.Say", "&6[%Servername%]&b[群消息]&3");// 后台文本
        Base_config.Minecraft_Mode = BungeeCord.config_data_bungee.config.getInt("Minecraft.Mode", 1);
        Base_config.Minecraft_SendMode = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.SendMode", true);
        BungeeCord.config_data_bungee.Minecraft_SendOneByOne = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.SendOneByOne", true);
        BungeeCord.config_data_bungee.Minecraft_SendOneByOneMessage = BungeeCord.config_data_bungee.config.getString("Minecraft.SendOneByOneMessage", "\n[%Server%-%player_number%]-%player_list%");
        BungeeCord.config_data_bungee.Minecraft_HideEmptyServer = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.HideEmptyServer", true);
        Base_config.Minecraft_PlayerListMessage = BungeeCord.config_data_bungee.config.getString("Minecraft.PlayerListMessage", "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%");
        BungeeCord.config_data_bungee.Minecraft_HideList = BungeeCord.config_data_bungee.config.getBoolean("Minecraft.HideList", false);
        Base_config.Minecraft_ServerOnlineMessage = BungeeCord.config_data_bungee.config.getString("Minecraft.ServerOnlineMessage", "%Servername%服务器在线");

        BungeeCord.config_data_bungee.SendAllServer_Enable = BungeeCord.config_data_bungee.config.getBoolean("SendAllServer.Enable", true);
        BungeeCord.config_data_bungee.SendAllServer_Message = BungeeCord.config_data_bungee.config.getString("SendAllServer.Message", "[%Servername%]玩家：[%Player%]发送群消息：[%Message%]");
        BungeeCord.config_data_bungee.SendAllServer_OnlySideServer = BungeeCord.config_data_bungee.config.getBoolean("SendAllServer.OnlySideServer", false);

        Base_config.System_IP = BungeeCord.config_data_bungee.config.getString("System.IP", "localhost"); // 服务器地址
        Base_config.System_PORT = BungeeCord.config_data_bungee.config.getInt("System.Port", 25555);// 服务器端口号
        Base_config.System_AutoConnet = BungeeCord.config_data_bungee.config.getBoolean("System.AutoConnet", false);
        Base_config.System_AutoConnetTime = BungeeCord.config_data_bungee.config.getInt("System.AutoConnetTime", 10000);
        Base_config.System_Debug = BungeeCord.config_data_bungee.config.getBoolean("System.Debug", false);
        Base_config.Head = BungeeCord.config_data_bungee.config.getString("System.Head", "[Head]");
        Base_config.End = BungeeCord.config_data_bungee.config.getString("System.End", "[End]");
        Base_config.System_Sleep = BungeeCord.config_data_bungee.config.getInt("System.Sleep", 50);

        Base_config.User_SendSucceed = BungeeCord.config_data_bungee.config.getBoolean("User.SendSucceed", true);
        Base_config.User_SendSucceedMessage = BungeeCord.config_data_bungee.config.getString("User.SendSucceedMessage", "已发送消息至群内");
        Base_config.User_NotSendCommder = BungeeCord.config_data_bungee.config.getBoolean("User.NotSendCommder", true);

        Base_config.Mute_List = ConfigurationProvider.getProvider(YamlConfiguration.class).load(use.player).getStringList("player");

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
            BungeeCord.log_b.warning("§d[Minecraft_QQ]§c配置文件读取失败:" + e.getMessage());
        }
    }
}
