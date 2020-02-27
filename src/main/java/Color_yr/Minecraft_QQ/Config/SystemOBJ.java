package Color_yr.Minecraft_QQ.Config;

public class SystemOBJ {
    private String IP;
    private short Port;
    private boolean AutoConnect;
    private int AutoConnectTime;
    private boolean Debug;
    private String Head;
    private String End;
    private int Sleep;

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

    public short getPort() {
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
