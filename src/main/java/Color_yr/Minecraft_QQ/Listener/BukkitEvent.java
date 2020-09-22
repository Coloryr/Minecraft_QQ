package Color_yr.Minecraft_QQ.Listener;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Minecraft_QQBukkit;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitEvent implements Listener {

    private String message(String message, Player player) {
        if (Minecraft_QQBukkit.PAPI) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), player.getName());
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.getJoin().isSendQQ()) {
            String playerName = event.getPlayer().getName();
            Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.getJoin().getMessage(), event.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.getQuit().isSendQQ()) {
            String playerName = event.getPlayer().getName();
            Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.getQuit().getMessage(), event.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerSay(AsyncPlayerChatEvent event) {
        String playerMessage = event.getMessage();
        if (Minecraft_QQ.Config.getUser().isNotSendCommand()) {
            if (playerMessage.indexOf("/") == 0)
                return;
        } else if (Minecraft_QQ.Config.getMute().contains(event.getPlayer().getName()))
            return;
        Player player = event.getPlayer();
        boolean sendok = false;
        String message = Minecraft_QQ.Config.getServerSet().getMessage();
        String playerName = player.getName();
        message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName)
                .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), "");
        message = ChatColor.translateAlternateColorCodes('&', message);
        if (Minecraft_QQ.Config.getServerSet().getMode() == 1 &&
                playerMessage.indexOf(Minecraft_QQ.Config.getServerSet().getCheck()) == 0) {
            playerMessage = playerMessage.replaceFirst(Minecraft_QQ.Config.getServerSet().getCheck(), "");
            message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getMessage(), playerMessage);
            sendok = Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
        } else if (Minecraft_QQ.Config.getServerSet().getMode() == 2) {
            message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getMessage(), playerMessage);
            if (Minecraft_QQBukkit.PAPI) {
                message = PlaceholderAPI.setPlaceholders(player, message);
            }
            sendok = Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
        }
        if (Minecraft_QQ.Config.getUser().isSendSucceed() && sendok)
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "§d[Minecraft_QQ]" + Minecraft_QQ.Config.getLanguage().getSucceedMessage()));
    }
}