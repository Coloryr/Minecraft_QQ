package Color_yr.Minecraft_QQ.Side;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Json.ReadOBJ;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Minecraft_QQBC;
import Color_yr.Minecraft_QQ.Socket.socketSend;
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
    public void LogInfo(String message) {
        Minecraft_QQBC.log_b.info(message);
    }

    @Override
    public void LogError(String message) {
        Minecraft_QQBC.log_b.warning(message);
    }

    @Override
    public void Message(String message) {
        try {
            String msg = message;
            if (Minecraft_QQ.Config.getSystem().isDebug())
                LogInfo("处理数据：" + msg);
            if (!Minecraft_QQ.hand.socketIsRun)
                return;
            ProxyServer proxyserver = ProxyServer.getInstance();
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
                        int all_player_number = 0;
                        String one_server_player = "";
                        StringBuilder all_server_player = new StringBuilder();
                        String send = Minecraft_QQ.Config.getServerSet().getPlayerListMessage();
                        if (Minecraft_QQ.Config.getServerSet().isSendOneByOne()) {
                            for (final ServerInfo serverinfo : proxyserver.getServers().values()) {
                                final String player_onserver = serverinfo.getPlayers().toString();
                                if (player_onserver.equals("[]")) {
                                    int one_player_number = 0;
                                    if (Minecraft_QQ.Config.getServerSet().isHideEmptyServer()) {
                                        one_server_player = "";
                                        one_player_number = 0;
                                    } else {
                                        String Server_name = Minecraft_QQ.Config.getServers().get(serverinfo.getName());
                                        if (Server_name == null || Server_name.isEmpty()) {
                                            Server_name = serverinfo.getName();
                                        }
                                        one_server_player = Minecraft_QQ.Config.getServerSet().getSendOneByOneMessage()
                                                .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), Server_name)
                                                .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "0")
                                                .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), "无");
                                        all_server_player.append(one_server_player);
                                    }
                                } else {
                                    int one_player_number = 1;
                                    for (int i = 0; i < player_onserver.length(); ++i) {
                                        if (player_onserver.charAt(i) == ',') {
                                            ++one_player_number;
                                        }
                                    }
                                    String Server_name = Minecraft_QQ.Config.getServers().get(serverinfo.getName());
                                    if (Server_name == null || Server_name.isEmpty()) {
                                        Server_name = serverinfo.getName();
                                    }
                                    one_server_player = Minecraft_QQ.Config.getServerSet().getSendOneByOneMessage()
                                            .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), Server_name)
                                            .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "" + one_player_number)
                                            .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), player_onserver.replace("[", "")
                                                    .replace("]", ""));
                                    all_player_number += one_player_number;
                                    all_server_player.append(one_server_player);
                                }
                            }
                            if (all_player_number == 0) {
                                if (Minecraft_QQ.Config.getServerSet().isHideEmptyServer())
                                    send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "");
                                else
                                    send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "0");
                                send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), "无");
                            } else {
                                send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "" + all_player_number)
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), all_server_player.toString().replace("null", ""));
                            }
                        } else {
                            all_server_player = new StringBuilder(proxyserver.getPlayers().toString());
                            if (all_server_player.toString().equals("[]")) {
                                send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "0")
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), "无");
                            } else {
                                for (int j = 0; j < all_server_player.length(); ++j) {
                                    if (all_server_player.charAt(j) == ',') {
                                        ++all_player_number;
                                    }
                                }
                                final String number = String.valueOf(all_player_number);
                                send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), number)
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), all_server_player.toString().replace("[", "").replace("]", ""));
                            }
                        }
                        send = send.replace(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName());
                        socketSend.send_data(Placeholder.data, readobj.getGroup(), "无", send);
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.logWrite("[group]查询在线人数");
                        }
                    } else if (readobj.getCommder().equalsIgnoreCase("server")) {
                        String send = Minecraft_QQ.Config.getServerSet().getServerOnlineMessage()
                                .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName());
                        socketSend.send_data(Placeholder.data, readobj.getGroup(), "无", send);
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.logWrite("[group]查询服务器状态");
                        }
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
                        LogInfo(e.toString());
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
                        send_message = new StringBuilder("指令无返回");
                    socketSend.send_data(Placeholder.data, readobj.getGroup(),
                            "控制台", send_message.toString());
                }
                int i = msg.indexOf(Minecraft_QQ.Config.getSystem().getEnd());
                msg = msg.substring(i + Minecraft_QQ.Config.getSystem().getEnd().length());
            }
        } catch (Exception e) {
            LogInfo("发生错误" + e.getMessage());
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
