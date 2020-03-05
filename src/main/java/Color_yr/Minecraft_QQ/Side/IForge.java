package Color_yr.Minecraft_QQ.Side;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Minecraft_QQForge;
import Color_yr.Minecraft_QQ.Json.ReadOBJ;
import Color_yr.Minecraft_QQ.Utils.Function;
import Color_yr.Minecraft_QQ.Utils.logs;
import Color_yr.Minecraft_QQ.Socket.socketSend;
import com.google.gson.Gson;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.command.FunctionObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
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
    public void LogInfo(String message) {
        Minecraft_QQForge.logger.info(message);
    }

    @Override
    public void LogError(String message) {
        Minecraft_QQForge.logger.error(message);
    }

    @Override
    public void Message(String Message) {
        try {
            String msg = Message;
            if (Minecraft_QQ.Config.getSystem().isDebug())
                LogInfo("处理数据：" + msg);
            if (!Minecraft_QQ.hand.socketIsRun)
                return;
            while (msg.indexOf(Minecraft_QQ.Config.getSystem().getHead()) == 0 && msg.contains(Minecraft_QQ.Config.getSystem().getEnd())) {
                String buff = Function.get_string(msg, Minecraft_QQ.Config.getSystem().getHead(), Minecraft_QQ.Config.getSystem().getEnd());
                ReadOBJ readobj;
                MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                try {
                    Gson read_gson = new Gson();
                    readobj = read_gson.fromJson(buff, ReadOBJ.class);
                } catch (Exception e) {
                    LogInfo("数据传输发生错误:" + e.getMessage());
                    return;
                }
                if (readobj.getIs_commder().equals("false")) {
                    if (readobj.getCommder().equalsIgnoreCase("speak")) {
                        final String say = Minecraft_QQ.Config.getServerSet().getSay().replaceFirst(Minecraft_QQ.Config.getPlaceholder().getServerName(),
                                Minecraft_QQ.Config.getServerSet().getServerName()).replaceFirst(Minecraft_QQ.Config.getPlaceholder().getMessage(), readobj.getMessage());
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.logWrite("[Group]" + say);
                        }
                        for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
                            if (!Minecraft_QQ.Config.getMute().contains(player.getName()))
                                player.sendMessage(new TextComponentString(say));
                        }
                    } else if (readobj.getCommder().equalsIgnoreCase("online")) {
                        String[] players = server.getOnlinePlayerNames();
                        StringBuilder player = new StringBuilder();
                        String send = Minecraft_QQ.Config.getServerSet().getPlayerListMessage();
                        if (players.length == 0) {
                            try {
                                send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "0")
                                        .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), "无");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            int player_number = 1;
                            for (String b : players) {
                                player.append(b).append(",");
                            }
                            player = new StringBuilder(player.substring(0, player.length() - 1));
                            send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerNumber(), "" + player_number)
                                    .replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayerList(), player.toString());
                        }
                        socketSend.send_data(Placeholder.data, readobj.getGroup(), "无", send);
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.logWrite("[group]查询在线人数");
                        }
                        if (Minecraft_QQ.Config.getSystem().isDebug())
                            LogInfo("§d[Minecraft_QQ]§5[Debug]查询在线人数");
                    } else if (readobj.getCommder().equalsIgnoreCase("server")) {
                        String send = Minecraft_QQ.Config.getServerSet().getServerOnlineMessage();
                        send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName());
                        socketSend.send_data(Placeholder.data, readobj.getGroup(), "无", send);
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.logWrite("[group]查询服务器状态");
                        }
                        if (Minecraft_QQ.Config.getSystem().isDebug())
                            LogInfo("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
                    }
                } else if (readobj.getIs_commder().equals("true")) {
                    StringBuilder send_message;
                    CommandSender sender = null;
                    boolean noUUID = false;
                    try {
                        List<String> com = new ArrayList<String>();
                        com.add(readobj.getCommder());
                        if (Minecraft_QQ.Config.getLogs().isGroup()) {
                            logs.logWrite("[Group]" + readobj.getPlayer() + "执行命令" + readobj.getCommder());
                        }
                        FunctionObject func = FunctionObject.create(server.getFunctionManager(), com);
                        GameProfile GameProfile = server.getPlayerProfileCache().getGameProfileForUsername(readobj.getPlayer());
                        if (GameProfile != null) {
                            FakePlayer player = new FakePlayer(server.worlds[0], GameProfile);
                            if (!readobj.getPlayer().equalsIgnoreCase("控制台")) {
                                sender = new CommandSender(player, player.getName());
                            } else {
                                sender = new CommandSender(player, "控制台");
                            }
                            server.getFunctionManager().execute(func, sender);
                        } else
                            noUUID = true;
                    } catch (Exception e) {
                        LogInfo(e.toString());
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
                        send_message = new StringBuilder("指令执行失败" + (noUUID ? "没有找到该玩家" : null));
                    socketSend.send_data(Placeholder.data, readobj.getGroup(),
                            "控制台", send_message.toString());
                }
                int i = msg.indexOf(Minecraft_QQ.Config.getSystem().getEnd());
                msg = msg.substring(i + Minecraft_QQ.Config.getSystem().getEnd().length());
            }
        } catch (Exception e) {
            LogInfo("发送错误：" + e.getMessage());
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
