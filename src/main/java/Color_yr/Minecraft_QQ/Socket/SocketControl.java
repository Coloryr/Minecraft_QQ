package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.API.ISocketControl;
import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Json.SendOBJ;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Utils.logs;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketControl implements ISocketControl {

    private final byte[] buf = new byte[4096];
    private OutputStream os = null;
    private Socket socket = null;
    private InputStream is = null;
    private boolean socketRun = false;
    private boolean serverIsClose = false;
    private boolean isRestart = false;
    private Thread readThread;
    private final Runnable read = () -> {
        socketRun = true;
        while (socketRun) {
            try {
                is = socket.getInputStream();
                int len = is.read(buf);
                if (len > 0) {
                    String a = new String(buf, 0, len);
                    if (!a.isEmpty()) {
                        if (Minecraft_QQ.Config.getSystem().isDebug())
                            Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§5[Debug]收到数据：" + a);
                        Minecraft_QQ.MinecraftQQ.message(a);
                    }
                }
                Thread.sleep(Minecraft_QQ.Config.getSystem().getSleep());
            } catch (Exception e) {
                if (isRestart)
                    break;
                Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§c数据接受发生错误");
                e.printStackTrace();
                stopT();
                break;
            }
        }
    };
    private Thread restartThread;
    private final Runnable restart = () -> {
        if (!isRestart && Minecraft_QQ.Config.getSystem().isAutoConnect()) {
            isRestart = true;
            Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§5正在进行自动重连");
            while (isRestart && !serverIsClose) {
                try {
                    if (socketConnect()) {
                        break;
                    }
                    Thread.sleep(Minecraft_QQ.Config.getSystem().getAutoConnectTime());
                } catch (Exception e) {
                    logs.logWrite(e.getMessage());
                }
            }
            isRestart = false;
        }
    };

    private void waitItStop() {
        try {
            socketRun = false;
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (os != null) os.close();
            if (is != null) is.close();
            socket = null;
            while (restartThread != null && restartThread.isAlive()) {
                socketRun = false;
                restartThread.stop();
            }
            readThread = null;
            os = null;
            is = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopT() {
        Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§c酷Q连接中断");
        socketRun = false;
        if (!serverIsClose)
            socketRestart();
    }

    @Override
    public void start() {
        if (!socketConnect()) socketRestart();
    }

    @Override
    public void close() {
        Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§5连接已断开");
        waitItStop();
        if (Minecraft_QQ.Config.getSystem().isDebug())
            Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§5[Debug]线程已关闭");
    }

    @Override
    public void stop() {
        serverIsClose = true;
        close();
    }

    @Override
    public boolean socketConnect() {
        Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§5正在连接酷Q");
        try {
            waitItStop();
            socket = new Socket(Minecraft_QQ.Config.getSystem().getIP(), Minecraft_QQ.Config.getSystem().getPort());
            Thread.sleep(1000);
            sendData(Placeholder.start, null, null, Minecraft_QQ.Config.getServerSet().getServerName());
            Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§5酷Q已连接");
            readThread = new Thread(read);
            readThread.start();
            socketRun = true;
            return true;
        } catch (Exception e) {
            Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§c酷Q连接失败");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendData(String data, String group, String player, String message) {
        SendOBJ send_bean = new SendOBJ(data, group, player, message);
        Gson send_gson = new Gson();
        return socketSend(send_gson.toJson(send_bean), player, message);
    }

    private boolean socketSend(String send, String Player, String message) {
        try {
            send = Minecraft_QQ.Config.getSystem().getHead() + send
                    + Minecraft_QQ.Config.getSystem().getEnd();
            if (os == null)
                os = socket.getOutputStream();
            os.write(send.getBytes());
            os.flush();
            if (Minecraft_QQ.Config.getLogs().isServer()) {
                logs.logWrite("[Server]" + (Player == null ? "测试" : Player) + ":" + message);
            }
            if (Minecraft_QQ.Config.getSystem().isDebug())
                Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§5[Debug]发送数据：" + send);
            return true;
        } catch (Exception e) {
            Minecraft_QQ.MinecraftQQ.logError("§d[Minecraft_QQ]§c数据发送失败");
            e.printStackTrace();
            close();
        }
        return false;
    }

    public void socketRestart() {
        if (!isRestart) {
            restartThread = new Thread(restart);
            restartThread.start();
        }
    }

    @Override
    public boolean isRun() {
        return socketRun;
    }
}
