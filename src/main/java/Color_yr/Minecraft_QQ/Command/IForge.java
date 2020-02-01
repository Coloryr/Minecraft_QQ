package Color_yr.Minecraft_QQ.Command;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
import Color_yr.Minecraft_QQ.Config.forge_config;
import Color_yr.Minecraft_QQ.Socket.socket_control;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import Color_yr.Minecraft_QQ.logs;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class IForge implements ICommand {

    private List aliases;

    public IForge() {
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
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人IP： " + BaseConfig.SystemIP));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人端口 " + BaseConfig.SystemPORT));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人模式 " + BaseConfig.MinecraftMode));
        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§eDebug模式 " + BaseConfig.SystemDebug));
        if (BaseConfig.SystemDebug) {
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e服务器名字 " + BaseConfig.MinecraftServerName));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e触发文本 " + BaseConfig.MinecraftCheck));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e发送至QQ群的文本 " + BaseConfig.MinecraftMessage));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e发送至玩家消息窗口的文本 " + BaseConfig.MinecraftSay));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e机器人模式 " + BaseConfig.MinecraftMode));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否开启在线人数显示 " + BaseConfig.MinecraftSendMode));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e在线人数文本 " + BaseConfig.MinecraftPlayerListMessage));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e服务器状态文本 " + BaseConfig.MinecraftServerOnlineMessage));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否开启自动重连 " + BaseConfig.SystemAutoConnet + "时间(ms)" + BaseConfig.SystemAutoConnetTime));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e完成发送后是否提醒 " + BaseConfig.UserSendSucceed + "文本" + BaseConfig.UserSendSucceedMessage));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否屏蔽玩家输入指令 " + BaseConfig.UserNotSendCommder));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否记录群发来的消息 " + logs.Group_log));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e是否记录发送至群的消息 " + logs.Send_log));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e数据包检测头 " + BaseConfig.Head));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e数据包检测尾 " + BaseConfig.End));
            sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§e线程休眠时间 " + BaseConfig.SystemSleep));
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
                            forge_config config_read = new forge_config();
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
                        forge_config config_read = new forge_config();
                        config_read.reload();
                        config_read.init();
                        reload(sender);
                        socket.socket_close();
                        socket.socket_start();
                    }
                } else if (string[0].equalsIgnoreCase("say")) {
                    if (string.length < 2) {
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请输入文本"));
                    } else if (use.hand.socket_runFlag) {
                        socket_send.send_data(Placeholder.data, Placeholder.group, "无", string[1]);
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2已发送" + string[1]));
                    } else if (!use.hand.socket_runFlag) {
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，酷Q未连接"));
                    } else {
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§c错误，请使用/qq help 获取帮助"));
                    }
                } else if (string[0].equalsIgnoreCase("char")) {
                    if (!BaseConfig.MuteList.contains(sender.getName())) {
                        BaseConfig.MuteList.add(sender.getName());
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2你已不会在收到群消息。"));
                    } else {
                        BaseConfig.MuteList.remove(sender.getName());
                        sender.sendMessage(new TextComponentString("§d[Minecraft_QQ]§2你开始接受群消息。"));
                    }
                    forge_config config_read = new forge_config();
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
