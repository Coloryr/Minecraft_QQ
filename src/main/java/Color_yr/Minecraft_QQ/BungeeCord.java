package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.Command.IBungeeCord;
import Color_yr.Minecraft_QQ.Config.bc_config;
import Color_yr.Minecraft_QQ.Config.use;
import Color_yr.Minecraft_QQ.Config.Base_config;
import Color_yr.Minecraft_QQ.Listener.bc_event;
import Color_yr.Minecraft_QQ.Load.bc_load;
import Color_yr.Minecraft_QQ.Log.Log_b;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Message.bc_r;
import Color_yr.Minecraft_QQ.Socket.socket_control;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public class BungeeCord extends Plugin {

    public static bc_config config_data_bungee;
    public static Logger log_b;
    public static Plugin Plugin;

    @Override
    public void onEnable() {

        Plugin = this;
        log_b = ProxyServer.getInstance().getLogger();

        config_data_bungee = new bc_config();
        use.iMessage = new bc_r();
        use.F_Log = new logs();
        use.ilog = new Log_b();
        bc_load start = new bc_load();

        log_b.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");

        start.setConfig();

        ProxyServer.getInstance().getPluginManager().registerListener(this, new bc_event());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new IBungeeCord());

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
