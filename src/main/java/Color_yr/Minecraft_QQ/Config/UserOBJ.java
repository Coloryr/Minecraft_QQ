package Color_yr.Minecraft_QQ.Config;

public class UserOBJ {
    private final boolean SendSucceed;
    private final boolean NotSendCommand;

    public UserOBJ() {
        SendSucceed = true;
        NotSendCommand = true;
    }

    public boolean isNotSendCommand() {
        return NotSendCommand;
    }

    public boolean isSendSucceed() {
        return SendSucceed;
    }
}
