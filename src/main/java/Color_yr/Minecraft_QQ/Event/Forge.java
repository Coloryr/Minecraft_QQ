package Color_yr.Minecraft_QQ.Event;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Socket.socket;
import Color_yr.Minecraft_QQ.Socket.socket_send;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "minecraft_qq")
public final class Forge {

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        String player_message;
        player_message = event.getMessage();
        if (Color_yr.Minecraft_QQ.Config.Bukkit.User_NotSendCommder == true) {
            if (player_message.indexOf("/") == 0)
                return;
        } else if (Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Mode != 0 && socket.hand.socket_runFlag == true) {
            boolean send_ok = false;
            EntityPlayer player = event.getPlayer();
            String message = Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Message;
            String playerName = player.getName();
            message = message.replaceAll(Placeholder.Player, playerName);
            message = message.replaceAll(Placeholder.Servername, Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_ServerName);
            if (player_message.indexOf(Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Check) == 0 && Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Mode == 1) {
                player_message = player_message.replaceFirst(Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Check, "");
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Color_yr.Minecraft_QQ.Config.Bukkit.Minecraft_Mode == 2) {
                message = message.replaceAll(Placeholder.Message, player_message);
                send_ok = socket_send.send_data(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (Color_yr.Minecraft_QQ.Config.Bukkit.User_SendSucceed == true && send_ok == true)
                player.sendMessage(new TextComponentString("§d[Minecraft_QQ]" + Color_yr.Minecraft_QQ.Config.Bukkit.User_SendSucceedMessage));
        }
    }
}
