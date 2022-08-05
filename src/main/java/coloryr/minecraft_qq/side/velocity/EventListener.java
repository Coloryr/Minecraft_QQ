package coloryr.minecraft_qq.side.velocity;

import coloryr.minecraft_qq.MVelocity;
import coloryr.minecraft_qq.Minecraft_QQ;
import coloryr.minecraft_qq.api.Placeholder;
import coloryr.minecraft_qq.utils.SocketUtils;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;

public class EventListener {
    private String message(String message, String playerName) {
        message = message.replaceAll(Minecraft_QQ.config.Placeholder.Player, playerName);
        message = message.replaceAll("&", "§");
        return message;
    }

    @Subscribe
    public void onPlayerQuit(final PostLoginEvent event) {
        if (SocketUtils.isRun() && Minecraft_QQ.config.Join.Send) {
            String playerName = event.getPlayer().getUsername();
            SocketUtils.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.config.Join.Message, playerName));
        }
    }

    @Subscribe
    public void onPlayerquit(DisconnectEvent event) {
        if (SocketUtils.isRun() && Minecraft_QQ.config.Quit.Send) {
            String playerName = event.getPlayer().getUsername();
            SocketUtils.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.config.Quit.Message, playerName));
        }
    }

    @Subscribe
    public void onPlayerChangeServer(ServerConnectedEvent event) {
        if (SocketUtils.isRun() && Minecraft_QQ.config.ChangeServer.Send) {
            String message = Minecraft_QQ.config.ChangeServer.Message;
            Player player = event.getPlayer();
            String playerName = player.getUsername();
            String server = Minecraft_QQ.config.Servers.get(event.getServer().getServerInfo().getName());
            if (server == null || server.isEmpty()) {
                server = event.getServer().getServerInfo().getName();
            }
            message = message.replaceAll(Minecraft_QQ.config.Placeholder.Player, playerName)
                    .replaceAll(Minecraft_QQ.config.Placeholder.Server, server);
            message = message.replaceAll("&", "§");
            SocketUtils.sendData(Placeholder.data, Placeholder.group, playerName, message);
        }
    }

    @Subscribe
    public void onChar(PlayerChatEvent event) {
        String playerMessage = event.getMessage();
        Player player = event.getPlayer();
        if (Minecraft_QQ.config.User.NotSendCommand) {
            if (playerMessage.startsWith("/"))
                return;
        } else if (Minecraft_QQ.config.Mute.contains(player.getUsername()))
            return;
        if (Minecraft_QQ.config.ServerSet.Mode != 0 && SocketUtils.isRun()) {
            String message = Minecraft_QQ.config.ServerSet.Message;
            String playerName = player.getUsername();
            if (player.getCurrentServer().isPresent()) {
                String Server = Minecraft_QQ.config.Servers.get(player.getCurrentServer().get().getServerInfo().getName());
                if (Server == null || Server.isEmpty()) {
                    Server = player.getCurrentServer().get().getServerInfo().getName();
                }
                message = message.replaceAll(Minecraft_QQ.config.Placeholder.Player, playerName)
                        .replaceAll(Minecraft_QQ.config.Placeholder.ServerName, Minecraft_QQ.config.ServerSet.ServerName)
                        .replaceAll(Minecraft_QQ.config.Placeholder.Server, Server)
                        .replaceAll("&", "§");
                if (Minecraft_QQ.config.ServerSet.Mode == 1
                        && playerMessage.indexOf(Minecraft_QQ.config.ServerSet.Check) == 0) {
                    playerMessage = playerMessage.replaceFirst(Minecraft_QQ.config.ServerSet.Check, "");
                    message = message.replaceAll(Minecraft_QQ.config.Placeholder.Message, playerMessage);
                    SocketUtils.sendData(Placeholder.data, Placeholder.group, playerName, message);
                } else if (Minecraft_QQ.config.ServerSet.Mode == 2) {
                    message = message.replaceAll(Minecraft_QQ.config.Placeholder.Message, playerMessage);
                    SocketUtils.sendData(Placeholder.data, Placeholder.group, playerName, message);
                }
                if (Minecraft_QQ.config.SendAllServer.Enable) {
                    String SendAllServer_send = Minecraft_QQ.config.SendAllServer.Message;
                    SendAllServer_send = SendAllServer_send
                            .replaceAll(Minecraft_QQ.config.Placeholder.ServerName, Minecraft_QQ.config.ServerSet.ServerName)
                            .replaceAll(Minecraft_QQ.config.Placeholder.Server, Server)
                            .replaceAll(Minecraft_QQ.config.Placeholder.Player, playerName)
                            .replaceAll(Minecraft_QQ.config.Placeholder.Message, playerMessage);
                    SendAllServer_send = SendAllServer_send.replaceAll("&", "§");
                    if (Minecraft_QQ.config.SendAllServer.OnlySideServer) {
                        for (Player player1 : MVelocity.plugin.server.getAllPlayers()) {
                            if (player.getCurrentServer().isPresent() && player1.getCurrentServer().isPresent() &&
                                    !player1.getCurrentServer().get().getServerInfo().getName().equals(player.getCurrentServer().get().getServerInfo().getName()))
                                player1.sendMessage(Component.text(SendAllServer_send));
                        }
                    } else {
                        for (Player player1 : MVelocity.plugin.server.getAllPlayers()) {
                            player1.sendMessage(Component.text(SendAllServer_send));
                        }
                        event.setResult(PlayerChatEvent.ChatResult.denied());
                    }
                }
            } else {
                Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c玩家：" + playerName + "服务器错误");
            }
        }
    }
}
