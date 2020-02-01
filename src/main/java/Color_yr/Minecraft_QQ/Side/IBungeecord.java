package Color_yr.Minecraft_QQ.Side;

import Color_yr.Minecraft_QQ.API.IMinecraft_QQ;
import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.BungeeCord;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
import Color_yr.Minecraft_QQ.Json.Read_Json;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import Color_yr.Minecraft_QQ.Utils;
import Color_yr.Minecraft_QQ.logs;
import com.google.gson.Gson;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

import static Color_yr.Minecraft_QQ.BungeeCord.config_data_bungee;

public class IBungeecord implements IMinecraft_QQ {
    public void Log_System(String message) {
        BungeeCord.log_b.info(message);
    }

    @Override
    public void Message(String message) {
        try {
            String msg = message;
            if (logs.Group_log) {
                logs.log_write("[Group]" + msg);
            }
            if (BaseConfig.SystemDebug)
                Log_System("处理数据：" + msg);
            if (!use.hand.socket_runFlag)
                return;
            ProxyServer proxyserver = ProxyServer.getInstance();
            while (msg.indexOf(BaseConfig.Head) == 0 && msg.contains(BaseConfig.End)) {
                String buff = Utils.get_string(msg, BaseConfig.Head, BaseConfig.End);
                Read_Json read_bean;
                try {
                    Gson read_gson = new Gson();
                    read_bean = read_gson.fromJson(buff, Read_Json.class);
                } catch (Exception e) {
                    Log_System("数据传输发生错误:" + e.getMessage());
                    return;
                }
                if (read_bean.getIs_commder().equals("false")) {
                    if (read_bean.getCommder().equalsIgnoreCase("speak")) {
                        String say = BaseConfig.MinecraftSay.replaceFirst(Placeholder.Servername, BaseConfig.MinecraftServerName)
                                .replaceFirst(Placeholder.Message, read_bean.getMessage());
                        say = ChatColor.translateAlternateColorCodes('&', say);
                        for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                            if (!BaseConfig.MuteList.contains(player1.getName()))
                                player1.sendMessage(new TextComponent(say));
                        }
                    } else if (read_bean.getCommder().equalsIgnoreCase("online")) {
                        int all_player_number = 0;
                        String one_server_player = "";
                        StringBuilder all_server_player = new StringBuilder();
                        String send = BaseConfig.MinecraftPlayerListMessage;
                        if (config_data_bungee.MinecraftSendOneByOne) {
                            final Map<String, ServerInfo> Server = proxyserver.getServers();
                            final Collection<ServerInfo> values = Server.values();
                            for (final ServerInfo serverinfo : values) {
                                final String player_onserver = serverinfo.getPlayers().toString();
                                if (player_onserver.equals("[]")) {
                                    int one_player_number = 0;
                                    if (config_data_bungee.MinecraftHideEmptyServer) {
                                        one_server_player = "";
                                        one_player_number = 0;
                                    } else {
                                        String Server_name = config_data_bungee.config.getString("Servers." + serverinfo.getName());
                                        if (Server_name.equals("")) {
                                            Server_name = serverinfo.getName().replace("null", "");
                                        }
                                        one_server_player = config_data_bungee.MinecraftSendOneByOneMessage
                                                .replaceAll(Placeholder.Server, Server_name)
                                                .replaceAll(Placeholder.player_number, "0")
                                                .replaceAll(Placeholder.player_list, "无");
                                        all_server_player.append(one_server_player);
                                    }
                                } else {
                                    int one_player_number = 1;
                                    for (int i = 0; i < player_onserver.length(); ++i) {
                                        if (player_onserver.charAt(i) == ',') {
                                            ++one_player_number;
                                        }
                                    }
                                    String Server_name = config_data_bungee.config.getString("Servers." + serverinfo.getName());
                                    if (Server_name.equals("")) {
                                        Server_name = serverinfo.getName().replace("null", "");
                                    }
                                    one_server_player = config_data_bungee.MinecraftSendOneByOneMessage
                                            .replaceAll(Placeholder.Server, Server_name)
                                            .replaceAll(Placeholder.player_number, "" + one_player_number)
                                            .replaceAll(Placeholder.player_list, player_onserver.replace("[", "")
                                                    .replace("]", ""));
                                    all_player_number += one_player_number;
                                    all_server_player.append(one_server_player);
                                }
                            }
                            if (all_player_number == 0) {
                                if (config_data_bungee.MinecraftHideList)
                                    send = send.replaceAll(Placeholder.player_number, "");
                                else
                                    send = send.replaceAll(Placeholder.player_number, "0");
                                send = send.replaceAll(Placeholder.player_list, "无");
                            } else {
                                send = send.replaceAll(Placeholder.player_number, "" + all_player_number)
                                        .replaceAll(Placeholder.player_list, all_server_player.toString().replace("null", ""));
                            }
                        } else {
                            all_server_player = new StringBuilder(proxyserver.getPlayers().toString());
                            if (all_server_player.toString().equals("[]")) {
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
                                        .replaceAll(Placeholder.player_list, all_server_player.toString().replace("[", "").replace("]", ""));
                            }
                        }
                        send = send.replace(Placeholder.Servername, BaseConfig.MinecraftServerName);
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                        if (logs.Group_log) {
                            logs.log_write("[group]查询在线人数");
                        }
                    } else if (read_bean.getCommder().equalsIgnoreCase("server")) {
                        String send = BaseConfig.MinecraftServerOnlineMessage
                                .replaceAll(Placeholder.Servername, BaseConfig.MinecraftServerName);
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                        if (logs.Group_log) {
                            logs.log_write("[group]查询服务器状态");
                        }
                    }
                } else if (read_bean.getIs_commder().equals("true")) {
                    StringBuilder send_message;
                    Command send = new Command();
                    send.setPlayer(read_bean.getPlayer());
                    try {
                        proxyserver.getPluginManager().dispatchCommand(send, read_bean.getCommder());
                    } catch (Exception e) {
                        Log_System(e.toString());
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
                }
                int i = msg.indexOf(BaseConfig.End);
                msg = msg.substring(i + BaseConfig.End.length());
            }
        } catch (Exception e) {
            Log_System("发生错误" + e.getMessage());
        }
    }

    public class Command implements CommandSender {
        public List<String> message = new ArrayList<String>();
        public String player;

        public void setPlayer(String player) {
            this.player = player;
        }

        public List<String> getMessage() {
            return message;
        }

        @Override
        public String getName() {
            return player;
        }

        @Override
        public void sendMessage(String message) {
            this.message.add(message);
        }

        @Override
        public void sendMessages(String... messages) {
            message.addAll(Arrays.asList(messages));
        }

        @Override
        public void sendMessage(BaseComponent... message) {
            this.message.add(Arrays.toString(message));
        }

        @Override
        public void sendMessage(BaseComponent message) {
            this.message.add(message.toLegacyText());
        }

        @Override
        public Collection<String> getGroups() {
            return null;
        }

        @Override
        public void addGroups(String... groups) {

        }

        @Override
        public void removeGroups(String... groups) {

        }

        @Override
        public boolean hasPermission(String permission) {
            return true;
        }

        @Override
        public void setPermission(String permission, boolean value) {

        }

        @Override
        public Collection<String> getPermissions() {
            return null;
        }
    }
}
