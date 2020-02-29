package Color_yr.Minecraft_QQ.Listener;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Socket.socketSend;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BCEvent implements Listener {

    private String message(String message, String playerName) {
        message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if (Minecraft_QQ.hand.socket_runFlag && Minecraft_QQ.Config.getJoin().isSendQQ()) {
            String playerName = event.getPlayer().getName();
            socketSend.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.getJoin().getMessage(), playerName));
        }
    }

    @EventHandler
    public void onPlayerquit(PlayerDisconnectEvent event) {
        if (Minecraft_QQ.hand.socket_runFlag && Minecraft_QQ.Config.getQuit().isSendQQ()) {
            String playerName = event.getPlayer().getName();
            socketSend.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.getQuit().getMessage(), playerName));
        }
    }

    @EventHandler
    public void onPlayerChangeServer(ServerSwitchEvent event) {
        if (Minecraft_QQ.hand.socket_runFlag && Minecraft_QQ.Config.getChangeServer().isSendQQ()) {
            String message = Minecraft_QQ.Config.getChangeServer().getMessage();
            ProxiedPlayer player = event.getPlayer();
            String playerName = player.getName();
            String Server = Minecraft_QQ.Config.getServers().get(player.getServer().getInfo().getName());
            if (Server == null || Server.isEmpty()){
                Server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName)
                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), Server);
            message = ChatColor.translateAlternateColorCodes('&', message);
            socketSend.send_data(Placeholder.data, Placeholder.group, playerName, message);
        }
    }

    @EventHandler
    public void onChar(ChatEvent event) {
        String player_message;
        player_message = event.getMessage();
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (Minecraft_QQ.Config.getUser().isNotSendCommand()) {
            if (player_message.indexOf("/") == 0)
                return;
        } else if (Minecraft_QQ.Config.getMute().contains(player.getName()))
            return;
        if (Minecraft_QQ.Config.getServerSet().getMode() != 0 && Minecraft_QQ.hand.socket_runFlag) {
            boolean send_ok = false;
            String message = Minecraft_QQ.Config.getServerSet().getMessage();
            String playerName = player.getName();
            String Server = Minecraft_QQ.Config.getServers().get(player.getServer().getInfo().getName());
            if (Server == null || Server.isEmpty()) {
                Server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName)
                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), Server);
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (player_message.indexOf(Minecraft_QQ.Config.getServerSet().getCheck()) == 0
                    && Minecraft_QQ.Config.getServerSet().getMode() == 1) {
                player_message = player_message.replaceFirst(Minecraft_QQ.Config.getServerSet().getCheck(), "");
                message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getMessage(), player_message);
                send_ok = socketSend.send_data(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Minecraft_QQ.Config.getServerSet().getMode() == 2) {
                message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getMessage(), player_message);
                send_ok = socketSend.send_data(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (Minecraft_QQ.Config.getSendAllServer().isEnable()) {
                String SendAllServer_send = Minecraft_QQ.Config.getSendAllServer().getMessage();
                SendAllServer_send = SendAllServer_send
                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), Server)
                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName)
                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getMessage(), player_message);
                SendAllServer_send = ChatColor.translateAlternateColorCodes('&', SendAllServer_send);
                if (Minecraft_QQ.Config.getSendAllServer().isOnlySideServer()) {
                    for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                        if (player.getServer() != null && player1.getServer() != null &&
                                !player1.getServer().getInfo().getName().equals(player.getServer().getInfo().getName()))
                            player1.sendMessage(new TextComponent(SendAllServer_send));
                    }
                } else {
                    for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                        player1.sendMessage(new TextComponent(SendAllServer_send));
                    }
                    event.setCancelled(true);
                }
            }
            if (Minecraft_QQ.Config.getUser().isSendSucceed() && send_ok)
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',
                        "§d[Minecraft_QQ]" + Minecraft_QQ.Config.getLanguage().getSucceedMessage())));
        }
    }
}
