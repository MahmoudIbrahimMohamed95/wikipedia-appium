package mobilePages.androidPages;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import mobileActions.AndroidActions;

public class OnBoardingPage extends AndroidActions {

    public OnBoardingPage(AndroidDriver driver){
        super(driver);
    }

    @Step("Landing on Home screen")
    public HomePage openWikipediaMobileAppHome(){
        pressMobileBackKey();
        return new HomePage(driver);
    }
}