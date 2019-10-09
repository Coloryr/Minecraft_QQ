package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Config.Bukkit_;
import Color_yr.Minecraft_QQ.Log.logs;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class socket_restart {
    static boolean is_restart = false;

    public boolean socket_restart_start() {
            config.ilog.Log_System("§d[Minecraft_QQ]§5正在连接酷Q");
        if (logs.Socket_log == true) {
            logs logs = new logs();
            logs.log_write("[socket]正在连接酷Q");
        }
        try {
            socket.hand.socket = new Socket(Bukkit_.System_IP, Bukkit_.System_PORT);

            socket.hand.socket_runFlag = true;
                config.ilog.Log_System("§d[Minecraft_QQ]§5酷Q已连接");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q已连接");
            }
            is_restart = false;
            socket.hand.socket_first = false;
            return true;
        } catch (UnknownHostException e) {
                config.ilog.Log_System("§d[Minecraft_QQ]§c酷Q连接失败");
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
                config.ilog.Log_System("§d[Minecraft_QQ]§c酷Q连接失败");
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
            try {
                if(!socket.hand.socket.isClosed())
                socket.hand.socket.close();
            } catch (IOException e) {
                if (logs.Error_log) {
                    logs logs = new logs();
                    logs.log_write("[ERROR]" + e.getMessage());
                }
            }
            socket.hand.readThread = new socket();
            socket.hand.readThread.start();
            return socket_restart_start();
        } else {
                config.ilog.Log_System("§d[Minecraft_QQ]§c酷Q连接失败");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q连接失败");
            }
            return false;
        }
    }
}
