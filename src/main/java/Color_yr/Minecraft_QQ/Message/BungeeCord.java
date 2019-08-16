package Color_yr.Minecraft_QQ.Message;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Config.Bukkit;
import Color_yr.Minecraft_QQ.Json.Read_Json;
import Color_yr.Minecraft_QQ.Socket.socket;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import Color_yr.Minecraft_QQ.Log.logs;
import com.google.gson.Gson;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.Map;

import static Color_yr.Minecraft_QQ.Main.Bungeecord.config_data_bungee;

public class BungeeCord extends Thread{
    public String get_string(String a, String b, String c) {
        int x = a.indexOf(b) + b.length();
        int y = a.indexOf(c);
        return a.substring(x, y);
    }
    @Override
    public void run() {
        config.log_b.info("线程开始");
        while (true) {
            try {
                while (socket.hand.have_message == false) {
                    Thread.sleep(Bukkit.System_Sleep);
                }
                socket.hand.have_message = false;
                String msg = socket.hand.info;
                socket.hand.info = null;
                if (logs.Group_log == true) {
                    logs logs = new logs();
                    logs.log_write("[Group]" + msg);
                }
                if(Bukkit.System_Debug == true)
                    config.log_b.info("处理数据：" + msg);
                if (socket.hand.socket_runFlag == false)
                    return;
                ProxyServer proxyserver = ProxyServer.getInstance();
                while (msg.indexOf(Bukkit.Head) == 0 && msg.indexOf(Bukkit.End) != -1) {
                    String buff = get_string(msg, Bukkit.Head, Bukkit.End);
                    Read_Json read_bean;
                    try {
                        Gson read_gson = new Gson();
                        read_bean = read_gson.fromJson(buff, Read_Json.class);
                    } catch (Exception e) {
                        config.log_b.warning("数据传输发生错误:" + e.getMessage());
                        return;
                    }
                    if (read_bean.getIs_commder().equals("false")) {
                        String a = read_bean.getMessage();
                        if (a.indexOf("说话") == 0) {
                            a = a.replaceFirst("说话", "");
                            String say = Bukkit.Minecraft_Say.replaceFirst(Placeholder.Servername, Bukkit.Minecraft_ServerName).replaceFirst("%Message%", a);
                            say = ChatColor.translateAlternateColorCodes('&', say);
                            for (final ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                                player1.sendMessage(new TextComponent(say));
                            }
                        } else if (a.indexOf("在线人数") == 0) {
                            int all_player_number = 0;
                            String one_server_player = "";
                            String all_server_player = null;
                            String send = Bukkit.Minecraft_PlayerListMessage;
                            if (config_data_bungee.Minecraft_SendOneByOne) {
                                final Map<String, ServerInfo> Server = proxyserver.getServers();
                                final Collection<ServerInfo> values = Server.values();
                                for (final ServerInfo serverinfo : values) {
                                    final String player_onserver = serverinfo.getPlayers().toString();
                                    if (player_onserver.equals("[]")) {
                                        int one_player_number = 0;
                                        if (config_data_bungee.Minecraft_HideEmptyServer) {
                                            one_player_number = 0;
                                            one_server_player = "";
                                        } else {
                                            String Server_name = config_data_bungee.config.getString("Servers." + serverinfo.getName());
                                            if (Server_name.equals("") || Server_name == null) {
                                                Server_name = serverinfo.getName().replace("null", "");
                                            }
                                            one_server_player = config_data_bungee.Minecraft_SendOneByOneMessage
                                                    .replaceAll(Placeholder.Server, Server_name)
                                                    .replaceAll(Placeholder.player_number, "0")
                                                    .replaceAll(Placeholder.player_list, "无");
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
                                        if (Server_name.equals("") || Server_name == null) {
                                            Server_name = serverinfo.getName().replace("null", "");
                                        }
                                        one_server_player = config_data_bungee.Minecraft_SendOneByOneMessage
                                                .replaceAll(Placeholder.Server, Server_name)
                                                .replaceAll(Placeholder.player_number, "" + one_player_number)
                                                .replaceAll(Placeholder.player_list, player_onserver.replace("[", "")
                                                        .replace("]", ""));
                                        all_player_number += one_player_number;
                                        all_server_player += one_server_player;
                                    }
                                }
                                if (all_player_number == 0) {
                                    if (config_data_bungee.Minecraft_HideList == true)
                                        send = send.replaceAll(Placeholder.player_number, "");
                                    else
                                        send = send.replaceAll(Placeholder.player_number, "0");
                                    send = send.replaceAll(Placeholder.player_list, "无");
                                } else {
                                    send = send.replaceAll(Placeholder.player_number, "" + all_player_number)
                                            .replaceAll(Placeholder.player_list, all_server_player.replace("null", ""));
                                }
                            } else {
                                all_server_player = proxyserver.getPlayers().toString();
                                if (all_server_player.equals("[]")) {
                                    send = send.replaceAll(Placeholder.player_number, "0")
                                            .replaceAll(Placeholder.player_list, "无");
                                } else {
                                    for (int j = 0; j < all_server_player.length(); ++j) {
                                        if (all_server_player.charAt(j) == ',') {
                                            ++all_player_number;
                                        }
                                    }
                                    final String number = String.valueOf(all_player_number);
                                    send = send.replaceAll(Placeholder.player_number, number)
                                            .replaceAll(Placeholder.player_list, all_server_player.replace("[", "").replace("]", ""));
                                }
                            }
                            send = send.replace(Placeholder.Servername, Bukkit.Minecraft_ServerName);
                            socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                            if (logs.Group_log) {
                                final logs logs2 = new logs();
                                logs2.log_write("[group]查询在线人数");
                            }
                        } else if (a.indexOf("服务器状态") == 0) {
                            String send = Bukkit.Minecraft_ServerOnlineMessage
                                    .replaceAll(Placeholder.Servername, Bukkit.Minecraft_ServerName);
                            socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                            if (logs.Group_log == true) {
                                new logs().log_write("[group]查询服务器状态");
                            }
                        }
                    } else if (read_bean.getIs_commder().equals("true") == true) {
                        String send_message;
                        send_bungee send = new send_bungee();
                        send.setPlayer(read_bean.getPlayer());
                        try {
                            proxyserver.getPluginManager().dispatchCommand(send.sender, read_bean.getMessage());
                        } catch (Exception e) {
                            config.log_b.warning(e.toString());
                        }
                        if (send.getMessage().size() == 1) {
                            send_message = send.getMessage().get(0);
                        } else if (send.getMessage().size() > 1) {
                            send_message = send.getMessage().get(0);
                            for (int i = 1; i < send.getMessage().size(); i++) {
                                send_message = send_message + "\n";
                                send_message = send_message + send.getMessage().get(i);
                            }
                        } else
                            send_message = "指令执行失败";
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(),
                                "控制台", send_message);
                        send.clear();
                    }
                    int i = msg.indexOf(Bukkit.End);
                    msg = msg.substring(i + Bukkit.End.length());
                }
                socket.hand.is_can_go = true;
            } catch (Exception e) {
                config.log_b.warning("发生错误" + e.getMessage());
            }
            socket.hand.is_can_go = true;
        }
    }
}
