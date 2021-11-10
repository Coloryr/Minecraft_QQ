package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.API.ISide;
import Color_yr.Minecraft_QQ.API.ILogger;
import Color_yr.Minecraft_QQ.config.ConfigOBJ;
import Color_yr.Minecraft_QQ.utils.SocketUtils;
import Color_yr.Minecraft_QQ.utils.logs;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Minecraft_QQ {
    public final static String Version = "2.7.0";
    public static ISide Side;
    public static ConfigOBJ Config;
    public static ILogger log;
    private static File FileName;

    public static void load() {
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(FileName), StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(reader);
            Config = new Gson().fromJson(bf, ConfigOBJ.class);
            if (Config == null || Config.System == null
                    || Config.ServerSet == null || Config.Mute == null
                    || Config.Language == null || Config.User == null
                    || Config.SendAllServer == null || Config.Join == null) {
                Config = new ConfigOBJ();
                throw (new Throwable("配置文件为空"));
            }
        } catch (Throwable e) {
            log.warning("§d[Minecraft_QQ]§c配置文件读取发生错误");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            String data = new Gson().toJson(Config);
            if (FileName.exists()) {
                FileOutputStream out = new FileOutputStream(FileName);
                OutputStreamWriter write = new OutputStreamWriter(
                        out, StandardCharsets.UTF_8);
                write.write(data);
                write.close();
                out.close();
            }
        } catch (Exception e) {
            log.warning("§d[Minecraft_QQ]§c配置文件保存错误");
            e.printStackTrace();
        }
    }

    public static void start() {
        SocketUtils.start();
        log.info("§d[Minecraft_QQ]§e已启动-" + Minecraft_QQ.Version);
        log.info("§d[Minecraft_QQ]§eDebug模式" + Minecraft_QQ.Config.System.Debug);
    }

    public static void stop() {
        SocketUtils.stop();
        log.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }

    public void init(File file) {
        try {
            log.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
            if (FileName == null) {
                FileName = new File(file, "config.json");
                if (!file.exists()) {
                    file.mkdir();
                }
            }
            if (!FileName.exists()) {
                Files.copy(this.getClass().getResourceAsStream("/config.json"), FileName.toPath());
            }
            logs.init(file);
            File wiki = new File(file, "wiki.txt");
            if (!wiki.exists()) {
                Files.copy(this.getClass().getResourceAsStream("/wiki.txt"), wiki.toPath());
            }
            load();
        } catch (Exception e) {
            log.warning("§d[Minecraft_QQ]§c配置文件初始化错误");
            e.printStackTrace();
        }
    }
}