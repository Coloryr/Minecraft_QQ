package coloryr.minecraft_qq.command;

import coloryr.minecraft_qq.api.Placeholder;
import coloryr.minecraft_qq.Minecraft_QQ;
import coloryr.minecraft_qq.utils.SocketUtils;

public class CommandEX {

    private static void reload(Object sender) {
        Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§e机器人IP： " + Minecraft_QQ.config.System.IP);
        Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§e机器人端口 " + Minecraft_QQ.config.System.Port);
        Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§e机器人模式 " + Minecraft_QQ.config.ServerSet.Mode);
        Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§eDebug模式 " + Minecraft_QQ.config.System.Debug);
        Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§e重载成功");
    }

    public static void ex(Object sender, String name, String[] args, boolean hasPermission) {
        if (args.length == 0) {
            Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助");
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
            Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§2帮助手册");
            Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§2使用/qq chat 来启用关闭群聊天");
            if (hasPermission) {
                Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§2使用/qq say 内容 往群里发送测试消息");
                Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§2使用/qq reload 来重读插件配置文件和重新连接");
                Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§2使用/qq reload config 来重读插件配置文件");
                Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§2使用/qq reload socket 来重新连接");
            }
        } else if (args[0].equalsIgnoreCase("say") && hasPermission) {
            if (args.length > 1) {
                if (SocketUtils.isRun()) {
                    SocketUtils.sendData(Placeholder.data, Placeholder.group, "无", args);
                } else
                    Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§c错误，Minecraft_QQ_Cmd/Gui未连接");
            } else {
                Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§c错误，请输入文本");
            }
        } else if (args[0].equalsIgnoreCase("chat")) {
            if (!Minecraft_QQ.config.Mute.contains(name)) {
                Minecraft_QQ.config.Mute.add(name);
                Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]" + Minecraft_QQ.config.Language.MessageOFF);
            } else {
                Minecraft_QQ.config.Mute.remove(name);
                Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]" + Minecraft_QQ.config.Language.MessageON);
            }
            Minecraft_QQ.save();
        } else {
            Minecraft_QQ.side.send(sender, "§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助");
        }
    }
}
