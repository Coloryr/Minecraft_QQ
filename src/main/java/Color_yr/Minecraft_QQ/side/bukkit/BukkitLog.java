package Color_yr.Minecraft_QQ.side.bukkit;

import Color_yr.Minecraft_QQ.API.IMyLogger;

import java.util.logging.Logger;

public class BukkitLog implements IMyLogger {
    private final Logger Logger;

    public BukkitLog(Logger Logger) {
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
