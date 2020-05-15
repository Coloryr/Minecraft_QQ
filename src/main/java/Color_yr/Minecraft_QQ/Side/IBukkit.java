package Color_yr.Minecraft_QQ.Side;

import Color_yr.Minecraft_QQ.API.IMinecraft_QQ;
import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Json.ReadOBJ;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Minecraft_QQBukkit;
import Color_yr.Minecraft_QQ.Utils.Function;
import Color_yr.Minecraft_QQ.Utils.logs;
import com.google.gson.Gson;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class IBukkit implements IMinecraft_QQ {

    @Override
    public void logInfo(String message) {
        Minecraft_QQBukkit.log_b.info(message);
    }

    @Override
    public void logError(String message) {
        Minecraft_QQBukkit.log_b.warning(message);
    }

    @Override
    public void send(Object sender, String message) {
        CommandSender temp = (CommandSender) sender;
        temp.sendMessage(message);
    }


    @Override
    public void message(String message) {
        try {
            String msg = message;
            if (Minecraft_QQ.Config.getSystem().isDebug())
                logInfo("处理数据：" + msg);
            while (msg.indexOf(Minecraft_QQ.Config.getSystem().getHead()) == 0 && msg.contains(Minecraft_QQ.Config.getSystem().getEnd())) {
                String buff = Function.get_string(msg, Minecraft_QQ.Config.getSystem().getHead(), Minecraft_QQ.Config.getSystem().getEnd());
                ReadOBJ readobj;
                try {
                    Gson read_gson = new Gson();
                    readobj = read_gson.fromJson(buff, ReadOBJ.class);
                } catch (Exception e) {
                    Minecraft_QQ.Side.logError("§d[Minecraft_QQ]§c发生错误：");
                    e.printStackTrace();
                    return;
                }
                if (readobj.getIsCommand().equals("false") && !Minecraft_QQ.Config.getServerSet().isBungeeCord()) {
                    if (readobj.getCommand().equalsIgnoreCase(Placeholder.speak)) {
                        String say = Minecraft_QQ.Config.getServerSet().getSay()
                                .replaceFirst(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                                .replaceFirst(Minecraft_QQ.Config.getPlaceholder().getMessage(), readobj.getMessage())
                                .replaceFirst(Minecraft_QQ.Config.getPlaceholder().getPlayer(), readobj.getPlayer());
                        say = ChatColor.translateAlternateColorCodes('&', say);
                        if (Minecraft_QQBukkit.PAPI && readobj.getPlayer() != null) {
                            Player player = Bukkit.getPlayer(readobj.getPlayer());
                            if (player != null)
                                say = PlaceholderAPI.setBracketPlaceholders(player, say);
                        }
                        final String finalSay = say;
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.logWrite("[Group]" + say);
                        }
                        Bukkit.getScheduler().runTask(Minecraft_QQBukkit.plugin, () ->
                        {
                            try {
                                for (Player b : Bukkit.getOnlinePlayers()) {
                                    if (!Minecraft_QQ.Config.getMute().contains(b.getName()))
                                        b.sendMessage(finalSay);
                                }
                            } catch (Exception e) {
                                Minecraft_QQ.Side.logError("§d[Minecraft_QQ]§c发生错误：");
                                e.printStackTrace();
                            }
                        });
                    } else if (readobj.getCommand().equalsIgnoreCase(Placeholder.online)) {
                        String send = Minecraft_QQ.Config.getServerSet().getPlayerListMessage();
                        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                        if (players.size() == 0) {
                            try {
                                send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "0")
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), "")
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), "无");
                            } catch (Exception e) {
                                Minecraft_QQ.Side.logError("§d[Minecraft_QQ]§c发生错误：");
                                e.printStackTrace();
                            }
                        } else {
                            StringBuilder temp = new StringBuilder();
                            for (Player player1 : players) {
                                temp.append(player1.getName()).append(",");
                            }
                            String player = temp.toString();

                            send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "" + players.size())
                                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), "")
                                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), player.substring(0, player.length() - 1));
                        }
                        if (Minecraft_QQBukkit.PAPI && readobj.getPlayer() != null) {
                            Player player1 = Bukkit.getPlayer(readobj.getPlayer());
                            if (player1 != null)
                                send = PlaceholderAPI.setBracketPlaceholders(player1, send);
                        }
                        if (Minecraft_QQ.Config.getLogs().isGroup())
                            logs.logWrite("[group]查询在线人数");
                        if (Minecraft_QQ.Config.getSystem().isDebug())
                            logInfo("§d[Minecraft_QQ]§5[Debug]查询在线人数");
                        Minecraft_QQ.control.sendData(Placeholder.data, readobj.getGroup(), "无", send);
                    } else {
                        ASide.globeCheck(readobj);
                    }
                } else if (readobj.getIsCommand().equals("true")) {
                    StringBuilder send_message;
                    Command send = new Command();
                    send.setPlayer(readobj.getPlayer());
                    if (Minecraft_QQ.Config.getLogs().isGroup()) {
                        logs.logWrite("[Group]" + readobj.getPlayer() + "执行命令" + readobj.getCommand());
                    }
                    try {
                        Bukkit.getScheduler().callSyncMethod(Minecraft_QQBukkit.plugin, () ->
                                Bukkit.dispatchCommand(send, readobj.getCommand())).get();
                        Thread.sleep(Minecraft_QQ.Config.getServerSet().getCommandDelay());
                    } catch (Exception e) {
                        logError("§d[Minecraft_QQ]§c指令执行出现错误");
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
                        send_message = new StringBuilder("已执行，指令无返回");
                    Minecraft_QQ.control.sendData(Placeholder.data, readobj.getGroup(), "控制台", send_message.toString());
                }
                int i = msg.indexOf(Minecraft_QQ.Config.getSystem().getEnd());
                msg = msg.substring(i + Minecraft_QQ.Config.getSystem().getEnd().length());
            }
        } catch (Exception e) {
            Minecraft_QQ.Side.logError("§d[Minecraft_QQ]§c发生错误：");
            e.printStackTrace();
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
            return new Spigot() {
                @Override
                public void sendMessage(BaseComponent component) {
                    message.add(component.toLegacyText());
                }

                @Override
                public void sendMessage(BaseComponent... components) {
                    for (BaseComponent temp : components)
                        message.add(temp.toLegacyText());
                }
            };
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
