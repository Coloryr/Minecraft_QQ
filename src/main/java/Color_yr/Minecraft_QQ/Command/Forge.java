package Color_yr.Minecraft_QQ.Command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Socket.socket;
import Color_yr.Minecraft_QQ.Socket.socket_restart;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import Color_yr.Minecraft_QQ.Config.Bukkit;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class Forge implements ICommand {

    private List aliases;
    public Forge() {
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
        return "Minecraft_QQ help";
    }

    @Override
    public List getAliases() {
        return aliases;
    }

    public void reload(ICommandSender sender) {
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人IP： " + Bukkit.System_IP));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人端口 " + Bukkit.System_PORT));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人模式 " + Bukkit.Minecraft_Mode));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§eDebug模式 " + Bukkit.System_Debug));
        if (Bukkit.System_Debug == true) {
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e服务器名字 " + Bukkit.Minecraft_ServerName));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e触发文本 " + Bukkit.Minecraft_Check));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e发送至QQ群的文本 " + Bukkit.Minecraft_Message));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e发送至玩家消息窗口的文本 " + Bukkit.Minecraft_Say));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人模式 " + Bukkit.Minecraft_Mode));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否开启在线人数显示 " + Bukkit.Minecraft_SendMode));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e在线人数文本 " + Bukkit.Minecraft_PlayerListMessage));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e服务器状态文本 " + Bukkit.Minecraft_ServerOnlineMessage));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否开启自动重连 " + Bukkit.System_AutoConnet + "时间(ms)" + Bukkit.System_AutoConnetTime));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e完成发送后是否提醒 " + Bukkit.User_SendSucceed + "文本" + Bukkit.User_SendSucceedMessage));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否屏蔽玩家输入指令 " + Bukkit.User_NotSendCommder));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否记录链接情况 " + logs.Socket_log));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否记录群发来的消息 " + logs.Group_log));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否记录发送至群的消息 " + logs.Send_log));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否记录错误内容 " + logs.Error_log));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e数据包检测头 " + Bukkit.Head));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e数据包检测尾 " + Bukkit.End));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e线程休眠时间 " + Bukkit.System_Sleep));
        }
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e重载成功"));
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender,
                        String[] string) throws CommandException {
        if (checkPermission(server, sender)) {
            if (string.length >= 1) {
                if (string[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2帮助手册"));
                    sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2使用/qq reload 来重读插件配置文件和重新连接"));
                    sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2使用/qq reload config 来重读插件配置文件"));
                    sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2使用/qq reload socket 来重新连接"));
                    return;
                } else if (string[0].equalsIgnoreCase("reload")) {
                    if (string.length > 2) {
                        if (string[1].equalsIgnoreCase("config")) {
                            Color_yr.Minecraft_QQ.Config.Forge config_read = new Color_yr.Minecraft_QQ.Config.Forge();
                            config_read.reload();
                            config_read.init();
                            reload(sender);
                            return;
                        } else if (string[1].equalsIgnoreCase("sock)et")) {
                            socket_restart socket_restart = new socket_restart();
                            socket_restart.restart_socket();
                            return;
                        } else {
                            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
                            return;
                        }
                    } else {
                        Color_yr.Minecraft_QQ.Config.Forge config_read = new Color_yr.Minecraft_QQ.Config.Forge();
                        config_read.reload();
                        config_read.init();
                        reload(sender);
                        socket_restart socket_restart = new socket_restart();
                        socket_restart.restart_socket();
                        return;
                    }
                } else if (string[0].equalsIgnoreCase("say")) {
                    if (string.length < 2) {
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请输入文本"));
                    } else if (socket.hand.socket_runFlag == true) {
                        socket_send.send_data(Placeholder.data, Placeholder.group, "无", string[1]);
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2已发送" + string[1]));
                        return;
                    } else if (socket.hand.socket_runFlag == false) {
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，酷Q未连接"));
                        return;
                    } else {
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
                        return;
                    }
                }
            }else {
                sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
            }
        } else {
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c你没有权限"));
        }
    }
    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(2, getName());
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server,
                                          ICommandSender sender, String[] string, BlockPos targetPos) {
        ArrayList<String> list = new ArrayList<String>();
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
