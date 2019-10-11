package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Config.Bukkit_;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Log.logs;

public class socket_restart extends Thread {

    private static boolean is_restart = false;

    public socket_restart() {
        if (!is_restart) this.start();
    }

    @Override
    public void run() {
        if (!config.hand.socket_runFlag
                && !config.hand.server_isclose
                && Bukkit_.System_AutoConnet
                && !is_restart) {
            is_restart = true;
            if (config.hand.readThread != null) {
                while (config.hand.readThread.isAlive()) ;
            }
            config.ilog.Log_System("§d[Minecraft_QQ]§5正在进行自动重连");
            while (is_restart) {
                try {
                    if (config.hand.socket != null && !config.hand.socket.isClosed())
                        config.hand.socket.close();
                    socket_control socket = new socket_control();
                    if (socket.socket_connet()) {
                        config.hand.readThread = new socket_read_t();
                        config.hand.readThread.start();
                        config.hand.socket_runFlag = true;
                        is_restart = false;
                    } else {
                        config.hand.socket_runFlag = false;
                    }
                    Thread.sleep(Bukkit_.System_AutoConnetTime);
                } catch (Exception e) {
                    logs.log_write(e.getMessage());
                }
            }
        }
    }
}
