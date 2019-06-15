package Color_yr.Minecraft.QQ;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import static Color_yr.Minecraft.QQ.Minecraft_QQ.config_data;
import static Color_yr.Minecraft.QQ.Minecraft_QQ_bungee.config_data_bungee;

public class command_bungee extends Command {

	public command_bungee() {
		super("qq");
	}

	public void reload(CommandSender sender) {
		sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人IP： " + config_data.System_IP));
		sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人端口 " + config_data.System_PORT));
		sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人模式 " + config_data.Minecraft_Mode));
		sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§eDebug模式 " + config_data.System_Debug));
		if (config_data.System_Debug == true) {
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家加入时是否发送文本 " + config_data.Join_sendQQ + "文本 " + config_data.Join_Message));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家退出时是否发送文本 " + config_data.Quit_sendQQ + "文本 " + config_data.Quit_Message));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家切换子服时是否发送文本 " + config_data_bungee.ChangeServer_sendQQ + "文本 " + config_data_bungee.ChangeServer_Message));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e服务器名字 " + config_data.Minecraft_ServerName));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e触发文本 " + config_data.Minecraft_Check));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e发送至QQ群的文本 " + config_data.Minecraft_Message));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e发送至玩家消息窗口的文本 " + config_data.Minecraft_Say));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人模式 " + config_data.Minecraft_Mode));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启在线人数显示 " + config_data.Minecraft_SendMode));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否单独显示子服的人数 " + config_data_bungee.Minecraft_SendOneByOne + "文本" + config_data_bungee.Minecraft_SendOneByOneMessage));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否隐藏空的子服 " + config_data_bungee.Minecraft_HideEmptyServer));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e在线人数文本 " + config_data.Minecraft_PlayerListMessage));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e服务器状态文本 " + config_data.Minecraft_ServerOnlineMessage));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否显示子服是否在线 " + config_data.Minecraft_ServerOnlineMessage));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启全服公告文本 " + config_data_bungee.SendAllServer_Enable + "文本" + config_data_bungee.SendAllServer_Message));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否隔离服务器消息 " + config_data_bungee.SendAllServer_OnlySideServer));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启自动重连 " + config_data.System_AutoConnet + "时间(ms)" + config_data.System_AutoConnetTime));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e完成发送后是否提醒 " + config_data.User_SendSucceed + "文本" + config_data.User_SendSucceedMessage));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否屏蔽玩家输入指令" + config_data.User_NotSendCommder));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录链接情况 " + logs.Socket_log));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录群发来的消息 " + logs.Group_log));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录发送至群的消息 " + logs.Send_log));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录错误内容 " + logs.Error_log));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e数据包检测头 " + message_bungee.Head));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e数据包检测尾 " + message_bungee.End));
		}
		sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e重载成功"));
	}

	public void execute(CommandSender sender, String[] args) {
		if (args[0].equalsIgnoreCase("reload")) {
			if (sender.hasPermission("qq.admin")) {
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("config")) {
						Minecraft_QQ_bungee.reloadConfig();
						reload(sender);
					} else if (args[1].equalsIgnoreCase("socket")) {
						socket_restart socket_restart = new socket_restart();
						socket_restart.restart_socket();
					}
					else
						sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
				} else {
					Minecraft_QQ_bungee
							.reloadConfig();
					reload(sender);
					socket_restart socket_restart = new socket_restart();
					socket_restart.restart_socket();
				}
				return;
			} else {
				sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c你没有权限"));
				return;
			}
		}
		if (args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2帮助手册"));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq say 来发送消息"));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload 来重读插件配置文件和重新连接"));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload config 来重读插件配置文件"));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload socket 来重新连接"));
			return;
		}
		if (args[0].equalsIgnoreCase("say")) {
			if (sender.hasPermission("qq.admin")) {
				if (args[1] != "") {
					if (socket.socket_runFlag == true) {
						socket.socket_send("[群消息]" + "()" + args[1]);
						sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2已发送" + args[1]));
					} else
						sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，酷Q未连接"));
				} else
					sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请输入文本"));
			} else
				sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c你没有权限"));
			return;
		} else {
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
			return;
		}
	}
}
