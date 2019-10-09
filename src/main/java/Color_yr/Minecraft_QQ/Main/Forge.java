package Color_yr.Minecraft_QQ.Main;

import Color_yr.Minecraft_QQ.Command.Forge_;
import Color_yr.Minecraft_QQ.Config.Bukkit_;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Log.Log_f;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Socket.socket;
import Color_yr.Minecraft_QQ.Socket.socket_start;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Forge.MODID,
        name = Forge.NAME,
        version = Forge.VERSION)
public class Forge {
    public static final String MODID = "minecraft_qq";
    public static final String NAME = "Minecraft_QQ";
    public static final String VERSION = config.Version;

    public static Logger logger;

    @EventHandler
    @SideOnly(Side.SERVER)
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        config.ilog = new Log_f();
        logs.file = new File(event.getModConfigurationDirectory(), "Minecraft_QQ.log");

        if (!logs.file.exists()) {
            try {
                logs.file.createNewFile();
            } catch (Exception e) {
                logger.warn("§d[Minecraft_QQ]§c日志文件错误：" + e.getMessage());
            }
        }

        Color_yr.Minecraft_QQ.Config.Forge_ config_read = new Color_yr.Minecraft_QQ.Config.Forge_();
        config_read.init();
    }

    @SideOnly(Side.SERVER)
    @EventHandler
    public void init(FMLServerStartingEvent event) {

        event.registerServerCommand(new Forge_());
        socket.iMessage = new Color_yr.Minecraft_QQ.Message.Forge_();
        socket_start socket = new socket_start();

        logger.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
        socket.socket_start();
        logger.info("§d[Minecraft_QQ]§e已启动-" + VERSION);
        logger.info("§d[Minecraft_QQ]§eDebug模式" + Bukkit_.System_Debug);
    }

    @SideOnly(Side.SERVER)
    @EventHandler
    public void stop(FMLServerStoppingEvent event) {
        socket.server_close();
        logger.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
