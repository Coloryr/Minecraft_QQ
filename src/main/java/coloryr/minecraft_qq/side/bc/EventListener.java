package coloryr.minecraft_qq.side.bc;

import coloryr.minecraft_qq.Minecraft_QQ;
import coloryr.minecraft_qq.api.Placeholder;
import coloryr.minecraft_qq.utils.SocketUtils;
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

public class EventListener implements Listener {

    private String message(String message, String playerName) {
        message = message.replaceAll(Minecraft_QQ.config.Placeholder.Player, playerName);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if (SocketUtils.isRun() && Minecraft_QQ.config.Join.sendQQ) {
            String playerName = event.getPlayer().getName();
            SocketUtils.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.config.Join.Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerquit(PlayerDisconnectEvent event) {
        if (SocketUtils.isRun() && Minecraft_QQ.config.Quit.sendQQ) {
            String playerName = event.getPlayer().getName();
            SocketUtils.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.config.Quit.Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerChangeServer(ServerSwitchEvent event) {
        if (SocketUtils.isRun() && Minecraft_QQ.config.ChangeServer.sendQQ) {
            String message = Minecraft_QQ.config.ChangeServer.Message;
            ProxiedPlayer player = event.getPlayer();
            String playerName = player.getName();
            String server = Minecraft_QQ.config.Servers.get(player.getServer().getInfo().getName());
            if (server == null || server.isEmpty()) {
                server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Minecraft_QQ.config.Placeholder.Player, playerName)
                    .replaceAll(Minecraft_QQ.config.Placeholder.Server, server);
            message = ChatColor.translateAlternateColorCodes('&', message);
            SocketUtils.sendData(Placeholder.data, Placeholder.group, playerName, message);
        }
    }

    @EventHandler
    public void onChar(ChatEvent event) {
        String playerMessage = event.getMessage();
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (Minecraft_QQ.config.User.NotSendCommand) {
            if (playerMessage.startsWith("/"))
                return;
        } else if (Minecraft_QQ.config.Mute.contains(player.getName()))
            return;
        if (Minecraft_QQ.config.ServerSet.Mode != 0 && SocketUtils.isRun()) {
            String message = Minecraft_QQ.config.ServerSet.Message;
            String playerName = player.getName();
            String Server = Minecraft_QQ.config.Servers.get(player.getServer().getInfo().getName());
            if (Server == null || Server.isEmpty()) {
                Server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Minecraft_QQ.config.Placeholder.Player, playerName)
                    .replaceAll(Minecraft_QQ.config.Placeholder.ServerName, Minecraft_QQ.config.ServerSet.ServerName)
                    .replaceAll(Minecraft_QQ.config.Placeholder.Server, Server);
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (Minecraft_QQ.config.ServerSet.Mode == 1
                    && playerMessage.indexOf(Minecraft_QQ.config.ServerSet.Check) == 0) {
                playerMessage = playerMessage.replaceFirst(Minecraft_QQ.config.ServerSet.Check, "");
                message = message.replaceAll(Minecraft_QQ.config.Placeholder.Message, playerMessage);
                SocketUtils.sendData(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Minecraft_QQ.config.ServerSet.Mode == 2) {
                message = message.replaceAll(Minecraft_QQ.config.Placeholder.Message, playerMessage);
                SocketUtils.sendData(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (Minecraft_QQ.config.SendAllServer.Enable) {
                String SendAllServer_send = Minecraft_QQ.config.SendAllServer.Message;
                SendAllServer_send = SendAllServer_send
                        .replaceAll(Minecraft_QQ.config.Placeholder.ServerName, Minecraft_QQ.config.ServerSet.ServerName)
                        .replaceAll(Minecraft_QQ.config.Placeholder.Server, Server)
                        .replaceAll(Minecraft_QQ.config.Placeholder.Player, playerName)
                        .replaceAll(Minecraft_QQ.config.Placeholder.Message, playerMessage);
                SendAllServer_send = ChatColor.translateAlternateColorCodes('&', SendAllServer_send);
                if (Minecraft_QQ.config.SendAllServer.OnlySideServer) {
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
        }
    }
}
