package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.Side.SideBukkit.CommandBukkit;
import Color_yr.Minecraft_QQ.Side.SideBukkit.EventBukkit;
import Color_yr.Minecraft_QQ.Side.SideBukkit.BukkitLog;
import Color_yr.Minecraft_QQ.Side.SideBukkit.MetricsBukkit;
import Color_yr.Minecraft_QQ.Side.SideBukkit.SideBukkit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Minecraft_QQBukkit extends JavaPlugin {

    public static Plugin plugin;
    public static Logger log_b;
    public static boolean PAPI = false;

    @Override
    public void onEnable() {
        plugin = this;
        log_b = getLogger();
        Minecraft_QQ.log = new BukkitLog(log_b);
        Minecraft_QQ.Side = new SideBukkit();
        new Minecraft_QQ().init(getDataFolder());

        if (!Minecraft_QQ.Config.getServerSet().isBungeeCord()) {
            Bukkit.getPluginManager().registerEvents(new EventBukkit(), this);
        }

        Bukkit.getPluginCommand("qq").setExecutor(new CommandBukkit());
        Bukkit.getPluginCommand("qq").setTabCompleter(new CommandBukkit());

        new MetricsBukkit(this, 6608);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PAPI = true;
            log_b.info("§2PAPI支持已启动");
        } else {
            log_b.info("§2PAPI未挂钩");
            PAPI = false;
        }

        Minecraft_QQ.start();
    }

    @Override
    public void onDisable() {
        Minecraft_QQ.stop();
    }
}
