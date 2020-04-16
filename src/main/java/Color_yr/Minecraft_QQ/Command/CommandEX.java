package Color_yr.Minecraft_QQ.Command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;

public class CommandEX {

    private static void reload(Object sender) {
        Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§e机器人IP： " + Minecraft_QQ.Config.getSystem().getIP());
        Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§e机器人端口 " + Minecraft_QQ.Config.getSystem().getPort());
        Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§e机器人模式 " + Minecraft_QQ.Config.getServerSet().getMode());
        Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§eDebug模式 " + Minecraft_QQ.Config.getSystem().isDebug());
        Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§e重载成功");
    }

    public static void Ex(Object sender, String name, String[] args, boolean hasPermission) {
        if (args.length == 0) {
            Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助");
            return;
        }
        if (args[0].equalsIgnoreCase("reload") && hasPermission) {
            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("config")) {
                    Minecraft_QQ.load();
                    reload(sender);
                } else if (args[1].equalsIgnoreCase("socket")) {
                    Minecraft_QQ.control.socketRestart();
                }
            } else {
                Minecraft_QQ.load();
                reload(sender);
                Minecraft_QQ.control.socketRestart();
            }
        } else if (args[0].equalsIgnoreCase("help")) {
            Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§2帮助手册");
            Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§2使用/qq chat 来启用关闭群聊天");
            if (hasPermission) {
                Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§2使用/qq say 内容 往群里发送测试消息");
                Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§2使用/qq reload 来重读插件配置文件和重新连接");
                Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§2使用/qq reload config 来重读插件配置文件");
                Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§2使用/qq reload socket 来重新连接");
            }
        } else if (args[0].equalsIgnoreCase("say") && hasPermission) {
            if (args.length > 1) {
                if (Minecraft_QQ.control.isRun()) {
                    boolean sendok = Minecraft_QQ.control.sendData(Placeholder.data, Placeholder.group, "无", args[1]);
                    if (sendok)
                        Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§2已发送" + args[1]);
                    else
                        Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§c发送失败");
                } else
                    Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§c错误，酷Q未连接");
            } else {
                Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§c错误，请输入文本");
            }
        } else if (args[0].equalsIgnoreCase("chat")) {
            if (!Minecraft_QQ.Config.getMute().contains(name)) {
                Minecraft_QQ.Config.AddMute(name);
                Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]" + Minecraft_QQ.Config.getLanguage().getMessageOFF());
            } else {
                Minecraft_QQ.Config.RemoveMute(name);
                Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]" + Minecraft_QQ.Config.getLanguage().getMessageON());
            }
            Minecraft_QQ.save();
        } else {
            Minecraft_QQ.MinecraftQQ.send(sender, "§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助");
        }
    }
}
