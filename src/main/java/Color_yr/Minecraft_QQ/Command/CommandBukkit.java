package Color_yr.Minecraft_QQ.Command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Minecraft_QQBukkit;
import Color_yr.Minecraft_QQ.Socket.SocketControl;
import Color_yr.Minecraft_QQ.Socket.socketSend;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class CommandBukkit implements CommandExecutor, TabExecutor {

    private void reload(CommandSender sender) {
        sender.sendMessage("§d[Minecraft_QQ]§e机器人IP： " + Minecraft_QQ.Config.getSystem().getIP());
        sender.sendMessage("§d[Minecraft_QQ]§e机器人端口 " + Minecraft_QQ.Config.getSystem().getPort());
        sender.sendMessage("§d[Minecraft_QQ]§e机器人模式 " + Minecraft_QQ.Config.getServerSet().getMode());
        sender.sendMessage("§d[Minecraft_QQ]§eDebug模式 " + Minecraft_QQ.Config.getSystem().isDebug());
        sender.sendMessage("§d[Minecraft_QQ]§e重载成功");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("qq")) {
            if (args.length == 0) {
                sender.sendMessage("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload") && sender.isOp()) {
                SocketControl socket = new SocketControl();
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("config")) {
                        Minecraft_QQBukkit.Load();
                        reload(sender);
                    } else if (args[1].equalsIgnoreCase("socket")) {
                        socket.Close();
                        socket.Start();
                    }
                } else {
                    Minecraft_QQBukkit.Load();
                    reload(sender);
                    socket.Close();
                    socket.Start();
                }
            }
            else if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§d[Minecraft_QQ]§2帮助手册");
                sender.sendMessage("§d[Minecraft_QQ]§2使用/qq chat 来启用关闭群聊天");
                if(sender.isOp()) {
                    sender.sendMessage("§d[Minecraft_QQ]§2使用/qq say 内容 往群里发送测试消息");
                    sender.sendMessage("§d[Minecraft_QQ]§2使用/qq reload 来重读插件配置文件和重新连接");
                    sender.sendMessage("§d[Minecraft_QQ]§2使用/qq reload config 来重读插件配置文件");
                    sender.sendMessage("§d[Minecraft_QQ]§2使用/qq reload socket 来重新连接");
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("say") && sender.isOp()) {
                if (args.length > 1) {
                    if (Minecraft_QQ.hand.socket_runFlag) {
                        socketSend.send_data(Placeholder.data, Placeholder.group, "无", args[1]);
                        sender.sendMessage("§d[Minecraft_QQ]§2已发送" + args[1]);
                    } else
                        sender.sendMessage("§d[Minecraft_QQ]§c错误，酷Q未连接");
                } else {
                    sender.sendMessage("§d[Minecraft_QQ]§c错误，请输入文本");
                }
            } else if (args[0].equalsIgnoreCase("chat")) {
                if (!Minecraft_QQ.Config.getMute().contains(sender.getName())) {
                    Minecraft_QQ.Config.AddMute(sender.getName());
                    sender.sendMessage("§d[Minecraft_QQ]" + Minecraft_QQ.Config.getLanguage().getMessageOFF());
                } else {
                    Minecraft_QQ.Config.RemoveMute(sender.getName());
                    sender.sendMessage("§d[Minecraft_QQ]" + Minecraft_QQ.Config.getLanguage().getMessageON());
                }
                Minecraft_QQBukkit.Save();
            } else {
                sender.sendMessage("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("qq")) {
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add("chat");
            if (sender.isOp()) {
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
        return null;
    }
}
