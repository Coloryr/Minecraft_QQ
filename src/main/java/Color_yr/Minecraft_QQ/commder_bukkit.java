package Color_yr.Minecraft_QQ;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;

public class commder_bukkit implements CommandExecutor, TabExecutor {
    public commder_bukkit(Minecraft_QQ_bukkit plugin) {
    }

    public void reload(CommandSender sender) {
        sender.sendMessage("§d[Minecraft_QQ]§e机器人IP： " + config_bukkit.System_IP);
        sender.sendMessage("§d[Minecraft_QQ]§e机器人端口 " + config_bukkit.System_PORT);
        sender.sendMessage("§d[Minecraft_QQ]§e机器人模式 " + config_bukkit.Minecraft_Mode);
        sender.sendMessage("§d[Minecraft_QQ]§eDebug模式 " + config_bukkit.System_Debug);
        if (config_bukkit.System_Debug == true) {
            sender.sendMessage("§d[Minecraft_QQ]§e玩家加入时是否发送文本 " + config_bukkit.Join_sendQQ + "文本 " + config_bukkit.Join_Message);
            sender.sendMessage("§d[Minecraft_QQ]§e玩家退出时是否发送文本 " + config_bukkit.Quit_sendQQ + "文本 " + config_bukkit.Quit_Message);
            sender.sendMessage("§d[Minecraft_QQ]§e服务器名字 " + config_bukkit.Minecraft_ServerName);
            sender.sendMessage("§d[Minecraft_QQ]§e触发文本 " + config_bukkit.Minecraft_Check);
            sender.sendMessage("§d[Minecraft_QQ]§e发送至QQ群的文本 " + config_bukkit.Minecraft_Message);
            sender.sendMessage("§d[Minecraft_QQ]§e发送至玩家消息窗口的文本 " + config_bukkit.Minecraft_Say);
            sender.sendMessage("§d[Minecraft_QQ]§e机器人模式 " + config_bukkit.Minecraft_Mode);
            sender.sendMessage("§d[Minecraft_QQ]§e是否开启在线人数显示 " + config_bukkit.Minecraft_SendMode);
            sender.sendMessage("§d[Minecraft_QQ]§e在线人数文本 " + config_bukkit.Minecraft_PlayerListMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e服务器状态文本 " + config_bukkit.Minecraft_ServerOnlineMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e是否开启自动重连 " + config_bukkit.System_AutoConnet + "时间(ms)" + config_bukkit.System_AutoConnetTime);
            sender.sendMessage("§d[Minecraft_QQ]§e完成发送后是否提醒 " + config_bukkit.User_SendSucceed + "文本" + config_bukkit.User_SendSucceedMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e是否屏蔽玩家输入指令 " + config_bukkit.User_NotSendCommder);
            sender.sendMessage("§d[Minecraft_QQ]§e是否记录链接情况 " + logs.Socket_log);
            sender.sendMessage("§d[Minecraft_QQ]§e是否记录群发来的消息 " + logs.Group_log);
            sender.sendMessage("§d[Minecraft_QQ]§e是否记录发送至群的消息 " + logs.Send_log);
            sender.sendMessage("§d[Minecraft_QQ]§e是否记录错误内容 " + logs.Error_log);
            sender.sendMessage("§d[Minecraft_QQ]§e数据包检测头 " + config_bukkit.Head);
            sender.sendMessage("§d[Minecraft_QQ]§e数据包检测尾 " + config_bukkit.End);
        }
        sender.sendMessage("§d[Minecraft_QQ]§e重载成功");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("qq")) {
            if (args.length == 0) {
                sender.sendMessage("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.isOp()) {
                    if (args.length > 1) {
                        if (args[1].equalsIgnoreCase("config")) {
                            Minecraft_QQ_bukkit.Config_reload();
                            reload(sender);
                        } else if (args[1].equalsIgnoreCase("socket")) {
                            socket_restart socket_restart = new socket_restart();
                            socket_restart.restart_socket();
                        }
                    } else {
                        Minecraft_QQ_bukkit.Config_reload();
                        reload(sender);
                        socket_restart socket_restart = new socket_restart();
                        socket_restart.restart_socket();
                    }
                } else {
                    sender.sendMessage("§d[Minecraft_QQ]§c你没有权限");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§d[Minecraft_QQ]§2帮助手册");
                sender.sendMessage("§d[Minecraft_QQ]§2使用/qq say 来发送消息");
                sender.sendMessage("§d[Minecraft_QQ]§2使用/qq reload 来重读插件配置文件和重新连接");
                sender.sendMessage("§d[Minecraft_QQ]§2使用/qq reload config 来重读插件配置文件");
                sender.sendMessage("§d[Minecraft_QQ]§2使用/qq reload socket 来重新连接");
                return true;
            }
            if (args[0].equalsIgnoreCase("say")) {
                if (sender.isOp()) {
                    if (args.length > 1) {
                        if (socket.socket_runFlag == true) {
                            socket_send.send_data("data", "group", "无", args[1]);
                            sender.sendMessage("§d[Minecraft_QQ]§2已发送" + args[1]);
                        } else
                            sender.sendMessage("§d[Minecraft_QQ]§c错误，酷Q未连接");
                    } else {
                        sender.sendMessage("§d[Minecraft_QQ]§c错误，请输入文本");
                    }
                } else {
                    sender.sendMessage("§d[Minecraft_QQ]§c你没有权限");
                }
                return true;
            } else {
                sender.sendMessage("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> arguments=null;
        if (command.getName().equalsIgnoreCase("qq") == true) {
            if (sender.hasPermission("Minecraft_QQ.admin")) {
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
        }
        return arguments;
    }
}
