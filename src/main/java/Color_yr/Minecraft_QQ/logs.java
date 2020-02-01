package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.API.use;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

public class logs {
    public static File file;

    public static boolean Group_log = false;
    public static boolean Send_log = false;

    public static void log_write(String text) {
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
            use.MinecraftQQ.Log_System("§d[Minecraft_QQ]§c日志文件写入失败" + e.getMessage());
        }
    }
}
