package mobilePages.androidPages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import mobileActions.AndroidActions;
import org.openqa.selenium.By;

public class SavedArticlesPage extends AndroidActions {
    private final By collectionList=AppiumBy.androidUIAutomator("new UiSelector().text(\"Collections\")");
    private final By searchIcon=AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/menu_search_lists\")");
    private final By searchField=AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/search_src_text\")");
    private final By snackBarNotificationConfirmIcon=AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/buttonView\")");
    private final By savedArticle=AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/page_list_item_title\")");

    private By selectListOption(String createdListName){
        return  AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").text(\"" + createdListName + "\")");
    }

    public SavedArticlesPage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Open reading lists section")
    public SavedArticlesPage openReadingListsSection(){
        clickOnElement(collectionList);
        return this;
    }

    @Step("Search for the new created list '{createdListName}' ")
    public SavedArticlesPage searchForTheNewCreatedReadingList(String createdListName){
        clickOnElement(searchIcon);
        typeTextToField(searchField , createdListName);
        return this;

    }

    @Step("Select createdlist '{createdListName}' ")
    public SavedArticlesPage selectCreatedList(String createdlistName){
        clickOnElement(selectListOption(createdlistName));
        clickOnElement(snackBarNotificationConfirmIcon);
        return this;
    }

    @Step("Get article name")
    public String getArticleTitle(){
        String articleTitle= getAttributeValue(savedArticle,"text");
        return articleTitle;
    }
}