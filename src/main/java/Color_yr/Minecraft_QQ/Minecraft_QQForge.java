package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.Command.CommandForge;
import Color_yr.Minecraft_QQ.Config.Load;
import Color_yr.Minecraft_QQ.Listener.ForgeEvent;
import Color_yr.Minecraft_QQ.Side.IForge;
import Color_yr.Minecraft_QQ.Socket.SocketControl;
import Color_yr.Minecraft_QQ.Utils.logs;
import com.google.gson.Gson;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;

@Mod(modid = Minecraft_QQForge.MODID,
        name = Minecraft_QQForge.NAME,
        version = Minecraft_QQForge.VERSION)
public class Minecraft_QQForge {
    public static final String MODID = "minecraft_qq";
    public static final String NAME = "Minecraft_QQ";
    public static final String VERSION = Minecraft_QQ.Version;

    private static final String config = "{\n" +
            "  \"Join\": {\n" +
            "    \"Message\": \"%Player%加入了服务器\",\n" +
            "    \"sendQQ\": true\n" +
            "  },\n" +
            "  \"Quit\": {\n" +
            "    \"Message\": \"%Player%退出了服务器\",\n" +
            "    \"sendQQ\": true\n" +
            "  },\n" +
            "  \"ChangeServer\": {\n" +
            "    \"Message\": \"%Player%加入了子服%Server%\",\n" +
            "    \"sendQQ\": true\n" +
            "  },\n" +
            "  \"ServerSet\": {\n" +
            "    \"ServerName\": \"[MC服务器]\",\n" +
            "    \"Check\": \"群：\",\n" +
            "    \"Message\": \"%ServerName%-%Server%-%Player%:%Message%\",\n" +
            "    \"Say\": \"[%ServerName%][群消息]%Message%\",\n" +
            "    \"Mode\": 1,\n" +
            "    \"SendOneByOne\": true,\n" +
            "    \"SendOneByOneMessage\": \"\\n[%Server%-%PlayerNumber%]-%PlayerList%\",\n" +
            "    \"HideEmptyServer\": true,\n" +
            "    \"PlayerListMessage\": \"%ServerName%当前在线人数：%PlayerNumber%，玩家列表：%PlayerList%\",\n" +
            "    \"ServerOnlineMessage\": \"%ServerName%服务器在线\",\n" +
            "    \"BungeeCord\": false\n" +
            "  },\n" +
            "  \"Servers\": {\n" +
            "    \"lobby\": \"登陆大厅\",\n" +
            "    \"server\": \"服务器\"\n" +
            "  },\n" +
            "  \"SendAllServer\": {\n" +
            "    \"Enable\": true,\n" +
            "    \"Message\": \"[%ServerName%-%Server%]玩家：[%Player%]发送群消息：[%Message%]\",\n" +
            "    \"OnlySideServer\": true\n" +
            "  },\n" +
            "  \"System\": {\n" +
            "    \"IP\": \"localhost\",\n" +
            "    \"Port\": 25555,\n" +
            "    \"AutoConnect\": false,\n" +
            "    \"AutoConnectTime\": 10000,\n" +
            "    \"Debug\": false,\n" +
            "    \"Head\": \"[Head]\",\n" +
            "    \"End\": \"[End]\",\n" +
            "    \"Sleep\": 50\n" +
            "  },\n" +
            "  \"User\": {\n" +
            "    \"SendSucceed\": true,\n" +
            "    \"NotSendCommand\": true\n" +
            "  },\n" +
            "  \"Logs\": {\n" +
            "    \"Group\": true,\n" +
            "    \"Server\": true\n" +
            "  },\n" +
            "  \"Placeholder\": {\n" +
            "    \"Message\": \"%Message%\",\n" +
            "    \"Player\": \"%Player%\",\n" +
            "    \"Servername\": \"%ServerName%\",\n" +
            "    \"Server\": \"%Server%\",\n" +
            "    \"PlayerNumber\": \"%PlayerNumber%\",\n" +
            "    \"PlayerList\": \"%PlayerList%\"\n" +
            "  },\n" +
            "  \"Language\": {\n" +
            "    \"MessageOFF\": \"§2你已不会在收到群消息\",\n" +
            "    \"MessageON\": \"§2你开始接受群消息\",\n" +
            "    \"SucceedMessage\": \"§2已发送消息至群内\"\n" +
            "  },\n" +
            "  \"Mute\": [],\n" +
            "  \"Version\": \"2.3.1\"\n" +
            "}";

    private static final String Wiki = "Minecraft_QQ帮助手册\n" +
            "该配置文件是Bukkit/BungeeCord(下面简称BC)/Forge的集合\n" +
            "配置文件说明：\n" +
            "\n" +
            "玩家加入服务器后会往群里发送消息，Forge无这个功能\n" +
            "  \"Join\": {\n" +
            "    \"Message\": \"%Player%加入了服务器\",\n" +
            "    \"sendQQ\": true\n" +
            "  },\n" +
            "\n" +
            "玩家退出服务器后会往群里发送消息，Forge无这个功能\n" +
            "  \"Quit\": {\n" +
            "    \"Message\": \"%Player%退出了服务器\",\n" +
            "    \"sendQQ\": true\n" +
            "  },\n" +
            "\n" +
            "玩家切换子服后会往群里发送消息，仅BC有这个功能\n" +
            "  \"ChangeServer\": {\n" +
            "    \"Message\": \"%Player%加入了子服%Server%\",\n" +
            "    \"sendQQ\": true\n" +
            "  },\n" +
            "\n" +
            "服务器相关设定\n" +
            "  \"ServerSet\": {\n" +
            "    服务器名字设置，用于发送至群\n" +
            "    \"ServerName\": \"[MC服务器]\",\n" +
            "    对话检测头\n" +
            "    \"Check\": \"群：\",\n" +
            "    发送至群的格式，%Server%变量为BC专属，其他端会删掉\n" +
            "    \"Message\": \"%ServerName%-%Server%-%Player%:%Message%\",\n" +
            "    发送至服务器的格式\n" +
            "    \"Say\": \"[%ServerName%][群消息]%Message%\",\n" +
            "    机器人模式，0：不发送玩家消息到群，1：检测是否有检测头，若玩家聊天的第一个字符存在检测头，则会把这句话发送到群，2：无论玩家说什么都会完整的发送到群\n" +
            "    \"Mode\": 1,\n" +
            "    单独显示子服的人数而不是混在一起显示，仅BC有这个功能\n" +
            "    \"SendOneByOne\": true,\n" +
            "    单独显示子服的人数的格式，仅BC有这个功能\n" +
            "    \"SendOneByOneMessage\": \"\\n[%Server%-%player_number%]-%player_list%\",\n" +
            "    单独显示子服的人数的格式，仅BC有这个功能\n" +
            "    \"HideEmptyServer\": true,\n" +
            "    发送在线人数到群的格式\n" +
            "    \"PlayerListMessage\": \"%ServerName%当前在线人数：%PlayerNumber%，玩家列表：%PlayerList%\",\n" +
            "    发送服务器在线到群的格式\n" +
            "    \"ServerOnlineMessage\": \"%ServerName%服务器在线\",\n" +
            "    群组服支持，如果你想要子服执行命令，则所有服务器都要装Minecraft_QQ并且子服开启这个\n" +
            "    开启后，服务器插件只有执行群发来命令的功能\n" +
            "    BungeeCord端开不开都一样\n" +
            "    \"BungeeCord\": false\n" +
            "  },\n" +
            "\n" +
            "子服别名，仅BC有这个功能\n" +
            "  \"Servers\": {\n" +
            "    \"lobby\": \"登陆大厅\",\n" +
            "    \"server\": \"服务器\"\n" +
            "  },\n" +
            "\n" +
            "发送消息到其他子服，仅BC有这个功能\n" +
            "  \"SendAllServer\": {\n" +
            "    \"Enable\": true,\n" +
            "    \"Message\": \"[%ServerName%-%Server%]玩家：[%Player%]发送群消息：[%Message%]\",\n" +
            "    \"OnlySideServer\": true\n" +
            "  },\n" +
            "\n" +
            "插件相关设置\n" +
            "  \"System\": {\n" +
            "     酷Q服务器地址\n" +
            "     \"IP\": \"localhost\",\n" +
            "     酷Q服务器端口\n" +
            "     \"Port\": 25555,\n" +
            "     是否自动重连\n" +
            "     \"AutoConnect\": false,\n" +
            "     自动重连间隔\n" +
            "     \"AutoConnectTime\": 10000,\n" +
            "     调试模式\n" +
            "     \"Debug\": false,\n" +
            "     数据包头\n" +
            "     \"Head\": \"[Head]\",\n" +
            "     数据包尾\n" +
            "     \"End\": \"[End]\",\n" +
            "     线程休眠\n" +
            "     \"Sleep\": 50\n" +
            "  },\n" +
            "\n" +
            "玩家相关设置\n" +
            "  \"User\": {\n" +
            "    发送消息到群后提示玩家\n" +
            "    \"SendSucceed\": true,\n" +
            "    不发送玩家输入的指令到群里\n" +
            "    \"NotSendCommand\": true\n" +
            "  },\n" +
            "\n" +
            "日志相关\n" +
            "  \"Logs\": {\n" +
            "    记录群发来的消息\n" +
            "    \"Group\": true,\n" +
            "    记录发送到服务器的消息\n" +
            "    \"Server\": true\n" +
            "  },\n" +
            "\n" +
            "占位符相关，替换上面的内容\n" +
            "  \"Placeholder\": {\n" +
            "    消息\n" +
            "    \"Message\": \"%Message%\",\n" +
            "    玩家名字\n" +
            "    \"Player\": \"%Player%\",\n" +
            "    服务器名字\n" +
            "    \"Servername\": \"%ServerName%\",\n" +
            "    子服名字\n" +
            "    \"Server\": \"%Server%\",\n" +
            "    玩家数量\n" +
            "    \"PlayerNumber\": \"%PlayerNumber%\",\n" +
            "    玩家列表\n" +
            "    \"PlayerList\": \"%PlayerList%\"\n" +
            "  },\n" +
            "\n" +
            "发送给玩家的内容\n" +
            "  \"Language\": {\n" +
            "    禁用聊天\n" +
            "    \"MessageOFF\": \"§2你已不会在收到群消息\",\n" +
            "    启用聊天\n" +
            "    \"MessageON\": \"§2你开始接受群消息\",\n" +
            "    成功发送消息\n" +
            "    \"SucceedMessage\": \"§2已发送消息至群内\"\n" +
            "  },\n" +
            "\n" +
            "不参与聊天列表\n" +
            "\"Mute\": [],\n" +
            "\n" +
            "配置文件版本号\n" +
            "\"Version\": \"2.3.1\"";

    public static Logger logger;
    private static File self;

    public static void Load()
    {
        try {
            new Load(self, new ByteArrayInputStream(config.getBytes()));
        } catch (Throwable e) {
            logger.error("§d[Minecraft_QQ]§c配置文件读取发生错误");
            e.printStackTrace();
        }
    }

    public static void Save()
    {
        try {
            String data = new Gson().toJson(Minecraft_QQ.Config);
            if (Minecraft_QQ.FileName.exists()) {
                Writer out = new FileWriter(Minecraft_QQ.FileName);
                out.write(data);
                out.close();
            }
        } catch (Exception e) {
            logger.error("§d[Minecraft_QQ]§c配置文件保存错误");
            e.printStackTrace();
        }
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        Minecraft_QQ.MinecraftQQ = new IForge();
        self = event.getModConfigurationDirectory();

        Load();

        try {
            new logs(self);
            File wiki = new File(self, "Wiki.txt");
            if (!wiki.exists()) {
                Files.copy(new ByteArrayInputStream(Wiki.getBytes()), wiki.toPath());
            }
        } catch (IOException e) {
            logger.error("§d[Minecraft_QQ]§c日志文件错误");
            e.printStackTrace();
        }

    }

    @SideOnly(Side.SERVER)
    @EventHandler
    public void init(FMLServerStartingEvent event) {

        if (!Minecraft_QQ.Config.getServerSet().isBungeeCord()) {
            MinecraftForge.EVENT_BUS.register(new ForgeEvent());
            event.registerServerCommand(new CommandForge());
        }
        SocketControl socket = new SocketControl();

        logger.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
        socket.Start();
        logger.info("§d[Minecraft_QQ]§e已启动-" + VERSION);
        logger.info("§d[Minecraft_QQ]§eDebug模式" + Minecraft_QQ.Config.getSystem().isDebug());
    }

    @SideOnly(Side.SERVER)
    @EventHandler
    public void stop(FMLServerStoppingEvent event) {
        Minecraft_QQ.hand.server_isclose = true;
        new SocketControl().Close();
        logger.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
