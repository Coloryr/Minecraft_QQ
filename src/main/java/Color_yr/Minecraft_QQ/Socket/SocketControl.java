package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;

import java.net.Socket;

public class SocketControl {

    public void Start() {
        if (socketConnet()) {
            Minecraft_QQ.hand.readThread = new socketRead();
            Minecraft_QQ.hand.readThread.start();
            Minecraft_QQ.hand.socketIsRun = true;
        } else {
            Minecraft_QQ.hand.socketIsRun = false;
            new socketRestart();
        }
    }

    public void Close() {
        try {
            Minecraft_QQ.hand.socket_stop = true;
            Minecraft_QQ.hand.socketIsRun = false;
            if (Minecraft_QQ.hand.socket != null && !Minecraft_QQ.hand.socket.isClosed()) {
                Minecraft_QQ.hand.socket.close();
            }
            if (Minecraft_QQ.hand.readThread != null && Minecraft_QQ.hand.readThread.isAlive()) {
                Minecraft_QQ.hand.readThread.stop();
            }
            if (Minecraft_QQ.Config.getSystem().isDebug())
                Minecraft_QQ.MinecraftQQ.LogInfo("§d[Minecraft_QQ]§5[Debug]线程已关闭");
            if (Minecraft_QQ.hand.pw != null) Minecraft_QQ.hand.pw.close();
            if (Minecraft_QQ.hand.os != null) Minecraft_QQ.hand.os.close();
            if (Minecraft_QQ.hand.is != null) Minecraft_QQ.hand.is.close();
            if (Minecraft_QQ.hand.socket != null) Minecraft_QQ.hand.socket.close();
            Minecraft_QQ.hand.socketIsRun = false;
        } catch (Exception ignored) {
        }
    }

    public boolean socketConnet() {
        Minecraft_QQ.MinecraftQQ.LogInfo("§d[Minecraft_QQ]§5正在连接酷Q");
        try {
            Minecraft_QQ.hand.socket = new Socket(Minecraft_QQ.Config.getSystem().getIP(), Minecraft_QQ.Config.getSystem().getPort());
            socketSend.send_data(Placeholder.start,null,null, Minecraft_QQ.Config.getServerSet().getServerName());
            Minecraft_QQ.MinecraftQQ.LogInfo("§d[Minecraft_QQ]§5酷Q已连接");
            return true;
        } catch (Exception e) {
            Minecraft_QQ.MinecraftQQ.LogInfo("§d[Minecraft_QQ]§c酷Q连接失败");
            return false;
        }
    }
}
