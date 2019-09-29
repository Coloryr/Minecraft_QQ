package Color_yr.Minecraft_QQ.Command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Config.Bukkit;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Socket.socket;
import Color_yr.Minecraft_QQ.Socket.socket_restart;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

import static Color_yr.Minecraft_QQ.Main.BungeeCord.config_data_bungee;

public class BungeeCord extends Command implements TabExecutor {

    public BungeeCord() {
        super("qq");
    }

    public void reload(CommandSender sender) {
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人IP： " + Bukkit.System_IP));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人端口 " + Bukkit.System_PORT));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人模式 " + Bukkit.Minecraft_Mode));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§eDebug模式 " + Bukkit.System_Debug));
        if (Bukkit.System_Debug == true) {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家加入时是否发送文本 " + Bukkit.Join_sendQQ + "文本 " + Bukkit.Join_Message));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家退出时是否发送文本 " + Bukkit.Quit_sendQQ + "文本 " + Bukkit.Quit_Message));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家切换子服时是否发送文本 " + config_data_bungee.ChangeServer_sendQQ + "文本 " + config_data_bungee.ChangeServer_Message));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e服务器名字 " + Bukkit.Minecraft_ServerName));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e触发文本 " + Bukkit.Minecraft_Check));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e发送至QQ群的文本 " + Bukkit.Minecraft_Message));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e发送至玩家消息窗口的文本 " + Bukkit.Minecraft_Say));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人模式 " + Bukkit.Minecraft_Mode));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启在线人数显示 " + Bukkit.Minecraft_SendMode));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否单独显示子服的人数 " + config_data_bungee.Minecraft_SendOneByOne + "文本" + config_data_bungee.Minecraft_SendOneByOneMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否隐藏空的子服 " + config_data_bungee.Minecraft_HideEmptyServer));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e在线人数文本 " + Bukkit.Minecraft_PlayerListMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e服务器状态文本 " + Bukkit.Minecraft_ServerOnlineMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否显示子服是否在线 " + Bukkit.Minecraft_ServerOnlineMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启全服公告文本 " + config_data_bungee.SendAllServer_Enable + "文本" + config_data_bungee.SendAllServer_Message));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否隔离服务器消息 " + config_data_bungee.SendAllServer_OnlySideServer));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启自动重连 " + Bukkit.System_AutoConnet + "时间(ms)" + Bukkit.System_AutoConnetTime));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e完成发送后是否提醒 " + Bukkit.User_SendSucceed + "文本" + Bukkit.User_SendSucceedMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否屏蔽玩家输入指令" + Bukkit.User_NotSendCommder));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录链接情况 " + logs.Socket_log));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录群发来的消息 " + logs.Group_log));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录发送至群的消息 " + logs.Send_log));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录错误内容 " + logs.Error_log));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e数据包检测头 " + Bukkit.Head));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e数据包检测尾 " + Bukkit.End));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e线程休眠时间 " + Bukkit.System_Sleep));
        }
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e重载成功"));
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
            return;
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("Minecraft_QQ.admin")) {
                Color_yr.Minecraft_QQ.Main.BungeeCord config = new Color_yr.Minecraft_QQ.Main.BungeeCord();
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("config")) {
                        config.setConfig();
                        reload(sender);
                    } else if (args[1].equalsIgnoreCase("socket")) {
                        socket_restart socket_restart = new socket_restart();
                        socket_restart.restart_socket();
                    } else
                        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
                } else {
                    config.setConfig();
                    reload(sender);
                    socket_restart socket_restart = new socket_restart();
                    socket_restart.restart_socket();
                }
                return;
            } else {
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c你没有权限"));
                return;
            }
        } else if (args[0].equalsIgnoreCase("help")) {
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
        } else if (args[0].equalsIgnoreCase("say")) {
            if (sender.hasPermission("Minecraft_QQ.admin")) {
                if (args[1].equalsIgnoreCase("") == false) {
                    if (socket.hand.socket_runFlag == true) {
                        socket_send.send_data(Placeholder.data, Placeholder.group, "无", args[1]);
                        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2已发送" + args[1]));
                    } else
                        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，酷Q未连接"));
                } else
                    sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请输入文本"));
            } else
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c你没有权限"));
        } else if (args[0].equalsIgnoreCase("char")) {
            if (!Color_yr.Minecraft_QQ.Config.Bukkit.Mute_List.contains(sender.getName())) {
                Color_yr.Minecraft_QQ.Config.Bukkit.Mute_List.add(sender.getName());
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2你已不会在收到群消息。"));
            } else {
                Color_yr.Minecraft_QQ.Config.Bukkit.Mute_List.remove(sender.getName());
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2你开始接受群消息。"));
            }
            try {
                Configuration a = new Configuration();
                a.set("player", Color_yr.Minecraft_QQ.Config.Bukkit.Mute_List);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(a, config.player);
            } catch (Exception e) {
                config.log_b.warning("§d[Minecraft_QQ]§c配置文件保存错误\n" + e.getMessage());
            }
        } else {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> arguments = new ArrayList<String>();
        if (args.length == 0)
            arguments.add("char");
        if (sender.hasPermission("Minecraft_QQ.admin") == true) {

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
