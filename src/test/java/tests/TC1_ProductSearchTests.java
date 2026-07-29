package tests;

import base.BaseTest;
import constants.AppUrls;
import constants.SearchTestData;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProductDetailPage;
import pages.DisplayedResultsPage;

import java.util.List;

public class TC1_ProductSearchTests extends BaseTest {

    @Test(priority = 1)
    public void shouldSearchAndOpenProduct() {
        DisplayedResultsPage displayedResultsPage = homePage.searchForProduct(SearchTestData.SEARCH_PRODUCT);

        Assert.assertEquals(displayedResultsPage.getSearchInputValue().toLowerCase(), SearchTestData.SEARCH_PRODUCT.toLowerCase(), "Search input does not contain searched term");
        Assert.assertTrue(displayedResultsPage.getPageTitle().toLowerCase().contains(SearchTestData.SEARCH_PRODUCT.toLowerCase()), "Browser tab title does not contain search term");
        Assert.assertTrue(displayedResultsPage.getProductCount() > 0, "No products found");

        ProductDetailPage productPage = displayedResultsPage.clickProductAtIndex(0);

        Assert.assertFalse(productPage.getProductTitle().isEmpty(), "Product title is empty");
        Assert.assertTrue(productPage.isProductPriceValid(), "Product price '" + productPage.getProductPrice() + "' does not match expected currency pattern!");
        Assert.assertTrue(productPage.isAddToCartButtonPresent(), "The 'Add to Cart' button is either missing or disabled!");
    }

    @Test(priority = 2)
    public void shouldVerifySoldOutAndNotifyMeOnUnavailableVariant() {
        driver.get(AppUrls.TARGET_PRODUCT_URL);
        ProductDetailPage productPage = new ProductDetailPage(driver);
        productPage.selectUnavailableVariant();

        Assert.assertTrue(productPage.isNotifyMeButtonDisplayed(), "The 'Notify Me' button was not displayed for the out-of-stock color variant!");
        Assert.assertTrue(productPage.isSoldOutButtonDisplayedAndDisabled(), "The 'Sold Out' button is either missing or is not correctly disabled!");
    }

    @Test(priority = 3)
    public void shouldHandleSearchWithSpecialCharacters() {
        DisplayedResultsPage displayedResultsPage = homePage.searchForProduct(SearchTestData.SPECIAL_CHARACTERS_PRODUCT);

        Assert.assertEquals(displayedResultsPage.getSearchInputValue(), SearchTestData.SPECIAL_CHARACTERS_PRODUCT, "Search input value does not match the invalid query string entered");
        Assert.assertEquals(displayedResultsPage.getProductCount(), 0, "Products were displayed for an invalid character search when zero were expected!");
    }

    @Test(priority = 4)
    public void shouldHandleEmptySearch() {
        DisplayedResultsPage displayedResultsPage = homePage.searchForProduct(SearchTestData.EMPTY_SEARCH_PRODUCT);

        if (displayedResultsPage.getCurrentUrl().equals(AppUrls.BASE_URL)) {
            Assert.assertTrue(true);
        } else {
            Assert.assertEquals(displayedResultsPage.getProductCount(), 0, "Products were loaded for an empty search query!");
        }
    }

    @Test(priority = 5)
    public void shouldHandleSearchWithLongNumericInput() {
        DisplayedResultsPage displayedResultsPage = homePage.searchForProduct(SearchTestData.LONG_NUMERIC_PRODUCT);

        Assert.assertEquals(displayedResultsPage.getSearchInputValue(), SearchTestData.LONG_NUMERIC_PRODUCT, "Search input value does not match the long numeric query string entered");
        Assert.assertTrue(displayedResultsPage.isNoResultsMessageDisplayed(), "The 'no results' feedback container was not displayed for an unrealistic long numeric search!");
    }

    @Test(priority = 6)
    public void shouldHandleSearchWithInvalidInput() {
        DisplayedResultsPage displayedResultsPage = homePage.searchForProduct(SearchTestData.INVALID_PRODUCT);

        Assert.assertEquals(displayedResultsPage.getSearchInputValue(), SearchTestData.INVALID_PRODUCT, "Search input value does not match the non-existing product entered");
        Assert.assertTrue(displayedResultsPage.isNoResultsMessageDisplayed(), "The 'no results' feedback container was not displayed!");
    }

    @Test(priority = 7)
    public void shouldVerifyAllSearchResultsContainSearchProduct() {
        DisplayedResultsPage displayedResultsPage = homePage.searchForProduct(SearchTestData.SEARCH_PRODUCT);
        Assert.assertTrue(displayedResultsPage.getProductCount() > 0, "No products found for the search term!");

        String searchProduct = SearchTestData.SEARCH_PRODUCT.toLowerCase();
        List<String> productTitles = displayedResultsPage.getProductTitles();

        SoftAssert softAssert = new SoftAssert();

        for (String title : productTitles) {
            softAssert.assertTrue(title.toLowerCase().contains(searchProduct), "Title '" + title + "' does not contain keyword: " + searchProduct);
        }
        softAssert.assertAll();
    }
}