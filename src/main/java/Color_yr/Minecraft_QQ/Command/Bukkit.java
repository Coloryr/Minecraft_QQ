package Color_yr.Minecraft_QQ.Command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Socket.socket;
import Color_yr.Minecraft_QQ.Socket.socket_restart;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class Bukkit implements CommandExecutor, TabExecutor {
    private Plugin plugin;

    public Bukkit(Plugin plugin) {
        this.plugin = plugin;
    }

    public void reload(CommandSender sender) {
        sender.sendMessage("§d[Minecraft_QQ]§e机器人IP： " + Color_yr.Minecraft_QQ.Config.Bukkit.System_IP);
        sender.sendMessage("§d[Minecraft_QQ]§e机器人端口 " + Color_yr.Minecraft_QQ.Config.Bukkit.System_PORT);
        sender.sendMessage("§d[Minecraft_QQ]§e机器人模式 " + Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Mode);
        sender.sendMessage("§d[Minecraft_QQ]§eDebug模式 " + Color_yr.Minecraft_QQ.Config.Bukkit.System_Debug);
        if (Color_yr.Minecraft_QQ.Config.Bukkit.System_Debug == true) {
            sender.sendMessage("§d[Minecraft_QQ]§e玩家加入时是否发送文本 " + Color_yr.Minecraft_QQ.Config.Bukkit.Join_sendQQ + "文本 " + Color_yr.Minecraft_QQ.Config.Bukkit.Join_Message);
            sender.sendMessage("§d[Minecraft_QQ]§e玩家退出时是否发送文本 " + Color_yr.Minecraft_QQ.Config.Bukkit.Quit_sendQQ + "文本 " + Color_yr.Minecraft_QQ.Config.Bukkit.Quit_Message);
            sender.sendMessage("§d[Minecraft_QQ]§e服务器名字 " + Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_ServerName);
            sender.sendMessage("§d[Minecraft_QQ]§e触发文本 " + Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Check);
            sender.sendMessage("§d[Minecraft_QQ]§e发送至QQ群的文本 " + Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Message);
            sender.sendMessage("§d[Minecraft_QQ]§e发送至玩家消息窗口的文本 " + Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Say);
            sender.sendMessage("§d[Minecraft_QQ]§e机器人模式 " + Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Mode);
            sender.sendMessage("§d[Minecraft_QQ]§e是否开启在线人数显示 " + Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_SendMode);
            sender.sendMessage("§d[Minecraft_QQ]§e在线人数文本 " + Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_PlayerListMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e服务器状态文本 " + Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_ServerOnlineMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e是否开启自动重连 " + Color_yr.Minecraft_QQ.Config.Bukkit.System_AutoConnet + "时间(ms)" + Color_yr.Minecraft_QQ.Config.Bukkit.System_AutoConnetTime);
            sender.sendMessage("§d[Minecraft_QQ]§e完成发送后是否提醒 " + Color_yr.Minecraft_QQ.Config.Bukkit.User_SendSucceed + "文本" + Color_yr.Minecraft_QQ.Config.Bukkit.User_SendSucceedMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e是否屏蔽玩家输入指令 " + Color_yr.Minecraft_QQ.Config.Bukkit.User_NotSendCommder);
            sender.sendMessage("§d[Minecraft_QQ]§e是否记录链接情况 " + logs.Socket_log);
            sender.sendMessage("§d[Minecraft_QQ]§e是否记录群发来的消息 " + logs.Group_log);
            sender.sendMessage("§d[Minecraft_QQ]§e是否记录发送至群的消息 " + logs.Send_log);
            sender.sendMessage("§d[Minecraft_QQ]§e是否记录错误内容 " + logs.Error_log);
            sender.sendMessage("§d[Minecraft_QQ]§e数据包检测头 " + Color_yr.Minecraft_QQ.Config.Bukkit.Head);
            sender.sendMessage("§d[Minecraft_QQ]§e数据包检测尾 " + Color_yr.Minecraft_QQ.Config.Bukkit.End);
            sender.sendMessage("§d[Minecraft_QQ]§e线程休眠时间 " + Color_yr.Minecraft_QQ.Config.Bukkit.System_Sleep);
            sender.sendMessage("§d[Minecraft_QQ]§e不参与聊天");
            for (String a : Color_yr.Minecraft_QQ.Config.Bukkit.Mute_List) {
                sender.sendMessage("§d[Minecraft_QQ]§e " + a);
            }
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
                    Color_yr.Minecraft_QQ.Load.Bukkit config = new Color_yr.Minecraft_QQ.Load.Bukkit();
                    if (args.length > 1) {
                        if (args[1].equalsIgnoreCase("config")) {
                            config.setConfig(plugin);
                            reload(sender);
                        } else if (args[1].equalsIgnoreCase("socket")) {
                            socket_restart socket_restart = new socket_restart();
                            socket_restart.restart_socket();
                        }
                    } else {
                        config.setConfig(plugin);
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
                        if (socket.hand.socket_runFlag == true) {
                            socket_send.send_data(Placeholder.data, Placeholder.group, "无", args[1]);
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
            if (args[0].equalsIgnoreCase("char")) {
                if (!Color_yr.Minecraft_QQ.Config.Bukkit.Mute_List.contains(sender.getName())) {
                    Color_yr.Minecraft_QQ.Config.Bukkit.Mute_List.add(sender.getName());

                    sender.sendMessage("§d[Minecraft_QQ]§2你已不会在收到群消息。");
                } else {
                    Color_yr.Minecraft_QQ.Config.Bukkit.Mute_List.remove(sender.getName());
                    sender.sendMessage("§d[Minecraft_QQ]§2你开始接受群消息。");
                }
                try {
                    FileConfiguration a = new YamlConfiguration();
                    a.set("player", Color_yr.Minecraft_QQ.Config.Bukkit.Mute_List);
                    a.save(config.player);
                } catch (Exception e) {
                    config.log_b.warning("§d[Minecraft_QQ]§c配置文件保存错误\n" + e.getMessage());
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> arguments = null;
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
