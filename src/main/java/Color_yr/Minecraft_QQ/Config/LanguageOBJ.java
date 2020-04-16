package Color_yr.Minecraft_QQ.Config;

public class LanguageOBJ {
    private final String MessageOFF;
    private final String MessageON;
    private final String SucceedMessage;

    public LanguageOBJ() {
        MessageOFF = "§2你已不会在收到群消息";
        MessageON = "§2你开始接受群消息";
        SucceedMessage = "§2已发送消息至群内";
    }

    public String getMessageOFF() {
        return MessageOFF;
    }

    public String getMessageON() {
        return MessageON;
    }

    public String getSucceedMessage() {
        return SucceedMessage;
    }
}
