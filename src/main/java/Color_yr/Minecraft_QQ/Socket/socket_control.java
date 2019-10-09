package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Config.Bukkit_;
import Color_yr.Minecraft_QQ.Config.config;

import java.net.Socket;

public class socket_control {

    public void socket_start() {
        if (socket_connet()) {
            config.hand.readThread = new socket_read_t();
            config.hand.readThread.start();
            config.hand.socket_runFlag = true;
        } else {
            config.hand.socket_runFlag = false;
            new socket_restart();
        }
    }

    public void socket_close() {
        if (config.hand.socket_runFlag) {
            try {
                config.hand.socket_stop = true;
                config.hand.socket_runFlag = false;
                if (config.hand.socket != null && !config.hand.socket.isClosed()) {
                    config.hand.socket.close();
                }
                if (config.hand.readThread!=null && config.hand.readThread.isAlive())
                {
                    config.hand.readThread.stop();
                }
                if (Bukkit_.System_Debug)
                    config.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]线程已关闭");
                if (config.hand.pw != null) config.hand.pw.close();
                if (config.hand.os != null) config.hand.os.close();
                if (config.hand.is != null) config.hand.is.close();
                if (config.hand.socket != null) config.hand.socket.close();
                config.hand.socket_runFlag = false;
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public boolean socket_connet()
    {
        config.ilog.Log_System("§d[Minecraft_QQ]§5正在连接酷Q");
        try {
            config.hand.socket = new Socket(Bukkit_.System_IP, Bukkit_.System_PORT);
            config.ilog.Log_System("§d[Minecraft_QQ]§5酷Q已连接");
            return true;
        } catch (Exception e) {
            config.ilog.Log_System("§d[Minecraft_QQ]§c酷Q连接失败");
            return false;
        }
    }
}
