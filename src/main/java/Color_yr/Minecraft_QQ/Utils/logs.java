package Color_yr.Minecraft_QQ.Utils;

import Color_yr.Minecraft_QQ.Minecraft_QQ;

import java.io.*;
import java.nio.file.Files;
import java.util.Date;

public class logs {
    public static File file;

    public logs(File file) throws IOException {
        logs.file = new File(file, "logs.log");
        if (!logs.file.exists()) {
            file.createNewFile();
        }
    }

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
            Minecraft_QQ.MinecraftQQ.LogInfo("§d[Minecraft_QQ]§c日志文件写入失败" + e.getMessage());
        }
    }
}
