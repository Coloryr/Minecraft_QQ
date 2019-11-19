package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Config.Base_config;
import Color_yr.Minecraft_QQ.Config.use;
import Color_yr.Minecraft_QQ.Log.logs;

public class socket_restart extends Thread {

    private static boolean is_restart = false;

    public socket_restart() {
        if (!is_restart) this.start();
    }

    @Override
    public void run() {
        if (!use.hand.socket_runFlag
                && !use.hand.server_isclose
                && Base_config.System_AutoConnet
                && !is_restart) {
            is_restart = true;
            if (use.hand.readThread != null) {
                while (use.hand.readThread.isAlive()) ;
            }
            use.ilog.Log_System("§d[Minecraft_QQ]§5正在进行自动重连");
            while (is_restart) {
                try {
                    if (use.hand.socket != null && !use.hand.socket.isClosed())
                        use.hand.socket.close();
                    socket_control socket = new socket_control();
                    if (socket.socket_connet()) {
                        use.hand.readThread = new socket_read_t();
                        use.hand.readThread.start();
                        use.hand.socket_runFlag = true;
                        is_restart = false;
                    } else {
                        use.hand.socket_runFlag = false;
                    }
                    Thread.sleep(Base_config.System_AutoConnetTime);
                } catch (Exception e) {
                    logs.log_write(e.getMessage());
                }
            }
        }
    }
}
