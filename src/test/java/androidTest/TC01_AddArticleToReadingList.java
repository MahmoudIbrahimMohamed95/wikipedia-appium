package androidTest;
import driverFactory.mobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import mobilePages.androidPages.OnBoardingPage;
import mobilePages.androidPages.SavedArticlesPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;

public class TC01_AddArticleToReadingList {
    private AppiumDriver driver;
    private JsonReader jsonReader;
    private SavedArticlesPage savedArticlesPage;

    @BeforeClass
    public void classSetup(){
        jsonReader = new JsonReader("ArticleToReadingList");
        driver= mobileDriverFactory.initDriver();
    }

    @Test
    public void validateAddingArticleToReadingList(){
        savedArticlesPage = new OnBoardingPage((AndroidDriver) driver)
                        .openWikipediaMobileAppHome()
                        .clickOnSearchIcon()
                        .searchForArticle(jsonReader.getJsonData("articleToSearch"))
                        .openArticleFromSearch(jsonReader.getJsonData("articleToSelect"))
                        .addArticleToReadingList()
                        .createNewReadingList(jsonReader.getJsonData("readingListName"))
                        .navigateUptoSearchPage()
                        .openSavedReadingListsPage()
                        .openReadingListsSection()
                        .searchForTheNewCreatedReadingList(jsonReader.getJsonData("readingListName"))
                        .selectCreatedList(jsonReader.getJsonData("readingListName"));

        Assert.assertEquals(savedArticlesPage.getArticleTitle() , jsonReader.getJsonData("articleToSelect"));
     }

    @AfterClass
    public void tearDown(){
        mobileDriverFactory.quitDriver();
     }
}