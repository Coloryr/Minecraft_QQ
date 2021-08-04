package Color_yr.Minecraft_QQ.json;

public class SendOBJ {
    public String group;
    public String message;
    public String data;
    public String player;

    public SendOBJ(String data, String group, String player, String message) {
        this.data = data;
        this.group = group;
        this.message = message;
        this.player = player;
    }
}
