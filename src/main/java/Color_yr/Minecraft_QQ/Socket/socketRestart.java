package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Utils.logs;

public class socketRestart extends Thread {

    private static boolean isRestart = false;

    public socketRestart() {
        if (!isRestart) this.start();
    }

    @Override
    public void run() {
        if (!Minecraft_QQ.hand.socketIsRun
                && !Minecraft_QQ.hand.server_isclose
                && Minecraft_QQ.Config.getSystem().isAutoConnect()
                && !isRestart) {
            isRestart = true;
            if (Minecraft_QQ.hand.readThread != null) {
                while (Minecraft_QQ.hand.readThread.isAlive()) ;
            }
            Minecraft_QQ.MinecraftQQ.LogInfo("§d[Minecraft_QQ]§5正在进行自动重连");
            while (isRestart) {
                try {
                    if (Minecraft_QQ.hand.socket != null && !Minecraft_QQ.hand.socket.isClosed())
                        Minecraft_QQ.hand.socket.close();
                    SocketControl socket = new SocketControl();
                    if (socket.socketConnet()) {
                        Minecraft_QQ.hand.readThread = new socketRead();
                        Minecraft_QQ.hand.readThread.start();
                        Minecraft_QQ.hand.socketIsRun = true;
                        isRestart = false;
                    } else {
                        Minecraft_QQ.hand.socketIsRun = false;
                    }
                    Thread.sleep(Minecraft_QQ.Config.getSystem().getAutoConnectTime());
                } catch (Exception e) {
                    logs.logWrite(e.getMessage());
                }
            }
        }
    }
}
