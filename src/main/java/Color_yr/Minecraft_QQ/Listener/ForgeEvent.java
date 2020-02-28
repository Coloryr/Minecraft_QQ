package Color_yr.Minecraft_QQ.Listener;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Socket.socketSend;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "minecraft_qq")
public final class ForgeEvent {

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        String player_message;
        player_message = event.getMessage();
        if (Minecraft_QQ.Config.getUser().isNotSendCommand()) {
            if (player_message.indexOf("/") == 0)
                return;
        } else if (Minecraft_QQ.Config.getMute().contains(event.getPlayer().getName()))
            return;
        if (Minecraft_QQ.Config.getServerSet().getMode() != 0 && Minecraft_QQ.hand.socket_runFlag) {
            boolean send_ok = false;
            EntityPlayer player = event.getPlayer();
            String message = Minecraft_QQ.Config.getServerSet().getMessage();
            String playerName = player.getName();
            message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getPlayer(), playerName);
            message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName())
                                .replaceAll(Minecraft_QQ.Config.getPlaceholder().getServer(),"");
            if (player_message.indexOf(Minecraft_QQ.Config.getServerSet().getCheck()) == 0 && Minecraft_QQ.Config.getServerSet().getMode() == 1) {
                player_message = player_message.replaceFirst(Minecraft_QQ.Config.getServerSet().getCheck(), "");
                message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getMessage(), player_message);
                send_ok = socketSend.send_data(Placeholder.data, Placeholder.group, playerName, message);
            } else if (Minecraft_QQ.Config.getServerSet().getMode() == 2) {
                message = message.replaceAll(Minecraft_QQ.Config.getPlaceholder().getMessage(), player_message);
                send_ok = socketSend.send_data(Placeholder.data, Placeholder.group, playerName, message);
            }
            if (Minecraft_QQ.Config.getUser().isSendSucceed() && send_ok)
                player.sendMessage(new TextComponentString("§d[Minecraft_QQ]" + Minecraft_QQ.Config.getLanguage().getSucceedMessage()));
        }
    }
}
