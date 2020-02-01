package Color_yr.Minecraft_QQ.Command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
import Color_yr.Minecraft_QQ.Load.bc_load;
import Color_yr.Minecraft_QQ.Socket.socket_control;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import Color_yr.Minecraft_QQ.logs;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

import static Color_yr.Minecraft_QQ.BungeeCord.config_data_bungee;

public class IBungeeCord extends Command implements TabExecutor {

    public IBungeeCord() {
        super("qq");
    }

    private void reload(CommandSender sender) {
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人IP： " + BaseConfig.SystemIP));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人端口 " + BaseConfig.SystemPORT));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人模式 " + BaseConfig.MinecraftMode));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§eDebug模式 " + BaseConfig.SystemDebug));
        if (BaseConfig.SystemDebug) {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家加入时是否发送文本 " + BaseConfig.JoinsendQQ + "文本 " + BaseConfig.JoinMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家退出时是否发送文本 " + BaseConfig.QuitsendQQ + "文本 " + BaseConfig.QuitMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e玩家切换子服时是否发送文本 " + config_data_bungee.ChangeServersendQQ + "文本 " + config_data_bungee.ChangeServerMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e服务器名字 " + BaseConfig.MinecraftServerName));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e触发文本 " + BaseConfig.MinecraftCheck));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e发送至QQ群的文本 " + BaseConfig.MinecraftMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e发送至玩家消息窗口的文本 " + BaseConfig.MinecraftSay));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人模式 " + BaseConfig.MinecraftMode));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启在线人数显示 " + BaseConfig.MinecraftSendMode));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否单独显示子服的人数 " + config_data_bungee.MinecraftSendOneByOne + "文本" + config_data_bungee.MinecraftSendOneByOneMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否隐藏空的子服 " + config_data_bungee.MinecraftHideEmptyServer));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e在线人数文本 " + BaseConfig.MinecraftPlayerListMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e服务器状态文本 " + BaseConfig.MinecraftServerOnlineMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否显示子服是否在线 " + BaseConfig.MinecraftServerOnlineMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启全服公告文本 " + config_data_bungee.SendAllServerEnable + "文本" + config_data_bungee.SendAllServerMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否隔离服务器消息 " + config_data_bungee.SendAllServerOnlySideServer));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否开启自动重连 " + BaseConfig.SystemAutoConnet + "时间(ms)" + BaseConfig.SystemAutoConnetTime));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e完成发送后是否提醒 " + BaseConfig.UserSendSucceed + "文本" + BaseConfig.UserSendSucceedMessage));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否屏蔽玩家输入指令" + BaseConfig.UserNotSendCommder));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录群发来的消息 " + logs.Group_log));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e是否记录发送至群的消息 " + logs.Send_log));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e数据包检测头 " + BaseConfig.Head));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e数据包检测尾 " + BaseConfig.End));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e线程休眠时间 " + BaseConfig.SystemSleep));
        }
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e重载成功"));
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("Minecraft_QQ.admin")) {
                bc_load config = new bc_load();
                socket_control socket = new socket_control();
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("config")) {
                        config.setConfig();
                        reload(sender);
                    } else if (args[1].equalsIgnoreCase("socket")) {
                        socket.socket_close();
                        socket.socket_start();
                    } else
                        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
                } else {
                    config.setConfig();
                    reload(sender);
                    socket.socket_close();
                    socket.socket_start();
                }
            } else {
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c你没有权限"));
            }
        } else if (args[0].equalsIgnoreCase("help")) {
            if (sender.hasPermission("Minecraft_QQ.admin")) {
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2帮助手册"));
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq say 来发送消息"));
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload 来重读插件配置文件和重新连接"));
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload config 来重读插件配置文件"));
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload socket 来重新连接"));
            } else {
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c你没有权限"));
            }
        } else if (args[0].equalsIgnoreCase("say")) {
            if (sender.hasPermission("Minecraft_QQ.admin")) {
                if (!args[1].equalsIgnoreCase("")) {
                    if (use.hand.socket_runFlag) {
                        socket_send.send_data(Placeholder.data, Placeholder.group, "无", args[1]);
                        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2已发送" + args[1]));
                    } else
                        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，酷Q未连接"));
                } else
                    sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请输入文本"));
            } else
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c你没有权限"));
        } else if (args[0].equalsIgnoreCase("char")) {
            if (!BaseConfig.MuteList.contains(sender.getName())) {
                BaseConfig.MuteList.add(sender.getName());
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2你已不会在收到群消息。"));
            } else {
                BaseConfig.MuteList.remove(sender.getName());
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2你开始接受群消息。"));
            }
            try {
                Configuration a = new Configuration();
                a.set("player", BaseConfig.MuteList);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(a, use.player);
            } catch (Exception e) {
                use.MinecraftQQ.Log_System("§d[Minecraft_QQ]§c配置文件保存错误\n" + e.getMessage());
            }
        } else {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (args.length == 0)
            arguments.add("char");
        if (sender.hasPermission("Minecraft_QQ.admin")) {

            if (args.length != 0 && args[0].equalsIgnoreCase("reload")) {
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
