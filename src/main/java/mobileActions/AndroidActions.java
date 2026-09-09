package mobileActions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import readers.Log;

public class AndroidActions extends CommonActions {
    protected final AndroidDriver driver;

    public AndroidActions(AndroidDriver driver){
        super(driver);
        this.driver=driver;
    }

    public void pressMobileBackKey()
    {
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        Log.info("Back Key is pressed from mobile screen buttons");
    }
}