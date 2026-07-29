package base;

import constants.AppUrls;
import constants.ProductCategory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import pages.CategoryProductListingPage;
import pages.DisplayedResultsPage;
import pages.HomePage;
import pages.ProductDetailPage;
import pages.components.PopUpComponent;
import driver.DriverFactory;
import listeners.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;

    public WebDriver getDriver() {
        return driver;
    }

    public ProductDetailPage addMultipleProductsToCart(ProductCategory category, int itemCount) {
        ProductDetailPage productPage = null;

        for (int i = 0; i < itemCount; i++) {
            CategoryProductListingPage listingPage = homePage.openCategory(category);
            productPage = listingPage.clickProductAtIndex(i);
            productPage.addToCartWithQuantity(1);
            productPage.closeCartDrawerIfPresent();
        }

        return productPage;
    }

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(AppUrls.BASE_URL);

        PopUpComponent popup = new PopUpComponent(driver);
        popup.dismissPopupIfPresent();

        homePage = new HomePage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}