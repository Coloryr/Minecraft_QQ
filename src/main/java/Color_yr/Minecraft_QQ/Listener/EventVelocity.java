package Color_yr.Minecraft_QQ.Listener;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Minecraft_QQVelocity;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;

public class EventVelocity {
    private String message(String message, String playerName) {
        message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName);
        message = message.replaceAll("&", "§");
        return message;
    }

    @Subscribe
    public void onPlayerQuit(final PostLoginEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.getJoin().isSendQQ()) {
            String playerName = event.getPlayer().getUsername();
            boolean sendOk = Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.getJoin().getMessage(), playerName));
            if (!sendOk)
                Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c数据发送失败");
        }
    }

    @Subscribe
    public void onPlayerquit(DisconnectEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.getQuit().isSendQQ()) {
            String playerName = event.getPlayer().getUsername();
            boolean sendOk = Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.getQuit().getMessage(), playerName));
            if (!sendOk)
                Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c数据发送失败");
        }
    }

    @Subscribe
    public void onPlayerChangeServer(ServerConnectedEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.getChangeServer().isSendQQ()) {
            String message = Minecraft_QQ.Config.getChangeServer().getMessage();
            Player player = event.getPlayer();
            String playerName = player.getUsername();
            String server = Minecraft_QQ.Config.getServers().get(event.getServer().getServerInfo().getName());
            if (server == null || server.isEmpty()) {
                server = event.getServer().getServerInfo().getName();
            }
            message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName)
                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), server);
            message = message.replaceAll("&", "§");
            boolean sendOk = Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
            if (!sendOk)
                Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c数据发送失败");
        }
    }

    @Subscribe
    public void onChar(PlayerChatEvent event) {
        String playerMessage = event.getMessage();
        Player player = event.getPlayer();
        if (Minecraft_QQ.Config.getUser().isNotSendCommand()) {
            if (playerMessage.indexOf("/") == 0)
                return;
        } else if (Minecraft_QQ.Config.getMute().contains(player.getUsername()))
            return;
        if (Minecraft_QQ.Config.getServerSet().getMode() != 0 && Minecraft_QQ.control.isRun()) {
            boolean sendOk = false;
            String message = Minecraft_QQ.Config.getServerSet().getMessage();
            String playerName = player.getUsername();
            if (player.getCurrentServer().isPresent()) {
                String Server = Minecraft_QQ.Config.getServers().get(player.getCurrentServer().get().getServerInfo().getName());
                if (Server == null || Server.isEmpty()) {
                    Server = player.getCurrentServer().get().getServerInfo().getName();
                }
                message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName)
                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(), Server);
                message = message.replaceAll("&", "§");
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
                    SendAllServer_send = SendAllServer_send.replaceAll("&", "§");
                    if (Minecraft_QQ.Config.getSendAllServer().isOnlySideServer()) {
                        for (Player player1 : Minecraft_QQVelocity.plugin.server.getAllPlayers()) {
                            if (player.getCurrentServer().isPresent() && player1.getCurrentServer().isPresent() &&
                                    !player1.getCurrentServer().get().getServerInfo().getName().equals(player.getCurrentServer().get().getServerInfo().getName()))
                                player1.sendMessage(Component.text(SendAllServer_send));
                        }
                    } else {
                        for (Player player1 : Minecraft_QQVelocity.plugin.server.getAllPlayers()) {
                            player1.sendMessage(Component.text(SendAllServer_send));
                        }
                        event.setResult(PlayerChatEvent.ChatResult.denied());
                    }
                }
                if (Minecraft_QQ.Config.getUser().isSendSucceed() && sendOk)
                    player.sendMessage(Component.text("§d[Minecraft_QQ]" +
                            Minecraft_QQ.Config.getLanguage().getSucceedMessage().replaceAll("&", "§")));
            } else {
                Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c玩家：" + playerName + "服务器错误");
            }
        }
    }
}
