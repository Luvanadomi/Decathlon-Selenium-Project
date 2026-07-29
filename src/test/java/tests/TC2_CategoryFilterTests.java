package tests;


import base.BaseTest;
import constants.FilterTestData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CategoryProductListingPage;

public class TC2_CategoryFilterTests extends BaseTest {

    private CategoryProductListingPage categoryProductListingPage;

    @BeforeMethod
    public void setUpCategoryPage() {
        categoryProductListingPage = homePage.hoverMenAndSelectJackets();
    }

    @Test(priority = 1)
    public void shouldFilterProductsByColor() {
        int initialProductCount = categoryProductListingPage.getProductCount();

        categoryProductListingPage.selectColorFilter(FilterTestData.FILTER_COLOR);
        int filteredProductCount = categoryProductListingPage.getProductCount();

        Assert.assertNotEquals(filteredProductCount, initialProductCount, "Product count should update after applying the color filter.");

        boolean areAllColorsMatching = categoryProductListingPage.areAllDisplayedProductsMatchingColor(FilterTestData.FILTER_COLOR);
        Assert.assertTrue(areAllColorsMatching, "All displayed products should match the selected color filter: " + FilterTestData.FILTER_COLOR);
    }

    @Test(priority = 2)
    public void shouldFilterProductsByPrice() {
        categoryProductListingPage.applyPriceRange(FilterTestData.MIN_PRICE, FilterTestData.MAX_PRICE);

        boolean arePricesWithinRange = categoryProductListingPage.areAllProductsWithinPriceRange(FilterTestData.MIN_PRICE, FilterTestData.MAX_PRICE);
        Assert.assertTrue(arePricesWithinRange, String.format("All product prices should be between $%.2f and $%.2f", FilterTestData.MIN_PRICE, FilterTestData.MAX_PRICE));
    }

    @Test(priority = 3)
    public void shouldNotFilterProductsWithNegativePriceRange() {
        categoryProductListingPage.applyPriceRange(FilterTestData.NEGATIVE_MIN_PRICE, FilterTestData.MAX_PRICE);

        boolean arePricesValid = categoryProductListingPage.areAllProductsWithinPriceRange(FilterTestData.NEGATIVE_MIN_PRICE, FilterTestData.MAX_PRICE);
        Assert.assertFalse(arePricesValid, "Products should not be filtered using a negative price range.");
    }
}