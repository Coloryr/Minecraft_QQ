package Color_yr.Minecraft_QQ.Json;

public class Send_Json {
    private String group;
    private String message;
    private String data;
    private String player;
    public Send_Json(String data, String group, String player, String message) {
        this.data = data;
        this.group = group;
        this.message = message;
        this.player = player;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
