package helpers;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import readers.Log;
import java.io.File;

public class FileHelpers {

    public static void deleteAndRestartLogging(File file) {
        try {
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            context.stop();
            if (file.exists()) {
                FileUtils.forceDelete(file);
            }
            context.reconfigure();
            Log.info("Log file was deleted and recreated successfully.");
        } catch (Exception e) {
            System.err.println("Failed to reset logging: " + e.getMessage());
            e.printStackTrace();
        }
    }
}