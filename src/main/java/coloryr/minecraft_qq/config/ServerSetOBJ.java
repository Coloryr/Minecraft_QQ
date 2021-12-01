package coloryr.minecraft_qq.config;

public class ServerSetOBJ {
    public String ServerName;
    public String Check;
    public String Message;
    public String Say;
    public int Mode;
    public boolean SendOneByOne;
    public String SendOneByOneMessage;
    public boolean HideEmptyServer;
    public String PlayerListMessage;
    public String ServerOnlineMessage;
    public boolean BungeeCord;
    public int CommandDelay;

    public ServerSetOBJ() {
        ServerName = "[MC服务器]";
        Check = "群：";
        Message = "%servername%-%server%-%player%:%message%";
        Say = "[%servername%][群消息]%message%";
        Mode = 1;
        SendOneByOne = true;
        SendOneByOneMessage = "\n[%server%-%playernumber%]-%playerlist%";
        HideEmptyServer = true;
        PlayerListMessage = "%servername%当前在线人数：%playernumber%，玩家列表：%playerlist%";
        ServerOnlineMessage = "%servername%服务器在线";
        BungeeCord = false;
        CommandDelay = 2000;
    }
}
