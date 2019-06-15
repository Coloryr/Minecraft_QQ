package Color_yr.Minecraft.QQ;

class Read_Json {
    private String group;
    private String message;
    private String player;
    private String is_commder;

    public String getGroup() {
        return group;
    }

    public String getMessage() {
        return message;
    }

    public String getPlayer() {
        return player;
    }

    public String getIs_commder() {
        return is_commder;
    }
}
class Send_Json {
    private String group;
    private String message;
    private String data;
    private String player;

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
