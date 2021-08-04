package Color_yr.Minecraft_QQ.config;

import Color_yr.Minecraft_QQ.Minecraft_QQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigOBJ {
    public MessageOBJ Join;
    public MessageOBJ Quit;
    public MessageOBJ ChangeServer;
    public ServerSetOBJ ServerSet;
    public Map<String, String> Servers;
    public SendAllServerOBJ SendAllServer;
    public SystemOBJ System;
    public UserOBJ User;
    public LogsOBJ Logs;
    public PlaceholderOBJ Placeholder;
    public LanguageOBJ Language;
    public List<String> Mute;
    public String Version;

    public ConfigOBJ() {
        Join = new MessageOBJ("%player%加入了服务器");
        Quit = new MessageOBJ("%player%退出了服务器");
        ChangeServer = new MessageOBJ("%player%加入了子服%server%");
        ServerSet = new ServerSetOBJ();
        Servers = new HashMap<>();
        SendAllServer = new SendAllServerOBJ();
        System = new SystemOBJ();
        User = new UserOBJ();
        Logs = new LogsOBJ();
        Placeholder = new PlaceholderOBJ();
        Language = new LanguageOBJ();
        Mute = new ArrayList<>();
        Version = Minecraft_QQ.Version;
    }
}
