package Color_yr.Minecraft_QQ.Listener;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "minecraft_qq")
public final class forge_event {

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        String player_message;
        player_message = event.getMessage();
        if (BaseConfig.UserNotSendCommder) {
            if (player_message.indexOf("/") == 0)
                return;
        } else if (BaseConfig.MuteList.contains(event.getPlayer().getName()))
            return;
        if (BaseConfig.MinecraftMode != 0 && use.hand.socket_runFlag) {
            boolean send_ok = false;
            EntityPlayer player = event.getPlayer();
            String message = BaseConfig.MinecraftMessage;
            String playerName = player.getName();
            message = message.replaceAll(Placeholder.Player, playerName);
            message = message.replaceAll(Placeholder.Servername, BaseConfig.MinecraftServerName);
            if (player_message.indexOf(BaseConfig.MinecraftCheck) == 0 && BaseConfig.MinecraftMode == 1) {
                player_message = player_message.replaceFirst(BaseConfig.MinecraftCheck, "");
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            } else if (BaseConfig.MinecraftMode == 2) {
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (BaseConfig.UserSendSucceed && send_ok)
                player.sendMessage(new TextComponentString("§d[Minecraft_QQ]" + BaseConfig.UserSendSucceedMessage));
        }
    }
}
