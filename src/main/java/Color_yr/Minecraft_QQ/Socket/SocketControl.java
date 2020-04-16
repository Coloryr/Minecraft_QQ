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
    private Thread restartThread;
    private final Runnable read = () -> {
        while (socketRun) {
            try {
                if (socket.isClosed()) {
                    socketRun = false;
                    socketRestart();
                    return;
                } else {
                    is = socket.getInputStream();
                    int len = is.read(buf);
                    if (len > 0) {
                        String a = new String(buf, 0, len);
                        if (!a.isEmpty()) {
                            if (Minecraft_QQ.Config.getSystem().isDebug())
                                Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§5[Debug]收到数据：" + a);
                            Minecraft_QQ.MinecraftQQ.message(a);
                        }
                    } else
                        break;
                }
                Thread.sleep(Minecraft_QQ.Config.getSystem().getSleep());
            } catch (Exception e) {
                Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§c数据接受发生错误");
                e.printStackTrace();
                break;
            }
        }
        Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§c酷Q连接中断");
        socketRun = false;
        if (!serverIsClose && Minecraft_QQ.Config.getSystem().isAutoConnect())
            socketRestart();
    };

    private final Runnable restart = () -> {
        if (!serverIsClose && !isRestart) {
            isRestart = true;
            socketRun = false;
            clear();
            Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§5正在进行自动重连");
            while (isRestart) {
                try {
                    socketRun = false;
                    if (socketConnect()) {
                        socketRun = true;
                        readThread = new Thread(read);
                        readThread.start();
                        break;
                    }
                    if (!Minecraft_QQ.Config.getSystem().isAutoConnect()) {
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
        while (readThread != null && readThread.isAlive()) {
            readThread.stop();
        }
        readThread = null;
    }

    private void clear() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            waitItStop();
            socket = null;
            if (Minecraft_QQ.Config.getSystem().isDebug())
                Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§5[Debug]线程已关闭");
            if (os != null) os.close();
            if (is != null) is.close();
            os = null;
            is = null;
        } catch (Exception ignored) {
        }
    }

    @Override
    public void start() {
        if (socketConnect()) {
            socketRun = true;
            readThread = new Thread(read);
            readThread.start();
        } else {
            socketRestart();
        }
    }

    @Override
    public void close() {
        socketRun = false;
        if (restartThread != null && restartThread.isAlive()) {
            restartThread.stop();
        }
        clear();
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
            if (socket != null && !socket.isClosed())
                socket.close();
            waitItStop();
            socket = new Socket(Minecraft_QQ.Config.getSystem().getIP(), Minecraft_QQ.Config.getSystem().getPort());
            Thread.sleep(1000);
            sendData(Placeholder.start, null, null, Minecraft_QQ.Config.getServerSet().getServerName());
            Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§5酷Q已连接");
            return true;
        } catch (Exception e) {
            Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§c酷Q连接失败");
            return false;
        }
    }

    public boolean sendData(String data, String group, String player, String message) {
        SendOBJ send_bean = new SendOBJ(data, group, player, message);
        Gson send_gson = new Gson();
        return socketSend(send_gson.toJson(send_bean), player, message);
    }

    private boolean socketSend(String send, String Player, String message) {
        try {
            send = Minecraft_QQ.Config.getSystem().getHead() + send
                    + Minecraft_QQ.Config.getSystem().getEnd();
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
            Minecraft_QQ.MinecraftQQ.logInfo("§d[Minecraft_QQ]§c酷Q连接中断");
            e.printStackTrace();
            clear();
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
