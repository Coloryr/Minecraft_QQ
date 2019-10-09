package Color_yr.Minecraft_QQ.Main;

import Color_yr.Minecraft_QQ.Config.Bukkit_;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Log.Log_s;
import Color_yr.Minecraft_QQ.Log.logs;
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

        config.ilog = new Log_s();
        config.F_Log = new logs();
        Color_yr.Minecraft_QQ.Load.Bukkit_ config_l = new Color_yr.Minecraft_QQ.Load.Bukkit_();
        config.iMessage = new Color_yr.Minecraft_QQ.Message.Bukkit_();

        log_b.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");

        config_l.setConfig(Minecraft_QQ);

        org.bukkit.Bukkit.getPluginManager().registerEvents(new Color_yr.Minecraft_QQ.Event.Bukkit_(), this);
        org.bukkit.Bukkit.getPluginCommand("qq").setExecutor(new Color_yr.Minecraft_QQ.Command.Bukkit_(this));

        log_b.info("§d[Minecraft_QQ]§e已启动-" + config.Version);
        log_b.info("§d[Minecraft_QQ]§eDebug模式" + Bukkit_.System_Debug);

        socket_control socket = new socket_control();
        socket.socket_start();
    }

    @Override
    public void onDisable() {
        socket_control socket = new socket_control();
        socket.socket_close();
        log_b.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
