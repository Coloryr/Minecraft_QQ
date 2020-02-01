package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.API.use;
import Color_yr.Minecraft_QQ.Config.BaseConfig;
import Color_yr.Minecraft_QQ.Json.Send_Json;
import Color_yr.Minecraft_QQ.logs;
import com.google.gson.Gson;

public class socket_send {
    public static boolean send_data(String data, String group, String player, String message) {
        Send_Json send_bean = new Send_Json(data, group, player, message);
        Gson send_gson = new Gson();
        return socket_send(send_gson.toJson(send_bean));
    }

    private static boolean socket_send(String send) {
        try {
            send = BaseConfig.Head + send + BaseConfig.End;
            use.hand.os = use.hand.socket.getOutputStream();
            use.hand.os.write(send.getBytes());
            if (logs.Send_log) {
                logs.log_write("[socket_send]" + send);
            }
            if (BaseConfig.SystemDebug)
                use.MinecraftQQ.Log_System("§d[Minecraft_QQ]§5[Debug]发送数据：" + send);
            return true;
        } catch (Exception e) {
            use.MinecraftQQ.Log_System("§d[Minecraft_QQ]§c酷Q连接中断");
            e.printStackTrace();
            use.hand.socket_runFlag = false;
        }
        return false;
    }
}
