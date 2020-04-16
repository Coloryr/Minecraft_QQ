package Color_yr.Minecraft_QQ.Config;

public class SystemOBJ {
    private final String IP;
    private final int Port;
    private final boolean AutoConnect;
    private final int AutoConnectTime;
    private final boolean Debug;
    private final String Head;
    private final String End;
    private final int Sleep;

    public SystemOBJ() {
        IP = "localhost";
        Port = 25555;
        AutoConnect = false;
        AutoConnectTime = 10000;
        Debug = false;
        Head = "[Head]";
        End = "[End]";
        Sleep = 50;
    }

    public boolean isAutoConnect() {
        return AutoConnect;
    }

    public boolean isDebug() {
        return Debug;
    }

    public int getAutoConnectTime() {
        return AutoConnectTime;
    }

    public int getSleep() {
        return Sleep;
    }

    public int getPort() {
        return Port;
    }

    public String getEnd() {
        return End;
    }

    public String getHead() {
        return Head;
    }

    public String getIP() {
        return IP;
    }
}
