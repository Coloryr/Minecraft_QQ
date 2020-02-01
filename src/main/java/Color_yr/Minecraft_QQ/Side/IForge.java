package Color_yr.Minecraft_QQ.Side;

import Color_yr.Minecraft_QQ.API.IMinecraft_QQ;
import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
import Color_yr.Minecraft_QQ.Forge;
import Color_yr.Minecraft_QQ.Json.Read_Json;
import Color_yr.Minecraft_QQ.Utils;
import Color_yr.Minecraft_QQ.logs;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import com.google.gson.Gson;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.command.FunctionObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class IForge implements IMinecraft_QQ {
    public void Log_System(String message) {
        Forge.logger.info(message);
    }

    @Override
    public void Message(String Message) {
        try {
            String msg = Message;
            if (logs.Group_log) {
                logs.log_write("[Group]" + msg);
            }
            if (BaseConfig.SystemDebug)
                Log_System("处理数据：" + msg);
            if (!use.hand.socket_runFlag)
                return;
            while (msg.indexOf(BaseConfig.Head) == 0 && msg.contains(BaseConfig.End)) {
                String buff = Utils.get_string(msg, BaseConfig.Head, BaseConfig.End);
                Read_Json read_bean;
                MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                try {
                    Gson read_gson = new Gson();
                    read_bean = read_gson.fromJson(buff, Read_Json.class);
                } catch (Exception e) {
                    Log_System("数据传输发生错误:" + e.getMessage());
                    return;
                }
                if (read_bean.getIs_commder().equals("false")) {
                    if (read_bean.getCommder().equalsIgnoreCase("speak")) {
                        final String say = BaseConfig.MinecraftSay.replaceFirst(Placeholder.Servername,
                                BaseConfig.MinecraftServerName).replaceFirst(Placeholder.Message, read_bean.getMessage());
                        for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
                            if (!BaseConfig.MuteList.contains(player.getName()))
                                player.sendMessage(new TextComponentString(say));
                        }
                    } else if (read_bean.getCommder().equalsIgnoreCase("online")) {
                        String[] players = server.getOnlinePlayerNames();
                        StringBuilder player = new StringBuilder();
                        String send = BaseConfig.MinecraftPlayerListMessage;
                        if (players.length == 0) {
                            try {
                                send = send.replaceAll(Placeholder.Servername, BaseConfig.MinecraftServerName)
                                        .replaceAll(Placeholder.player_number, "0")
                                        .replaceAll(Placeholder.player_list, "无");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            int player_number = 1;
                            for (String b : players) {
                                player.append(b).append(",");
                            }
                            player = new StringBuilder(player.substring(0, player.length() - 1));
                            send = send.replaceAll(Placeholder.Servername, BaseConfig.MinecraftServerName)
                                    .replaceAll(Placeholder.player_number, "" + player_number)
                                    .replaceAll(Placeholder.player_list, player.toString());
                        }
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                        if (logs.Group_log) {
                            logs.log_write("[group]查询在线人数");
                        }
                        if (BaseConfig.SystemDebug)
                            Log_System("§d[Minecraft_QQ]§5[Debug]查询在线人数");
                    } else if (read_bean.getCommder().equalsIgnoreCase("server")) {
                        String send = BaseConfig.MinecraftServerOnlineMessage;
                        send = send.replaceAll(Placeholder.Servername, BaseConfig.MinecraftServerName);
                        socket_send.send_data(Placeholder.data, read_bean.getGroup(), "无", send);
                        if (logs.Group_log) {
                            logs.log_write("[group]查询服务器状态");
                        }
                        if (BaseConfig.SystemDebug)
                            Log_System("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
                    }
                } else if (read_bean.getIs_commder().equals("true")) {
                    StringBuilder send_message;
                    CommandSender sender = null;
                    boolean noUUID = false;
                    try {
                        List<String> com = new ArrayList<String>();
                        com.add(read_bean.getCommder());
                        FunctionObject func = FunctionObject.create(server.getFunctionManager(), com);
                        GameProfile GameProfile = server.getPlayerProfileCache().getGameProfileForUsername(read_bean.getPlayer());
                        if (GameProfile != null) {
                            FakePlayer player = new FakePlayer(server.worlds[0], GameProfile);
                            if (!read_bean.getPlayer().equalsIgnoreCase("控制台")) {
                                sender = new CommandSender(player, player.getName());
                            } else {
                                sender = new CommandSender(player, "控制台");
                            }
                            server.getFunctionManager().execute(func, sender);
                        } else
                            noUUID = true;
                    } catch (Exception e) {
                        Log_System(e.toString());
                    }
                    if (sender != null && sender.message.size() == 1) {
                        send_message = new StringBuilder(sender.message.get(0));
                    } else if (sender.message.size() > 1) {
                        send_message = new StringBuilder(sender.message.get(0));
                        for (int i = 1; i < sender.message.size(); i++) {
                            send_message.append("\n");
                            send_message.append(sender.message.get(i));
                        }
                    } else
                        send_message = new StringBuilder("指令执行失败" + (noUUID == true ? "没有找到该玩家" : null));
                    socket_send.send_data(Placeholder.data, read_bean.getGroup(),
                            "控制台", send_message.toString());
                }
                int i = msg.indexOf(BaseConfig.End);
                msg = msg.substring(i + BaseConfig.End.length());
            }
        } catch (Exception e) {
            Log_System("发送错误：" + e.getMessage());
        }
    }

    public class CommandSender extends CommandBlockBaseLogic {
        private final Entity entity;
        private final String name;
        public List<String> message = new ArrayList<>();
        private final World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();

        private CommandSender(Entity entity, String name) {
            this.entity = entity;
            this.name = name;
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
            return entity;
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
            return name;
        }

        @Override
        public MinecraftServer getServer() {
            return entity == null ? FMLCommonHandler.instance().getMinecraftServerInstance() : entity.getServer();
        }
    }
}
