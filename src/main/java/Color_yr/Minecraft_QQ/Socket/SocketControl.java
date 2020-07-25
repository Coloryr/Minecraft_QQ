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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketControl implements ISocketControl {

    private final byte[] buf = new byte[4096];
    private OutputStream os = null;
    private Socket socket = null;
    private InputStream is = null;
    private boolean socketRun = false;
    private boolean serverIsClose = false;
    private boolean isRestart = false;
    private Thread restartThread;
    private Thread readThread;
    private final Runnable read = () -> {
        while(isRestart) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        socketRun = true;
        while (socketRun) {
            try {
                is = socket.getInputStream();
                int len = is.read(buf);
                if (len > 0) {
                    String a = new String(buf, StandardCharsets.UTF_8);
                    if (!a.isEmpty()) {
                        if (Minecraft_QQ.Config.getSystem().isDebug())
                            Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§5[Debug]收到数据：" + a);
                        Minecraft_QQ.Side.message(a);
                    }
                } else if (len == -1) {
                    Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§c连接中断");
                    stopRestart();
                    break;
                }
                Thread.sleep(Minecraft_QQ.Config.getSystem().getSleep());
            } catch (Exception e) {
                if (isRestart || serverIsClose)
                    break;
                Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§c数据接受发生错误");
                e.printStackTrace();
                stopRestart();
                break;
            }
        }
        socketRun = false;
        readThread = null;
    };
    private final Runnable restart = () -> {
        isRestart = true;
        waitItStop();
        while (!serverIsClose) {
            try {
                Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§5正在进行连接");
                if (socketConnect()) {
                    readThread = new Thread(read);
                    readThread.start();
                    break;
                } else if (!Minecraft_QQ.Config.getSystem().isAutoConnect()) {
                    break;
                }
                Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§5" +
                        Minecraft_QQ.Config.getSystem().getAutoConnectTime() + "毫秒后自动重连");
                Thread.sleep(Minecraft_QQ.Config.getSystem().getAutoConnectTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isRestart = false;
        restartThread = null;
    };

    private void waitItStop() {
        try {
            socketRun = false;
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (readThread != null && readThread.isAlive()) {
                try {
                    readThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) os.close();
            if (is != null) is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRestart() {
        socketRun = false;
        if (!serverIsClose && Minecraft_QQ.Config.getSystem().isAutoConnect())
            socketRestart();
    }

    @Override
    public void start() {
        socketRestart();
    }

    @Override
    public void close() {
        Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§5连接已断开");
        waitItStop();
        if (Minecraft_QQ.Config.getSystem().isDebug())
            Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§5[Debug]线程已关闭");
    }

    @Override
    public void stop() {
        serverIsClose = true;
        close();
    }

    @Override
    public boolean socketConnect() {
        Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§5正在连接酷Q");
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(Minecraft_QQ.Config.getSystem().getIP(),
                    Minecraft_QQ.Config.getSystem().getPort()), 5000);
            Thread.sleep(200);
            sendData(Placeholder.start, null, null, Minecraft_QQ.Config.getServerSet().getServerName());
            Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§5酷Q已连接");
            return true;
        } catch (Exception e) {
            Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§c酷Q连接失败");
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
            os = socket.getOutputStream();
            os.write(send.getBytes(StandardCharsets.UTF_8));
            os.flush();
            if (Minecraft_QQ.Config.getLogs().isServer()) {
                logs.logWrite("[Server]" + (Player == null ? "测试" : Player) + ":" + message);
            }
            if (Minecraft_QQ.Config.getSystem().isDebug())
                Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§5[Debug]发送数据：" + send);
            return true;
        } catch (Exception e) {
            if (isRestart || serverIsClose)
                return true;
            Minecraft_QQ.Side.logError("§d[Minecraft_QQ]§c数据发送失败");
            e.printStackTrace();
            close();
        }
        return false;
    }

    public void socketRestart() {
        if(isRestart)
            return;
        if (restartThread == null) {
            restartThread = new Thread(restart);
            restartThread.start();
        } else {
            try {
                restartThread = new Thread(restart);
                restartThread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isRun() {
        return socketRun;
    }
}
