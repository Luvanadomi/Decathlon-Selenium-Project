package tests;

import base.BaseTest;
import constants.CartTestData;
import constants.ProductCategory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductDetailPage;

public class TC4_AddToCartTest extends BaseTest {

    @Test
    public void shouldAddThreeDistinctProductsAndVerifyCartTotals() {
        ProductDetailPage productPage = addMultipleProductsToCart(ProductCategory.MEN, CartTestData.TARGET_ITEM_COUNT);

        int actualBadgeCount = productPage.getCartBadgeCount();
        Assert.assertEquals(actualBadgeCount, CartTestData.TARGET_ITEM_COUNT, String.format("Cart badge expected to be %d, but was %d.", CartTestData.TARGET_ITEM_COUNT, actualBadgeCount));

        CartPage cartPage = productPage.openCart();
        Assert.assertTrue(cartPage.isCartPageLoaded(), "Failed to navigate to Cart page! Current URL: " + cartPage.isCartPageLoaded());

        int lineItemCount = cartPage.getItemPrices().size();
        Assert.assertEquals(lineItemCount, CartTestData.TARGET_ITEM_COUNT, String.format("Expected %d distinct line items in the cart, but found %d.", CartTestData.TARGET_ITEM_COUNT, lineItemCount));


    }
}