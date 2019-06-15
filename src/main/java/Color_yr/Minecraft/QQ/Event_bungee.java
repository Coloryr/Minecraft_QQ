package Color_yr.Minecraft.QQ;

import com.google.gson.Gson;
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

import static Color_yr.Minecraft.QQ.Minecraft_QQ.config_data;
import static Color_yr.Minecraft.QQ.Minecraft_QQ_bungee.config_data_bungee;

public class Event_bungee implements Listener {
	@EventHandler
	public void onPostLogin(PostLoginEvent event) {
		if (socket.socket_runFlag == true && config_data.Join_sendQQ == true) {
			String message = config_data.Join_Message;
			String playerName = event.getPlayer().getName();
			message = message.replaceAll("%Player%", playerName);
			message = ChatColor.translateAlternateColorCodes('&', message);
			Send_Json send_bean = new Send_Json();
			Gson send_gson = new Gson();
			send_bean.setData("data");
			send_bean.setGroup("group");
			send_bean.setPlayer("无");
			send_bean.setMessage(message);
			socket.socket_send(send_gson.toJson(send_bean));
		}
	}

	@EventHandler
	public void onPlayerquit(PlayerDisconnectEvent event) {
		if (socket.socket_runFlag == true && config_data.Quit_sendQQ == true) {
			String message = config_data.Quit_Message;
			String playerName = event.getPlayer().getName();
			message = message.replaceAll("%Player%", playerName);
			message = ChatColor.translateAlternateColorCodes('&', message);
			Send_Json send_bean = new Send_Json();
			Gson send_gson = new Gson();
			send_bean.setData("data");
			send_bean.setGroup("group");
			send_bean.setPlayer("无");
			send_bean.setMessage(message);
			socket.socket_send(send_gson.toJson(send_bean));
		}
	}

	@EventHandler
	public void onPlayerChangeServer(ServerSwitchEvent event) {
		if (socket.socket_runFlag == true && config_data_bungee.ChangeServer_sendQQ == true) {
			String message = config_data_bungee.ChangeServer_Message;
			ProxiedPlayer player = event.getPlayer();
			String playerName = player.getName();
			String Server = config_data_bungee.config.getString("Servers." + player.getServer().getInfo().getName());
			if (Server == "" || Server == null) {
				Server = player.getServer().getInfo().getName();
			}
			message = message.replaceAll("%Player%", playerName).replaceAll("%Server%", Server);
			message = ChatColor.translateAlternateColorCodes('&', message);
			Send_Json send_bean = new Send_Json();
			Gson send_gson = new Gson();
			send_bean.setData("data");
			send_bean.setGroup("group");
			send_bean.setPlayer("无");
			send_bean.setMessage(message);
			socket.socket_send(send_gson.toJson(send_bean));
		}
	}

	@EventHandler
	public void onChar(ChatEvent event) {
		String player_message;
		player_message = event.getMessage();
		if (config_data.User_NotSendCommder == true) {
			if (player_message.indexOf("/") == 0)
				return;
		}
		if (config_data.Minecraft_Mode != 0 && socket.socket_runFlag == true) {
			ProxiedPlayer player = (ProxiedPlayer) event.getSender();
			String message = config_data.Minecraft_Message;
			String playerName = player.getName();
			String Server = config_data_bungee.config.getString("Servers." + player.getServer().getInfo().getName());
			if (Server == "" || Server == null) {
				Server = player.getServer().getInfo().getName();
			}
			message = message.replaceAll("%Player%", playerName)
					.replaceAll("%Servername%", config_data.Minecraft_ServerName)
					.replaceAll("%Server%", Server);
			message = ChatColor.translateAlternateColorCodes('&', message);
			if (player_message.indexOf(config_data.Minecraft_Check) == 0
					&& config_data.Minecraft_Mode == 1) {
				player_message = player_message.replaceFirst(config_data.Minecraft_Check, "");
				message = message.replaceAll("%Message%", player_message);
				Send_Json send_bean = new Send_Json();
				Gson send_gson = new Gson();
				send_bean.setData("data");
				send_bean.setGroup("group");
				send_bean.setPlayer(playerName);
				send_bean.setMessage(message);
				socket.socket_send(send_gson.toJson(send_bean));
			} else if (config_data.Minecraft_Mode == 2) {
				message = message.replaceAll("%Message%", player_message);
				Send_Json send_bean = new Send_Json();
				Gson send_gson = new Gson();
				send_bean.setData("data");
				send_bean.setGroup("group");
				send_bean.setPlayer(playerName);
				send_bean.setMessage(message);
				socket.socket_send(send_gson.toJson(send_bean));
			}
			if (config_data_bungee.SendAllServer_Enable == true) {
				String SendAllServer_send = config_data_bungee.SendAllServer_Message;
				SendAllServer_send = SendAllServer_send
						.replaceAll("%Servername%", config_data.Minecraft_ServerName)
						.replaceAll("%Server%", Server)
						.replaceAll("%Player%", playerName)
						.replaceAll("%Message%", player_message);
				SendAllServer_send = ChatColor.translateAlternateColorCodes('&', SendAllServer_send);
				if (config_data_bungee.SendAllServer_OnlySideServer == true) {
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
			if (config_data.User_SendSucceed == true)
				player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',
						"§d[Minecraft_QQ]" + config_data.User_SendSucceedMessage)));
		}
	}
}
