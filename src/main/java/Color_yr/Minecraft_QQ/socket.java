package Color_yr.Minecraft_QQ;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class socket extends Thread {

    static PrintWriter pw = null;
    static OutputStream os = null;
    static Socket socket = null;
    static InputStream is = null;

    static boolean socket_runFlag = false;
    static boolean server_isclose = false;
    static boolean socket_stop = false;
    static boolean socket_first = true;

    static byte[] buf = new byte[1024];

    public static socket readThread = null;

    public void run() {
        socket_stop = false;
        if (config_bukkit.System_Debug == true)
            config.log.info("§d[Minecraft_QQ]§5[Debug]线程开始");
        if (logs.Socket_log == true) {
            logs logs = new logs();
            logs.log_write("[socket]线程开始");
        }
        while (true) {
            if (socket_runFlag == false
                    && server_isclose == false
                    && config_bukkit.System_AutoConnet == true
                    && socket_restart.is_restart == false
                    && socket_first == false) {
                if (logs.Socket_log == true) {
                    logs logs = new logs();
                    logs.log_write("[socket]正在进行自动重连");
                }
                if (config_bukkit.System_Debug == true)
                    config.log.info("§d[Minecraft_QQ]§5[Debug]正在进行自动重连");
                socket_restart socket_restart = new socket_restart();
                if (socket_restart.socket_restart_start() == true) {
                    if (socket_first == true) {
                        if (config_bukkit.System_Debug == true)
                            config.log.info("§d[Minecraft_QQ]§5[Debug]线程销毁-重连销毁");
                        if (logs.Socket_log == true) {
                            logs logs = new logs();
                            logs.log_write("[socket]线程销毁-重连销毁");
                        }
                        return;
                    }
                } else {
                    socket_stop = false;
                    socket_runFlag = false;
                    try {
                        Thread.sleep(config_bukkit.System_AutoConnetTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        if (logs.Error_log == true) {
                            logs logs = new logs();
                            logs.log_write("[ERROR]" + e.getMessage());
                        }
                    }
                }
            } else if (socket_stop == true) {
                if (config_bukkit.System_Debug == true)
                    config.log.info("§d[Minecraft_QQ]§5[Debug]线程销毁-停止销毁");
                if (logs.Socket_log == true) {
                    logs logs = new logs();
                    logs.log_write("[socket]线程销毁-停止销毁");
                }
                return;
            } else if (socket_runFlag == true) {
                try {
                    if (socket.isClosed()) {
                        socket_runFlag = false;
                        while (socket_stop) {
                            if (config_bukkit.System_Debug == true)
                                config.log.info("§d[Minecraft_QQ]§5[Debug]线程销毁-断开销毁");
                            if (logs.Socket_log == true) {
                                logs logs = new logs();
                                logs.log_write("[socket]线程销毁-断开销毁");
                            }
                            return;
                        }
                    } else {
                        is = socket.getInputStream();
                        int len = is.read(buf);
                        if (len <= 0) {
                            config.log.warning("§d[Minecraft_QQ]§c酷Q连接中断");
                            if (logs.Socket_log == true) {
                                logs logs = new logs();
                                logs.log_write("[socket]酷Q连接中断");
                            }
                            socket_runFlag = false;
                        } else {
                            String info = new String(buf, 0, len);
                            if (info != null && !info.isEmpty()) {
                                if (config_bukkit.System_Debug == true)
                                    config.log.info("§d[Minecraft_QQ]§5[Debug]收到数据：" + info);
                                if (config.is_bungee == true)
                                    config.message_b.message_read(info);
                                else
                                    config.message_a.message_read(info);
                            }
                        }
                    }
                } catch (UnknownHostException e) {
                    config.log.warning("§d[Minecraft_QQ]§c酷Q连接中断");
                    if (logs.Error_log == true) {
                        logs logs = new logs();
                        logs.log_write("[ERROR]" + e.getMessage());
                    }
                    socket_runFlag = false;
                } catch (IOException e) {
                    config.log.warning("§d[Minecraft_QQ]§c酷Q连接中断");
                    if (logs.Error_log == true) {
                        logs logs = new logs();
                        logs.log_write("[ERROR]" + e.getMessage());
                    }
                    socket_runFlag = false;
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                if (logs.Error_log == true) {
                    logs logs = new logs();
                    logs.log_write("[ERROR]" + e.getMessage());
                }
            }
        }
    }

    public static void socket_send(String send) {
        try {
            send = config_bukkit.Head + send + config_bukkit.End;
            os = socket.getOutputStream();
            os.write(send.getBytes());
        } catch (IOException e) {
            config.log.warning("§d[Minecraft_QQ]§c酷Q连接中断");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q连接中断");
            }
            socket_runFlag = false;
        }
        if (logs.Send_log == true) {
            logs logs = new logs();
            logs.log_write("[socket_send]" + send);
        }
        if (config_bukkit.System_Debug == true)
            config.log.info("§d[Minecraft_QQ]§5[Debug]发送数据：" + send);
    }

    public static void server_close() {
        socket_runFlag = false;
        try {
            socket_stop();
            if (pw != null) pw.close();
            if (os != null) os.close();
            if (is != null) is.close();
            if (socket != null) socket.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public static void socket_stop() {
        socket_stop = true;
        socket_runFlag = false;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                if (logs.Error_log == true) {
                    logs logs = new logs();
                    logs.log_write("[ERROR]" + e.getMessage());
                }
            }
            if (readThread != null) {
                if (readThread.isAlive() == true)
                    readThread.stop();
                if (config_bukkit.System_Debug == true)
                    config.log.info("§d[Minecraft_QQ]§5[Debug]线程已关闭");
                if (logs.Socket_log == true) {
                    logs logs = new logs();
                    logs.log_write("[socket]线程已关闭");
                }
            }
        }
    }

    public void socket_start() {
        readThread = new socket();
        readThread.start();
        socket_restart socket_restart = new socket_restart();
        socket_restart.socket_restart_start();
    }
}
