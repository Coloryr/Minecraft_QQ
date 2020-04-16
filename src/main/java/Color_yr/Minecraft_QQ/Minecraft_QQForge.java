package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.Command.CommandForge;
import Color_yr.Minecraft_QQ.Listener.ForgeEvent;
import Color_yr.Minecraft_QQ.Side.IForge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Minecraft_QQForge.MODID,
        name = Minecraft_QQForge.NAME,
        version = Minecraft_QQForge.VERSION,
        serverSideOnly = true,
        acceptableRemoteVersions = "*")
public class Minecraft_QQForge {
    public static final String MODID = "minecraft_qq";
    public static final String NAME = "Minecraft_QQ";
    public static final String VERSION = Minecraft_QQ.Version;

    public static Logger logger;

    @EventHandler
    @SideOnly(Side.SERVER)
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        Minecraft_QQ.MinecraftQQ = new IForge();
        File self = new File(event.getModConfigurationDirectory().getPath() + "/Minecraft_QQ");
        new Minecraft_QQ().init(self);
    }

    @SideOnly(Side.SERVER)
    @EventHandler
    public void init(FMLServerStartingEvent event) {

        if (!Minecraft_QQ.Config.getServerSet().isBungeeCord()) {
            MinecraftForge.EVENT_BUS.register(new ForgeEvent());
            event.registerServerCommand(new CommandForge());
        }

        Minecraft_QQ.start();
    }

    @SideOnly(Side.SERVER)
    @EventHandler
    public void stop(FMLServerStoppingEvent event) {
        Minecraft_QQ.stop();
    }
}
