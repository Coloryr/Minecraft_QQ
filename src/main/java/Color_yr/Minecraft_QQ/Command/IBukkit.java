package Color_yr.Minecraft_QQ.Command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
import Color_yr.Minecraft_QQ.Load.bukkit_load;
import Color_yr.Minecraft_QQ.Socket.socket_control;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import Color_yr.Minecraft_QQ.logs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class IBukkit implements CommandExecutor, TabExecutor {
    private Plugin plugin;

    public IBukkit(Plugin plugin) {
        this.plugin = plugin;
    }

    private void reload(CommandSender sender) {
        sender.sendMessage("§d[Minecraft_QQ]§e机器人IP： " + BaseConfig.SystemIP);
        sender.sendMessage("§d[Minecraft_QQ]§e机器人端口 " + BaseConfig.SystemPORT);
        sender.sendMessage("§d[Minecraft_QQ]§e机器人模式 " + BaseConfig.MinecraftMode);
        sender.sendMessage("§d[Minecraft_QQ]§eDebug模式 " + BaseConfig.SystemDebug);
        if (BaseConfig.SystemDebug) {
            sender.sendMessage("§d[Minecraft_QQ]§e玩家加入时是否发送文本 " + BaseConfig.JoinsendQQ + "文本 " + BaseConfig.JoinMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e玩家退出时是否发送文本 " + BaseConfig.QuitsendQQ + "文本 " + BaseConfig.QuitMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e服务器名字 " + BaseConfig.MinecraftServerName);
            sender.sendMessage("§d[Minecraft_QQ]§e触发文本 " + BaseConfig.MinecraftCheck);
            sender.sendMessage("§d[Minecraft_QQ]§e发送至QQ群的文本 " + BaseConfig.MinecraftMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e发送至玩家消息窗口的文本 " + BaseConfig.MinecraftSay);
            sender.sendMessage("§d[Minecraft_QQ]§e机器人模式 " + BaseConfig.MinecraftMode);
            sender.sendMessage("§d[Minecraft_QQ]§e是否开启在线人数显示 " + BaseConfig.MinecraftSendMode);
            sender.sendMessage("§d[Minecraft_QQ]§e在线人数文本 " + BaseConfig.MinecraftPlayerListMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e服务器状态文本 " + BaseConfig.MinecraftServerOnlineMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e是否开启自动重连 " + BaseConfig.SystemAutoConnet + "时间(ms)" + BaseConfig.SystemAutoConnetTime);
            sender.sendMessage("§d[Minecraft_QQ]§e完成发送后是否提醒 " + BaseConfig.UserSendSucceed + "文本" + BaseConfig.UserSendSucceedMessage);
            sender.sendMessage("§d[Minecraft_QQ]§e是否屏蔽玩家输入指令 " + BaseConfig.UserNotSendCommder);
            sender.sendMessage("§d[Minecraft_QQ]§e是否记录群发来的消息 " + logs.Group_log);
            sender.sendMessage("§d[Minecraft_QQ]§e是否记录发送至群的消息 " + logs.Send_log);
            sender.sendMessage("§d[Minecraft_QQ]§e数据包检测头 " + BaseConfig.Head);
            sender.sendMessage("§d[Minecraft_QQ]§e数据包检测尾 " + BaseConfig.End);
            sender.sendMessage("§d[Minecraft_QQ]§e线程休眠时间 " + BaseConfig.SystemSleep);
            sender.sendMessage("§d[Minecraft_QQ]§e不参与聊天");
            for (String a : BaseConfig.MuteList) {
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
                    bukkit_load config = new bukkit_load();
                    socket_control socket = new socket_control();
                    if (args.length > 1) {
                        if (args[1].equalsIgnoreCase("config")) {
                            config.setConfig(plugin);
                            reload(sender);
                        } else if (args[1].equalsIgnoreCase("socket")) {
                            socket.socket_close();
                            socket.socket_start();
                        }
                    } else {
                        config.setConfig(plugin);
                        reload(sender);
                        socket.socket_close();
                        socket.socket_start();
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
                        if (use.hand.socket_runFlag) {
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
            } else if (args[0].equalsIgnoreCase("char")) {
                if (!BaseConfig.MuteList.contains(sender.getName())) {
                    BaseConfig.MuteList.add(sender.getName());
                    sender.sendMessage("§d[Minecraft_QQ]§2你已不会在收到群消息。");
                } else {
                    BaseConfig.MuteList.remove(sender.getName());
                    sender.sendMessage("§d[Minecraft_QQ]§2你开始接受群消息。");
                }
                try {
                    FileConfiguration a = new YamlConfiguration();
                    a.set("player", BaseConfig.MuteList);
                    a.save(use.player);
                } catch (Exception e) {
                    use.MinecraftQQ.Log_System("§d[Minecraft_QQ]§c配置文件保存错误\n" + e.getMessage());
                }
            } else {
                sender.sendMessage("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> arguments = null;
        if (command.getName().equalsIgnoreCase("qq")) {
            if (sender.hasPermission("Minecraft_QQ.admin")) {
                arguments = new ArrayList<>();
                if (args.length != 0 && args[0].equalsIgnoreCase("reload")) {
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
