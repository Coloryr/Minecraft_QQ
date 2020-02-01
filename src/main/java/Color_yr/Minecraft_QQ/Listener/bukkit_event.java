package Color_yr.Minecraft_QQ.Listener;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
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
        if (use.hand.socket_runFlag && BaseConfig.JoinsendQQ) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(BaseConfig.JoinMessage, playerName));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (use.hand.socket_runFlag && BaseConfig.QuitsendQQ) {
            String playerName = event.getPlayer().getName();
            socket_send.send_data(Placeholder.data, Placeholder.group,
                    playerName, message(BaseConfig.QuitMessage, playerName));
        }
    }

    @EventHandler
    public void onPlayerSay(AsyncPlayerChatEvent event) {
        String player_message;
        player_message = event.getMessage();
        if (BaseConfig.UserNotSendCommder) {
            if (player_message.indexOf("/") == 0)
                return;
        } else if (BaseConfig.MuteList.contains(event.getPlayer().getName()))
            return;
        if (BaseConfig.MinecraftMode != 0 && use.hand.socket_runFlag) {
            boolean send_ok = false;
            Player player = event.getPlayer();
            String message = BaseConfig.MinecraftMessage;
            String playerName = player.getName();
            message = message.replaceAll(Placeholder.Player, playerName);
            message = message.replaceAll(Placeholder.Servername, BaseConfig.MinecraftServerName);
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (player_message.indexOf(BaseConfig.MinecraftCheck) == 0 && BaseConfig.MinecraftMode == 1) {
                player_message = player_message.replaceFirst(BaseConfig.MinecraftCheck, "");
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            } else if (BaseConfig.MinecraftMode == 2) {
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (BaseConfig.UserSendSucceed && send_ok)
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "§d[Minecraft_QQ]" + BaseConfig.UserSendSucceedMessage));
        }
    }
}