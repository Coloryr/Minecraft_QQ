package Color_yr.Minecraft_QQ.Main;

import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Socket.socket;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(modid = Forge.MODID,
        name = Forge.NAME,
        version = Forge.VERSION)
public class Forge {
    public static final String MODID = "minecraft_qq";
    public static final String NAME = "Minecraft_QQ";
    public static final String VERSION = "2.1.0";

    public static Logger logger;

    @EventHandler
    @SideOnly(Side.SERVER)
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        new Color_yr.Minecraft_QQ.Config.Forge(event);
        config.is_forge = true;
    }

    @SideOnly(Side.SERVER)
    @EventHandler
    public void init(FMLServerStartingEvent event)
    {
        logger.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
        event.registerServerCommand(new Color_yr.Minecraft_QQ.Command.Forge());
        config.read_thread = new Color_yr.Minecraft_QQ.Message.Forge();
        config.read_thread.start();
        logger.info("§d[Minecraft_QQ]§e已启动-" + config.Version);
        logger.info("§d[Minecraft_QQ]§eDebug模式" + Color_yr.Minecraft_QQ.Config.Bukkit.System_Debug);
        socket socket = new socket();
        socket.socket_start();
    }

    @SideOnly(Side.SERVER)
    @EventHandler
    public void stop(FMLServerStoppingEvent event)
    {
        if (socket.hand.socket_runFlag == true) {
            try {
                socket.server_close();
                if(config.read_thread.isAlive()) {
                    config.read_thread.stop();
                }
            } catch(Exception e) {
                e.getMessage();
                if (logs.Error_log == true) {
                    logs logs = new logs();
                    logs.log_write("[ERROR]" + e.getMessage());
                }
            }
        }
        logger.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
