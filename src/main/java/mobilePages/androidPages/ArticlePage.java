package mobilePages.androidPages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import mobileActions.AndroidActions;
import org.openqa.selenium.By;

public class ArticlePage extends AndroidActions {
    private final By wikipediaGameCloseIcon=AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/closeButton\")");
    private final By articleSaveIcon=AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/page_save\")");
    private final By createNewCollectionButton=AppiumBy.androidUIAutomator("new UiSelector().text(\"Create a new collection\")");
    private final By collectionNameTextField=AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/text_input\")");
    private final By okButton=AppiumBy.androidUIAutomator("new UiSelector().text(\"OK\")");
    private final By navigateUpIcon=AppiumBy.androidUIAutomator("new UiSelector().description(\"Navigate up\")");

    public ArticlePage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Add article to reading list")
    public ArticlePage addArticleToReadingList(){
        clickOnElement(wikipediaGameCloseIcon);
        clickOnElement(articleSaveIcon);
        clickOnElement(createNewCollectionButton);
        return this;
    }

    @Step("Create new reading list '{collectionName}' ")
    public ArticlePage createNewReadingList(String collectionName){
        typeTextToField(collectionNameTextField , collectionName);
        clickOnElement(okButton);
        return this;
    }

    @Step("Back to search screen")
    public SearchPage navigateUptoSearchPage(){
        clickOnElement(navigateUpIcon);
        clickOnElement(navigateUpIcon);
        return new SearchPage(driver);
    }
}