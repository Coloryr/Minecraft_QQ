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
import net.minecraftforge.fml.common.SidedProxy;
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
        version = Minecraft_QQForge.VERSION,
        serverSideOnly = true)
public class Minecraft_QQForge {
    public static final String MODID = "minecraft_qq";
    public static final String NAME = "Minecraft_QQ";
    public static final String VERSION = Minecraft_QQ.Version;

    public static Logger logger;
    private static File self;

    public static void Load() {
        try {
            new Load(self);
        } catch (Throwable e) {
            logger.error("§d[Minecraft_QQ]§c配置文件读取发生错误");
            e.printStackTrace();
        }
    }

    public static void Save() {
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
        self = new File(event.getModConfigurationDirectory().getPath() + "/Minecraft_QQ");

        Load();

        try {
            new logs(self);
            File wiki = new File(self, "Wiki.txt");
            if (!wiki.exists()) {
                Files.copy(new ByteArrayInputStream(Minecraft_QQ.Wiki.getBytes()), wiki.toPath());
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
