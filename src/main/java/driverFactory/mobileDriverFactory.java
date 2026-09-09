package driverFactory;
import io.appium.java_client.AppiumDriver;
import readers.PropertyReader;
/*THREAD LOCAL FOR FUTURE EXTENSION FOR DISTRIBUTE TESTS TO RUN PARALLEL ON SEVERAL PORTS AND DEVICES*/
public class mobileDriverFactory {
    private static ThreadLocal<AppiumDriver> driverThreadLocal=new ThreadLocal<>();
    private final static String mobileOs= PropertyReader.getProperty("mobileOs");

    public static AppiumDriver initDriver(){
        Mobiles mobileType=Mobiles.valueOf(mobileOs.toUpperCase());
        AbstractDriver abstractDriver=mobileType.getDriverFactory();
        AppiumDriver driver = abstractDriver.createDriver();
        driverThreadLocal.set(driver);
        return driverThreadLocal.get();
    }

    public static void quitDriver(){
        AppiumDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}