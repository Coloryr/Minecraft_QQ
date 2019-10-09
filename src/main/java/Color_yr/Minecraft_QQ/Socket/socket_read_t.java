package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Config.Bukkit_;

public class socket_read_t extends Thread {

    @Override
    public void run() {
        config.hand.socket_stop = false;
        while (true) {
            try {
                if (config.hand.socket_runFlag) {
                    if (config.hand.socket.isClosed()) {
                        config.hand.socket_runFlag = false;
                        new socket_restart();
                        return;
                    } else {
                        config.hand.is = config.hand.socket.getInputStream();
                        int len = config.hand.is.read(config.hand.buf);
                        if (len <= 0) {
                            config.ilog.Log_System("§d[Minecraft_QQ]§c酷Q连接中断");
                            config.hand.socket_runFlag = false;
                            new socket_restart();
                            return;
                        } else {
                            String a = new String(config.hand.buf, 0, len);
                            if (!a.isEmpty()) {
                                if (Bukkit_.System_Debug)
                                    config.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]收到数据：" + a);
                                config.iMessage.Message(a);
                            }
                        }
                    }
                }
                Thread.sleep(2);
            } catch (Exception e) {
                config.ilog.Log_System("§d[Minecraft_QQ]§c酷Q连接中断");
                config.hand.socket_runFlag = false;
                new socket_restart();
                return;
            }
        }
    }
}
