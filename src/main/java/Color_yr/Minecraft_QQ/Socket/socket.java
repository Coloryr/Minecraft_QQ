package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Config.Bukkit_;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Message.IMessage;

import java.io.IOException;

public class socket extends Thread {

    public static socket_hand hand = new socket_hand();
    public static IMessage iMessage;

    @Override
    public void run() {
        hand.socket_stop = false;
        if (Bukkit_.System_Debug)
                config.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]线程开始");
        if (logs.Socket_log) {
            logs logs = new logs();
            logs.log_write("[socket]线程开始");
        }
        while (true) {
            try {
                if (!hand.socket_runFlag
                        && !hand.server_isclose
                        && Bukkit_.System_AutoConnet
                        && !socket_restart.is_restart
                        && !hand.socket_first) {
                    if (logs.Socket_log) {
                        logs logs = new logs();
                        logs.log_write("[socket]正在进行自动重连");
                    }
                    if (Bukkit_.System_Debug)
                            config.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]正在进行自动重连");
                    socket_restart socket_restart = new socket_restart();
                    if (socket_restart.socket_restart_start()) {
                        if (hand.socket_first) {
                            if (Bukkit_.System_Debug)
                                    config.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]线程销毁-重连销毁");
                            if (logs.Socket_log) {
                                logs logs = new logs();
                                logs.log_write("[socket]线程销毁-重连销毁");
                            }
                            return;
                        }
                    } else {
                        hand.socket_stop = false;
                        hand.socket_runFlag = false;
                        try {
                            Thread.sleep(Bukkit_.System_AutoConnetTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            if (logs.Error_log) {
                                logs logs = new logs();
                                logs.log_write("[ERROR]" + e.getMessage());
                            }
                        }
                    }
                } else if (hand.socket_stop) {
                    if (Bukkit_.System_Debug)
                            config.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]线程销毁-停止销毁");
                    if (logs.Socket_log) {
                        logs logs = new logs();
                        logs.log_write("[socket]线程销毁-停止销毁");
                    }
                    return;
                } else if (hand.socket_runFlag) {
                    try {
                        if (hand.socket.isClosed()) {
                            hand.socket_runFlag = false;
                                return;
                        } else {
                            hand.is = hand.socket.getInputStream();
                            int len = hand.is.read(hand.buf);
                            if (len <= 0) {
                                    config.ilog.Log_System("§d[Minecraft_QQ]§c酷Q连接中断");
                                if (logs.Socket_log) {
                                    logs logs = new logs();
                                    logs.log_write("[socket]酷Q连接中断");
                                }
                                hand.socket_runFlag = false;
                            } else {
                                String a = new String(hand.buf, 0, len);
                                if (!a.isEmpty()) {
                                    if (Bukkit_.System_Debug)
                                            config.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]收到数据：" + a);
                                    iMessage.Message(a);
                                }
                            }
                        }
                    } catch (Exception e) {
                            config.ilog.Log_System("§d[Minecraft_QQ]§c酷Q连接中断");
                        if (logs.Error_log) {
                            logs logs = new logs();
                            logs.log_write("[ERROR]" + e.getMessage());
                        }
                        hand.socket_runFlag = false;
                    }
                }
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    if (logs.Error_log) {
                        logs logs = new logs();
                        logs.log_write("[ERROR]" + e.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (logs.Error_log) {
                    logs logs = new logs();
                    logs.log_write("[ERROR]" + e.getMessage());
                }
            }
        }
    }

    public static boolean socket_send(String send) {
        try {
            send = Bukkit_.Head + send + Bukkit_.End;
            hand.os = hand.socket.getOutputStream();
            hand.os.write(send.getBytes());
            if (logs.Send_log) {
                logs logs = new logs();
                logs.log_write("[socket_send]" + send);
            }
            if (Bukkit_.System_Debug)
                    config.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]发送数据：" + send);
            return true;
        } catch (IOException e) {
                config.ilog.Log_System("§d[Minecraft_QQ]§c酷Q连接中断");
            if (logs.Socket_log) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q连接中断");
            }
            hand.socket_runFlag = false;
        }
        return false;
    }

    public static void server_close() {
        if (hand.socket_runFlag) {
            try {
                hand.socket_stop = true;
                hand.socket_runFlag = false;
                if (!hand.socket.isClosed()) {
                    hand.socket.close();
                }
                if (hand.readThread.isAlive())
                    hand.readThread.stop();
                if (Bukkit_.System_Debug)
                    config.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]线程已关闭");
                if (logs.Socket_log) {
                    logs logs = new logs();
                    logs.log_write("[socket]线程已关闭");
                }
                if (hand.pw != null) hand.pw.close();
                if (hand.os != null) hand.os.close();
                if (hand.is != null) hand.is.close();
                if (hand.socket != null) hand.socket.close();
                hand.socket_runFlag = false;
            } catch (Exception e) {
                e.getMessage();
                if (logs.Error_log) {
                    logs logs = new logs();
                    logs.log_write("[ERROR]" + e.getMessage());
                }
            }
        }
    }
}
