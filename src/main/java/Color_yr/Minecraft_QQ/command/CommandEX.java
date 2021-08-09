package Color_yr.Minecraft_QQ.command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.utils.SocketUtils;

public class CommandEX {

    private static void reload(Object sender) {
        Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§e机器人IP： " + Minecraft_QQ.Config.System.IP);
        Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§e机器人端口 " + Minecraft_QQ.Config.System.Port);
        Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§e机器人模式 " + Minecraft_QQ.Config.ServerSet.Mode);
        Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§eDebug模式 " + Minecraft_QQ.Config.System.Debug);
        Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§e重载成功");
    }

    public static void Ex(Object sender, String name, String[] args, boolean hasPermission) {
        if (args.length == 0) {
            Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助");
            return;
        }
        if (args[0].equalsIgnoreCase("reload") && hasPermission) {
            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("config")) {
                    Minecraft_QQ.load();
                    reload(sender);
                } else if (args[1].equalsIgnoreCase("socket")) {
                    SocketUtils.socketRestart();
                }
            } else {
                Minecraft_QQ.load();
                reload(sender);
                SocketUtils.socketRestart();
            }
        } else if (args[0].equalsIgnoreCase("help")) {
            Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§2帮助手册");
            Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§2使用/qq chat 来启用关闭群聊天");
            if (hasPermission) {
                Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§2使用/qq say 内容 往群里发送测试消息");
                Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§2使用/qq reload 来重读插件配置文件和重新连接");
                Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§2使用/qq reload config 来重读插件配置文件");
                Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§2使用/qq reload socket 来重新连接");
            }
        } else if (args[0].equalsIgnoreCase("say") && hasPermission) {
            if (args.length > 1) {
                if (SocketUtils.isRun()) {
                    SocketUtils.sendData(Placeholder.data, Placeholder.group, "无", args[1]);
                } else
                    Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§c错误，Minecraft_QQ_Cmd/Gui未连接");
            } else {
                Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§c错误，请输入文本");
            }
        } else if (args[0].equalsIgnoreCase("chat")) {
            if (!Minecraft_QQ.Config.Mute.contains(name)) {
                Minecraft_QQ.Config.Mute.add(name);
                Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]" + Minecraft_QQ.Config.Language.MessageOFF);
            } else {
                Minecraft_QQ.Config.Mute.remove(name);
                Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]" + Minecraft_QQ.Config.Language.MessageON);
            }
            Minecraft_QQ.save();
        } else {
            Minecraft_QQ.Side.send(sender, "§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助");
        }
    }
}
