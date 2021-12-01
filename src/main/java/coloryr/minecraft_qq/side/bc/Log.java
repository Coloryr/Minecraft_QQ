package coloryr.minecraft_qq.side.bc;

import coloryr.minecraft_qq.api.ILogger;

import java.util.logging.Logger;

public class Log implements ILogger {
    private final Logger logger;

    public Log(Logger Logger) {
        this.logger = Logger;
    }

    @Override
    public void warning(String data) {
        logger.warning(data);
    }

    @Override
    public void info(String data) {
        logger.info(data);
    }
}
