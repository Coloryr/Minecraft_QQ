package Color_yr.Minecraft_QQ;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class message_bukkit {
    public static String get_string(String a, String b, String c) {
        int x = a.indexOf(b) + b.length();
        int y = a.indexOf(c);
        return a.substring(x, y);
    }

    public static void message_read(String info) {
        if (logs.Group_log == true) {
            logs logs = new logs();
            logs.log_write("[Group]" + info);
        }
        if (socket.socket_runFlag == false)
            return;
        while (info.indexOf(config_bukkit.Head) == 0 && info.indexOf(config_bukkit.End) != -1) {
            String buff = get_string(info, config_bukkit.Head, config_bukkit.End);
            Gson read_gson = new Gson();
            Read_Json read_bean = read_gson.fromJson(buff, Read_Json.class);
            if (read_bean.getIs_commder() == "false") {
                String a = read_bean.getMessage();
                if (a.indexOf("说话") == 0) {
                    a = a.replaceFirst("说话", "");
                    String say = config_bukkit.Minecraft_Say.replaceFirst("%Servername%", config_bukkit.Minecraft_ServerName).replaceFirst("%Message%", a);
                    say = ChatColor.translateAlternateColorCodes('&', say);
                    Bukkit.broadcastMessage(say);
                } else if (a.indexOf("在线人数") == 0) {
                    String player;
                    String send = config_bukkit.Minecraft_PlayerListMessage;
                    player = Bukkit.getOnlinePlayers().toString();
                    if (player == "[]") {
                        try {
                            send = send.replaceAll("%Servername%", config_bukkit.Minecraft_ServerName)
                                    .replaceAll("%player_number%", "0")
                                    .replaceAll("%player_list%", "无");
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
                        player = player.replaceAll("\\}", "");

                        send = send.replaceAll("%Servername%", config_bukkit.Minecraft_ServerName)
                                .replaceAll("%player_number%", "" + player_number)
                                .replaceAll("%player_list%", player);
                    }
                    socket_send.send_data("data", read_bean.getGroup(), "无", send);
                    if (logs.Group_log == true) {
                        logs logs = new logs();
                        logs.log_write("[group]查询在线人数");
                    }
                    if (config_bukkit.System_Debug == true)
                        config.log.info("§d[Minecraft_QQ]§5[Debug]查询在线人数");
                } else if (info.indexOf("服务器状态") == 0) {
                    String send = config_bukkit.Minecraft_ServerOnlineMessage;
                    send = send.replaceAll("%Servername%", config_bukkit.Minecraft_ServerName);
                    socket_send.send_data("data", read_bean.getGroup(), "无", send);
                    if (logs.Group_log == true) {
                        logs logs = new logs();
                        logs.log_write("[group]查询服务器状态");
                    }
                    if (config_bukkit.System_Debug == true)
                        config.log.info("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
                }
                int i = info.indexOf(config_bukkit.End);
                info = info.substring(i + config_bukkit.End.length());
            }
        }
    }
}
