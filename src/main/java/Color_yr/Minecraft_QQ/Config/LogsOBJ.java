package Color_yr.Minecraft_QQ.Config;

public class LogsOBJ {
    private boolean Group;
    private boolean Server;

    public LogsOBJ()
    {
        Group = true;
        Server = true;
    }

    public boolean isGroup() {
        return Group;
    }

    public boolean isServer() {
        return Server;
    }
}
