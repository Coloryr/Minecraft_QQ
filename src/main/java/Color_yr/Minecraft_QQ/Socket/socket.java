package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Config.Bukkit;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Main.Forge;

import java.io.IOException;
import java.net.UnknownHostException;

public class socket extends Thread {

    public static socket_hand hand = new socket_hand();

    @Override
    public void run() {
        hand.socket_stop = false;
        if (Bukkit.System_Debug == true)
            if (config.is_forge == true)
                Forge.logger.info("§d[Minecraft_QQ]§5[Debug]线程开始");
            else
                config.log_b.info("§d[Minecraft_QQ]§5[Debug]线程开始");
        if (logs.Socket_log == true) {
            logs logs = new logs();
            logs.log_write("[socket]线程开始");
        }
        while (true) {
            try {
                if (hand.socket_runFlag == false
                        && hand.server_isclose == false
                        && Bukkit.System_AutoConnet == true
                        && socket_restart.is_restart == false
                        && hand.socket_first == false) {
                    if (logs.Socket_log == true) {
                        logs logs = new logs();
                        logs.log_write("[socket]正在进行自动重连");
                    }
                    if (Bukkit.System_Debug == true)
                        if (config.is_forge == true)
                            Forge.logger.info("§d[Minecraft_QQ]§5[Debug]正在进行自动重连");
                        else
                            config.log_b.info("§d[Minecraft_QQ]§5[Debug]正在进行自动重连");
                    socket_restart socket_restart = new socket_restart();
                    if (socket_restart.socket_restart_start() == true) {
                        if (hand.socket_first == true) {
                            if (Bukkit.System_Debug == true)
                                if (config.is_forge == true)
                                    Forge.logger.info("§d[Minecraft_QQ]§5[Debug]线程销毁-重连销毁");
                                else
                                    config.log_b.info("§d[Minecraft_QQ]§5[Debug]线程销毁-重连销毁");
                            if (logs.Socket_log == true) {
                                logs logs = new logs();
                                logs.log_write("[socket]线程销毁-重连销毁");
                            }
                            return;
                        }
                    } else {
                        hand.socket_stop = false;
                        hand.socket_runFlag = false;
                        try {
                            Thread.sleep(Bukkit.System_AutoConnetTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            if (logs.Error_log == true) {
                                logs logs = new logs();
                                logs.log_write("[ERROR]" + e.getMessage());
                            }
                        }
                    }
                } else if (hand.socket_stop == true) {
                    if (Bukkit.System_Debug == true)
                        if (config.is_forge == true)
                            Forge.logger.info("§d[Minecraft_QQ]§5[Debug]线程销毁-停止销毁");
                        else
                            config.log_b.info("§d[Minecraft_QQ]§5[Debug]线程销毁-停止销毁");
                    if (logs.Socket_log == true) {
                        logs logs = new logs();
                        logs.log_write("[socket]线程销毁-停止销毁");
                    }
                    return;
                } else if (hand.socket_runFlag == true) {
                    try {
                        if (hand.socket.isClosed()) {
                            hand.socket_runFlag = false;
                            while (hand.socket_stop) {
                                if (Bukkit.System_Debug == true)
                                    if (config.is_forge == true)
                                        Forge.logger.info("§d[Minecraft_QQ]§5[Debug]线程销毁-断开销毁");
                                    else
                                        config.log_b.info("§d[Minecraft_QQ]§5[Debug]线程销毁-断开销毁");
                                if (logs.Socket_log == true) {
                                    logs logs = new logs();
                                    logs.log_write("[socket]线程销毁-断开销毁");
                                }
                                return;
                            }
                        } else {
                            hand.is = hand.socket.getInputStream();
                            int len = hand.is.read(hand.buf);
                            if (len <= 0) {
                                if (config.is_forge == true)
                                    Forge.logger.warn("§d[Minecraft_QQ]§c酷Q连接中断");
                                else
                                    config.log_b.warning("§d[Minecraft_QQ]§c酷Q连接中断");
                                if (logs.Socket_log == true) {
                                    logs logs = new logs();
                                    logs.log_write("[socket]酷Q连接中断");
                                }
                                hand.socket_runFlag = false;
                            } else {
                                hand.info = new String(hand.buf, 0, len);
                                if (!hand.info.isEmpty()) {
                                    if (Bukkit.System_Debug == true)
                                        if (config.is_forge == true)
                                            Forge.logger.info("§d[Minecraft_QQ]§5[Debug]收到数据：" + hand.info);
                                        else
                                            config.log_b.info("§d[Minecraft_QQ]§5[Debug]收到数据：" + hand.info);
                                    hand.have_message = true;
                                    hand.is_can_go = false;
                                    while (hand.is_can_go == false) {
                                        try {
                                            Thread.sleep(Bukkit.System_Sleep);
                                        } catch (Exception e) {
                                            if (config.is_forge == true)
                                                Forge.logger.warn("§d[Minecraft_QQ]发生错误§c" + e.getMessage());
                                            else
                                                config.log_b.warning("§d[Minecraft_QQ]发生错误§c" + e.getMessage());
                                        }
                                    }
                                    hand.is_can_go = false;
                                    hand.have_message = false;
                                }
                            }
                        }
                    } catch (UnknownHostException e) {
                        if (config.is_forge == true)
                            Forge.logger.warn("§d[Minecraft_QQ]§c酷Q连接中断");
                        else
                            config.log_b.warning("§d[Minecraft_QQ]§c酷Q连接中断");
                        if (logs.Error_log == true) {
                            logs logs = new logs();
                            logs.log_write("[ERROR]" + e.getMessage());
                        }
                        hand.socket_runFlag = false;
                    } catch (IOException e) {
                        if (config.is_forge == true)
                            Forge.logger.warn("§d[Minecraft_QQ]§c酷Q连接中断");
                        else
                            config.log_b.warning("§d[Minecraft_QQ]§c酷Q连接中断");
                        if (logs.Error_log == true) {
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
                    if (logs.Error_log == true) {
                        logs logs = new logs();
                        logs.log_write("[ERROR]" + e.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (logs.Error_log == true) {
                    logs logs = new logs();
                    logs.log_write("[ERROR]" + e.getMessage());
                }
            }
        }
    }

    public static boolean socket_send(String send) {
        try {
            send = Bukkit.Head + send + Bukkit.End;
            hand.os = hand.socket.getOutputStream();
            hand.os.write(send.getBytes());
            if (logs.Send_log == true) {
                logs logs = new logs();
                logs.log_write("[socket_send]" + send);
            }
            if (Bukkit.System_Debug == true)
                if (config.is_forge == true)
                    Forge.logger.info("§d[Minecraft_QQ]§5[Debug]发送数据：" + send);
                else
                    config.log_b.info("§d[Minecraft_QQ]§5[Debug]发送数据：" + send);
            return true;
        } catch (IOException e) {
            if (config.is_forge == true)
                Forge.logger.warn("§d[Minecraft_QQ]§c酷Q连接中断");
            else
                config.log_b.warning("§d[Minecraft_QQ]§c酷Q连接中断");
            if (logs.Socket_log == true) {
                logs logs = new logs();
                logs.log_write("[socket]酷Q连接中断");
            }
            hand.socket_runFlag = false;
        }
        return false;
    }

    public static void server_close() {
        hand.socket_runFlag = false;
        try {
            socket_stop();
            if (hand.pw != null) hand.pw.close();
            if (hand.os != null) hand.os.close();
            if (hand.is != null) hand.is.close();
            if (hand.socket != null) hand.socket.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void socket_stop() {
        hand.socket_stop = true;
        hand.socket_runFlag = false;
        if (hand.socket != null) {
            try {
                hand.socket.close();
            } catch (IOException e) {
                if (logs.Error_log == true) {
                    logs logs = new logs();
                    logs.log_write("[ERROR]" + e.getMessage());
                }
            }
            if (hand.readThread != null) {
                if (hand.readThread.isAlive() == true)
                    hand.readThread.stop();
                if (Bukkit.System_Debug == true)
                    if (config.is_forge == true)
                        Forge.logger.info("§d[Minecraft_QQ]§5[Debug]线程已关闭");
                    else
                        config.log_b.info("§d[Minecraft_QQ]§5[Debug]线程已关闭");
                if (logs.Socket_log == true) {
                    logs logs = new logs();
                    logs.log_write("[socket]线程已关闭");
                }
            }
        }
    }

    public void socket_start() {
        hand.readThread = new socket();
        hand.readThread.start();
        socket_restart socket_restart = new socket_restart();
        socket_restart.socket_restart_start();
    }
}
