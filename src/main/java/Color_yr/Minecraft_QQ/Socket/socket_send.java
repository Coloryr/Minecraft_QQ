package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Config.Base_config;
import Color_yr.Minecraft_QQ.Config.use;
import Color_yr.Minecraft_QQ.Json.Send_Json;
import Color_yr.Minecraft_QQ.Log.logs;
import com.google.gson.Gson;

public class socket_send {
    public static boolean send_data(String data, String group, String player, String message) {
        Send_Json send_bean = new Send_Json();
        Gson send_gson = new Gson();
        send_bean.setData(data);
        send_bean.setGroup(group);
        send_bean.setPlayer(player);
        send_bean.setMessage(message);
        return socket_send(send_gson.toJson(send_bean));
    }

    private static boolean socket_send(String send) {
        try {
            send = Base_config.Head + send + Base_config.End;
            use.hand.os = use.hand.socket.getOutputStream();
            use.hand.os.write(send.getBytes());
            if (logs.Send_log) {
                logs.log_write("[socket_send]" + send);
            }
            if (Base_config.System_Debug)
                use.ilog.Log_System("§d[Minecraft_QQ]§5[Debug]发送数据：" + send);
            return true;
        } catch (Exception e) {
            use.ilog.Log_System("§d[Minecraft_QQ]§c酷Q连接中断" + e.getMessage());
            use.hand.socket_runFlag = false;
        }
        return false;
    }
}
