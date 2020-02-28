package Color_yr.Minecraft_QQ.Side;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Minecraft_QQBukkit;
import Color_yr.Minecraft_QQ.Json.ReadOBJ;
import Color_yr.Minecraft_QQ.Socket.socketSend;
import Color_yr.Minecraft_QQ.Utils.Function;
import Color_yr.Minecraft_QQ.Utils.logs;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class IBukkit implements IMinecraft_QQ {

    @Override
    public void LogInfo(String message) {
        Minecraft_QQBukkit.log_b.info(message);
    }
    @Override
    public void LogError(String message) {
        Minecraft_QQBukkit.log_b.warning(message);
    }
    @Override
    public void Message(String message) {
        try {
            String msg = message;
            if (Minecraft_QQ.Config.getSystem().isDebug())
                LogInfo("处理数据：" + msg);
            if (!Minecraft_QQ.hand.socket_runFlag)
                return;
            while (msg.indexOf(Minecraft_QQ.Config.getSystem().getHead()) == 0 && msg.contains(Minecraft_QQ.Config.getSystem().getEnd())) {
                String buff = Function.get_string(msg, Minecraft_QQ.Config.getSystem().getHead(), Minecraft_QQ.Config.getSystem().getEnd());
                ReadOBJ readobj;
                try {
                    Gson read_gson = new Gson();
                    readobj = read_gson.fromJson(buff, ReadOBJ.class);
                } catch (Exception e) {
                    LogInfo("数据传输发生错误:" + e.getMessage());
                    return;
                }
                if (readobj.getIs_commder().equals("false")) {
                    if (readobj.getCommder().equalsIgnoreCase("speak")) {
                        String say = Minecraft_QQ.Config.getServerSet().getSay()
                                .replaceFirst(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                                .replaceFirst(Minecraft_QQ.Config.getPlaceholder().getMessage(), readobj.getMessage());
                        say = ChatColor.translateAlternateColorCodes('&', say);
                        final String finalSay = say;
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.log_write("[Group]" + say);
                        }
                        org.bukkit.Bukkit.getScheduler().runTask(Minecraft_QQBukkit.plugin, () ->
                        {
                            try {
                                for (Player b : org.bukkit.Bukkit.getOnlinePlayers()) {
                                    if (!Minecraft_QQ.Config.getMute().contains(b.getName()))
                                        b.sendMessage(finalSay);
                                }
                            } catch (Exception e) {
                                LogInfo(e.toString());
                            }
                        });
                    } else if (readobj.getCommder().equalsIgnoreCase("online")) {
                        String player;
                        String send = Minecraft_QQ.Config.getServerSet().getPlayerListMessage();
                        player = org.bukkit.Bukkit.getOnlinePlayers().toString();
                        if (player.equals("[]")) {
                            try {
                                send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "0")
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), "")
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), "无");
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

                            send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "" + player_number)
                                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), "")
                                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), player);
                        }
                        socketSend.send_data(Placeholder.data, readobj.getGroup(), "无", send);
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.log_write("[group]查询在线人数");
                        }
                        if (Minecraft_QQ.Config.getSystem().isDebug())
                            LogInfo("§d[Minecraft_QQ]§5[Debug]查询在线人数");
                    } else if (readobj.getCommder().equalsIgnoreCase("server")) {
                        String send = Minecraft_QQ.Config.getServerSet().getServerOnlineMessage();
                        send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName());
                        socketSend.send_data(Placeholder.data, readobj.getGroup(), "无", send);
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.log_write("[group]查询服务器状态");
                        }
                        if (Minecraft_QQ.Config.getSystem().isDebug())
                            LogInfo("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
                    }
                } else if (readobj.getIs_commder().equals("true")) {
                    StringBuilder send_message;
                    Command send = new Command();
                    send.setPlayer(readobj.getPlayer());
                    try {
                        Bukkit.getScheduler().callSyncMethod(Minecraft_QQBukkit.plugin, () ->
                                Bukkit.dispatchCommand(send, readobj.getCommder())).get();
                    } catch (Exception e) {
                        LogError("§d[Minecraft_QQ]§c指令执行出现错误");
                        e.printStackTrace();
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
                    socketSend.send_data(Placeholder.data, readobj.getGroup(),
                            "控制台", send_message.toString());
                }
                int i = msg.indexOf(Minecraft_QQ.Config.getSystem().getEnd());
                msg = msg.substring(i + Minecraft_QQ.Config.getSystem().getEnd().length());
            }
        } catch (Exception e) {
            LogInfo("发送错误：" + e.getMessage());
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
            return Bukkit.getServer();
        }

        @Override
        public String getName() {
            return player;
        }

        @Override
        public Spigot spigot() {
            return null;
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
    }
}
