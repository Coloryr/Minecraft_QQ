package Color_yr.Minecraft_QQ.Listener;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Config.Base_config;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class bukkit_event implements Listener {

    private String message(String message, String playerName) {
        message = message.replaceAll(Placeholder.Player, playerName);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (use.hand.socket_runFlag && Base_config.Join_sendQQ) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(Base_config.Join_Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (use.hand.socket_runFlag && Base_config.Quit_sendQQ) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(Base_config.Quit_Message, playerName));
        }
    }

    @EventHandler
    public void onPlayerSay(AsyncPlayerChatEvent event) {
        String player_message;
        player_message = event.getMessage();
        if (Base_config.User_NotSendCommder) {
            if (player_message.indexOf("/") == 0)
                return;
        } else if (Base_config.Mute_List.contains(event.getPlayer().getName()))
            return;
        if (Base_config.Minecraft_Mode != 0 && use.hand.socket_runFlag) {
            boolean send_ok = false;
            Player player = event.getPlayer();
            String message = Base_config.Minecraft_Message;
            String playerName = player.getName();
            message = message.replaceAll(Placeholder.Player, playerName);
            message = message.replaceAll(Placeholder.Servername, Base_config.Minecraft_ServerName);
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (player_message.indexOf(Base_config.Minecraft_Check) == 0 && Base_config.Minecraft_Mode == 1) {
                player_message = player_message.replaceFirst(Base_config.Minecraft_Check, "");
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Base_config.Minecraft_Mode == 2) {
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (Base_config.User_SendSucceed && send_ok)
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "§d[Minecraft_QQ]" + Base_config.User_SendSucceedMessage));
        }
    }
}