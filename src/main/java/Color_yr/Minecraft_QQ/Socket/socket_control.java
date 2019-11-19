package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Config.Base_config;
import Color_yr.Minecraft_QQ.Config.use;

import java.net.Socket;

public class socket_control {

    public void socket_start() {
        if (socket_connet()) {
            use.hand.readThread = new socket_read_t();
            use.hand.readThread.start();
            use.hand.socket_runFlag = true;
        } else {
            use.hand.socket_runFlag = false;
            new socket_restart();
        }
    }

    public void socket_close() {
        try {
            use.hand.socket_stop = true;
            use.hand.socket_runFlag = false;
            if (use.hand.socket != null && !use.hand.socket.isClosed()) {
                use.hand.socket.close();
            }
            if (use.hand.readThread != null && use.hand.readThread.isAlive()) {
                use.hand.readThread.stop();
            }
            if (Base_config.System_Debug)
                use.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]线程已关闭");
            if (use.hand.pw != null) use.hand.pw.close();
            if (use.hand.os != null) use.hand.os.close();
            if (use.hand.is != null) use.hand.is.close();
            if (use.hand.socket != null) use.hand.socket.close();
            use.hand.socket_runFlag = false;
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public boolean socket_connet() {
        use.ilog.Log_System("§d[Minecraft_QQ]§5正在连接酷Q");
        try {
            use.hand.socket = new Socket(Base_config.System_IP, Base_config.System_PORT);
            use.ilog.Log_System("§d[Minecraft_QQ]§5酷Q已连接");
            return true;
        } catch (Exception e) {
            use.ilog.Log_System("§d[Minecraft_QQ]§c酷Q连接失败");
            return false;
        }
    }
}
