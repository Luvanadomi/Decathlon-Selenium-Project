package tests;

import base.BaseTest;
import constants.CartTestData;
import constants.ProductCategory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;

public class TC6_EmptyCartTest extends BaseTest {

    @Test
    public void testEmptyCartByDeletingItemsFromCartPage() {
        addMultipleProductsToCart(ProductCategory.MEN, CartTestData.TARGET_ITEM_COUNT);
        CartPage cartPage = homePage.openCartPage();
        Assert.assertTrue(cartPage.isCartPageLoaded(), "Failed to navigate to Cart page!");

        int initialCount = cartPage.getCartRowCount();
        Assert.assertTrue(initialCount > 0, "Cart should initially contain items!");

        while (cartPage.getCartRowCount() > 0) {
            int previousCount = cartPage.getCartRowCount();

            cartPage.deleteFirstItem();

            int newCount = cartPage.getCartRowCount();
            Assert.assertEquals(newCount, previousCount - 1, "Cart row count should decrease by exactly 1 after item removal.");
        }

        Assert.assertTrue(cartPage.isEmptyCartMessageDisplayed(), "Empty cart message should bse displayed when all items are deleted!");
    }
}