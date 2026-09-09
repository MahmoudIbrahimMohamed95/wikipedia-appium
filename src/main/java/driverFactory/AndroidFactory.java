package driverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import readers.PropertyReader;
import serverManager.AppiumServer;
import java.time.Duration;

public class AndroidFactory extends AbstractDriver{
    private UiAutomator2Options getAndroidOptions(){
        UiAutomator2Options options=new UiAutomator2Options();
        options.setDeviceName(PropertyReader.getProperty("deviceName"));
        options.setAppPackage(PropertyReader.getProperty("appPackage"));
        options.setAppActivity(PropertyReader.getProperty("appActivity"));
        options.setAdbExecTimeout(Duration.ofSeconds(120));
        options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(60));
        options.setUiautomator2ServerInstallTimeout(Duration.ofSeconds(60));
        options.setApp(PropertyReader.getProperty("user.dir") + PropertyReader.getProperty("wikipediaPath"));
        return options;
    }

    public AndroidDriver createDriver(){
        return new AndroidDriver(AppiumServer.getUrl(),getAndroidOptions());
    }
}