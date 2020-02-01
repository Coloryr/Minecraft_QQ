package Color_yr.Minecraft_QQ.Listener;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
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

import static Color_yr.Minecraft_QQ.BungeeCord.config_data_bungee;

public class bc_event implements Listener {

    private String message(String message, String playerName) {
        message = message.replaceAll(Placeholder.Player, playerName);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if (use.hand.socket_runFlag && BaseConfig.JoinsendQQ) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(BaseConfig.JoinMessage, playerName));
        }
    }

    @EventHandler
    public void onPlayerquit(PlayerDisconnectEvent event) {
        if (use.hand.socket_runFlag && BaseConfig.QuitsendQQ) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(BaseConfig.QuitMessage, playerName));
        }
    }

    @EventHandler
    public void onPlayerChangeServer(ServerSwitchEvent event) {
        if (use.hand.socket_runFlag && config_data_bungee.ChangeServersendQQ) {
            String message = config_data_bungee.ChangeServerMessage;
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
        if (BaseConfig.UserNotSendCommder) {
            if (player_message.indexOf("/") == 0)
                return;
        } else if (BaseConfig.MuteList.contains(player.getName()))
            return;
        if (BaseConfig.MinecraftMode != 0 && use.hand.socket_runFlag) {
            boolean send_ok = false;
            String message = BaseConfig.MinecraftMessage;
            String playerName = player.getName();
            String Server = config_data_bungee.config.getString("Servers." + player.getServer().getInfo().getName());
            if (Server.equals("")) {
                Server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Placeholder.Player, playerName)
                    .replaceAll(Placeholder.Servername, BaseConfig.MinecraftServerName)
                    .replaceAll(Placeholder.Server, Server);
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (player_message.indexOf(BaseConfig.MinecraftCheck) == 0
                    && BaseConfig.MinecraftMode == 1) {
                player_message = player_message.replaceFirst(BaseConfig.MinecraftCheck, "");
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            } else if (BaseConfig.MinecraftMode == 2) {
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (config_data_bungee.SendAllServerEnable) {
                String SendAllServer_send = config_data_bungee.SendAllServerMessage;
                SendAllServer_send = SendAllServer_send
                        .replaceAll(Placeholder.Servername, BaseConfig.MinecraftServerName)
                        .replaceAll(Placeholder.Server, Server)
                        .replaceAll(Placeholder.Player, playerName)
                        .replaceAll(Placeholder.Message, player_message);
                SendAllServer_send = ChatColor.translateAlternateColorCodes('&', SendAllServer_send);
                if (config_data_bungee.SendAllServerOnlySideServer) {
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
            if (BaseConfig.UserSendSucceed && send_ok)
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',
                        "§d[Minecraft_QQ]" + BaseConfig.UserSendSucceedMessage)));
        }
    }
}
