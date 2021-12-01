package coloryr.minecraft_qq.side;

import coloryr.minecraft_qq.api.Placeholder;
import coloryr.minecraft_qq.Minecraft_QQ;
import coloryr.minecraft_qq.config.ConfigOBJ;
import coloryr.minecraft_qq.json.ReadOBJ;
import coloryr.minecraft_qq.utils.SocketUtils;
import coloryr.minecraft_qq.utils.Logs;
import com.google.gson.Gson;

public class ASide {
    public static void globeCheck(ReadOBJ readobj) {
        if (readobj.command.equalsIgnoreCase(Placeholder.server)) {
            String send = Minecraft_QQ.config.ServerSet.ServerOnlineMessage;
            send = send.replaceAll(Minecraft_QQ.config.Placeholder.ServerName, Minecraft_QQ.config.ServerSet.ServerName);
            SocketUtils.sendData(Placeholder.data, readobj.group, "无", send);
            if (Minecraft_QQ.config.Logs.Group) {
                Logs.logWrite("[group]查询服务器状态");
            }
            if (Minecraft_QQ.config.System.Debug)
                Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]查询服务器状态");
        } else if (readobj.command.equalsIgnoreCase(Placeholder.pause)) {
            SocketUtils.sendData(Placeholder.pause, readobj.group, "无", "data");
        } else if (readobj.command.equalsIgnoreCase(Placeholder.config)) {
            String config = new Gson().toJson(Minecraft_QQ.config);
            SocketUtils.sendData(Placeholder.config, readobj.group, "无", config);
        } else if (readobj.command.equalsIgnoreCase(Placeholder.set)) {
            try {
                Minecraft_QQ.config = new Gson().fromJson(readobj.message, ConfigOBJ.class);
                Minecraft_QQ.save();
            } catch (Exception e) {
                Minecraft_QQ.log.warning("配置文件动态更新失败");
                e.printStackTrace();
            }
        }
    }
}
