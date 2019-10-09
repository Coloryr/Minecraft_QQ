package Color_yr.Minecraft_QQ.Config;

import Color_yr.Minecraft_QQ.Log.logs;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

import java.util.List;

@Config(modid = "minecraft_qq", name = "Minecraft_QQ")
public class Forge_ {
    @Config.Name("Minecraft_QQ")
    public static Minecraft_QQ Minecraft_QQ = new Minecraft_QQ();

    public static class Minecraft_QQ {
        @Config.Comment("发送群的服务器名字")
        @Config.Name("ServerName")
        public String ServerName = "[MC服务器]";

        @Config.Comment("触发文本")
        @Config.Name("Check")
        public String Check = "群：";

        @Config.Comment("发送至QQ群的文本，变量：%Servername%服务器名字，%Player%玩家名字，%Message%玩家说的话")
        @Config.Name("Message")
        public String Message = "%Servername%-%Player%:%Message%";

        @Config.Comment("触发文本")
        @Config.Name("Say")
        public String Say = "[%Servername%][群消息]%Message%";

        @Config.Comment("机器人模式，0:不发送消息至QQ群，1:检测是否有触发文本，如果有就发送，2:无论玩家说什么，都发送到QQ群")
        @Config.Name("Mode")
        @Config.RangeInt(min = 0, max = 2)
        public int Mode = 1;

        @Config.Comment("是否开启在线人数显示")
        @Config.Name("SendMode")
        public boolean SendMode = true;

        @Config.Comment("在线人数文本，变量：%Servername%服务器名字，%player_number%玩家数量，%player_list%所有玩家列表")
        @Config.Name("PlayerListMessage")
        public String PlayerListMessage = "%Servername%当前在线人数：%player_number%，玩家列表：%player_list%";

        @Config.Comment("服务器状态文本")
        @Config.Name("ServerOnlineMessage")
        public String ServerOnlineMessage = "%Servername%服务器在线";

        @Config.Comment("机器人链接IP")
        @Config.Name("IP")
        public String IP = "localhost";

        @Config.Comment("机器人链接端口")
        @Config.Name("Port")
        @Config.RangeInt(min = 0, max = 65535)
        public int Port = 25555;

        @Config.Comment("是否启用自动重连")
        @Config.Name("AutoConnet")
        public boolean AutoConnet = false;

        @Config.Comment("自动重连时间间隔")
        @Config.Name("AutoConnetTime")
        @Config.RangeInt(min = 1, max = 100000)
        public int AutoConnetTime = 10000;

        @Config.Comment("是否启用Debug模式")
        @Config.Name("Debug")
        public boolean Debug = false;

        @Config.Comment("数据包头")
        @Config.Name("Head")
        public String Head = "[Head]";

        @Config.Comment("数据包尾")
        @Config.Name("End")
        public String End = "[End]";

        @Config.Comment("机器人链接端口")
        @Config.Name("Sleep")
        @Config.RangeInt(min = 1, max = 100)
        public int Sleep = 50;

        @Config.Comment("完成发送后是否提醒")
        @Config.Name("SendSucceed")
        public boolean SendSucceed = true;

        @Config.Comment("完成发送后显示的文本")
        @Config.Name("SendSucceedMessage")
        public String SendSucceedMessage = "已发送消息至群内";

        @Config.Comment("是否屏蔽玩家输入指令")
        @Config.Name("NotSendCommder")
        public boolean NotSendCommder = true;

        @Config.Comment("是否记录链接情况")
        @Config.Name("Socket")
        public boolean Socket = true;

        @Config.Comment("是否记录群发来的消息")
        @Config.Name("Group")
        public boolean Group = true;

        @Config.Comment("是否记录发送至群的消息")
        @Config.Name("Send")
        public boolean Send = true;

        @Config.Comment("是否记录错误内容")
        @Config.Name("Error")
        public boolean Error = true;

        @Config.Comment("不参与聊天玩家")
        @Config.Name("Player")
        public List<String> Player = Bukkit_.Mute_List;

        @Config.Comment("配置文件版本号")
        @Config.Name("Version")
        public String Version = Color_yr.Minecraft_QQ.Main.Forge.VERSION;
    }

    public void init() {
        Bukkit_.Mute_List = Minecraft_QQ.Player;
        Bukkit_.Minecraft_ServerName = Minecraft_QQ.ServerName;
        Bukkit_.Minecraft_Check = Minecraft_QQ.Check;
        Bukkit_.Minecraft_Message = Minecraft_QQ.Message;
        Bukkit_.Minecraft_Say = Minecraft_QQ.Say;
        Bukkit_.Minecraft_Mode = Minecraft_QQ.Mode;
        Bukkit_.Minecraft_SendMode = Minecraft_QQ.SendMode;
        Bukkit_.Minecraft_PlayerListMessage = Minecraft_QQ.PlayerListMessage;
        Bukkit_.Minecraft_ServerOnlineMessage = Minecraft_QQ.ServerOnlineMessage;
        Bukkit_.System_IP = Minecraft_QQ.IP;
        Bukkit_.System_PORT = Minecraft_QQ.Port;
        Bukkit_.System_AutoConnet = Minecraft_QQ.AutoConnet;
        Bukkit_.System_AutoConnetTime = Minecraft_QQ.AutoConnetTime;
        Bukkit_.System_Debug = Minecraft_QQ.Debug;
        Bukkit_.System_Sleep = Minecraft_QQ.Sleep;
        Bukkit_.User_SendSucceed = Minecraft_QQ.SendSucceed;
        Bukkit_.User_SendSucceedMessage = Minecraft_QQ.SendSucceedMessage;
        Bukkit_.User_NotSendCommder = Minecraft_QQ.NotSendCommder;
        Bukkit_.Head = Minecraft_QQ.Head;
        Bukkit_.End = Minecraft_QQ.End;
        logs.Socket_log = Minecraft_QQ.Socket;
        logs.Group_log = Minecraft_QQ.Group;
        logs.Send_log = Minecraft_QQ.Send;
        logs.Error_log = Minecraft_QQ.Error;
    }

    public void reload() {
        ConfigManager.sync("minecraft_qq", Config.Type.INSTANCE);
    }
}