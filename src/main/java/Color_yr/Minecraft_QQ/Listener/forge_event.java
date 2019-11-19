package Color_yr.Minecraft_QQ.Listener;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Config.Base_config;
import Color_yr.Minecraft_QQ.Config.use;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "minecraft_qq")
public final class forge_event {

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        String player_message;
        player_message = event.getMessage();
        if (Base_config.User_NotSendCommder) {
            if (player_message.indexOf("/") == 0)
                return;
        } else if (Base_config.Mute_List.contains(event.getPlayer().getName()))
            return;
        if (Base_config.Minecraft_Mode != 0 && use.hand.socket_runFlag) {
            boolean send_ok = false;
            EntityPlayer player = event.getPlayer();
            String message = Base_config.Minecraft_Message;
            String playerName = player.getName();
            message = message.replaceAll(Placeholder.Player, playerName);
            message = message.replaceAll(Placeholder.Servername, Base_config.Minecraft_ServerName);
            if (player_message.indexOf(Base_config.Minecraft_Check) == 0 && Base_config.Minecraft_Mode == 1) {
                player_message = player_message.replaceFirst(Base_config.Minecraft_Check, "");
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Base_config.Minecraft_Mode == 2) {
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (Base_config.User_SendSucceed && send_ok)
                player.sendMessage(new TextComponentString("§d[Minecraft_QQ]" + Base_config.User_SendSucceedMessage));
        }
    }
}
