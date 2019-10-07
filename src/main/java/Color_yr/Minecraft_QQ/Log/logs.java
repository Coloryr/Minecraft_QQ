package Color_yr.Minecraft_QQ.Log;

import Color_yr.Minecraft_QQ.Config.config;
import Color_yr.Minecraft_QQ.Main.Forge;

import java.io.*;
import java.util.Date;

public class logs {
    public static File file;

    public static boolean Socket_log = false;
    public static boolean Group_log = false;
    public static boolean Send_log = false;
    public static boolean Error_log = false;

    public void log_write(String text) {
        FileWriter fw;
        try {
            fw = new FileWriter(file, true);
            Date date = new Date();
            String year = String.format("%tF", date);
            String time = String.format("%tT", date);
            String write = "[" + year + "]" + "[" + time + "]" + text;
            PrintWriter pw = new PrintWriter(fw);
            pw.println(write);
            pw.flush();
            fw.flush();
            pw.close();
            fw.close();
        } catch (Exception e) {
            if (config.is_forge)
                Forge.logger.warn("§d[Minecraft_QQ]§c日志文件写入失败" + e.getMessage());
            else
                config.log_b.warning("§d[Minecraft_QQ]§c日志文件写入失败" + e.getMessage());
        }
    }
}
