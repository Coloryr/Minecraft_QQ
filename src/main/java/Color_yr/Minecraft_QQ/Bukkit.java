package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.Command.IBukkit;
import Color_yr.Minecraft_QQ.Config.Base_config;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Listener.bukkit_event;
import Color_yr.Minecraft_QQ.Load.bukkit_load;
import Color_yr.Minecraft_QQ.Log.Log_s;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Message.bukkit_r;
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

        use.ilog = new Log_s();
        use.F_Log = new logs();
        bukkit_load config_l = new bukkit_load();
        use.iMessage = new bukkit_r();

        log_b.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");

        config_l.setConfig(Minecraft_QQ);

        org.bukkit.Bukkit.getPluginManager().registerEvents(new bukkit_event(), this);
        org.bukkit.Bukkit.getPluginCommand("qq").setExecutor(new IBukkit(this));

        log_b.info("§d[Minecraft_QQ]§e已启动-" + use.Version);
        log_b.info("§d[Minecraft_QQ]§eDebug模式" + Base_config.System_Debug);

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
