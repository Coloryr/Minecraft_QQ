package Color_yr.Minecraft_QQ.Config;

public class MessageOBJ {
    private String Message;
    private boolean sendQQ;

    public MessageOBJ(String Message)
    {
        this.Message = Message;
        sendQQ = true;
    }

    public String getMessage() {
        return Message;
    }

    public boolean isSendQQ() {
        return sendQQ;
    }
}
