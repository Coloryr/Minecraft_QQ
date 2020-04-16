package Color_yr.Minecraft_QQ.Config;

public class ServerSetOBJ {
    private final String ServerName;
    private final String Check;
    private final String Message;
    private final String Say;
    private final int Mode;
    private final boolean SendOneByOne;
    private final String SendOneByOneMessage;
    private final boolean HideEmptyServer;
    private final String PlayerListMessage;
    private final String ServerOnlineMessage;
    private final boolean BungeeCord;
    private final int CommandDelay;

    public ServerSetOBJ() {
        ServerName = "[MC服务器]";
        Check = "群：";
        Message = "%ServerName%-%Server%-%Player%:%message%";
        Say = "[%ServerName%][群消息]%message%";
        Mode = 1;
        SendOneByOne = true;
        SendOneByOneMessage = "\n[%Server%-%PlayerNumber%]-%PlayerList%";
        HideEmptyServer = true;
        PlayerListMessage = "%ServerName%当前在线人数：%PlayerNumber%，玩家列表：%PlayerList%";
        ServerOnlineMessage = "%ServerName%服务器在线";
        BungeeCord = false;
        CommandDelay = 2000;
    }

    public int getCommandDelay() {
        return CommandDelay;
    }

    public boolean isBungeeCord() {
        return BungeeCord;
    }

    public int getMode() {
        return Mode;
    }

    public String getCheck() {
        return Check;
    }

    public String getPlayerListMessage() {
        return PlayerListMessage;
    }

    public String getSay() {
        return Say;
    }

    public String getSendOneByOneMessage() {
        return SendOneByOneMessage;
    }

    public String getServerName() {
        return ServerName;
    }

    public String getServerOnlineMessage() {
        return ServerOnlineMessage;
    }

    public String getMessage() {
        return Message;
    }

    public boolean isHideEmptyServer() {
        return HideEmptyServer;
    }

    public boolean isSendOneByOne() {
        return SendOneByOne;
    }
}
