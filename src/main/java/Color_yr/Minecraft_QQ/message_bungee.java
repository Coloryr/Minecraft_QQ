package Color_yr.Minecraft_QQ;

import java.util.Collection;
import java.util.Map;

import com.google.gson.Gson;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import static Color_yr.Minecraft_QQ.Minecraft_QQ_bungee.config_data_bungee;

public class message_bungee {

    public String get_string(String a, String b, String c) {
        int x = a.indexOf(b) + b.length();
        int y = a.indexOf(c);
        return a.substring(x, y);
    }

    public void message_read(String info) {
        if (logs.Group_log == true) {
            logs logs = new logs();
            logs.log_write("[Group]" + info);
        }
        if (socket.socket_runFlag == false)
            return;
        ProxyServer proxyserver = ProxyServer.getInstance();
        while (info.indexOf(config_bukkit.Head) == 0 && info.indexOf(config_bukkit.End) != -1) {
            String buff = get_string(info, config_bukkit.Head, config_bukkit.End);
            Read_Json read_bean;
            try {
                Gson read_gson = new Gson();
                read_bean = read_gson.fromJson(buff, Read_Json.class);
            }catch(Exception e){
                config.log.warning("数据传输发生错误:" + e.getMessage());
                return;
            }
            if (read_bean.getIs_commder() == "false") {
                String a = read_bean.getMessage();
                if (a.indexOf("说话") == 0) {
                    a = a.replaceFirst("说话", "");
                    String say = config_bukkit.Minecraft_Say.replaceFirst("%Servername%", config_bukkit.Minecraft_ServerName).replaceFirst("%Message%", a);
                    say = ChatColor.translateAlternateColorCodes('&', say);
                    for (final ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                        player1.sendMessage(new TextComponent(say));
                    }
                } else if (a.indexOf("在线人数") == 0) {
                    int all_player_number = 0;
                    String one_server_player = "";
                    String all_server_player = null;
                    String send = config_bukkit.Minecraft_PlayerListMessage;
                    if (config_data_bungee.Minecraft_SendOneByOne) {
                        final Map<String, ServerInfo> Server = proxyserver.getServers();
                        final Collection<ServerInfo> values = Server.values();
                        for (final ServerInfo serverinfo : values) {
                            final String player_onserver = serverinfo.getPlayers().toString();
                            if (player_onserver == "[]") {
                                int one_player_number = 0;
                                if (config_data_bungee.Minecraft_HideEmptyServer) {
                                    one_player_number = 0;
                                    one_server_player = "";
                                } else {
                                    String Server_name = config_data_bungee.config.getString("Servers." + serverinfo.getName());
                                    if (Server_name == "" || Server_name == null) {
                                        Server_name = serverinfo.getName();
                                        Server_name = Server_name.replace("null", "");
                                    }
                                    one_server_player = config_data_bungee.Minecraft_SendOneByOneMessage
                                            .replaceAll("%Server%", Server_name)
                                            .replaceAll("%player_number%", "0")
                                            .replaceAll("%player_list%", "无");
                                    all_server_player += one_server_player;
                                }
                            } else {
                                int one_player_number = 1;
                                for (int i = 0; i < player_onserver.length(); ++i) {
                                    if (player_onserver.charAt(i) == ',') {
                                        ++one_player_number;
                                    }
                                }
                                String Server_name = config_data_bungee.config.getString("Servers." + serverinfo.getName());
                                if (Server_name == "" || Server_name == null) {
                                    Server_name = serverinfo.getName();
                                    Server_name = Server_name.replace("null", "");
                                }
                                one_server_player = config_data_bungee.Minecraft_SendOneByOneMessage
                                        .replaceAll("%Server%", Server_name)
                                        .replaceAll("%player_number%", "" + one_player_number)
                                        .replaceAll("%player_list%", player_onserver.replace("[", "")
                                                .replace("]", ""));
                                all_player_number += one_player_number;
                                all_server_player += one_server_player;
                            }
                        }
                        if (all_player_number == 0) {
                            if (config_data_bungee.Minecraft_HideList == true)
                                send = send.replaceAll("%player_number%", "");
                            else
                                send = send.replaceAll("%player_number%", "0");
                            send = send.replaceAll("%player_list%", "无");
                        } else {
                            send = send.replaceAll("%player_number%", "" + all_player_number);
                            send = send.replaceAll("%player_list%", all_server_player.replace("null", ""));
                        }
                    } else {
                        all_server_player = proxyserver.getPlayers().toString();
                        if (all_server_player == "[]") {
                            send = send.replaceAll("%player_number%", "0");
                            send = send.replaceAll("%player_list%", "无");
                        } else {
                            for (int j = 0; j < all_server_player.length(); ++j) {
                                if (all_server_player.charAt(j) == ',') {
                                    ++all_player_number;
                                }
                            }
                            final String number = "" + all_player_number;
                            send = send.replaceAll("%player_number%", number);
                            send = send.replaceAll("%player_list%", all_server_player.replace("[", "").replace("]", ""));
                        }
                    }
                    send = send.replace("%Servername%", config_bukkit.Minecraft_ServerName);
                    socket_send.send_data("data", read_bean.getGroup(), "无", send);
                    if (logs.Group_log) {
                        final logs logs2 = new logs();
                        logs2.log_write("[group]查询在线人数");
                    }
                } else if (a.indexOf("服务器状态") == 0) {
                    String send = config_bukkit.Minecraft_ServerOnlineMessage;
                    send = send.replaceAll("%Servername%", config_bukkit.Minecraft_ServerName);
                    socket_send.send_data("data", read_bean.getGroup(), "无", send);
                    if (logs.Group_log == true) {
                        logs logs = new logs();
                        logs.log_write("[group]查询服务器状态");
                    }
                }
            } else {
                socket_send.send_data("data", read_bean.getGroup(),
                        "无", "BC不支持使用指令");
            }
            int i = info.indexOf(config_bukkit.End);
            info = info.substring(i + config_bukkit.End.length());
        }
    }
}
