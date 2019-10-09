package Color_yr.Minecraft_QQ.Log;

import Color_yr.Minecraft_QQ.Main.Forge;

public class Log_f implements ILog {
    public void Log_System(String message)
    {
        Forge.logger.info(message);
    }
}
