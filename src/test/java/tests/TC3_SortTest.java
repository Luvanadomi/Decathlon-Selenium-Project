package tests;

import base.BaseTest;
import constants.ProductCategory;
import constants.SortTestData;
import pages.CategoryProductListingPage;
import utils.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TC3_SortTest extends BaseTest {

    private CategoryProductListingPage sortingPage;

    @BeforeMethod
    public void setUpCategoryPage() {
        sortingPage = homePage.openCategory(ProductCategory.MEN);
    }

    @Test
    public void verifyPriceLowToHighSorting() {
        sortingPage.selectSortOption(SortTestData.SORT_LOW_TO_HIGH);
        List<Double> prices = sortingPage.getProductPrices();
        System.out.println("Prices found:");
        prices.forEach(System.out::println);
        Assert.assertTrue(CollectionUtils.isAscending(prices), "Products are not sorted: " + prices);
    }

    @Test
    public void verifyPriceHighToLowSorting() {
        sortingPage.selectSortOption(SortTestData.SORT_HIGH_TO_LOW);
        List<Double> prices = sortingPage.getProductPrices();
        Assert.assertFalse(prices.isEmpty(), "No product prices were found on the page.");
        System.out.println("Prices found:");
        prices.forEach(System.out::println);
        Assert.assertTrue(CollectionUtils.isDescending(prices), "Products are not sorted from high to low. Found: " + prices);
    }
}