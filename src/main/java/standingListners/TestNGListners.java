package standingListners;
import helpers.FileHelpers;
import org.testng.IExecutionListener;
import readers.Log;
import readers.PropertyReader;
import reporter.AllureEnvironmentSetup;
import serverManager.AppiumServer;
import java.io.File;

public class TestNGListners implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        PropertyReader.loadProperties();
        FileHelpers.deleteAndRestartLogging(new File(PropertyReader.getProperty("user.dir") +
                PropertyReader.getProperty("appender.file.fileName")));
        Log.info("Properties is loaded to system");
        Log.info("Test Execution started");
        AllureEnvironmentSetup.setAllureEnvironment();
        AppiumServer.startServer();
    }

    @Override
    public void onExecutionFinish() {
        AppiumServer.stopServer();
        Log.info("Test Execution finished");
    }
}