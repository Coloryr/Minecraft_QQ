package Color_yr.Minecraft_QQ.Event;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Config.Bukkit;
import Color_yr.Minecraft_QQ.Socket.socket;
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

public class BungeeCord implements Listener {

    private String message(String message, String playerName) {
        message = message.replaceAll(Placeholder.Player, playerName);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if (socket.hand.socket_runFlag == true && Bukkit.Join_sendQQ == true) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(Bukkit.Join_Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerquit(PlayerDisconnectEvent event) {
        if (socket.hand.socket_runFlag == true && Bukkit.Quit_sendQQ == true) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(Bukkit.Quit_Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerChangeServer(ServerSwitchEvent event) {
        if (socket.hand.socket_runFlag == true && config_data_bungee.ChangeServer_sendQQ == true) {
            String message = config_data_bungee.ChangeServer_Message;
            ProxiedPlayer player = event.getPlayer();
            String playerName = player.getName();
            String Server = config_data_bungee.config.getString("Servers." + player.getServer().getInfo().getName());
            if (Server.equals("") == true || Server == null) {
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
        if (Bukkit.User_NotSendCommder == true) {
            if (player_message.indexOf("/") == 0)
                return;
        } else if (Bukkit.Mute_List.contains(player.getName()))
            return;
        else if (Bukkit.Minecraft_Mode != 0 && socket.hand.socket_runFlag == true) {
            boolean send_ok = false;
            String message = Bukkit.Minecraft_Message;
            String playerName = player.getName();
            String Server = config_data_bungee.config.getString("Servers." + player.getServer().getInfo().getName());
            if (Server.equals("") == true || Server == null) {
                Server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Placeholder.Player, playerName)
                    .replaceAll(Placeholder.Servername, Bukkit.Minecraft_ServerName)
                    .replaceAll(Placeholder.Server, Server);
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (player_message.indexOf(Bukkit.Minecraft_Check) == 0
                    && Bukkit.Minecraft_Mode == 1) {
                player_message = player_message.replaceFirst(Bukkit.Minecraft_Check, "");
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Bukkit.Minecraft_Mode == 2) {
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (config_data_bungee.SendAllServer_Enable == true) {
                String SendAllServer_send = config_data_bungee.SendAllServer_Message;
                SendAllServer_send = SendAllServer_send
                        .replaceAll(Placeholder.Servername, Bukkit.Minecraft_ServerName)
                        .replaceAll(Placeholder.Server, Server)
                        .replaceAll(Placeholder.Player, playerName)
                        .replaceAll(Placeholder.Message, player_message);
                SendAllServer_send = ChatColor.translateAlternateColorCodes('&', SendAllServer_send);
                if (config_data_bungee.SendAllServer_OnlySideServer == true) {
                    for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                        if (player1.getServer().getInfo().getName().equals(player.getServer().getInfo().getName()) == false)
                            player1.sendMessage(new TextComponent(SendAllServer_send));
                    }
                } else {
                    for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                        player1.sendMessage(new TextComponent(SendAllServer_send));
                    }
                    event.setCancelled(true);
                }
            }
            if (Bukkit.User_SendSucceed == true && send_ok == true)
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',
                        "§d[Minecraft_QQ]" + Bukkit.User_SendSucceedMessage)));
        }
    }
}
