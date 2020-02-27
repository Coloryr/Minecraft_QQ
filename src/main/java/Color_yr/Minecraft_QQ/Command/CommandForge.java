package Color_yr.Minecraft_QQ.Command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Minecraft_QQForge;
import Color_yr.Minecraft_QQ.Socket.SocketControl;
import Color_yr.Minecraft_QQ.Socket.socketSend;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class CommandForge implements ICommand {

    private List aliases;

    public CommandForge() {
        aliases = new ArrayList<String>();
        aliases.add("qq");
        aliases.add("QQ");
    }

    @Override
    public String getName() {
        return "Minecraft_QQ";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/qq help";
    }

    @Override
    public List getAliases() {
        return aliases;
    }

    private void reload(ICommandSender sender) {
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人IP： " + Minecraft_QQ.Config.getSystem().getIP()));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人端口 " + Minecraft_QQ.Config.getSystem().getPort()));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人模式 " + Minecraft_QQ.Config.getServerSet().getMode()));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§eDebug模式 " + Minecraft_QQ.Config.getSystem().isDebug()));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e重载成功"));
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] string) {
        if (string.length >= 1) {
            if (string[0].equalsIgnoreCase("help")) {
                sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2帮助手册"));
                sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2使用/qq chat 来启用关闭群聊天"));
                if(checkPermission(server, sender)) {
                    sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2使用/qq say 内容 往群里发送测试消息"));
                    sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2使用/qq reload 来重读插件配置文件和重新连接"));
                    sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2使用/qq reload config 来重读插件配置文件"));
                    sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2使用/qq reload socket 来重新连接"));
                }
            } else if (string[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c警告，配置文件需要关服修改才能生效"));
                SocketControl socket = new SocketControl();
                if (string.length > 2) {
                    if (string[1].equalsIgnoreCase("config")) {
                        Minecraft_QQForge.Load();
                    } else if (string[1].equalsIgnoreCase("socket")) {
                        socket.Close();
                        socket.Start();
                    } else {
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
                    }
                } else {
                    Minecraft_QQForge.Load();
                    socket.Close();
                    socket.Start();
                }
            } else if (string[0].equalsIgnoreCase("say")) {
                if (string.length < 2) {
                    sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请输入文本"));
                } else if (Minecraft_QQ.hand.socket_runFlag) {
                    socketSend.send_data(Placeholder.data, Placeholder.group, "无", string[1]);
                    sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2已发送" + string[1]));
                } else {
                    sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，酷Q未连接"));
                }
            }
        }
        if (string[0].equalsIgnoreCase("chat")) {
            if (!Minecraft_QQ.Config.getMute().contains(sender.getName())) {
                Minecraft_QQ.Config.AddMute(sender.getName());
                sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2你已不会在收到群消息。"));
            } else {
                Minecraft_QQ.Config.RemoveMute(sender.getName());
                sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2你开始接受群消息。"));
            }
            Minecraft_QQForge.Save();
            reload(sender);
        } else {
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(3, getName());
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] string, BlockPos targetPos) {
        List<String> list = new ArrayList<>();
        list.add("chat");
        if (checkPermission(server, sender)) {
            if (string.length == 0) {
                list.add("help");
                list.add("say");
                list.add("reload");
            } else if (string.length == 1 && string[0].equalsIgnoreCase("reload")) {
                list.add("config");
                list.add("socket");
            }
        }
        return list;
    }

    @Override
    public boolean isUsernameIndex(String[] string, int number) {
        return number == 1 && string[0].equalsIgnoreCase("qq");
    }

    @Override
    public int compareTo(ICommand arg0) {
        return this.getName().compareTo(arg0.getName());
    }
}
