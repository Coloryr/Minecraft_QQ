package Color_yr.Minecraft_QQ.Message;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Json.Read_Json;
import Color_yr.Minecraft_QQ.Log.logs;
import Color_yr.Minecraft_QQ.Socket.socket;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import net.minecraft.command.FunctionObject;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Forge extends Thread {
    public static String get_string(String a, String b, String c) {
        int x = a.indexOf(b) + b.length();
        int y = a.indexOf(c);
        return a.substring(x, y);
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (socket.hand.have_message == false) {
                    Thread.sleep(Color_yr.Minecraft_QQ.Config.Bukkit.System_Sleep);
                }
                socket.hand.have_message = false;
                String msg = socket.hand.info;
                socket.hand.info = null;
                if (logs.Group_log == true) {
                    logs logs = new logs();
                    logs.log_write("[Group]" + msg);
                }
                if (Color_yr.Minecraft_QQ.Config.Bukkit.System_Debug == true)
                    config.log_b.info("处理数据：" + msg);
                if (socket.hand.socket_runFlag == false)
                    return;
                while (msg.indexOf(Color_yr.Minecraft_QQ.Config.Bukkit.Head) == 0 && msg.indexOf(Color_yr.Minecraft_QQ.Config.Bukkit.End) != -1) {
                    String buff = get_string(msg, Color_yr.Minecraft_QQ.Config.Bukkit.Head, Color_yr.Minecraft_QQ.Config.Bukkit.End);
                    Read_Json read_bean;
                    MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                    try {
                        Gson read_gson = new Gson();
                        read_bean = read_gson.fromJson(buff, Read_Json.class);
                    } catch (Exception e) {
                        config.log_b.warning("数据传输发生错误:" + e.getMessage());
                        return;
                    }
                    if (read_bean.getIs_commder().equals("false") == true) {
                        String a = read_bean.getMessage();
                        if (a.indexOf("说话") == 0) {
                            a = a.replaceFirst("说话", "");
                            final String say = Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Say.replaceFirst(Placeholder.Servername,
                                    Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_ServerName).replaceFirst(Placeholder.Message, a);
                            try {
                                server.sendMessage(new TextComponentString(say));
                            } catch (Exception e) {
                                config.log_b.warning(e.toString());
                            }
                        } else if (a.indexOf("在线人数") == 0) {
                            String players[];
                            String player = "";
                            String send = Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_PlayerListMessage;
                            players = server.getOnlinePlayerNames();
                            if (players.length == 0) {
                                try {
                                    send = send.replaceAll(Placeholder.Servername, Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_ServerName)
                                            .replaceAll(Placeholder.player_number, "0")
                                            .replaceAll(Placeholder.player_list, "无");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                int player_number = 1;
                                for (String b : players) {
                                    player += b + ",";
                                }
                                player = player.substring(0, player.length() - 1);
                                send = send.replaceAll(Placeholder.Servername, Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_ServerName)
                                        .replaceAll(Placeholder.player_number, "" + player_number)
                                        .replaceAll(Placeholder.player_list, player);
                            }
                            socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                            if (logs.Group_log == true) {
                                logs logs = new logs();
                                logs.log_write("[group]查询在线人数");
                            }
                            if (Color_yr.Minecraft_QQ.Config.Bukkit.System_Debug == true)
                                config.log_b.info("§d[Minecraft_QQ]§5[Debug]查询在线人数");
                        } else if (a.indexOf("服务器状态") == 0) {
                            String send = Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_ServerOnlineMessage;
                            send = send.replaceAll(Placeholder.Servername, Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_ServerName);
                            socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                            if (logs.Group_log == true) {
                                logs logs = new logs();
                                logs.log_write("[group]查询服务器状态");
                            }
                            if (Color_yr.Minecraft_QQ.Config.Bukkit.System_Debug == true)
                                config.log_b.info("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
                        }
                    } else if (read_bean.getIs_commder().equals("true") == true) {
                        String send_message;
                        CommandSender sender = new CommandSender(null);
                        try {
                            List<String> com = new ArrayList<>();
                            com.add(read_bean.getMessage());
                            FunctionObject func = FunctionObject.create(server.getFunctionManager(), com);
                            if (!read_bean.getPlayer().equalsIgnoreCase("控制台")) {
                                EntityPlayer player = server.getServer().getEntityWorld().getPlayerEntityByName(read_bean.getPlayer());
                                sender = new CommandSender(player);
                            }
                            FMLCommonHandler.instance().getMinecraftServerInstance().getServer().getFunctionManager().execute(func, sender);
                        } catch (Exception e) {
                            config.log_b.warning(e.toString());
                        }
                        if (sender.message.size() == 1) {
                            send_message = sender.message.get(0);
                        } else if (sender.message.size() > 1) {
                            send_message = sender.message.get(0);
                            for (int i = 1; i < sender.message.size(); i++) {
                                send_message = send_message + "\n";
                                send_message = send_message + sender.message.get(i);
                            }
                        } else
                            send_message = "指令执行失败";
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(),
                                "控制台", send_message);
                    }
                    int i = msg.indexOf(Color_yr.Minecraft_QQ.Config.Bukkit.End);
                    msg = msg.substring(i + Color_yr.Minecraft_QQ.Config.Bukkit.End.length());
                }
                socket.hand.is_can_go = true;
            } catch (Exception e) {
                config.log_b.warning("发送错误：" + e.getMessage());
            }
            socket.hand.is_can_go = true;
        }
    }

    public class CommandSender extends CommandBlockBaseLogic {
        private final Entity entity;
        public List<String> message = new ArrayList<>();
        private final World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();

        private CommandSender(Entity entity) {
            this.entity = entity;
        }

        @Override
        public void sendMessage(ITextComponent component) {
            message.add(component.getFormattedText());
        }

        @Nonnull
        @Override
        public BlockPos getPosition() {
            return entity == null ? new BlockPos(0, 0, 0) : entity.getPosition();
        }

        @Nonnull
        @Override
        public Vec3d getPositionVector() {
            return entity == null ? new Vec3d(0, 0, 0) : entity.getPositionVector();
        }

        @Nonnull
        @Override
        public World getEntityWorld() {
            return entity == null ? world : entity.getEntityWorld();
        }

        @Override
        public Entity getCommandSenderEntity() {
            return entity == null ? new Entity(world) {
                @Override
                protected void entityInit() {

                }

                @Override
                protected void readEntityFromNBT(NBTTagCompound compound) {

                }

                @Override
                protected void writeEntityToNBT(NBTTagCompound compound) {

                }
            } : entity;
        }

        @Override
        public void updateCommand() {
        }

        @Override
        public int getCommandBlockType() {
            return 0;
        }

        @Override
        public void fillInInfo(@Nonnull ByteBuf p_145757_1_) {
        }

        @Nonnull
        @Override
        public String getName() {
            return entity == null ? "控制台" : entity.getName();
        }

        @Override
        public MinecraftServer getServer() {
            return entity == null ? FMLCommonHandler.instance().getMinecraftServerInstance() : entity.getServer();
        }
    }
}
