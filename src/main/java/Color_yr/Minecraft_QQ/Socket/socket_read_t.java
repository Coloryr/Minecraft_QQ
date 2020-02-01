package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Config.BaseConfig;

public class socket_read_t extends Thread {

    @Override
    public void run() {
        use.hand.socket_stop = false;
        while (true) {
            try {
                if (use.hand.socket_runFlag) {
                    if (use.hand.socket.isClosed()) {
                        use.hand.socket_runFlag = false;
                        new socket_restart();
                        return;
                    } else {
                        use.hand.is = use.hand.socket.getInputStream();
                        int len = use.hand.is.read(use.hand.buf);
                        if (len <= 0) {
                            use.MinecraftQQ.Log_System("§d[Minecraft_QQ]§c酷Q连接中断");
                            use.hand.socket_runFlag = false;
                            new socket_restart();
                            return;
                        } else {
                            String a = new String(use.hand.buf, 0, len);
                            if (!a.isEmpty()) {
                                if (BaseConfig.SystemDebug)
                                    use.MinecraftQQ.Log_System("§d[Minecraft_QQ]§5[Debug]收到数据：" + a);
                                use.MinecraftQQ.Message(a);
                            }
                        }
                    }
                }
                Thread.sleep(BaseConfig.SystemSleep);
            } catch (Exception e) {
                use.MinecraftQQ.Log_System("§d[Minecraft_QQ]§c酷Q连接中断");
                use.hand.socket_runFlag = false;
                new socket_restart();
                return;
            }
        }
    }
}
