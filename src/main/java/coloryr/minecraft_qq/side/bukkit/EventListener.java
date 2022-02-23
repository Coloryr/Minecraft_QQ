package coloryr.minecraft_qq.side.bukkit;

import coloryr.minecraft_qq.MBukkit;
import coloryr.minecraft_qq.Minecraft_QQ;
import coloryr.minecraft_qq.api.Placeholder;
import coloryr.minecraft_qq.utils.SocketUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    private String message(String message, Player player) {
        if (MBukkit.PAPI) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        message = message.replaceAll(Minecraft_QQ.config.Placeholder.Player, player.getName());
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (SocketUtils.isRun() && Minecraft_QQ.config.Join.sendQQ) {
            String playerName = event.getPlayer().getName();
            SocketUtils.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.config.Join.Message, event.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (SocketUtils.isRun() && Minecraft_QQ.config.Quit.sendQQ) {
            String playerName = event.getPlayer().getName();
            SocketUtils.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.config.Quit.Message, event.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerSay(AsyncPlayerChatEvent event) {
        String playerMessage = event.getMessage();
        if (Minecraft_QQ.config.User.NotSendCommand) {
            if (playerMessage.startsWith("/"))
                return;
        } else if (Minecraft_QQ.config.Mute.contains(event.getPlayer().getName()))
            return;
        if (Minecraft_QQ.config.ServerSet.Mode != 0 && SocketUtils.isRun()) {
            Player player = event.getPlayer();
            String message = Minecraft_QQ.config.ServerSet.Message;
            String playerName = player.getName();
            message = message.replaceAll(Minecraft_QQ.config.Placeholder.Player, playerName)
                    .replaceAll(Minecraft_QQ.config.Placeholder.ServerName, Minecraft_QQ.config.ServerSet.ServerName)
                    .replaceAll(Minecraft_QQ.config.Placeholder.Server, "");
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (Minecraft_QQ.config.ServerSet.Mode == 1 &&
                    playerMessage.indexOf(Minecraft_QQ.config.ServerSet.Check) == 0) {
                playerMessage = playerMessage.replaceFirst(Minecraft_QQ.config.ServerSet.Check, "");
                message = message.replaceAll(Minecraft_QQ.config.Placeholder.Message, playerMessage);
                SocketUtils.sendData(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Minecraft_QQ.config.ServerSet.Mode == 2) {
                message = message.replaceAll(Minecraft_QQ.config.Placeholder.Message, playerMessage);
                if (MBukkit.PAPI) {
                    message = PlaceholderAPI.setPlaceholders(player, message);
                }
                SocketUtils.sendData(Placeholder.data, Placeholder.group, playerName, message);
            }
        }
    }
}