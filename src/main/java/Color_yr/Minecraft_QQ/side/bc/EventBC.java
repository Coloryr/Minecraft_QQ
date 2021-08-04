package Color_yr.Minecraft_QQ.side.bc;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
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

public class EventBC implements Listener {

    private String message(String message, String playerName) {
        message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Player, playerName);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.Join.sendQQ) {
            String playerName = event.getPlayer().getName();
            Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.Join.Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerquit(PlayerDisconnectEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.Quit.sendQQ) {
            String playerName = event.getPlayer().getName();
           Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.Quit.Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerChangeServer(ServerSwitchEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.ChangeServer.sendQQ) {
            String message = Minecraft_QQ.Config.ChangeServer.Message;
            ProxiedPlayer player = event.getPlayer();
            String playerName = player.getName();
            String server = Minecraft_QQ.Config.Servers.get(player.getServer().getInfo().getName());
            if (server == null || server.isEmpty()) {
                server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Player, playerName)
                    .replaceAll(Minecraft_QQ.Config.Placeholder.Server, server);
            message = ChatColor.translateAlternateColorCodes('&', message);
           Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
        }
    }

    @EventHandler
    public void onChar(ChatEvent event) {
        String playerMessage = event.getMessage();
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (Minecraft_QQ.Config.User.NotSendCommand) {
            if (playerMessage.startsWith("/"))
                return;
        } else if (Minecraft_QQ.Config.Mute.contains(player.getName()))
            return;
        if (Minecraft_QQ.Config.ServerSet.Mode != 0 && Minecraft_QQ.control.isRun()) {
            String message = Minecraft_QQ.Config.ServerSet.Message;
            String playerName = player.getName();
            String Server = Minecraft_QQ.Config.Servers.get(player.getServer().getInfo().getName());
            if (Server == null || Server.isEmpty()) {
                Server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Player, playerName)
                    .replaceAll(Minecraft_QQ.Config.Placeholder.ServerName, Minecraft_QQ.Config.ServerSet.ServerName)
                    .replaceAll(Minecraft_QQ.Config.Placeholder.Server, Server);
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (Minecraft_QQ.Config.ServerSet.Mode == 1
                    && playerMessage.indexOf(Minecraft_QQ.Config.ServerSet.Check) == 0) {
                playerMessage = playerMessage.replaceFirst(Minecraft_QQ.Config.ServerSet.Check, "");
                message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Message, playerMessage);
                Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Minecraft_QQ.Config.ServerSet.Mode == 2) {
                message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Message, playerMessage);
                Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (Minecraft_QQ.Config.SendAllServer.Enable) {
                String SendAllServer_send = Minecraft_QQ.Config.SendAllServer.Message;
                SendAllServer_send = SendAllServer_send
                        .replaceAll(Minecraft_QQ.Config.Placeholder.ServerName, Minecraft_QQ.Config.ServerSet.ServerName)
                        .replaceAll(Minecraft_QQ.Config.Placeholder.Server, Server)
                        .replaceAll(Minecraft_QQ.Config.Placeholder.Player, playerName)
                        .replaceAll(Minecraft_QQ.Config.Placeholder.Message, playerMessage);
                SendAllServer_send = ChatColor.translateAlternateColorCodes('&', SendAllServer_send);
                if (Minecraft_QQ.Config.SendAllServer.OnlySideServer) {
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
