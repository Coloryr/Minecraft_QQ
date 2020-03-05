package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Minecraft_QQ;

public class socketRead extends Thread {

    @Override
    public void run() {
        Minecraft_QQ.hand.socket_stop = false;
        while (true) {
            try {
                if (Minecraft_QQ.hand.socketIsRun) {
                    if (Minecraft_QQ.hand.socket.isClosed()) {
                        Minecraft_QQ.hand.socketIsRun = false;
                        new socketRestart();
                        return;
                    } else {
                        Minecraft_QQ.hand.is = Minecraft_QQ.hand.socket.getInputStream();
                        int len = Minecraft_QQ.hand.is.read(Minecraft_QQ.hand.buf);
                        if (len <= 0) {
                            Minecraft_QQ.MinecraftQQ.LogInfo("§d[Minecraft_QQ]§c酷Q连接中断");
                            Minecraft_QQ.hand.socketIsRun = false;
                            new socketRestart();
                            return;
                        } else {
                            String a = new String(Minecraft_QQ.hand.buf, 0, len);
                            if (!a.isEmpty()) {
                                if (Minecraft_QQ.Config.getSystem().isDebug())
                                    Minecraft_QQ.MinecraftQQ.LogInfo("§d[Minecraft_QQ]§5[Debug]收到数据：" + a);
                                Minecraft_QQ.MinecraftQQ.Message(a);
                            }
                        }
                    }
                }
                Thread.sleep(Minecraft_QQ.Config.getSystem().getSleep());
            } catch (Exception e) {
                Minecraft_QQ.MinecraftQQ.LogInfo("§d[Minecraft_QQ]§c酷Q连接中断");
                Minecraft_QQ.hand.socketIsRun = false;
                if(!Minecraft_QQ.hand.server_isclose)
                    new socketRestart();
                return;
            }
        }
    }
}
