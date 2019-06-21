package Color_yr.Minecraft_QQ;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Event_bukkit implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (socket.socket_runFlag == true && config_bukkit.Join_sendQQ == true) {
            String message = config_bukkit.Join_Message;
            String playerName = event.getPlayer().getName();
            message = message.replaceAll("%Player%", playerName);
            message = ChatColor.translateAlternateColorCodes('&', message);
            socket_send.send_data("data", "group", playerName, message);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (socket.socket_runFlag == true && config_bukkit.Quit_sendQQ == true) {
            String message = config_bukkit.Quit_Message;
            String playerName = event.getPlayer().getName();
            message = message.replaceAll("%Player%", playerName);
            message = ChatColor.translateAlternateColorCodes('&', message);
            socket_send.send_data("data", "group", playerName, message);
        }
    }

    @EventHandler
    public void onPlayerSay(AsyncPlayerChatEvent event) {
        String player_message;
        player_message = event.getMessage();
        if (config_bukkit.User_NotSendCommder == true) {
            if (player_message.indexOf("/") == 0)
                return;
        }
        if (config_bukkit.Minecraft_Mode != 0 && socket.socket_runFlag == true) {
            boolean send_ok = false;
            Player player = event.getPlayer();
            String message = config_bukkit.Minecraft_Message;
            String playerName = player.getName();
            message = message.replaceAll("%Player%", playerName);
            message = message.replaceAll("%Servername%", config_bukkit.Minecraft_ServerName);
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (player_message.indexOf(config_bukkit.Minecraft_Check) == 0 && config_bukkit.Minecraft_Mode == 1) {
                player_message = player_message.replaceFirst(config_bukkit.Minecraft_Check, "");
                message = message.replaceAll("%Message%", player_message);
                send_ok = socket_send.send_data("data", "group", playerName, message);
            } else if (config_bukkit.Minecraft_Mode == 2) {
                message = message.replaceAll("%Message%", player_message);
                send_ok = socket_send.send_data("data", "group", playerName, message);
            }
            if (config_bukkit.User_SendSucceed == true && send_ok == true)
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "§d[Minecraft_QQ]" + config_bukkit.User_SendSucceedMessage));
        }
    }
}