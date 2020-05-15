package Color_yr.Minecraft_QQ.Side;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.Config.ConfigOBJ;
import Color_yr.Minecraft_QQ.Json.ReadOBJ;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.Utils.logs;
import com.google.gson.Gson;

public class ASide {
    public static void globeCheck(ReadOBJ readobj) {
        if (readobj.getCommand().equalsIgnoreCase(Placeholder.server)) {
            String send = Minecraft_QQ.Config.getServerSet().getServerOnlineMessage();
            send = send.replaceAll(Minecraft_QQ.Config.getPlaceholder().getServerName(), Minecraft_QQ.Config.getServerSet().getServerName());
            Minecraft_QQ.control.sendData(Placeholder.data, readobj.getGroup(), "无", send);
            if (Minecraft_QQ.Config.getLogs().isGroup()) {
                logs.logWrite("[group]查询服务器状态");
            }
            if (Minecraft_QQ.Config.getSystem().isDebug())
                Minecraft_QQ.Side.logInfo("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
        } else if (readobj.getCommand().equalsIgnoreCase(Placeholder.pause)) {
            boolean sendok = Minecraft_QQ.control.sendData(Placeholder.pause, readobj.getGroup(), "无", "data");
            if (!sendok)
                Minecraft_QQ.Side.logError("§d[Minecraft_QQ]§c心跳包发送失败");
        } else if (readobj.getCommand().equalsIgnoreCase(Placeholder.config)) {
            String config = new Gson().toJson(Minecraft_QQ.Config);
            boolean sendok = Minecraft_QQ.control.sendData(Placeholder.config, readobj.getGroup(), "无", config);
            if (!sendok)
                Minecraft_QQ.Side.logError("§d[Minecraft_QQ]§c配置文件发送失败");
        } else if (readobj.getCommand().equalsIgnoreCase(Placeholder.set)) {
            try {
                Minecraft_QQ.Config = new Gson().fromJson(readobj.getMessage(), ConfigOBJ.class);
            } catch (Exception e) {
                Minecraft_QQ.Side.logError("配置文件动态更新失败");
                e.printStackTrace();
            }
        }
    }
}
