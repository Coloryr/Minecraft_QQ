package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.Command.CommandBC;
import Color_yr.Minecraft_QQ.Listener.BCEvent;
import Color_yr.Minecraft_QQ.Side.IBungeecord;
import Color_yr.Minecraft_QQ.bStats.MetricsBC;
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

        Minecraft_QQ.Side = new IBungeecord();
        new Minecraft_QQ().init(plugin.getDataFolder());

        ProxyServer.getInstance().getPluginManager().registerListener(this, new BCEvent());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CommandBC());

        new MetricsBC(this, 6608);

        Minecraft_QQ.start();
    }

    @Override
    public void onDisable() {
        Minecraft_QQ.stop();
    }
}
