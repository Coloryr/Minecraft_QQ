package Color_yr.Minecraft.QQ;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class command extends Command {

	public command() {
		super("qq");
	}

	public void reload(CommandSender sender) {
		sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人IP： " + Minecraft_QQ.System_IP));
		sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人端口 " + Minecraft_QQ.System_PORT));
		sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人模式 " + Minecraft_QQ.Minecraft_Mode));
		sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§eDebug模式 " + Minecraft_QQ.System_Debug));
		if (Minecraft_QQ.System_Debug == true) {
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家加入时是否发送文本 " + Minecraft_QQ.Join_sendQQ + "文本 " + Minecraft_QQ.Join_Message));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家退出时是否发送文本 " + Minecraft_QQ.Quit_sendQQ + "文本 " + Minecraft_QQ.Quit_Message));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家切换子服时是否发送文本 " + Minecraft_QQ.ChangeServer_sendQQ + "文本 " + Minecraft_QQ.ChangeServer_Message));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e服务器名字 " + Minecraft_QQ.Minecraft_ServerName));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e触发文本 " + Minecraft_QQ.Minecraft_Check));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e发送至QQ群的文本 " + Minecraft_QQ.Minecraft_Message));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e发送至玩家消息窗口的文本 " + Minecraft_QQ.Minecraft_Say));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人模式 " + Minecraft_QQ.Minecraft_Mode));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启在线人数显示 " + Minecraft_QQ.Minecraft_SendMode));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否单独显示子服的人数 " + Minecraft_QQ.Minecraft_SendOneByOne + "文本" + Minecraft_QQ.Minecraft_SendOneByOneMessage));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否隐藏空的子服 " + Minecraft_QQ.Minecraft_HideEmptyServer));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e在线人数文本 " + Minecraft_QQ.Minecraft_PlayerListMessage));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e服务器状态文本 " + Minecraft_QQ.Minecraft_ServerOnlineMessage));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否显示子服是否在线 " + Minecraft_QQ.Minecraft_ServerOnlineMessage));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启全服公告文本 " + Minecraft_QQ.SendAllServer_Enable + "文本" + Minecraft_QQ.SendAllServer_Message));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否隔离服务器消息 " + Minecraft_QQ.SendAllServer_OnlySideServer));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启自动重连 " + Minecraft_QQ.System_AutoConnet + "时间(ms)" + Minecraft_QQ.System_AutoConnetTime));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e完成发送后是否提醒 " + Minecraft_QQ.User_SendSucceed + "文本" + Minecraft_QQ.User_SendSucceedMessage));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否屏蔽玩家输入指令" + Minecraft_QQ.User_NotSendCommder));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录链接情况 " + logs.Socket_log));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录群发来的消息 " + logs.Group_log));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录发送至群的消息 " + logs.Send_log));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录错误内容 " + logs.Error_log));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e数据包检测头 " + message.Head));
			sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e数据包检测尾 " + message.End));
		}
		sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e重载成功"));
	}

	public void execute(CommandSender sender, String[] args) {
		if (args[0].equalsIgnoreCase("reload")) {
			if (sender.hasPermission("qq.admin")) {
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("config")) {
						Minecraft_QQ.reloadConfig();
						reload(sender);
					} else if (args[1].equalsIgnoreCase("socket")) {
						socket_restart socket_restart = new socket_restart();
						socket_restart.restart_socket();
					}
					else
						sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
				} else {
					Minecraft_QQ.reloadConfig();
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
