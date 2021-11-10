package Color_yr.Minecraft_QQ.side.bc;

import Color_yr.Minecraft_QQ.API.ILogger;

import java.util.logging.Logger;

public class BCLog implements ILogger {
    private final Logger Logger;

    public BCLog(Logger Logger) {
        this.Logger = Logger;
    }

    @Override
    public void warning(String data) {
        Logger.warning(data);
    }

    @Override
    public void info(String data) {
        Logger.info(data);
    }
}
