package coloryr.minecraft_qq.side.velocity;

import coloryr.minecraft_qq.api.ILogger;
import org.slf4j.Logger;

public class Log implements ILogger {
    private final Logger logger;

    public Log(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void warning(String data) {
        logger.warn(data);
    }

    @Override
    public void info(String data) {
        logger.info(data);
    }
}
