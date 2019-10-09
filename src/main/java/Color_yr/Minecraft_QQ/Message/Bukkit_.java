package Color_yr.Minecraft_QQ.Message;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Json.Read_Json;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Socket.socket;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import com.google.gson.Gson;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.Callable;

public class Bukkit_ implements IMessage {
    private String get_string(String a, String b, String c) {
        int x = a.indexOf(b) + b.length();
        int y = a.indexOf(c);
        return a.substring(x, y);
    }

    public void Message(String message) {
        try {
            String msg = message;
            if (logs.Group_log) {
                logs logs = new logs();
                logs.log_write("[Group]" + msg);
            }
            if (Color_yr.Minecraft_QQ.Config.Bukkit_.System_Debug)
                config.ilog.Log_System("处理数据：" + msg);
            if (!socket.hand.socket_runFlag)
                return;
            while (msg.indexOf(Color_yr.Minecraft_QQ.Config.Bukkit_.Head) == 0 && msg.contains(Color_yr.Minecraft_QQ.Config.Bukkit_.End)) {
                String buff = get_string(msg, Color_yr.Minecraft_QQ.Config.Bukkit_.Head, Color_yr.Minecraft_QQ.Config.Bukkit_.End);
                Read_Json read_bean;
                try {
                    Gson read_gson = new Gson();
                    read_bean = read_gson.fromJson(buff, Read_Json.class);
                } catch (Exception e) {
                    config.ilog.Log_System("数据传输发生错误:" + e.getMessage());
                    return;
                }
                if (read_bean.getIs_commder().equals("false")) {
                    String a = read_bean.getMessage();
                    if (a.indexOf("说话") == 0) {
                        a = a.replaceFirst("说话", "");
                        String say = Color_yr.Minecraft_QQ.Config.Bukkit_.Minecraft_Say.replaceFirst(Placeholder.Servername, Color_yr.Minecraft_QQ.Config.Bukkit_.Minecraft_ServerName).replaceFirst("%Message%", a);
                        say = ChatColor.translateAlternateColorCodes('&', say);
                        final String m = say;
                        try {
                            final Collection<Player> values = (Collection<Player>) org.bukkit.Bukkit.getOnlinePlayers();
                            for (Player b : values) {
                                if (!Color_yr.Minecraft_QQ.Config.Bukkit_.Mute_List.contains(b.getName()))
                                    b.sendMessage(m);
                            }
                        } catch (Exception e) {
                            config.ilog.Log_System(e.toString());
                        }
                    } else if (a.indexOf("在线人数") == 0) {
                        String player;
                        String send = Color_yr.Minecraft_QQ.Config.Bukkit_.Minecraft_PlayerListMessage;
                        player = org.bukkit.Bukkit.getOnlinePlayers().toString();
                        if (player.equals("[]")) {
                            try {
                                send = send.replaceAll(Placeholder.Servername, Color_yr.Minecraft_QQ.Config.Bukkit_.Minecraft_ServerName)
                                        .replaceAll(Placeholder.player_number, "0")
                                        .replaceAll(Placeholder.player_list, "无");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            int player_number = 1;
                            for (int i = 0; i < player.length(); i++) {
                                if (player.charAt(i) == ',')
                                    player_number++;
                            }
                            player = player.replace("[", "");
                            player = player.replace("]", "");
                            player = player.replaceAll("CraftPlayer\\{name=", "");
                            player = player.replaceAll("}", "");

                            send = send.replaceAll(Placeholder.Servername, Color_yr.Minecraft_QQ.Config.Bukkit_.Minecraft_ServerName)
                                    .replaceAll(Placeholder.player_number, "" + player_number)
                                    .replaceAll(Placeholder.player_list, player);
                        }
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                        if (logs.Group_log) {
                            logs logs = new logs();
                            logs.log_write("[group]查询在线人数");
                        }
                        if (Color_yr.Minecraft_QQ.Config.Bukkit_.System_Debug)
                            config.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]查询在线人数");
                    } else if (a.indexOf("服务器状态") == 0) {
                        String send = Color_yr.Minecraft_QQ.Config.Bukkit_.Minecraft_ServerOnlineMessage;
                        send = send.replaceAll(Placeholder.Servername, Color_yr.Minecraft_QQ.Config.Bukkit_.Minecraft_ServerName);
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                        if (logs.Group_log) {
                            logs logs = new logs();
                            logs.log_write("[group]查询服务器状态");
                        }
                        if (Color_yr.Minecraft_QQ.Config.Bukkit_.System_Debug)
                            config.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
                    }
                } else if (read_bean.getIs_commder().equals("true")) {
                    StringBuilder send_message;
                    send_bukkit send = new send_bukkit();
                    send.setPlayer(read_bean.getPlayer());
                    try {
                        org.bukkit.Bukkit.getScheduler().callSyncMethod(Color_yr.Minecraft_QQ.Main.Bukkit.Minecraft_QQ, new Callable<Boolean>() {
                            @Override
                            public Boolean call() {
                                return org.bukkit.Bukkit.dispatchCommand(send.sender, read_bean.getMessage());
                            }
                        }).get();
                    } catch (Exception e) {
                        config.ilog.Log_System(e.toString());
                    }
                    if (send.getMessage().size() == 1) {
                        send_message = new StringBuilder(send.getMessage().get(0));
                    } else if (send.getMessage().size() > 1) {
                        send_message = new StringBuilder(send.getMessage().get(0));
                        for (int i = 1; i < send.getMessage().size(); i++) {
                            send_message.append("\n");
                            send_message.append(send.getMessage().get(i));
                        }
                    } else
                        send_message = new StringBuilder("指令执行失败");
                    socket_send.send_data(Placeholder.data, read_bean.getGroup(),
                            "控制台", send_message.toString());
                    send.clear();
                }
                int i = msg.indexOf(Color_yr.Minecraft_QQ.Config.Bukkit_.End);
                msg = msg.substring(i + Color_yr.Minecraft_QQ.Config.Bukkit_.End.length());
            }
        } catch (Exception e) {
            config.ilog.Log_System("发送错误：" + e.getMessage());
        }
    }
}