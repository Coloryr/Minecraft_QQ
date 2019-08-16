package Color_yr.Minecraft_QQ.Socket;

import Color_yr.Minecraft_QQ.Json.Send_Json;
import com.google.gson.Gson;

public class socket_send {
    public static boolean send_data(String data, String group, String player, String message) {
        Send_Json send_bean = new Send_Json();
        Gson send_gson = new Gson();
        send_bean.setData(data);
        send_bean.setGroup(group);
        send_bean.setPlayer(player);
        send_bean.setMessage(message);
        return socket.socket_send(send_gson.toJson(send_bean));
    }
}
