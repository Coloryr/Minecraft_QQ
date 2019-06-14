package Color_yr.Minecraft.QQ;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.lang.model.type.NullType;

public class message {
	public static String Head;
	public static String End;

	public static String get_string(String a, String b, String c)
	{
		int x = a.indexOf(b) + b.length();
		int y = a.indexOf(c);
		return a.substring(x, y);
	}

	public static void message_read(String info) {
		if (Minecraft_QQ.System_Debug == true)
			Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]收到数据：" + info);
		if (logs.Group_log == true) {
			logs logs = new logs();
			logs.log_write("[Group]" + info);
		}
		if (socket.socket_runFlag == false)
			return;
		ProxyServer proxyserver = ProxyServer.getInstance();
		while (info.indexOf(Head) == 0 && info.indexOf(End) != -1)
		{
			String buff = get_string(info, Head, End);
			if (buff.indexOf("[群消息]") == 0) {
				buff = buff.replaceAll("\\[群消息\\]", "");
				String say = Minecraft_QQ.Minecraft_Say;
				say = Minecraft_QQ.Minecraft_Say.replaceAll("%Servername%", Minecraft_QQ.Minecraft_ServerName).replaceAll("%Message%", buff);
				say = ChatColor.translateAlternateColorCodes('&', say);
				for (ProxiedPlayer player1 : ProxyServer.getInstance().getPlayers()) {
					player1.sendMessage(new TextComponent(say));
				}
			} else if (buff.indexOf("[在线人数]") == 0) {
				int all_player_number = 0;
				int one_player_number = 0;
				String one_server_player = null;
				String all_server_player = null;
				String send = Minecraft_QQ.Minecraft_PlayerListMessage;
				if (Minecraft_QQ.Minecraft_SendOneByOne == true) {
					Map<String, ServerInfo> Server = proxyserver.getServers();
					Collection<ServerInfo> values = Server.values();
					Iterator<ServerInfo> iterator2 = values.iterator();
					while (iterator2.hasNext()) {
						ServerInfo serverinfo = iterator2.next();
						String player_onserver = serverinfo.getPlayers().toString();
						if (player_onserver == "[]") {
							one_player_number = 0;
							if (Minecraft_QQ.Minecraft_HideEmptyServer == true) {
								one_player_number = 0;
								one_server_player = "";
							} else {
								String Server_name = Minecraft_QQ.config.getString("Servers." + serverinfo.getName());
								if (Server_name == "" || Server_name == null) {
									Server_name = serverinfo.getName();
									Server_name = Server_name.replace("null", "");
								}
								one_server_player = Minecraft_QQ.Minecraft_SendOneByOneMessage
										.replaceAll("%Server%", Server_name)
										.replaceAll("%player_number%", "0")
										.replaceAll("%player_list%", "无");
								all_server_player = all_server_player + one_server_player;
							}
						} else {
							one_player_number = 1;
							for (int i = 0; i < player_onserver.length(); i++) {
								if (player_onserver.charAt(i) == ',')
									one_player_number++;
							}
							String Server_name = Minecraft_QQ.config.getString("Servers." + serverinfo.getName());
							if (Server_name == "" || Server_name == null) {
								Server_name = serverinfo.getName();
								Server_name = Server_name.replace("null", "");
							}
							one_server_player = Minecraft_QQ.Minecraft_SendOneByOneMessage
									.replaceAll("%Server%", Server_name)
									.replaceAll("%player_number%", "" + one_player_number)
									.replaceAll("%player_list%", player_onserver.replace("[", "").replace("]", ""));
							all_player_number = all_player_number + one_player_number;
							all_server_player = all_server_player + one_server_player;
						}
					}
					if (all_player_number == 0) {
						send = send.replaceAll("%player_number%", "");
						send = send.replaceAll("%player_list%", "无");
					} else {
						send = send.replaceAll("%player_number%", "" + all_player_number);
						send = send.replaceAll("%player_list%", all_server_player.replace("null", ""));
					}
				} else {
					all_server_player = proxyserver.getPlayers().toString();

					if (all_server_player == "[]") {
						send = send.replaceAll("%player_number%", "0");
						send = send.replaceAll("%player_list%", "无");
					} else {
						for (int i = 0; i < all_server_player.length(); i++) {
							if (all_server_player.charAt(i) == ',')
								all_player_number++;
						}
						String number = "" + all_player_number;
						send = send.replaceAll("%player_number%", number);
						send = send.replaceAll("%player_list%", all_server_player.replace("[", "").replace("]", ""));
					}
				}
				send = send.replace("%Servername%", Minecraft_QQ.Minecraft_ServerName);
				socket.socket_send(send);
				if (logs.Group_log == true) {
					logs logs = new logs();
					logs.log_write("[group]查询在线人数");
				}
				if (Minecraft_QQ.System_Debug == true)
					Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]查询在线人数");
			} else if (buff.indexOf("[服务器状态]") == 0) {
				String send = Minecraft_QQ.Minecraft_ServerOnlineMessage;
				send = send.replaceAll("%Servername%", Minecraft_QQ.Minecraft_ServerName);
				socket.socket_send(send);
				if (logs.Group_log == true) {
					logs logs = new logs();
					logs.log_write("[group]查询服务器状态");
				}
				if (Minecraft_QQ.System_Debug == true)
					Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
			}
			int i = info.indexOf(End);
			info = info.substring(i + End.length());
		}
	}
}
