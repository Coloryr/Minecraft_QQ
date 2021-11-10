package Color_yr.Minecraft_QQ.side.bc;

import Color_yr.Minecraft_QQ.API.ISide;
import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.json.ReadOBJ;
import Color_yr.Minecraft_QQ.side.ASide;
import Color_yr.Minecraft_QQ.utils.SocketUtils;
import Color_yr.Minecraft_QQ.utils.logs;
import com.google.gson.Gson;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;

public class SideBC implements ISide {

    @Override
    public void send(Object sender, String message) {
        CommandSender temp = (CommandSender) sender;
        temp.sendMessage(new TextComponent(message));
    }

    @Override
    public void message(String message) {
        try {
            if (Minecraft_QQ.Config.System.Debug)
                Minecraft_QQ.log.info("处理数据：" + message);
            ProxyServer proxyserver = ProxyServer.getInstance();
            ReadOBJ readobj;
            try {
                Gson read_gson = new Gson();
                readobj = read_gson.fromJson(message, ReadOBJ.class);
            } catch (Exception e) {
                Minecraft_QQ.log.info("数据传输发生错误:");
                e.printStackTrace();
                return;
            }
            if (!readobj.isCommand) {
                if (readobj.command.equalsIgnoreCase(Placeholder.speak)) {
                    String say = Minecraft_QQ.Config.ServerSet.Say
                            .replaceFirst(Minecraft_QQ.Config.Placeholder.ServerName, Minecraft_QQ.Config.ServerSet.ServerName)
                            .replaceFirst(Minecraft_QQ.Config.Placeholder.Message, readobj.message)
                            .replaceFirst(Minecraft_QQ.Config.Placeholder.Player, readobj.player);
                    say = ChatColor.translateAlternateColorCodes('&', say);
                    if (Minecraft_QQ.Config.Logs.Group) {
                        logs.logWrite("[Group]" + say);
                    }
                    for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                        if (!Minecraft_QQ.Config.Mute.contains(player1.getName()))
                            player1.sendMessage(new TextComponent(say));
                    }
                } else if (readobj.command.equalsIgnoreCase(Placeholder.online)) {
                    int allPlayerNumber = 0;
                    StringBuilder allServerPlayer = new StringBuilder();
                    String send = Minecraft_QQ.Config.ServerSet.PlayerListMessage;
                    if (Minecraft_QQ.Config.ServerSet.SendOneByOne) {
                        for (final ServerInfo serverinfo : proxyserver.getServers().values()) {
                            String oneServerPlayer;
                            int oneServerNumber;
                            final Collection<ProxiedPlayer> oneServerPlayers = serverinfo.getPlayers();
                            if (oneServerPlayers.size() == 0) {
                                if (!Minecraft_QQ.Config.ServerSet.HideEmptyServer) {
                                    String serverName = Minecraft_QQ.Config.Servers.get(serverinfo.getName());
                                    if (serverName == null || serverName.isEmpty()) {
                                        serverName = serverinfo.getName();
                                    }
                                    oneServerPlayer = Minecraft_QQ.Config.ServerSet.SendOneByOneMessage
                                            .replaceAll(Minecraft_QQ.Config.Placeholder.Server, serverName)
                                            .replaceAll(Minecraft_QQ.Config.Placeholder.PlayerNumber, "0")
                                            .replaceAll(Minecraft_QQ.Config.Placeholder.PlayerList, "无");
                                    allServerPlayer.append(oneServerPlayer);
                                }
                            } else {
                                oneServerNumber = oneServerPlayers.size();
                                String serverName = Minecraft_QQ.Config.Servers.get(serverinfo.getName());
                                if (serverName == null || serverName.isEmpty()) {
                                    serverName = serverinfo.getName();
                                }
                                StringBuilder players = new StringBuilder();
                                for (ProxiedPlayer player : oneServerPlayers) {
                                    players.append(player.getName()).append(",");
                                }
                                String players1 = players.toString();
                                oneServerPlayer = Minecraft_QQ.Config.ServerSet.SendOneByOneMessage
                                        .replaceAll(Minecraft_QQ.Config.Placeholder.Server, serverName)
                                        .replaceAll(Minecraft_QQ.Config.Placeholder.PlayerNumber, "" + oneServerNumber)
                                        .replaceAll(Minecraft_QQ.Config.Placeholder.PlayerList, players1.substring(0, players1.length() - 1));
                                allPlayerNumber += oneServerNumber;
                                allServerPlayer.append(oneServerPlayer);
                            }
                        }
                        if (allPlayerNumber == 0) {
                            send = send.replaceAll(Minecraft_QQ.Config.Placeholder.PlayerNumber, "0")
                                    .replaceAll(Minecraft_QQ.Config.Placeholder.PlayerList, "无");
                        } else {
                            send = send.replaceAll(Minecraft_QQ.Config.Placeholder.PlayerNumber, "" + allPlayerNumber)
                                    .replaceAll(Minecraft_QQ.Config.Placeholder.PlayerList, allServerPlayer.toString());
                        }
                    } else {
                        final Collection<ProxiedPlayer> players = proxyserver.getPlayers();
                        if (players.size() == 0) {
                            send = send.replaceAll(Minecraft_QQ.Config.Placeholder.PlayerNumber, "0")
                                    .replaceAll(Minecraft_QQ.Config.Placeholder.PlayerList, "无");
                        } else {
                            StringBuilder temp = new StringBuilder();
                            for (ProxiedPlayer player : players) {
                                temp.append(player.getName()).append(",");
                            }
                            String players1 = temp.toString();
                            send = send.replaceAll(Minecraft_QQ.Config.Placeholder.PlayerNumber, "" + players.size())
                                    .replaceAll(Minecraft_QQ.Config.Placeholder.PlayerList, players1.substring(0, players1.length() - 1));
                        }
                    }
                    send = send.replace(Minecraft_QQ.Config.Placeholder.ServerName, Minecraft_QQ.Config.ServerSet.ServerName);
                    SocketUtils.sendData(Placeholder.data, readobj.group, "无", send);
                    if (Minecraft_QQ.Config.Logs.Group) {
                        logs.logWrite("[group]查询在线人数");
                    }
                } else {
                    ASide.globeCheck(readobj);
                }
            } else {
                StringBuilder send_message;
                BCCommander send = new BCCommander();
                send.player = readobj.player;
                if (Minecraft_QQ.Config.Logs.Group) {
                    logs.logWrite("[Group]" + readobj.player + "执行命令" + readobj.command);
                }
                try {
                    proxyserver.getPluginManager().dispatchCommand(send, readobj.command);
                    Thread.sleep(Minecraft_QQ.Config.ServerSet.CommandDelay);
                } catch (Exception e) {
                    Minecraft_QQ.log.warning(e.toString());
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
                SocketUtils.sendData(Placeholder.data, readobj.group,
                        "控制台", send_message.toString());
            }
        } catch (Exception e) {
            Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c发送错误：");
            e.printStackTrace();
        }
    }
}
