package Color_yr.Minecraft_QQ;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;

import static Color_yr.Minecraft_QQ.Minecraft_QQ_bungee.config_data_bungee;

public class command_bungee extends Command implements TabExecutor {

    public command_bungee() {
        super("qq");
    }

    public void reload(CommandSender sender) {
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人IP： " + config_bukkit.System_IP));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人端口 " + config_bukkit.System_PORT));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人模式 " + config_bukkit.Minecraft_Mode));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§eDebug模式 " + config_bukkit.System_Debug));
        if (config_bukkit.System_Debug == true) {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家加入时是否发送文本 " + config_bukkit.Join_sendQQ + "文本 " + config_bukkit.Join_Message));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家退出时是否发送文本 " + config_bukkit.Quit_sendQQ + "文本 " + config_bukkit.Quit_Message));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家切换子服时是否发送文本 " + config_data_bungee.ChangeServer_sendQQ + "文本 " + config_data_bungee.ChangeServer_Message));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e服务器名字 " + config_bukkit.Minecraft_ServerName));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e触发文本 " + config_bukkit.Minecraft_Check));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e发送至QQ群的文本 " + config_bukkit.Minecraft_Message));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e发送至玩家消息窗口的文本 " + config_bukkit.Minecraft_Say));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人模式 " + config_bukkit.Minecraft_Mode));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启在线人数显示 " + config_bukkit.Minecraft_SendMode));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否单独显示子服的人数 " + config_data_bungee.Minecraft_SendOneByOne + "文本" + config_data_bungee.Minecraft_SendOneByOneMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否隐藏空的子服 " + config_data_bungee.Minecraft_HideEmptyServer));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e在线人数文本 " + config_bukkit.Minecraft_PlayerListMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e服务器状态文本 " + config_bukkit.Minecraft_ServerOnlineMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否显示子服是否在线 " + config_bukkit.Minecraft_ServerOnlineMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启全服公告文本 " + config_data_bungee.SendAllServer_Enable + "文本" + config_data_bungee.SendAllServer_Message));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否隔离服务器消息 " + config_data_bungee.SendAllServer_OnlySideServer));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启自动重连 " + config_bukkit.System_AutoConnet + "时间(ms)" + config_bukkit.System_AutoConnetTime));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e完成发送后是否提醒 " + config_bukkit.User_SendSucceed + "文本" + config_bukkit.User_SendSucceedMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否屏蔽玩家输入指令" + config_bukkit.User_NotSendCommder));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录链接情况 " + logs.Socket_log));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录群发来的消息 " + logs.Group_log));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录发送至群的消息 " + logs.Send_log));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录错误内容 " + logs.Error_log));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e数据包检测头 " + config_bukkit.Head));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e数据包检测尾 " + config_bukkit.End));
        }
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e重载成功"));
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("Minecraft_QQ.admin")) {
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("config")) {
                        Minecraft_QQ_bungee.reloadConfig();
                        reload(sender);
                    } else if (args[1].equalsIgnoreCase("socket")) {
                        socket_restart socket_restart = new socket_restart();
                        socket_restart.restart_socket();
                    } else
                        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
                } else {
                    Minecraft_QQ_bungee.reloadConfig();
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
            if (sender.hasPermission("Minecraft_QQ.admin")) {
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2帮助手册"));
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq say 来发送消息"));
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload 来重读插件配置文件和重新连接"));
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload config 来重读插件配置文件"));
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload socket 来重新连接"));
                return;
            } else {
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c你没有权限"));
                return;
            }
        }
        if (args[0].equalsIgnoreCase("say")) {
            if (sender.hasPermission("Minecraft_QQ.admin")) {
                if (args[1].equalsIgnoreCase("") == false) {
                    if (socket.socket_runFlag == true) {
                        socket_send.send_data("data", "group", "无", args[1]);
                        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2已发送" + args[1]));
                    } else
                        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，酷Q未连接"));
                } else
                    sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请输入文本"));
            } else
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c你没有权限"));
        } else {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> arguments = null;
        if (sender.hasPermission("Minecraft_QQ.admin") == true) {
            arguments = new ArrayList<String>();
            if (args.length != 0 && args[0].equalsIgnoreCase("reload") == true) {
                arguments.add("config");
                arguments.add("socket");
            } else {
                arguments.add("help");
                arguments.add("say");
                arguments.add("reload");
            }
        }
        return arguments;
    }
}
