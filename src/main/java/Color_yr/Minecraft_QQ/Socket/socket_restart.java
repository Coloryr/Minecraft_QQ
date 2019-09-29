package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Config.Bukkit;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Main.Forge;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class socket_restart {
    static boolean is_restart = false;

    public boolean socket_restart_start() {
        if (config.is_forge == true)
            Forge.logger.info("§d[Minecraft_QQ]§5正在连接酷Q");
        else
            config.log_b.info("§d[Minecraft_QQ]§5正在连接酷Q");
        if (logs.Socket_log == true) {
            logs logs = new logs();
            logs.log_write("[socket]正在连接酷Q");
        }
        try {
            socket.hand.socket = new Socket(Bukkit.System_IP, Bukkit.System_PORT);

            socket.hand.socket_runFlag = true;
            if (config.is_forge == true)
                Forge.logger.info("§d[Minecraft_QQ]§5酷Q已连接");
            else
                config.log_b.info("§d[Minecraft_QQ]§5酷Q已连接");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q已连接");
            }
            is_restart = false;
            socket.hand.socket_first = false;
            return true;
        } catch (UnknownHostException e) {
            if (config.is_forge == true)
                Forge.logger.warn("§d[Minecraft_QQ]§c酷Q连接失败");
            else
                config.log_b.warning("§d[Minecraft_QQ]§c酷Q连接失败");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q连接失败");
            }
            if (logs.Error_log == true) {
                logs logs = new logs();
                logs.log_write("[ERROR]" + e.getMessage());
            }
            is_restart = false;
            socket.hand.socket_runFlag = false;
            socket.hand.socket_first = false;
            return false;
        } catch (IOException e) {
            if (config.is_forge == true)
                Forge.logger.warn("§d[Minecraft_QQ]§c酷Q连接失败");
            else
                config.log_b.warning("§d[Minecraft_QQ]§c酷Q连接失败");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q连接失败");
            }
            if (logs.Error_log == true) {
                logs logs = new logs();
                logs.log_write("[ERROR]" + e.getMessage());
            }
            is_restart = false;
            socket.hand.socket_runFlag = false;
            socket.hand.socket_first = false;
            return false;
        }
    }

    public boolean restart_socket() {
        if (is_restart != true) {
            socket.hand.socket_first = true;
            socket.socket_stop();
            socket.hand.readThread = new socket();
            socket.hand.readThread.start();
            return socket_restart_start();
        } else {
            if (config.is_forge == true)
                Forge.logger.warn("§d[Minecraft_QQ]§c酷Q连接失败");
            else
                config.log_b.warning("§d[Minecraft_QQ]§c酷Q连接失败");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q连接失败");
            }
            return false;
        }
    }
}
