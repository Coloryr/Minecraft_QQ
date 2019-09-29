package Color_yr.Minecraft_QQ.Main;

import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Socket.socket;
import Color_yr.Minecraft_QQ.Log.logs;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Bukkit extends JavaPlugin {

    public static Plugin Minecraft_QQ;

    @Override
    public void onEnable() {
        Minecraft_QQ = this;
        config.log_b = org.bukkit.Bukkit.getLogger();
        config.is_bungee = false;
        config.log_b.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
        Color_yr.Minecraft_QQ.Load.Bukkit config_l = new Color_yr.Minecraft_QQ.Load.Bukkit();
        config_l.setConfig(Minecraft_QQ);
        config.read_thread = new Color_yr.Minecraft_QQ.Message.Bukkit();
        config.read_thread.start();
        org.bukkit.Bukkit.getPluginManager().registerEvents(new Color_yr.Minecraft_QQ.Event.Bukkit(), this);
        org.bukkit.Bukkit.getPluginCommand("qq").setExecutor(new Color_yr.Minecraft_QQ.Command.Bukkit(this));
        config.log_b.info("§d[Minecraft_QQ]§e已启动-" + config.Version);
        config.log_b.info("§d[Minecraft_QQ]§eDebug模式" + Color_yr.Minecraft_QQ.Config.Bukkit.System_Debug);
        socket socket = new socket();
        socket.socket_start();
    }

    @Override
    public void onDisable() {
        if (socket.hand.socket_runFlag == true) {
            try {
                socket.server_close();
                if (config.read_thread.isAlive()) {
                    config.read_thread.stop();
                }
            } catch (Exception e) {
                e.getMessage();
                if (logs.Error_log == true) {
                    logs logs = new logs();
                    logs.log_write("[ERROR]" + e.getMessage());
                }
            }
        }
        config.log_b.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}
