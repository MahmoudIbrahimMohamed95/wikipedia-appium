package mobilePages.androidPages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import mobileActions.AndroidActions;
import org.openqa.selenium.By;

public class HomePage extends AndroidActions {
    private final By searchIcon =AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/navigation_bar_item_icon_container\").instance(2)");

    public HomePage(AndroidDriver driver){
        super(driver);
    }

    @Step("Open search screen")
    public SearchPage clickOnSearchIcon(){
        clickOnElement(searchIcon);
        return new SearchPage(driver);
    }
}