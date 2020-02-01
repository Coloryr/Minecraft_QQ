package Color_yr.Minecraft_QQ.Side;

import Color_yr.Minecraft_QQ.API.IMinecraft_QQ;
import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Bukkit;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
import Color_yr.Minecraft_QQ.Json.Read_Json;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import Color_yr.Minecraft_QQ.Utils;
import Color_yr.Minecraft_QQ.logs;
import com.google.gson.Gson;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class IBukkit implements IMinecraft_QQ {

    public void Log_System(String message) {
        Bukkit.log_b.info(message);
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
                        final String finalSay = say;
                        org.bukkit.Bukkit.getScheduler().runTask(Color_yr.Minecraft_QQ.Bukkit.Minecraft_QQ, () ->
                        {
                            try {
                                for (Player b : org.bukkit.Bukkit.getOnlinePlayers()) {
                                    if (!BaseConfig.MuteList.contains(b.getName()))
                                        b.sendMessage(finalSay);
                                }
                            } catch (Exception e) {
                                Log_System(e.toString());
                            }
                        });
                    } else if (read_bean.getCommder().equalsIgnoreCase("online")) {
                        String player;
                        String send = BaseConfig.MinecraftPlayerListMessage;
                        player = org.bukkit.Bukkit.getOnlinePlayers().toString();
                        if (player.equals("[]")) {
                            try {
                                send = send.replaceAll(Placeholder.Servername, BaseConfig.MinecraftServerName)
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

                            send = send.replaceAll(Placeholder.Servername, BaseConfig.MinecraftServerName)
                                    .replaceAll(Placeholder.player_number, "" + player_number)
                                    .replaceAll(Placeholder.player_list, player);
                        }
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                        if (logs.Group_log) {
                            logs.log_write("[group]查询在线人数");
                        }
                        if (BaseConfig.SystemDebug)
                            Log_System("§d[Minecraft_QQ]§5[Debug]查询在线人数");
                    } else if (read_bean.getCommder().equalsIgnoreCase("server")) {
                        String send = BaseConfig.MinecraftServerOnlineMessage;
                        send = send.replaceAll(Placeholder.Servername, BaseConfig.MinecraftServerName);
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                        if (logs.Group_log) {
                            logs.log_write("[group]查询服务器状态");
                        }
                        if (BaseConfig.SystemDebug)
                            Log_System("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
                    }
                } else if (read_bean.getIs_commder().equals("true")) {
                    StringBuilder send_message;
                    Command send = new Command();
                    send.setPlayer(read_bean.getPlayer());
                    try {
                        org.bukkit.Bukkit.getScheduler().callSyncMethod(Bukkit.Minecraft_QQ, () ->
                                org.bukkit.Bukkit.dispatchCommand(send, read_bean.getCommder())).get();
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
            Log_System("发送错误：" + e.getMessage());
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
        public void sendMessage(String message) {
            this.message.add(message);
        }

        @Override
        public void sendMessage(String[] messages) {
            message.addAll(Arrays.asList(messages));
        }

        @Override
        public Server getServer() {
            return org.bukkit.Bukkit.getServer();
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
            return org.bukkit.Bukkit.getConsoleSender().addAttachment(plugin, name, value);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin) {
            return org.bukkit.Bukkit.getConsoleSender().addAttachment(plugin);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
            return org.bukkit.Bukkit.getConsoleSender().addAttachment(plugin, name, value, ticks);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
            return org.bukkit.Bukkit.getConsoleSender().addAttachment(plugin, ticks);
        }

        @Override
        public void removeAttachment(PermissionAttachment attachment) {

        }

        @Override
        public void recalculatePermissions() {

        }

        @Override
        public Set<PermissionAttachmentInfo> getEffectivePermissions() {
            return org.bukkit.Bukkit.getConsoleSender().getEffectivePermissions();
        }

        @Override
        public boolean isOp() {
            return true;
        }

        @Override
        public void setOp(boolean value) {

        }
    }
}
