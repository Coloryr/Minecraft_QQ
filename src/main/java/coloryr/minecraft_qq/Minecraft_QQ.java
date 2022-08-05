package coloryr.minecraft_qq;

import coloryr.minecraft_qq.api.ILogger;
import coloryr.minecraft_qq.api.ISide;
import coloryr.minecraft_qq.config.ConfigOBJ;
import coloryr.minecraft_qq.utils.Logs;
import coloryr.minecraft_qq.utils.SocketUtils;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Minecraft_QQ {
    public final static String version = "3.0.0";
    public static ISide side;
    public static ConfigOBJ config;
    public static ILogger log;
    private static File fileName;

    public static void load() {
        try {
            InputStreamReader reader = new InputStreamReader(Files.newInputStream(fileName.toPath()), StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(reader);
            config = new Gson().fromJson(bf, ConfigOBJ.class);
            if (config == null || config.System == null
                    || config.ServerSet == null || config.Mute == null
                    || config.Language == null || config.User == null
                    || config.SendAllServer == null || config.Join == null) {
                config = new ConfigOBJ();
                throw (new Throwable("配置文件为空"));
            }
        } catch (Throwable e) {
            log.warning("§d[Minecraft_QQ]§c配置文件读取发生错误");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            String data = new Gson().toJson(config);
            if (fileName.exists()) {
                FileOutputStream out = new FileOutputStream(fileName);
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
        log.info("§d[Minecraft_QQ]§e已启动-" + Minecraft_QQ.version);
        log.info("§d[Minecraft_QQ]§eDebug模式" + Minecraft_QQ.config.System.Debug);
    }

    public static void stop() {
        SocketUtils.stop();
        log.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }

    public void init(File file) {
        try {
            log.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
            if (fileName == null) {
                fileName = new File(file, "config.json");
                if (!file.exists()) {
                    file.mkdir();
                }
            }
            if (!fileName.exists()) {
                Files.copy(this.getClass().getResourceAsStream("/config.json"), fileName.toPath());
            }
            Logs.init(file);
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