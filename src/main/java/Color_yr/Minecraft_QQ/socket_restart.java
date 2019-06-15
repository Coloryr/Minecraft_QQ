package Color_yr.Minecraft_QQ;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class socket_restart {
    static boolean is_restart = false;

    public boolean socket_restart_start() {
        config.log.info("§d[Minecraft_QQ]§5正在连接酷Q");
        if (logs.Socket_log == true) {
            logs logs = new logs();
            logs.log_write("[socket]正在连接酷Q");
        }
        try {
            socket.socket = new Socket(config_bukkit.System_IP, config_bukkit.System_PORT);

            socket.socket_runFlag = true;
            config.log.info("§d[Minecraft_QQ]§5酷Q已连接");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q已连接");
            }
            is_restart = false;
            socket.socket_first = false;
            return true;
        } catch (UnknownHostException e) {
            config.log.warning("§d[Minecraft_QQ]§c酷Q连接失败");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q连接失败");
            }
            if (logs.Error_log == true) {
                logs logs = new logs();
                logs.log_write("[ERROR]" + e.getMessage());
            }
            is_restart = false;
            socket.socket_runFlag = false;
            socket.socket_first = false;
            return false;
        } catch (IOException e) {
            config.log.warning("§d[Minecraft_QQ]§c酷Q连接失败");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q连接失败");
            }
            if (logs.Error_log == true) {
                logs logs = new logs();
                logs.log_write("[ERROR]" + e.getMessage());
            }
            is_restart = false;
            socket.socket_runFlag = false;
            socket.socket_first = false;
            return false;
        }
    }

    public boolean restart_socket() {
        if (is_restart != true) {
            socket.socket_first = true;
            socket.socket_stop();
            socket.readThread = new socket();
            socket.readThread.start();
            return socket_restart_start();
        } else {
            config.log.warning("§d[Minecraft_QQ]§c酷Q连接失败");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q连接失败");
            }
            return false;
        }
    }
}
