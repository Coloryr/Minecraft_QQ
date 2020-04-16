package Color_yr.Minecraft_QQ.Config;

import Color_yr.Minecraft_QQ.Minecraft_QQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigOBJ {
    private final MessageOBJ Join;
    private final MessageOBJ Quit;
    private final MessageOBJ ChangeServer;
    private final ServerSetOBJ ServerSet;
    private final Map<String, String> Servers;
    private final SendAllServerOBJ SendAllServer;
    private final SystemOBJ System;
    private final UserOBJ User;
    private final LogsOBJ Logs;
    private final PlaceholderOBJ Placeholder;
    private final LanguageOBJ Language;
    private final List<String> Mute;
    private final String Version;

    public ConfigOBJ() {
        Join = new MessageOBJ("%Player%加入了服务器");
        Quit = new MessageOBJ("%Player%退出了服务器");
        ChangeServer = new MessageOBJ("%Player%加入了子服%Server%");
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

    public PlaceholderOBJ getPlaceholder() {
        return Placeholder;
    }

    public List<String> getMute() {
        return Mute;
    }

    public void AddMute(String player) {
        if (!Mute.contains(player))
            Mute.add(player);
    }

    public void RemoveMute(String player) {
        Mute.remove(player);
    }

    public LanguageOBJ getLanguage() {
        return Language;
    }

    public String getVersion() {
        return Version;
    }

    public LogsOBJ getLogs() {
        return Logs;
    }

    public Map<String, String> getServers() {
        return Servers;
    }

    public MessageOBJ getChangeServer() {
        return ChangeServer;
    }

    public MessageOBJ getJoin() {
        return Join;
    }

    public MessageOBJ getQuit() {
        return Quit;
    }

    public ServerSetOBJ getServerSet() {
        return ServerSet;
    }

    public SendAllServerOBJ getSendAllServer() {
        return SendAllServer;
    }

    public SystemOBJ getSystem() {
        return System;
    }

    public UserOBJ getUser() {
        return User;
    }
}
