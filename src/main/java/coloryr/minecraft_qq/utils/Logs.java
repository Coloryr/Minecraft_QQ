package coloryr.minecraft_qq.utils;

import coloryr.minecraft_qq.Minecraft_QQ;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Logs {
    public static File file;

    public static void init(File file) throws IOException {
        Logs.file = new File(file, "logs.log");
        if (!Logs.file.exists()) {
            file.createNewFile();
        }
    }

    public static void logWrite(String text) {
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
            Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c日志文件写入失败" + e.getMessage());
        }
    }
}
