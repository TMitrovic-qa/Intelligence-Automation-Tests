package support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DualLogger {
    private final Logger logger;

    private DualLogger(Class<?> clazz) {
        logger = LogManager.getLogger(clazz);
    }

    public static DualLogger getLogger(Class<?> clazz) {
        return new DualLogger(clazz);
    }

    public void infoWithReport(String message) {
        logger.info(message);
    }

    public void errorWithReport(String message) {
        logger.error(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void info(String message) {
        logger.info(message);
    }
}
