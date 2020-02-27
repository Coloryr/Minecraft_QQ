package Color_yr.Minecraft_QQ.Config;

public class MessageOBJ {
    private String Message;
    private boolean SendQQ;

    public MessageOBJ(String Message)
    {
        this.Message = Message;
        SendQQ = true;
    }

    public String getMessage() {
        return Message;
    }

    public boolean isSendQQ() {
        return SendQQ;
    }
}
