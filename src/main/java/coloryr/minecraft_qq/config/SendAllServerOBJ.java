package coloryr.minecraft_qq.config;

public class SendAllServerOBJ {
    public boolean Enable;
    public String Message;
    public boolean OnlySideServer;

    public SendAllServerOBJ() {
        Enable = true;
        Message = "[%servername%-%server%]玩家：[%player%]发送群消息：%message%";
        OnlySideServer = true;
    }
}
