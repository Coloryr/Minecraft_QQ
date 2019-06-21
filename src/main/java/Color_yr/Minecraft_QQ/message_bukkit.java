package Color_yr.Minecraft_QQ;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

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
            Read_Json read_bean;
            try {
                Gson read_gson = new Gson();
                read_bean = read_gson.fromJson(buff, Read_Json.class);
            } catch (Exception e) {
                config.log.warning("数据传输发生错误:" + e.getMessage());
                return;
            }
            if (read_bean.getIs_commder().equals("false") == true) {
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
                    if (player.equals("[]")) {
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
                } else if (a.indexOf("服务器状态") == 0) {
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
            } else if (read_bean.getIs_commder().equals("true") == true) {
                String send_message;
                send_bukkit.player = read_bean.getPlayer();
                try {
                    Bukkit.getScheduler().callSyncMethod(Minecraft_QQ_bukkit.Minecraft_QQ, new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            return Bukkit.dispatchCommand(send_bukkit.sender, read_bean.getMessage());
                        }
                    }).get();
                } catch (Exception e) {
                    config.log.warning(e.toString());
                }
                if (send_bukkit.message.size() == 1) {
                    send_message = send_bukkit.message.get(0);
                } else if (send_bukkit.message.size() > 1) {
                    send_message = send_bukkit.message.get(0);
                    for (int i = 1; i < send_bukkit.message.size(); i++) {
                        send_message = send_message + "\n";
                        send_message = send_message + send_bukkit.message.get(i);
                    }
                } else
                    send_message = "指令执行失败";
                socket_send.send_data("data", read_bean.getGroup(),
                        "控制台", send_message);
                send_bukkit.message.clear();
            }
            int i = info.indexOf(config_bukkit.End);
            info = info.substring(i + config_bukkit.End.length());
        }
    }
}

class send_bukkit {
    public static List<String> message = new ArrayList<String>();
    public static String player;
    public static CommandSender sender = new CommandSender() {
        @Override
        public void sendMessage(String message) {
            send_bukkit.message.add(message);
        }

        @Override
        public void sendMessage(String[] messages) {
            for (int i = 0; i < messages.length; i++)
                send_bukkit.message.add(messages[i]);
        }

        @Override
        public Server getServer() {
            return Bukkit.getServer();
        }

        @Override
        public String getName() {
            return player;
        }

        @Override
        public boolean isPermissionSet(String name) {
            return true;
        }

        @Override
        public boolean isPermissionSet(Permission perm) {
            return true;
        }

        @Override
        public boolean hasPermission(String name) {
            return true;
        }

        @Override
        public boolean hasPermission(Permission perm) {
            return true;
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
            return Bukkit.getConsoleSender().addAttachment(plugin, name, value);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin) {
            return Bukkit.getConsoleSender().addAttachment(plugin);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
            return Bukkit.getConsoleSender().addAttachment(plugin, name, value, ticks);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
            return Bukkit.getConsoleSender().addAttachment(plugin, ticks);
        }

        @Override
        public void removeAttachment(PermissionAttachment attachment) {

        }

        @Override
        public void recalculatePermissions() {

        }

        @Override
        public Set<PermissionAttachmentInfo> getEffectivePermissions() {
            return Bukkit.getConsoleSender().getEffectivePermissions();
        }

        @Override
        public boolean isOp() {
            return true;
        }

        @Override
        public void setOp(boolean value) {

        }
    };
}
