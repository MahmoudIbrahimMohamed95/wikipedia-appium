package serverManager;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import readers.Log;
import readers.PropertyReader;
import java.io.File;
import java.net.URL;
import java.time.Duration;

public class AppiumServer {
    private static final String APPIUM_JS = PropertyReader.getProperty("appiumMainJsPATH");
    private static final int APPIUM_PORT = Integer.parseInt(PropertyReader.getProperty("appiumPortAddress"));
    private static final String APPUIM_IP_ADDRESS = PropertyReader.getProperty("appiumIpAddress");
    private static AppiumDriverLocalService service;

    public static void startServer() {
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File(APPIUM_JS))
                .withIPAddress(APPUIM_IP_ADDRESS)
                .usingPort(APPIUM_PORT)
                .withTimeout(Duration.ofSeconds(60))
                .build();
        service.start();
        Log.info("Appuim server starts");
    }

    public static URL getUrl() {
        return service.getUrl();
    }

    public static void stopServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            Log.info("Appuim server stopped");
        }
    }
}