package mobilePages.androidPages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import mobileActions.AndroidActions;
import org.openqa.selenium.By;

public class SearchPage extends AndroidActions {
    private final By searchIcon = AppiumBy.androidUIAutomator( "new UiSelector().resourceId(\"org.wikipedia:id/navigation_bar_item_active_indicator_view\")");
    private final By searchField=AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/search_src_text\")");
    private final By bottomSheetCloseIcon=AppiumBy.androidUIAutomator("new UiSelector().description(\"Close\")");
    private final By savedReadingListsIcon=AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"org.wikipedia:id/nav_tab_reading_lists\")");

    private By selectArticleOption(String articleName){
            return AppiumBy.androidUIAutomator
                    ("new UiSelector().className(\"android.widget.TextView\").text(\"" + articleName + "\")");
    }

    public SearchPage(AndroidDriver driver)
    {
        super(driver);
    }

    @Step("Search for article '{articleName}' ")
    public SearchPage searchForArticle(String articleName){
        clickOnElement(bottomSheetCloseIcon);
        clickOnElement(searchIcon);
        typeTextToField(searchField , articleName);
        return this;
    }

    @Step("Open article '{articleName}' from search result ")
    public ArticlePage openArticleFromSearch(String articleName){
        clickOnElement(selectArticleOption(articleName));
        return new ArticlePage(driver);
    }

    @Step("Open saved reading list page ")
    public SavedArticlesPage openSavedReadingListsPage(){
        clickOnElement(savedReadingListsIcon);
        return new SavedArticlesPage(driver);
    }
}