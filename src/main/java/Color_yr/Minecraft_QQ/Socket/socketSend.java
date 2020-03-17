package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Json.SendOBJ;
import Color_yr.Minecraft_QQ.Utils.logs;
import com.google.gson.Gson;

public class socketSend {
    public static boolean send_data(String data, String group, String player, String message) {
        SendOBJ send_bean = new SendOBJ(data, group, player, message);
        Gson send_gson = new Gson();
        return socket_send(send_gson.toJson(send_bean), player, message);
    }

    private static boolean socket_send(String send, String Player, String message) {
        try {
            send = Minecraft_QQ.Config.getSystem().getHead() + send + Minecraft_QQ.Config.getSystem().getEnd();
            Minecraft_QQ.hand.os = Minecraft_QQ.hand.socket.getOutputStream();
            Minecraft_QQ.hand.os.write(send.getBytes());
            if (Minecraft_QQ.Config.getLogs().isServer()) {
                logs.logWrite("[Server]" + (Player == null ? "测试" : Player) + ":" + message);
            }
            if (Minecraft_QQ.Config.getSystem().isDebug())
                Minecraft_QQ.MinecraftQQ.LogInfo("§d[Minecraft_QQ]§5[Debug]发送数据：" + send);
            return true;
        } catch (Exception e) {
            Minecraft_QQ.MinecraftQQ.LogInfo("§d[Minecraft_QQ]§c酷Q连接中断");
            e.printStackTrace();
            Minecraft_QQ.hand.socketIsRun = false;
        }
        return false;
    }
}
