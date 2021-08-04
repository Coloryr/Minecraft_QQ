package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.side.bc.CommandBC;
import Color_yr.Minecraft_QQ.side.bc.EventBC;
import Color_yr.Minecraft_QQ.side.bc.BCLog;
import Color_yr.Minecraft_QQ.side.bc.MetricsBC;
import Color_yr.Minecraft_QQ.side.bc.SideBC;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public class Minecraft_QQBC extends Plugin {

    public static Logger log_b;
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        log_b = ProxyServer.getInstance().getLogger();

        Minecraft_QQ.log = new BCLog(log_b);
        Minecraft_QQ.Side = new SideBC();
        new Minecraft_QQ().init(plugin.getDataFolder());

        ProxyServer.getInstance().getPluginManager().registerListener(this, new EventBC());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CommandBC());

        new MetricsBC(this, 6608);

        Minecraft_QQ.start();
    }

    @Override
    public void onDisable() {
        Minecraft_QQ.stop();
    }
}
