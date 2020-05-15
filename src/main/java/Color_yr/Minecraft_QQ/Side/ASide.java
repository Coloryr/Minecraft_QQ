package Color_yr.Minecraft_QQ.Side;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import com.google.gson.Gson;

public class ASide {
    public static boolean S()
    {
else if (readobj.getCommder().equalsIgnoreCase("pause")) {
        boolean sendok = Minecraft_QQ.control.sendData(Placeholder.pause, readobj.getGroup(), "无", "data");
        if (!sendok)
            logError("§d[Minecraft_QQ]§c心跳包发送失败");
    } else if (readobj.getCommder().equalsIgnoreCase("config")) {
        String config = new Gson().toJson(Minecraft_QQ.Config);
        boolean sendok = Minecraft_QQ.control.sendData(Placeholder.config, readobj.getGroup(), "无", config);
        if (!sendok)
            logError("§d[Minecraft_QQ]§c配置文件发送失败");
    }
    }
}
