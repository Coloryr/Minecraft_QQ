package Color_yr.Minecraft_QQ.side.bukkit;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Minecraft_QQBukkit;
import Color_yr.Minecraft_QQ.utils.SocketUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventBukkit implements Listener {

    private String message(String message, Player player) {
        if (Minecraft_QQBukkit.PAPI) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Player, player.getName());
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (SocketUtils.isRun() && Minecraft_QQ.Config.Join.sendQQ) {
            String playerName = event.getPlayer().getName();
            SocketUtils.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.Join.Message, event.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (SocketUtils.isRun() && Minecraft_QQ.Config.Quit.sendQQ) {
            String playerName = event.getPlayer().getName();
            SocketUtils.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.Quit.Message, event.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerSay(AsyncPlayerChatEvent event) {
        String playerMessage = event.getMessage();
        if (Minecraft_QQ.Config.User.NotSendCommand) {
            if (playerMessage.startsWith("/"))
                return;
        } else if (Minecraft_QQ.Config.Mute.contains(event.getPlayer().getName()))
            return;
        if (Minecraft_QQ.Config.ServerSet.Mode != 0 && SocketUtils.isRun()) {
            Player player = event.getPlayer();
            String message = Minecraft_QQ.Config.ServerSet.Message;
            String playerName = player.getName();
            message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Player, playerName)
                    .replaceAll(Minecraft_QQ.Config.Placeholder.ServerName, Minecraft_QQ.Config.ServerSet.ServerName)
                    .replaceAll(Minecraft_QQ.Config.Placeholder.Server, "");
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (Minecraft_QQ.Config.ServerSet.Mode == 1 &&
                    playerMessage.indexOf(Minecraft_QQ.Config.ServerSet.Check) == 0) {
                playerMessage = playerMessage.replaceFirst(Minecraft_QQ.Config.ServerSet.Check, "");
                message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Message, playerMessage);
                SocketUtils.sendData(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Minecraft_QQ.Config.ServerSet.Mode == 2) {
                message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Message, playerMessage);
                if (Minecraft_QQBukkit.PAPI) {
                    message = PlaceholderAPI.setPlaceholders(player, message);
                }
                SocketUtils.sendData(Placeholder.data, Placeholder.group, playerName, message);
            }
        }
    }
}