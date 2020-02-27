package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.Command.CommandForge;
import Color_yr.Minecraft_QQ.Config.Load;
import Color_yr.Minecraft_QQ.Side.IForge;
import Color_yr.Minecraft_QQ.Socket.SocketControl;
import Color_yr.Minecraft_QQ.Utils.logs;
import com.google.gson.Gson;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.io.*;

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
            "    \"OnlinePlayerShow\": true,\n" +
            "    \"SendOneByOne\": true,\n" +
            "    \"SendOneByOneMessage\": \"\\n[%Server%-%player_number%]-%player_list%\",\n" +
            "    \"HideEmptyServer\": true,\n" +
            "    \"PlayerListMessage\": \"%ServerName%当前在线人数：%PlayerNumber%，玩家列表：%PlayerList%\",\n" +
            "    \"HidePlayerList\": false,\n" +
            "    \"ServerOnlineMessage\": \"%ServerName%服务器在线\"\n" +
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
            "  \"Version\": \"2.3.0\"\n" +
            "}";

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

        try {
            new logs(self);
        } catch (IOException e) {
            logger.error("§d[Minecraft_QQ]§c日志文件错误");
            e.printStackTrace();
        }

        Load();

    }

    @SideOnly(Side.SERVER)
    @EventHandler
    public void init(FMLServerStartingEvent event) {

        event.registerServerCommand(new CommandForge());
        SocketControl socket = new SocketControl();

        logger.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
        socket.Start();
        logger.info("§d[Minecraft_QQ]§e已启动-" + VERSION);
        logger.info("§d[Minecraft_QQ]§eDebug模式" + Minecraft_QQ.Config.getSystem().isDebug());
    }

    @SideOnly(Side.SERVER)
    @EventHandler
    public void stop(FMLServerStoppingEvent event) {
        SocketControl socket = new SocketControl();
        socket.Close();
        logger.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
