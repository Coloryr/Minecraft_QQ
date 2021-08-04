package Color_yr.Minecraft_QQ.side.velocity;

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
        message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Player, playerName);
        message = message.replaceAll("&", "§");
        return message;
    }

    @Subscribe
    public void onPlayerQuit(final PostLoginEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.Join.sendQQ) {
            String playerName = event.getPlayer().getUsername();
            Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.Join.Message, playerName));
        }
    }

    @Subscribe
    public void onPlayerquit(DisconnectEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.Quit.sendQQ) {
            String playerName = event.getPlayer().getUsername();
            Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group,
                    playerName, message(Minecraft_QQ.Config.Quit.Message, playerName));
        }
    }

    @Subscribe
    public void onPlayerChangeServer(ServerConnectedEvent event) {
        if (Minecraft_QQ.control.isRun() && Minecraft_QQ.Config.ChangeServer.sendQQ) {
            String message = Minecraft_QQ.Config.ChangeServer.Message;
            Player player = event.getPlayer();
            String playerName = player.getUsername();
            String server = Minecraft_QQ.Config.Servers.get(event.getServer().getServerInfo().getName());
            if (server == null || server.isEmpty()) {
                server = event.getServer().getServerInfo().getName();
            }
            message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Player, playerName)
                    .replaceAll(Minecraft_QQ.Config.Placeholder.Server, server);
            message = message.replaceAll("&", "§");
            Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
        }
    }

    @Subscribe
    public void onChar(PlayerChatEvent event) {
        String playerMessage = event.getMessage();
        Player player = event.getPlayer();
        if (Minecraft_QQ.Config.User.NotSendCommand) {
            if (playerMessage.startsWith("/"))
                return;
        } else if (Minecraft_QQ.Config.Mute.contains(player.getUsername()))
            return;
        if (Minecraft_QQ.Config.ServerSet.Mode != 0 && Minecraft_QQ.control.isRun()) {
            String message = Minecraft_QQ.Config.ServerSet.Message;
            String playerName = player.getUsername();
            if (player.getCurrentServer().isPresent()) {
                String Server = Minecraft_QQ.Config.Servers.get(player.getCurrentServer().get().getServerInfo().getName());
                if (Server == null || Server.isEmpty()) {
                    Server = player.getCurrentServer().get().getServerInfo().getName();
                }
                message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Player, playerName)
                        .replaceAll(Minecraft_QQ.Config.Placeholder.ServerName, Minecraft_QQ.Config.ServerSet.ServerName)
                        .replaceAll(Minecraft_QQ.Config.Placeholder.Server, Server)
                        .replaceAll("&", "§");
                if (Minecraft_QQ.Config.ServerSet.Mode == 1
                        && playerMessage.indexOf(Minecraft_QQ.Config.ServerSet.Check) == 0) {
                    playerMessage = playerMessage.replaceFirst(Minecraft_QQ.Config.ServerSet.Check, "");
                    message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Message, playerMessage);
                    Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
                } else if (Minecraft_QQ.Config.ServerSet.Mode == 2) {
                    message = message.replaceAll(Minecraft_QQ.Config.Placeholder.Message, playerMessage);
                    Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, playerName, message);
                }
                if (Minecraft_QQ.Config.SendAllServer.Enable) {
                    String SendAllServer_send = Minecraft_QQ.Config.SendAllServer.Message;
                    SendAllServer_send = SendAllServer_send
                            .replaceAll(Minecraft_QQ.Config.Placeholder.ServerName, Minecraft_QQ.Config.ServerSet.ServerName)
                            .replaceAll(Minecraft_QQ.Config.Placeholder.Server, Server)
                            .replaceAll(Minecraft_QQ.Config.Placeholder.Player, playerName)
                            .replaceAll(Minecraft_QQ.Config.Placeholder.Message, playerMessage);
                    SendAllServer_send = SendAllServer_send.replaceAll("&", "§");
                    if (Minecraft_QQ.Config.SendAllServer.OnlySideServer) {
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
            } else {
                Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c玩家：" + playerName + "服务器错误");
            }
        }
    }
}
