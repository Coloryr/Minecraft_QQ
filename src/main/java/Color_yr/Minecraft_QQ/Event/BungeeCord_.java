package Color_yr.Minecraft_QQ.Event;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Config.Bukkit_;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Socket.socket_send;
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

import static Color_yr.Minecraft_QQ.Main.BungeeCord.config_data_bungee;

public class BungeeCord_ implements Listener {

    private String message(String message, String playerName) {
        message = message.replaceAll(Placeholder.Player, playerName);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if (config.hand.socket_runFlag && Bukkit_.Join_sendQQ) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(Bukkit_.Join_Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerquit(PlayerDisconnectEvent event) {
        if (config.hand.socket_runFlag && Bukkit_.Quit_sendQQ) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(Bukkit_.Quit_Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerChangeServer(ServerSwitchEvent event) {
        if (config.hand.socket_runFlag && config_data_bungee.ChangeServer_sendQQ) {
            String message = config_data_bungee.ChangeServer_Message;
            ProxiedPlayer player = event.getPlayer();
            String playerName = player.getName();
            String Server = config_data_bungee.config.getString("Servers." + player.getServer().getInfo().getName());
            if (Server.equals("")) {
                Server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Placeholder.Player, playerName).replaceAll(Placeholder.Server, Server);
            message = ChatColor.translateAlternateColorCodes('&', message);
            socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
        }
    }

    @EventHandler
    public void onChar(ChatEvent event) {
        String player_message;
        player_message = event.getMessage();
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (Bukkit_.User_NotSendCommder) {
            if (player_message.indexOf("/") == 0)
                return;
        } else if (Bukkit_.Mute_List.contains(player.getName()))
            return;
        if (Bukkit_.Minecraft_Mode != 0 && config.hand.socket_runFlag) {
            boolean send_ok = false;
            String message = Bukkit_.Minecraft_Message;
            String playerName = player.getName();
            String Server = config_data_bungee.config.getString("Servers." + player.getServer().getInfo().getName());
            if (Server.equals("")) {
                Server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Placeholder.Player, playerName)
                    .replaceAll(Placeholder.Servername, Bukkit_.Minecraft_ServerName)
                    .replaceAll(Placeholder.Server, Server);
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (player_message.indexOf(Bukkit_.Minecraft_Check) == 0
                    && Bukkit_.Minecraft_Mode == 1) {
                player_message = player_message.replaceFirst(Bukkit_.Minecraft_Check, "");
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Bukkit_.Minecraft_Mode == 2) {
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (config_data_bungee.SendAllServer_Enable) {
                String SendAllServer_send = config_data_bungee.SendAllServer_Message;
                SendAllServer_send = SendAllServer_send
                        .replaceAll(Placeholder.Servername, Bukkit_.Minecraft_ServerName)
                        .replaceAll(Placeholder.Server, Server)
                        .replaceAll(Placeholder.Player, playerName)
                        .replaceAll(Placeholder.Message, player_message);
                SendAllServer_send = ChatColor.translateAlternateColorCodes('&', SendAllServer_send);
                if (config_data_bungee.SendAllServer_OnlySideServer) {
                    for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                        if (!player1.getServer().getInfo().getName().equals(player.getServer().getInfo().getName()))
                            player1.sendMessage(new TextComponent(SendAllServer_send));
                    }
                } else {
                    for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                        player1.sendMessage(new TextComponent(SendAllServer_send));
                    }
                    event.setCancelled(true);
                }
            }
            if (Bukkit_.User_SendSucceed && send_ok)
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',
                        "§d[Minecraft_QQ]" + Bukkit_.User_SendSucceedMessage)));
        }
    }
}
