package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
import Color_yr.Minecraft_QQ.Listener.bukkit_event;
import Color_yr.Minecraft_QQ.Load.bukkit_load;
import Color_yr.Minecraft_QQ.Socket.socket_control;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Bukkit extends JavaPlugin {

    public static Plugin Minecraft_QQ;
    public static Logger log_b;

    @Override
    public void onEnable() {
        Minecraft_QQ = this;
        log_b = getLogger();

        use.MinecraftQQ = new Color_yr.Minecraft_QQ.Side.IBukkit();

        log_b.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");

        new bukkit_load().setConfig(Minecraft_QQ);

        org.bukkit.Bukkit.getPluginManager().registerEvents(new bukkit_event(), this);
        org.bukkit.Bukkit.getPluginCommand("qq").setExecutor(new Color_yr.Minecraft_QQ.Command.IBukkit(this));

        log_b.info("§d[Minecraft_QQ]§e已启动-" + use.Version);
        log_b.info("§d[Minecraft_QQ]§eDebug模式" + BaseConfig.SystemDebug);

        socket_control socket = new socket_control();
        socket.socket_start();
    }

    @Override
    public void onDisable() {
        new socket_control().socket_close();
        log_b.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
