package Color_yr.Minecraft_QQ.Config;

public class PlaceholderOBJ {
    private String Message;
    private String Player;
    private String ServerName;
    private String Server;
    private String PlayerNumber;
    private String PlayerList;

    public PlaceholderOBJ() {
        Message = "%message%";
        Player = "%Player%";
        ServerName = "%ServerName%";
        Server = "%Server%";
        PlayerNumber = "%PlayerNumber%";
        PlayerList = "%PlayerList%";
    }

    public String getMessage() {
        return Message;
    }

    public String getPlayer() {
        return Player;
    }

    public String getPlayerList() {
        return PlayerList;
    }

    public String getPlayerNumber() {
        return PlayerNumber;
    }

    public String getServer() {
        return Server;
    }

    public String getServerName() {
        return ServerName;
    }
}
