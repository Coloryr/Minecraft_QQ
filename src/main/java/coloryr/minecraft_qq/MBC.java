package coloryr.minecraft_qq;

import coloryr.minecraft_qq.side.bc.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public class MBC extends Plugin {

    public static Logger log;
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        log = ProxyServer.getInstance().getLogger();

        Minecraft_QQ.log = new Log(log);
        Minecraft_QQ.side = new Side();
        new Minecraft_QQ().init(plugin.getDataFolder());

        ProxyServer.getInstance().getPluginManager().registerListener(this, new EventListener());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command());

        new Metrics(this, 6608);

        Minecraft_QQ.start();
    }

    @Override
    public void onDisable() {
        Minecraft_QQ.stop();
    }
}
