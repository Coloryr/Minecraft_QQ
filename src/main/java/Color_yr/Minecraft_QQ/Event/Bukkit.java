package Color_yr.Minecraft_QQ.Event;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Socket.socket;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Bukkit implements Listener {

    private String message(String message, String playerName) {
        message = message.replaceAll(Placeholder.Player, playerName);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (socket.hand.socket_runFlag == true && Color_yr.Minecraft_QQ.Config.Bukkit.Join_sendQQ == true) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(Color_yr.Minecraft_QQ.Config.Bukkit.Join_Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (socket.hand.socket_runFlag == true && Color_yr.Minecraft_QQ.Config.Bukkit.Quit_sendQQ == true) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(Color_yr.Minecraft_QQ.Config.Bukkit.Quit_Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerSay(AsyncPlayerChatEvent event) {
        String player_message;
        player_message = event.getMessage();
        if (Color_yr.Minecraft_QQ.Config.Bukkit.User_NotSendCommder == true) {
            if (player_message.indexOf("/") == 0)
                return;
        } else if (Color_yr.Minecraft_QQ.Config.Bukkit.Mute_List.contains(event.getPlayer().getName()))
            return;
        else if (Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Mode != 0 && socket.hand.socket_runFlag == true) {
            boolean send_ok = false;
            Player player = event.getPlayer();
            String message = Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Message;
            String playerName = player.getName();
            message = message.replaceAll(Placeholder.Player, playerName);
            message = message.replaceAll(Placeholder.Servername, Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_ServerName);
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (player_message.indexOf(Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Check) == 0 && Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Mode == 1) {
                player_message = player_message.replaceFirst(Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Check, "");
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Mode == 2) {
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (Color_yr.Minecraft_QQ.Config.Bukkit.User_SendSucceed == true && send_ok == true)
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "§d[Minecraft_QQ]" + Color_yr.Minecraft_QQ.Config.Bukkit.User_SendSucceedMessage));
        }
    }
}