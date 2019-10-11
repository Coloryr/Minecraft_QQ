package Color_yr.Minecraft_QQ.Main;

import Color_yr.Minecraft_QQ.Config.BungeeCord_;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Config.Bukkit_;
import Color_yr.Minecraft_QQ.Log.Log_b;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Socket.socket_control;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public class BungeeCord extends Plugin {

    public static BungeeCord_ config_data_bungee;
    public static Logger log_b;
    public static Plugin Plugin;

    @Override
    public void onEnable() {

        Plugin = this;
        log_b = ProxyServer.getInstance().getLogger();

        config_data_bungee = new BungeeCord_();
        config.iMessage = new Color_yr.Minecraft_QQ.Message.BungeeCord_();
        config.F_Log = new logs();
        config.ilog = new Log_b();
        Color_yr.Minecraft_QQ.Load.BungeeCord_ start = new Color_yr.Minecraft_QQ.Load.BungeeCord_();

        log_b.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");

        start.setConfig();

        ProxyServer.getInstance().getPluginManager().registerListener(this, new Color_yr.Minecraft_QQ.Event.BungeeCord_());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Color_yr.Minecraft_QQ.Command.BungeeCord_());

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
