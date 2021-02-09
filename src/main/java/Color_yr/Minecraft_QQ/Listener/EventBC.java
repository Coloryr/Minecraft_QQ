package Color_yr.Minecraft_QQ.Listener;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
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

public class EventBC implements Listener {

    private String message(String message, String playerName) {
        message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.getJoin().isSendQQ()) {
            String playerName = event.getPlayer().getName();
            boolean sendOk = Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.getJoin().getMessage(), playerName));
            if (!sendOk)
                Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c数据发送失败");
        }
    }

    @EventHandler
    public void onPlayerquit(PlayerDisconnectEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.getQuit().isSendQQ()) {
            String playerName = event.getPlayer().getName();
            boolean sendOk = Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.getQuit().getMessage(), playerName));
            if (!sendOk)
                Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c数据发送失败");
        }
    }

    @EventHandler
    public void onPlayerChangeServer(ServerSwitchEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.getChangeServer().isSendQQ()) {
            String message = Minecraft_QQ.Config.getChangeServer().getMessage();
            ProxiedPlayer player = event.getPlayer();
            String playerName = player.getName();
            String server = Minecraft_QQ.Config.getServers().get(player.getServer().getInfo().getName());
            if (server == null || server.isEmpty()) {
                server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName)
                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), server);
            message = ChatColor.translateAlternateColorCodes('&', message);
            boolean sendOk = Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
            if (!sendOk)
                Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c数据发送失败");
        }
    }

    @EventHandler
    public void onChar(ChatEvent event) {
        String playerMessage = event.getMessage();
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (Minecraft_QQ.Config.getUser().isNotSendCommand()) {
            if (playerMessage.indexOf("/") == 0)
                return;
        } else if (Minecraft_QQ.Config.getMute().contains(player.getName()))
            return;
        if (Minecraft_QQ.Config.getServerSet().getMode() != 0 && Minecraft_QQ.control.isRun()) {
            boolean sendOk = false;
            String message = Minecraft_QQ.Config.getServerSet().getMessage();
            String playerName = player.getName();
            String Server = Minecraft_QQ.Config.getServers().get(player.getServer().getInfo().getName());
            if (Server == null || Server.isEmpty()) {
                Server = player.getServer().getInfo().getName();
            }
            message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName)
                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), Server);
            message = ChatColor.translateAlternateColorCodes('&', message);
            if (Minecraft_QQ.Config.getServerSet().getMode() == 1
                    && playerMessage.indexOf(Minecraft_QQ.Config.getServerSet().getCheck()) == 0) {
                playerMessage = playerMessage.replaceFirst(Minecraft_QQ.Config.getServerSet().getCheck(), "");
                message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getMessage(), playerMessage);
                sendOk = Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Minecraft_QQ.Config.getServerSet().getMode() == 2) {
                message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getMessage(), playerMessage);
                sendOk = Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (Minecraft_QQ.Config.getSendAllServer().isEnable()) {
                String SendAllServer_send = Minecraft_QQ.Config.getSendAllServer().getMessage();
                SendAllServer_send = SendAllServer_send
                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), Server)
                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName)
                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getMessage(), playerMessage);
                SendAllServer_send = ChatColor.translateAlternateColorCodes('&', SendAllServer_send);
                if (Minecraft_QQ.Config.getSendAllServer().isOnlySideServer()) {
                    for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                        if (player.getServer() != null && player1.getServer() != null &&
                                !player1.getServer().getInfo().getName().equals(player.getServer().getInfo().getName()))
                            player1.sendMessage(new TextComponent(SendAllServer_send));
                    }
                } else {
                    for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
                        player1.sendMessage(new TextComponent(SendAllServer_send));
                    }
                    event.setCancelled(true);
                }
            }
            if (Minecraft_QQ.Config.getUser().isSendSucceed() && sendOk)
                player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',
                        "§d[Minecraft_QQ]" + Minecraft_QQ.Config.getLanguage().getSucceedMessage())));
        }
    }
}
