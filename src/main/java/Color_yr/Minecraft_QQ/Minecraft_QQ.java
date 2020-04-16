package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.API.IMinecraft_QQ;
import Color_yr.Minecraft_QQ.API.ISocketControl;
import Color_yr.Minecraft_QQ.Config.ConfigOBJ;
import Color_yr.Minecraft_QQ.Socket.SocketControl;
import Color_yr.Minecraft_QQ.Utils.logs;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Minecraft_QQ {
    public final static String Version = "2.4.0.1";
    public static ISocketControl control = new SocketControl();
    public static IMinecraft_QQ MinecraftQQ;
    public static ConfigOBJ Config;
    private static File FileName;

    public static void load() {
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(FileName), StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(reader);
            Config = new Gson().fromJson(bf, ConfigOBJ.class);
            if (Config == null || Config.getSystem() == null
                    || Config.getServerSet() == null || Config.getMute() == null
                    || Config.getLanguage() == null || Config.getUser() == null
                    || Config.getSendAllServer() == null || Config.getJoin() == null) {
                Config = new ConfigOBJ();
                throw (new Throwable("配置文件为空"));
            }
        } catch (Throwable e) {
            MinecraftQQ.logError("§d[Minecraft_QQ]§c配置文件读取发生错误");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            String data = new Gson().toJson(Config);
            if (FileName.exists()) {
                Writer out = new FileWriter(FileName);
                out.write(data);
                out.close();
            }
        } catch (Exception e) {
            MinecraftQQ.logError("§d[Minecraft_QQ]§c配置文件保存错误");
            e.printStackTrace();
        }
    }

    public void init(File file) {
        try {
            MinecraftQQ.logInfo("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
            if (FileName == null) {
                FileName = new File(file, "config.json");
                if (!file.exists()) {
                    file.mkdir();
                }
            }
            if (!FileName.exists()) {
                Files.copy(this.getClass().getResourceAsStream("/Minecraft_QQ.json"), FileName.toPath());
            }
            new logs(file);
            File wiki = new File(file, "wiki.txt");
            if (!wiki.exists()) {
                Files.copy(this.getClass().getResourceAsStream("/wiki.txt"), wiki.toPath());
            }
            load();
        } catch (Exception e) {
            MinecraftQQ.logError("§d[Minecraft_QQ]§c配置文件初始化错误");
            e.printStackTrace();
        }
    }

    public static void start() {
        control.start();
        MinecraftQQ.logInfo("§d[Minecraft_QQ]§e已启动-" + Minecraft_QQ.Version);
        MinecraftQQ.logInfo("§d[Minecraft_QQ]§eDebug模式" + Minecraft_QQ.Config.getSystem().isDebug());
    }

    public static void stop() {
        control.stop();
        MinecraftQQ.logInfo("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}