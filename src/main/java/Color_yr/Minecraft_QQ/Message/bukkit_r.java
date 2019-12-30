package Color_yr.Minecraft_QQ.Message;

import Color_yr.Minecraft_QQ.API.IMessage;
import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Bukkit;
import Color_yr.Minecraft_QQ.Config.Base_config;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Json.Read_Json;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import com.google.gson.Gson;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class bukkit_r implements IMessage {
    private String get_string(String a, String b, String c) {
        int x = a.indexOf(b) + b.length();
        int y = a.indexOf(c);
        return a.substring(x, y);
    }

    public void Message(String message) {
        try {
            String msg = message;
            if (logs.Group_log) {
                logs.log_write("[Group]" + msg);
            }
            if (Base_config.System_Debug)
                use.ilog.Log_System("处理数据：" + msg);
            if (!use.hand.socket_runFlag)
                return;
            while (msg.indexOf(Base_config.Head) == 0 && msg.contains(Base_config.End)) {
                String buff = get_string(msg, Base_config.Head, Base_config.End);
                Read_Json read_bean;
                try {
                    Gson read_gson = new Gson();
                    read_bean = read_gson.fromJson(buff, Read_Json.class);
                } catch (Exception e) {
                    use.ilog.Log_System("数据传输发生错误:" + e.getMessage());
                    return;
                }
                if (read_bean.getIs_commder().equals("false")) {
                    if (read_bean.getCommder().equalsIgnoreCase("speak")) {
                        String say = Base_config.Minecraft_Say.replaceFirst(Placeholder.Servername, Base_config.Minecraft_ServerName)
                                .replaceFirst(Placeholder.Message, read_bean.getMessage());
                        say = ChatColor.translateAlternateColorCodes('&', say);
                        final String finalSay = say;
                        org.bukkit.Bukkit.getScheduler().runTask(Color_yr.Minecraft_QQ.Bukkit.Minecraft_QQ, () ->
                        {
                            try {
                                for (Player b : org.bukkit.Bukkit.getOnlinePlayers()) {
                                    if (!Base_config.Mute_List.contains(b.getName()))
                                        b.sendMessage(finalSay);
                                }
                            } catch (Exception e) {
                                use.ilog.Log_System(e.toString());
                            }
                        });
                    } else if (read_bean.getCommder().equalsIgnoreCase("online")) {
                        String player;
                        String send = Base_config.Minecraft_PlayerListMessage;
                        player = org.bukkit.Bukkit.getOnlinePlayers().toString();
                        if (player.equals("[]")) {
                            try {
                                send = send.replaceAll(Placeholder.Servername, Base_config.Minecraft_ServerName)
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
                            player = player.replace("[", "")
                                    .replace("]", "")
                                    .replaceAll("CraftPlayer\\{name=", "")
                                    .replaceAll("}", "");

                            send = send.replaceAll(Placeholder.Servername, Base_config.Minecraft_ServerName)
                                    .replaceAll(Placeholder.player_number, "" + player_number)
                                    .replaceAll(Placeholder.player_list, player);
                        }
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                        if (logs.Group_log) {
                            logs.log_write("[group]查询在线人数");
                        }
                        if (Base_config.System_Debug)
                            use.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]查询在线人数");
                    } else if (read_bean.getCommder().equalsIgnoreCase("server")) {
                        String send = Base_config.Minecraft_ServerOnlineMessage;
                        send = send.replaceAll(Placeholder.Servername, Base_config.Minecraft_ServerName);
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                        if (logs.Group_log) {
                            logs.log_write("[group]查询服务器状态");
                        }
                        if (Base_config.System_Debug)
                            use.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
                    }
                } else if (read_bean.getIs_commder().equals("true")) {
                    StringBuilder send_message;
                    send_bukkit send = new send_bukkit();
                    send.setPlayer(read_bean.getPlayer());
                    try {
                        org.bukkit.Bukkit.getScheduler().callSyncMethod(Bukkit.Minecraft_QQ, () ->
                                org.bukkit.Bukkit.dispatchCommand(send.sender, read_bean.getCommder())).get();
                    } catch (Exception e) {
                        use.ilog.Log_System(e.toString());
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
                int i = msg.indexOf(Base_config.End);
                msg = msg.substring(i + Base_config.End.length());
            }
        } catch (Exception e) {
            use.ilog.Log_System("发送错误：" + e.getMessage());
        }
    }
}