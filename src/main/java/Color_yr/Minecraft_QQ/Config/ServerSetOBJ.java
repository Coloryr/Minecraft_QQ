package Color_yr.Minecraft_QQ.Config;

public class ServerSetOBJ {
    private String ServerName;
    private String Check;
    private String Message;
    private String Say;
    private int Mode;
    private boolean SendOneByOne;
    private String SendOneByOneMessage;
    private boolean HideEmptyServer;
    private String PlayerListMessage;
    private String ServerOnlineMessage;
    private boolean BungeeCord;

    public ServerSetOBJ()
    {
        ServerName = "[MC服务器]";
        Check = "群：";
        Message= "%ServerName%-%Server%-%Player%:%Message%";
        Say = "[%ServerName%][群消息]%Message%";
        Mode = 1;
        SendOneByOne = true;
        SendOneByOneMessage = "\n[%Server%-%PlayerNumber%]-%PlayerList%";
        HideEmptyServer =true;
        PlayerListMessage = "%ServerName%当前在线人数：%PlayerNumber%，玩家列表：%PlayerList%";
        ServerOnlineMessage = "%ServerName%服务器在线";
        BungeeCord = false;
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
