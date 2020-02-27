package Color_yr.Minecraft_QQ.Config;

import Color_yr.Minecraft_QQ.Minecraft_QQ;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Load {
    public Load(File file, InputStream inputStream) throws Throwable {
        if (Minecraft_QQ.FileName == null) {
            Minecraft_QQ.FileName = new File(file, "config.json");
            if (!file.exists()) {
                file.mkdir();
            }
            if (!Minecraft_QQ.FileName.exists()) {
                Files.copy(inputStream, Minecraft_QQ.FileName.toPath());
            }
        }
        InputStreamReader reader = new InputStreamReader(new FileInputStream(Minecraft_QQ.FileName), StandardCharsets.UTF_8);
        BufferedReader bf = new BufferedReader(reader);
        Minecraft_QQ.Config = new Gson().fromJson(bf, ConfigOBJ.class);
        if (Minecraft_QQ.Config == null) {
            Minecraft_QQ.Config = new ConfigOBJ();
            throw (new Throwable("配置文件为空"));
        }
    }
}
