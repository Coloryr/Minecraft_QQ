package Color_yr.Minecraft_QQ.side;

import Color_yr.Minecraft_QQ.API.Placeholder;
import Color_yr.Minecraft_QQ.config.ConfigOBJ;
import Color_yr.Minecraft_QQ.json.ReadOBJ;
import Color_yr.Minecraft_QQ.Minecraft_QQ;
import Color_yr.Minecraft_QQ.utils.logs;
import com.google.gson.Gson;

public class ASide {
    public static void globeCheck(ReadOBJ readobj) {
        if (readobj.command.equalsIgnoreCase(Placeholder.server)) {
            String send = Minecraft_QQ.Config.ServerSet.ServerOnlineMessage;
            send = send.replaceAll(Minecraft_QQ.Config.Placeholder.ServerName, Minecraft_QQ.Config.ServerSet.ServerName);
            Minecraft_QQ.control.sendData(Placeholder.data, readobj.group, "无", send);
            if (Minecraft_QQ.Config.Logs.Group) {
                logs.logWrite("[group]查询服务器状态");
            }
            if (Minecraft_QQ.Config.System.Debug)
                Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
        } else if (readobj.command.equalsIgnoreCase(Placeholder.pause)) {
            Minecraft_QQ.control.sendData(Placeholder.pause, readobj.group, "无", "data");
        } else if (readobj.command.equalsIgnoreCase(Placeholder.config)) {
            String config = new Gson().toJson(Minecraft_QQ.Config);
            Minecraft_QQ.control.sendData(Placeholder.config, readobj.group, "无", config);
        } else if (readobj.command.equalsIgnoreCase(Placeholder.set)) {
            try {
                Minecraft_QQ.Config = new Gson().fromJson(readobj.message, ConfigOBJ.class);
                Minecraft_QQ.save();
            } catch (Exception e) {
                Minecraft_QQ.log.warning("配置文件动态更新失败");
                e.printStackTrace();
            }
        }
    }
}
