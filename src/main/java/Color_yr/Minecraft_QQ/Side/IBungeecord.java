package Color_yr.Minecraft_QQ.Side;

import Color_yr.Minecraft_QQ.API.IMinecraft_QQ;
import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Json.ReadOBJ;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Minecraft_QQBC;
import Color_yr.Minecraft_QQ.Utils.Function;
import Color_yr.Minecraft_QQ.Utils.logs;
import com.google.gson.Gson;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class IBungeecord implements IMinecraft_QQ {
    @Override
    public void logInfo(String message) {
        Minecraft_QQBC.log_b.info(message);
    }

    @Override
    public void logError(String message) {
        Minecraft_QQBC.log_b.warning(message);
    }

    @Override
    public void send(Object sender, String message) {
        CommandSender temp = (CommandSender) sender;
        temp.sendMessage(new TextComponent(message));
    }

    @Override
    public void message(String message) {
        try {
            String msg = message;
            if (Minecraft_QQ.Config.getSystem().isDebug())
                logInfo("处理数据：" + msg);
            ProxyServer proxyserver = ProxyServer.getInstance();
            while (msg.indexOf(Minecraft_QQ.Config.getSystem().getHead()) == 0 && msg.contains(Minecraft_QQ.Config.getSystem().getEnd())) {
                String buff = Function.get_string(msg, Minecraft_QQ.Config.getSystem().getHead(), Minecraft_QQ.Config.getSystem().getEnd());
                ReadOBJ readobj;
                try {
                    Gson read_gson = new Gson();
                    readobj = read_gson.fromJson(buff, ReadOBJ.class);
                } catch (Exception e) {
                    logInfo("数据传输发生错误:" + e.getMessage());
                    return;
                }
                if (readobj.getIs_commder().equals("false")) {
                    if (readobj.getCommder().equalsIgnoreCase("speak")) {
                        String say = Minecraft_QQ.Config.getServerSet().getSay()
                                .replaceFirst(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                                .replaceFirst(Minecraft_QQ.Config.getPlaceholder().getMessage(), readobj.getMessage())
                                .replaceFirst(Minecraft_QQ.Config.getPlaceholder().getPlayer(), readobj.getPlayer());
                        say = ChatColor.translateAlternateColorCodes('&', say);
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.logWrite("[Group]" + say);
                        }
                        for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                            if (!Minecraft_QQ.Config.getMute().contains(player1.getName()))
                                player1.sendMessage(new TextComponent(say));
                        }
                    } else if (readobj.getCommder().equalsIgnoreCase("online")) {
                        int allPlayerNumber = 0;
                        StringBuilder allServerPlayer = new StringBuilder();
                        String send = Minecraft_QQ.Config.getServerSet().getPlayerListMessage();
                        if (Minecraft_QQ.Config.getServerSet().isSendOneByOne()) {
                            for (final ServerInfo serverinfo : proxyserver.getServers().values()) {
                                String oneServerPlayer;
                                int oneServerNumber;
                                final Collection<ProxiedPlayer> oneServerPlayers = serverinfo.getPlayers();
                                if (oneServerPlayers.size() == 0) {
                                    if (!Minecraft_QQ.Config.getServerSet().isHideEmptyServer()) {
                                        String serverName = Minecraft_QQ.Config.getServers().get(serverinfo.getName());
                                        if (serverName == null || serverName.isEmpty()) {
                                            serverName = serverinfo.getName();
                                        }
                                        oneServerPlayer = Minecraft_QQ.Config.getServerSet().getSendOneByOneMessage()
                                                .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), serverName)
                                                .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "0")
                                                .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), "无");
                                        allServerPlayer.append(oneServerPlayer);
                                    }
                                } else {
                                    oneServerNumber = oneServerPlayers.size();
                                    String serverName = Minecraft_QQ.Config.getServers().get(serverinfo.getName());
                                    if (serverName == null || serverName.isEmpty()) {
                                        serverName = serverinfo.getName();
                                    }
                                    StringBuilder players = new StringBuilder();
                                    for (ProxiedPlayer player : oneServerPlayers) {
                                        players.append(player.getName()).append(",");
                                    }
                                    String players1 = players.toString();
                                    oneServerPlayer = Minecraft_QQ.Config.getServerSet().getSendOneByOneMessage()
                                            .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), serverName)
                                            .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "" + oneServerNumber)
                                            .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), players1.substring(0, players1.length() - 1));
                                    allPlayerNumber += oneServerNumber;
                                    allServerPlayer.append(oneServerPlayer);
                                }
                            }
                            if (allPlayerNumber == 0) {
                                send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "0")
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), "无");
                            } else {
                                send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "" + allPlayerNumber)
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), allServerPlayer.toString());
                            }
                        } else {
                            final Collection<ProxiedPlayer> players = proxyserver.getPlayers();
                            if (players.size() == 0) {
                                send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "0")
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), "无");
                            } else {
                                StringBuilder temp = new StringBuilder();
                                for (ProxiedPlayer player : players) {
                                    temp.append(player.getName()).append(",");
                                }
                                String players1 = temp.toString();
                                send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "" + players.size())
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), players1.substring(0, players1.length() - 1));
                            }
                        }
                        send = send.replace(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName());
                        boolean sendok = Minecraft_QQ.control.sendData(Placeholder.data, readobj.getGroup(), "无", send);
                        if (!sendok)
                            logError("§d[Minecraft_QQ]§c数据发送失败");
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.logWrite("[group]查询在线人数");
                        }
                    } else if (readobj.getCommder().equalsIgnoreCase("server")) {
                        String send = Minecraft_QQ.Config.getServerSet().getServerOnlineMessage()
                                .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName());
                        Minecraft_QQ.control.sendData(Placeholder.data, readobj.getGroup(), "无", send);
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.logWrite("[group]查询服务器状态");
                        }
                    } else if (readobj.getCommder().equalsIgnoreCase("pause")) {
                        boolean sendok = Minecraft_QQ.control.sendData(Placeholder.pause, readobj.getGroup(), "无", "data");
                        if (!sendok)
                            logError("§d[Minecraft_QQ]§c心跳包发送失败");
                    } else if (readobj.getCommder().equalsIgnoreCase("config")) {
                        String config = new Gson().toJson(Minecraft_QQ.Config);
                        boolean sendok = Minecraft_QQ.control.sendData(Placeholder.config, readobj.getGroup(), "无", config);
                        if (!sendok)
                            logError("§d[Minecraft_QQ]§c配置文件发送失败");
                    }
                } else if (readobj.getIs_commder().equals("true")) {
                    StringBuilder send_message;
                    Command send = new Command();
                    send.setPlayer(readobj.getPlayer());
                    if (Minecraft_QQ.Config.getLogs().isGroup()) {
                        logs.logWrite("[Group]" + readobj.getPlayer() + "执行命令" + readobj.getCommder());
                    }
                    try {
                        proxyserver.getPluginManager().dispatchCommand(send, readobj.getCommder());
                        Thread.sleep(Minecraft_QQ.Config.getServerSet().getCommandDelay());
                    } catch (Exception e) {
                        logInfo(e.toString());
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
                    Minecraft_QQ.control.sendData(Placeholder.data, readobj.getGroup(),
                            "控制台", send_message.toString());
                }
                int i = msg.indexOf(Minecraft_QQ.Config.getSystem().getEnd());
                msg = msg.substring(i + Minecraft_QQ.Config.getSystem().getEnd().length());
            }
        } catch (Exception e) {
            Minecraft_QQ.MinecraftQQ.logError("§d[Minecraft_QQ]§c发送错误：");
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
