package Color_yr.Minecraft_QQ.Command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Socket.socket_control;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import Color_yr.Minecraft_QQ.Config.Bukkit_;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class Forge_ implements ICommand {

    private List aliases;

    public Forge_() {
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

    private void reload(ICommandSender sender) {
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人IP： " + Bukkit_.System_IP));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人端口 " + Bukkit_.System_PORT));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人模式 " + Bukkit_.Minecraft_Mode));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§eDebug模式 " + Bukkit_.System_Debug));
        if (Bukkit_.System_Debug) {
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e服务器名字 " + Bukkit_.Minecraft_ServerName));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e触发文本 " + Bukkit_.Minecraft_Check));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e发送至QQ群的文本 " + Bukkit_.Minecraft_Message));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e发送至玩家消息窗口的文本 " + Bukkit_.Minecraft_Say));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人模式 " + Bukkit_.Minecraft_Mode));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否开启在线人数显示 " + Bukkit_.Minecraft_SendMode));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e在线人数文本 " + Bukkit_.Minecraft_PlayerListMessage));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e服务器状态文本 " + Bukkit_.Minecraft_ServerOnlineMessage));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否开启自动重连 " + Bukkit_.System_AutoConnet + "时间(ms)" + Bukkit_.System_AutoConnetTime));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e完成发送后是否提醒 " + Bukkit_.User_SendSucceed + "文本" + Bukkit_.User_SendSucceedMessage));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否屏蔽玩家输入指令 " + Bukkit_.User_NotSendCommder));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否记录群发来的消息 " + logs.Group_log));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否记录发送至群的消息 " + logs.Send_log));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e数据包检测头 " + Bukkit_.Head));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e数据包检测尾 " + Bukkit_.End));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e线程休眠时间 " + Bukkit_.System_Sleep));
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
                } else if (string[0].equalsIgnoreCase("reload")) {
                    socket_control socket = new socket_control();
                    if (string.length > 2) {
                        if (string[1].equalsIgnoreCase("config")) {
                            Color_yr.Minecraft_QQ.Config.Forge_ config_read = new Color_yr.Minecraft_QQ.Config.Forge_();
                            config_read.reload();
                            config_read.init();
                            reload(sender);
                        } else if (string[1].equalsIgnoreCase("sock)et")) {
                            socket.socket_close();
                            socket.socket_start();
                        } else {
                            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
                        }
                    } else {
                        Color_yr.Minecraft_QQ.Config.Forge_ config_read = new Color_yr.Minecraft_QQ.Config.Forge_();
                        config_read.reload();
                        config_read.init();
                        reload(sender);
                        socket.socket_close();
                        socket.socket_start();
                    }
                } else if (string[0].equalsIgnoreCase("say")) {
                    if (string.length < 2) {
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请输入文本"));
                    } else if (config.hand.socket_runFlag) {
                        socket_send.send_data(Placeholder.data, Placeholder.group, "无", string[1]);
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2已发送" + string[1]));
                    } else if (!config.hand.socket_runFlag) {
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，酷Q未连接"));
                    } else {
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
                    }
                } else if (string[0].equalsIgnoreCase("char")) {
                    if (!Bukkit_.Mute_List.contains(sender.getName())) {
                        Bukkit_.Mute_List.add(sender.getName());
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2你已不会在收到群消息。"));
                    } else {
                        Bukkit_.Mute_List.remove(sender.getName());
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2你开始接受群消息。"));
                    }
                    Color_yr.Minecraft_QQ.Config.Forge_ config_read = new Color_yr.Minecraft_QQ.Config.Forge_();
                    config_read.reload();
                    config_read.init();
                    reload(sender);
                }
            } else {
                sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
            }
        } else {
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c你没有权限"));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(3, getName());
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
