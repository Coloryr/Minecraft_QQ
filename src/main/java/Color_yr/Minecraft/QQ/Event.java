package Color_yr.Minecraft.QQ;

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

public class Event implements Listener {
	@EventHandler
	public void onPostLogin(PostLoginEvent event) {
		if (socket.socket_runFlag == true && Minecraft_QQ.Join_sendQQ == true) {
			String message = Minecraft_QQ.Join_Message;
			String playerName = event.getPlayer().getName();
			message = message.replaceAll("%Player%", playerName);
			message = ChatColor.translateAlternateColorCodes('&', message);
			socket.socket_send("[群消息]" + "()" + message);
		}
	}

	@EventHandler
	public void onPlayerquit(PlayerDisconnectEvent event) {
		if (socket.socket_runFlag == true && Minecraft_QQ.Quit_sendQQ == true) {
			String message = Minecraft_QQ.Quit_Message;
			String playerName = event.getPlayer().getName();
			message = message.replaceAll("%Player%", playerName);
			message = ChatColor.translateAlternateColorCodes('&', message);
			socket.socket_send("[群消息]" + "()" + message);
		}
	}

	@EventHandler
	public void onPlayerChangeServer(ServerSwitchEvent event) {
		if (socket.socket_runFlag == true && Minecraft_QQ.ChangeServer_sendQQ == true) {
			String message = Minecraft_QQ.ChangeServer_Message;
			ProxiedPlayer player = (ProxiedPlayer) event.getPlayer();
			String playerName = player.getName();
			String Server = Minecraft_QQ.config.getString("Servers." + player.getServer().getInfo().getName());
			if (Server == "" || Server == null) {
				Server = player.getServer().getInfo().getName();
			}
			message = message.replaceAll("%Player%", playerName).replaceAll("%Server%", Server);
			message = ChatColor.translateAlternateColorCodes('&', message);
			socket.socket_send("[群消息]" + "()" + message);
		}
	}

	@EventHandler
	public void onChar(ChatEvent event) {
		String player_message;
		player_message = event.getMessage();
		if (Minecraft_QQ.User_NotSendCommder == true) {
			if (player_message.indexOf("/") == 0)
				return;
		}
		if (Minecraft_QQ.Minecraft_Mode != 0 && socket.socket_runFlag == true) {
			ProxiedPlayer player = (ProxiedPlayer) event.getSender();
			String message = Minecraft_QQ.Minecraft_Message;
			String playerName = player.getName();
			String Server = Minecraft_QQ.config.getString("Servers." + player.getServer().getInfo().getName());
			if (Server == "" || Server == null) {
				Server = player.getServer().getInfo().getName();
			}
			message = message.replaceAll("%Player%", playerName)
					.replaceAll("%Servername%", Minecraft_QQ.Minecraft_ServerName)
					.replaceAll("%Server%", Server);
			message = ChatColor.translateAlternateColorCodes('&', message);
			if (player_message.indexOf(Minecraft_QQ.Minecraft_Check) == 0
					&& Minecraft_QQ.Minecraft_Mode == 1) {
				player_message = player_message.replaceFirst(Minecraft_QQ.Minecraft_Check, "");
				message = message.replaceAll("%Message%", player_message);
				socket.socket_send("[群消息]" + '(' + playerName + ')' + message);
			} else if (Minecraft_QQ.Minecraft_Mode == 2) {
				message = message.replaceAll("%Message%", player_message);
				socket.socket_send("[群消息]" + '(' + playerName + ')' + message);
			}
			if (Minecraft_QQ.SendAllServer_Enable == true) {
				String SendAllServer_send = Minecraft_QQ.SendAllServer_Message;
				SendAllServer_send = SendAllServer_send
						.replaceAll("%Servername%", Minecraft_QQ.Minecraft_ServerName)
						.replaceAll("%Server%", Server)
						.replaceAll("%Player%", playerName)
						.replaceAll("%Message%", player_message);
				SendAllServer_send = ChatColor.translateAlternateColorCodes('&', SendAllServer_send);
				if (Minecraft_QQ.SendAllServer_OnlySideServer == true) {
					for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
						if (player1.getServer().getInfo().getName() != player.getServer().getInfo().getName())
							player1.sendMessage(new TextComponent(SendAllServer_send));
					}
				} else {
					for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
						player1.sendMessage(new TextComponent(SendAllServer_send));
					}
					event.setCancelled(true);
				}
			}
			if (Minecraft_QQ.User_SendSucceed == true)
				player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',
						"§d[Minecraft_QQ]" + Minecraft_QQ.User_SendSucceedMessage)));
		}
	}
}
