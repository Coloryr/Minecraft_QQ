package Color_yr.Minecraft_QQ.Command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Minecraft_QQBC;
import Color_yr.Minecraft_QQ.Socket.SocketControl;
import Color_yr.Minecraft_QQ.Socket.socketSend;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandBC extends Command /*implements TabExecutor*/ {

    public CommandBC() {
        super("qq");
    }

    private void reload(CommandSender sender) {
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人IP： " + Minecraft_QQ.Config.getSystem().getIP()));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人端口 " + Minecraft_QQ.Config.getSystem().getPort()));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e机器人模式 " + Minecraft_QQ.Config.getServerSet().getMode()));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§eDebug模式 " + Minecraft_QQ.Config.getSystem().isDebug()));
        sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§e重载成功"));
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
        } else if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("Minecraft_QQ.admin")) {
            SocketControl socket = new SocketControl();
            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("config")) {
                    Minecraft_QQBC.Load();
                    reload(sender);
                } else if (args[1].equalsIgnoreCase("socket")) {
                    socket.Close();
                    socket.Start();
                } else
                    sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
            } else {
                Minecraft_QQBC.Load();
                reload(sender);
                socket.Close();
                socket.Start();
            }
        } else if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2帮助手册"));
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq chat 来启用关闭群聊天"));
            if (sender.hasPermission("Minecraft_QQ.admin")) {
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq say 内容 往群里发送测试消息"));
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload 来重读插件配置文件和重新连接"));
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload config 来重读插件配置文件"));
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2使用/qq reload socket 来重新连接"));
            } else {
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c你没有权限"));
            }
        } else if (args[0].equalsIgnoreCase("say") && sender.hasPermission("Minecraft_QQ.admin")) {
            if (!args[1].equalsIgnoreCase("")) {
                if (Minecraft_QQ.hand.socket_runFlag) {
                    socketSend.send_data(Placeholder.data, Placeholder.group, "无", args[1]);
                    sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§2已发送" + args[1]));
                } else
                    sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，酷Q未连接"));
            } else
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请输入文本"));
        } else if (args[0].equalsIgnoreCase("chat")) {
            if (!Minecraft_QQ.Config.getMute().contains(sender.getName())) {
                Minecraft_QQ.Config.AddMute(sender.getName());
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]" + Minecraft_QQ.Config.getLanguage().getMessageOFF()));
            } else {
                Minecraft_QQ.Config.RemoveMute(sender.getName());
                sender.sendMessage(new TextComponent("§d[Minecraft_QQ]" + Minecraft_QQ.Config.getLanguage().getMessageON()));
            }
            Minecraft_QQBC.Save();
        } else {
            sender.sendMessage(new TextComponent("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
        }
    }

//    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
//        List<String> arguments = new ArrayList<>();
//        if (args.length == 0)
//            arguments.add("chat");
//        if (sender.hasPermission("Minecraft_QQ.admin")) {
//
//            if (args.length != 0 && args[0].equalsIgnoreCase("reload")) {
//                arguments.add("config");
//                arguments.add("socket");
//            } else {
//                arguments.add("help");
//                arguments.add("say");
//                arguments.add("reload");
//            }
//        }
//        return arguments;
//    }
}
