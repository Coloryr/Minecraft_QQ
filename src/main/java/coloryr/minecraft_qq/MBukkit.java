package coloryr.minecraft_qq;

import coloryr.minecraft_qq.side.bukkit.Command;
import coloryr.minecraft_qq.side.bukkit.EventListener;
import coloryr.minecraft_qq.side.bukkit.Log;
import coloryr.minecraft_qq.side.bukkit.Metrics;
import coloryr.minecraft_qq.side.bukkit.Side;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class MBukkit extends JavaPlugin {

    public static Plugin plugin;
    public static Logger log;
    public static boolean PAPI = false;

    @Override
    public void onEnable() {
        plugin = this;
        log = getLogger();
        Minecraft_QQ.log = new Log(log);
        Minecraft_QQ.side = new Side();
        new Minecraft_QQ().init(getDataFolder());

        if (!Minecraft_QQ.config.ServerSet.BungeeCord) {
            Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        }

        Bukkit.getPluginCommand("qq").setExecutor(new Command());
        Bukkit.getPluginCommand("qq").setTabCompleter(new Command());

        new Metrics(this, 6608);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PAPI = true;
            log.info("§2PAPI支持已启动");
        } else {
            log.info("§2PAPI未挂钩");
            PAPI = false;
        }

        Minecraft_QQ.start();
    }

    @Override
    public void onDisable() {
        Minecraft_QQ.stop();
    }
}
