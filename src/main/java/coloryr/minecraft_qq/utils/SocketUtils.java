package coloryr.minecraft_qq.utils;

import coloryr.minecraft_qq.api.Placeholder;
import coloryr.minecraft_qq.Minecraft_QQ;
import coloryr.minecraft_qq.json.SendOBJ;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SocketUtils {

    private static Socket socket = null;
    private static final Queue<String> queueRead = new ConcurrentLinkedQueue<>();
    private static final Queue<byte[]> queueSend = new ConcurrentLinkedQueue<>();
    private static Thread readThread;
    private static Thread doThread;
    private static boolean isRun;
    private static boolean isConnect;
    private static final Gson gson = new Gson();

    public static void start() {
        doThread = new Thread(() -> {
            while (isRun) {
                try {
                    String temp = queueRead.poll();
                    if (temp != null) {
                        if (Minecraft_QQ.config.System.Debug)
                            Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]收到数据：" + temp);
                        if (!temp.equals("test"))
                            Minecraft_QQ.side.message(temp);
                    }
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        readThread = new Thread(() -> {
            boolean first = true;
            try {
                while (!isRun) {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            doThread.start();
            byte[] data;
            int b = 0;
            while (isRun) {
                try {
                    b++;
                    if (b > 100) {
                        b = 0;
                        queueSend.add("test".getBytes(StandardCharsets.UTF_8));
                    }
                    if (!isConnect) {
                        if (first) {
                            first = false;
                            reConnect();
                        } else if (Minecraft_QQ.config.System.AutoConnect) {
                            Minecraft_QQ.log.warning("§d[Minecraft_QQ]§5" + Minecraft_QQ.config.System.AutoConnectTime + "秒后重连");
                            try {
                                int a = Minecraft_QQ.config.System.AutoConnectTime;
                                while (isRun && a > 0) {
                                    Thread.sleep(1000);
                                    a--;
                                }
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            Minecraft_QQ.log.warning("§d[Minecraft_QQ]§5Minecraft_QQ_Cmd/Gui重连中");
                            reConnect();
                        }
                    } else {
                        if (socket.getInputStream().available() > 0) {
                            data = new byte[socket.getInputStream().available()];
                            socket.getInputStream().read(data);
                            queueRead.add(new String(data, StandardCharsets.UTF_8));
                        } else if (socket.getInputStream().available() < 0) {
                            Minecraft_QQ.log.info("§d[Minecraft_QQ]§5Minecraft_QQ_Cmd/Gui连接中断");
                            isConnect = false;
                        } else if (!queueSend.isEmpty()) {
                            data = queueSend.poll();
                            socket.getOutputStream().write(data);
                            socket.getOutputStream().flush();
                        }
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    Minecraft_QQ.log.warning("§d[Minecraft_QQ]§5Minecraft_QQ_Cmd/Gui连接失败");
                    e.printStackTrace();
                    isConnect = false;
                }
            }
        });
        readThread.start();
        isRun = true;
    }

    private static void reConnect() {
        try {
            if (socket != null)
                socket.close();

            Minecraft_QQ.log.info("§d[Minecraft_QQ]§5正在连接Minecraft_QQ_Cmd/Gui");
            socket = new Socket();
            socket.connect(new InetSocketAddress(Minecraft_QQ.config.System.IP,
                    Minecraft_QQ.config.System.Port), 5000);
            Thread.sleep(200);
            queueRead.clear();
            queueSend.clear();
            sendData(Placeholder.start, null, null, Minecraft_QQ.config.ServerSet.ServerName);
            Minecraft_QQ.log.info("§d[Minecraft_QQ]§5Minecraft_QQ_Cmd/Gui已连接");
            isConnect = true;
        } catch (Exception e) {
            Minecraft_QQ.log.info("§d[Minecraft_QQ]§cMinecraft_QQ_Cmd/Gui连接失败");
            e.printStackTrace();
        }
    }

    public static void stop() {
        Minecraft_QQ.log.info("§d[Minecraft_QQ]§5连接已断开");
        isRun = false;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Minecraft_QQ.config.System.Debug)
            Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]线程已关闭");
    }

    public static void sendData(String data, String group, String player, String message) {
        SendOBJ send_bean = new SendOBJ(data, group, player, message);
        socketSend(gson.toJson(send_bean), player, message);
    }

    private static String build(String[] arg) {
        StringBuilder builder = new StringBuilder();
        for (int a = 1; a < arg.length; a++) {
            builder.append(arg[a]).append(" ");
        }
        return builder.toString();
    }

    public static void sendData(String data, String group, String player, String[] arg) {
        String message = build(arg);
        SendOBJ send_bean = new SendOBJ(data, group, player, message);
        socketSend(gson.toJson(send_bean), player, message);
    }

    private static void socketSend(String send, String Player, String message) {
        queueSend.add(send.getBytes(StandardCharsets.UTF_8));
        if (Minecraft_QQ.config.Logs.Server) {
            Logs.logWrite("[Server]" + (Player == null ? "测试" : Player) + ":" + message);
        }
        if (Minecraft_QQ.config.System.Debug)
            Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]发送数据：" + send);
    }

    public static void socketRestart() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isRun() {
        return isConnect;
    }
}
