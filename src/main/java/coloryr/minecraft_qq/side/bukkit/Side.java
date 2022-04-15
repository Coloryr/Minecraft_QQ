package coloryr.minecraft_qq.side.bukkit;

import coloryr.minecraft_qq.MBukkit;
import coloryr.minecraft_qq.Minecraft_QQ;
import coloryr.minecraft_qq.api.ISide;
import coloryr.minecraft_qq.api.Placeholder;
import coloryr.minecraft_qq.json.ReadOBJ;
import coloryr.minecraft_qq.side.ASide;
import coloryr.minecraft_qq.utils.Logs;
import coloryr.minecraft_qq.utils.SocketUtils;
import com.google.gson.Gson;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Side implements ISide {
    @Override
    public void send(Object sender, String message) {
        CommandSender temp = (CommandSender) sender;
        temp.sendMessage(message);
    }

    @Override
    public void message(String message) {
        try {
            if (Minecraft_QQ.config.System.Debug)
                Minecraft_QQ.log.info("处理数据：" + message);
            ReadOBJ readobj;
            try {
                Gson read_gson = new Gson();
                readobj = read_gson.fromJson(message, ReadOBJ.class);
                Bukkit.getScheduler().runTask(MBukkit.plugin, () -> {
                    GroupEvent eventBukkit = new GroupEvent(readobj);
                    Bukkit.getPluginManager().callEvent(eventBukkit);
                });
            } catch (Exception e) {
                Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c发生错误：");
                e.printStackTrace();
                return;
            }
            if (!readobj.isCommand && !Minecraft_QQ.config.ServerSet.BungeeCord) {
                if (readobj.command.equalsIgnoreCase(Placeholder.speak)) {
                    String say = Minecraft_QQ.config.ServerSet.Say
                            .replaceFirst(Minecraft_QQ.config.Placeholder.ServerName, Minecraft_QQ.config.ServerSet.ServerName)
                            .replaceFirst(Minecraft_QQ.config.Placeholder.Message, readobj.message)
                            .replaceFirst(Minecraft_QQ.config.Placeholder.Player, readobj.player);
                    say = ChatColor.translateAlternateColorCodes('&', say);
                    if (MBukkit.PAPI && readobj.player != null) {
                        OfflinePlayer player = Bukkit.getPlayer(readobj.player);
                        if (player != null)
                            say = PlaceholderAPI.setBracketPlaceholders(player, say);
                    }
                    final String finalSay = say;
                    if (Minecraft_QQ.config.Logs.Group) {
                        Logs.logWrite("[Group]" + say);
                    }
                    Bukkit.getScheduler().runTask(MBukkit.plugin, () -> {
                        try {
                            for (Player b : Bukkit.getOnlinePlayers()) {
                                if (!Minecraft_QQ.config.Mute.contains(b.getName()))
                                    b.sendMessage(finalSay);
                            }
                        } catch (Exception e) {
                            Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c发生错误：");
                            e.printStackTrace();
                        }
                    });
                } else if (readobj.command.equalsIgnoreCase(Placeholder.online)) {
                    String send = Minecraft_QQ.config.ServerSet.PlayerListMessage;
                    Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                    if (players.size() == 0) {
                        try {
                            send = send.replaceAll(Minecraft_QQ.config.Placeholder.ServerName, Minecraft_QQ.config.ServerSet.ServerName)
                                    .replaceAll(Minecraft_QQ.config.Placeholder.PlayerNumber, "0")
                                    .replaceAll(Minecraft_QQ.config.Placeholder.Server, "")
                                    .replaceAll(Minecraft_QQ.config.Placeholder.PlayerList, "无");
                        } catch (Exception e) {
                            Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c发生错误：");
                            e.printStackTrace();
                        }
                    } else {
                        StringBuilder temp = new StringBuilder();
                        for (Player player1 : players) {
                            temp.append(player1.getName()).append(",");
                        }
                        String player = temp.toString();

                        send = send.replaceAll(Minecraft_QQ.config.Placeholder.ServerName, Minecraft_QQ.config.ServerSet.ServerName)
                                .replaceAll(Minecraft_QQ.config.Placeholder.PlayerNumber, "" + players.size())
                                .replaceAll(Minecraft_QQ.config.Placeholder.Server, "")
                                .replaceAll(Minecraft_QQ.config.Placeholder.PlayerList, player.substring(0, player.length() - 1));
                    }
                    if (MBukkit.PAPI && readobj.player != null) {
                        OfflinePlayer player = Bukkit.getPlayer(readobj.player);
                        if (player != null)
                            send = PlaceholderAPI.setBracketPlaceholders(player, send);
                    }
                    if (Minecraft_QQ.config.Logs.Group)
                        Logs.logWrite("[group]查询在线人数");
                    if (Minecraft_QQ.config.System.Debug)
                        Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]查询在线人数");
                    SocketUtils.sendData(Placeholder.data, readobj.group, "无", send);
                } else {
                    ASide.globeCheck(readobj);
                }
            } else if (readobj.isCommand) {
                StringBuilder send_message;
                Commander send = new Commander();
                send.player = readobj.player;
                if (Minecraft_QQ.config.Logs.Group) {
                    Logs.logWrite("[Group]" + readobj.player + "执行命令" + readobj.command);
                }
                try {
                    Bukkit.getScheduler().callSyncMethod(MBukkit.plugin, () ->
                            Bukkit.dispatchCommand(send, readobj.command)).get();
                    Thread.sleep(Minecraft_QQ.config.ServerSet.CommandDelay);
                } catch (Exception e) {
                    Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c指令执行出现错误");
                    e.printStackTrace();
                }
                if (send.message.size() == 1) {
                    send_message = new StringBuilder(send.message.get(0));
                } else if (send.message.size() > 1) {
                    send_message = new StringBuilder(send.message.get(0));
                    for (int i = 1; i < send.message.size(); i++) {
                        send_message.append("\n");
                        send_message.append(send.message.get(i));
                    }
                } else
                    send_message = new StringBuilder("已执行，指令无返回");
                SocketUtils.sendData(Placeholder.data, readobj.group, "控制台", send_message.toString());
            }
        } catch (Exception e) {
            Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c发生错误：");
            e.printStackTrace();
        }
    }
}
