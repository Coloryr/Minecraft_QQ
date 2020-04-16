package Color_yr.Minecraft_QQ.Config;

public class SendAllServerOBJ {
    private final boolean Enable;
    private final String Message;
    private final boolean OnlySideServer;

    public SendAllServerOBJ() {
        Enable = true;
        Message = "[%ServerName%-%Server%]玩家：[%Player%]发送群消息：[%message%";
        OnlySideServer = true;
    }

    public String getMessage() {
        return Message;
    }

    public boolean isEnable() {
        return Enable;
    }

    public boolean isOnlySideServer() {
        return OnlySideServer;
    }
}
