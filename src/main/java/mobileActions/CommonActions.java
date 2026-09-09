package mobileActions;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import readers.Log;
import readers.PropertyReader;
import java.time.Duration;

public class CommonActions {
    private final WebDriverWait wait;
    private static final int waitingTimeOut= Integer.parseInt(PropertyReader.getProperty("defaultWait"));

    public CommonActions(AppiumDriver driver)
    {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTimeOut));
    }

    public void typeTextToField(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .sendKeys(text);
        Log.info("Entered text '" + text + "' into field: " + locator);
    }

    public void clickOnElement(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator))
                .click();
        Log.info("Clicked on element: " + locator);
    }

    public String getAttributeValue(By locator, String attribute) {
        String value= wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getAttribute(attribute);
        Log.info("Retrieved attribute '" + attribute + "' = '" + value + "' from element: " + locator);
        return value;
    }
}