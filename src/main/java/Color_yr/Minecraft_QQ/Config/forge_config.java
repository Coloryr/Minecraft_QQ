package Color_yr.Minecraft_QQ.Config;

import Color_yr.Minecraft_QQ.Forge;
import Color_yr.Minecraft_QQ.logs;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Config(modid = "minecraft_qq", name = "Minecraft_QQ")
public class forge_config {
    @Config.Name("Minecraft_QQ")
    public static Minecraft_QQ Minecraft_QQ = new Minecraft_QQ();

    public static void write(List<String> a)
    {
        Minecraft_QQ.PlayerMute.clear();
        for (String b : a)
        {
            Minecraft_QQ.PlayerMute.put(b, true);
        }
    }

    public static void init() {
        List<String> list = new ArrayList<>();
        for (String a : Minecraft_QQ.PlayerMute.keySet())
        {
            if(!list.contains(a))
                list.add(a);
        }
        BaseConfig.MuteList = list;
        BaseConfig.MinecraftServerName = Minecraft_QQ.ServerName;
        BaseConfig.MinecraftCheck = Minecraft_QQ.Check;
        BaseConfig.MinecraftMessage = Minecraft_QQ.Message;
        BaseConfig.MinecraftSay = Minecraft_QQ.Say;
        BaseConfig.MinecraftMode = Minecraft_QQ.Mode;
        BaseConfig.MinecraftSendMode = Minecraft_QQ.SendMode;
        BaseConfig.MinecraftPlayerListMessage = Minecraft_QQ.PlayerListMessage;
        BaseConfig.MinecraftServerOnlineMessage = Minecraft_QQ.ServerOnlineMessage;
        BaseConfig.SystemIP = Minecraft_QQ.IP;
        BaseConfig.SystemPORT = Minecraft_QQ.Port;
        BaseConfig.SystemAutoConnet = Minecraft_QQ.AutoConnet;
        BaseConfig.SystemAutoConnetTime = Minecraft_QQ.AutoConnetTime;
        BaseConfig.SystemDebug = Minecraft_QQ.Debug;
        BaseConfig.SystemSleep = Minecraft_QQ.Sleep;
        BaseConfig.UserSendSucceed = Minecraft_QQ.SendSucceed;
        BaseConfig.UserSendSucceedMessage = Minecraft_QQ.SendSucceedMessage;
        BaseConfig.UserNotSendCommder = Minecraft_QQ.NotSendCommder;
        BaseConfig.Head = Minecraft_QQ.Head;
        BaseConfig.End = Minecraft_QQ.End;
        logs.Group_log = Minecraft_QQ.Group;
        logs.Send_log = Minecraft_QQ.Send;
    }

    public static void reload() {
        ConfigManager.sync("minecraft_qq", Config.Type.INSTANCE);
    }

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

        @Config.Comment("是否记录群发来的消息")
        @Config.Name("Group")
        public boolean Group = true;

        @Config.Comment("是否记录发送至群的消息")
        @Config.Name("Send")
        public boolean Send = true;

        @Config.Comment("不参与聊天玩家")
        @Config.Name("PlayerMute")
        public Map<String, Boolean> PlayerMute = new HashMap<>();

        @Config.Comment("配置文件版本号")
        @Config.Name("Version")
        public String Version = Forge.VERSION;
    }
}