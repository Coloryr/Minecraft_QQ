package coloryr.minecraft_qq.json;

public class SendObj {
    public String group;
    public String message;
    public String data;
    public String player;

    public SendObj(String data, String group, String player, String message) {
        this.data = data;
        this.group = group;
        this.message = message;
        this.player = player;
    }
}
