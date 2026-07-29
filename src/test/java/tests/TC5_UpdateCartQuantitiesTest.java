package tests;

import base.BaseTest;
import constants.ProductCategory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;

public class TC5_UpdateCartQuantitiesTest extends BaseTest {

    private static final double PRICE_DELTA = 0.05;

    @Test
    public void shouldUpdateFirstLineItemQuantityAndVerifyCartTotals() {
        addMultipleProductsToCart(ProductCategory.MEN, 3);
        CartPage cartPage = homePage.openCartPage();
        Assert.assertTrue(cartPage.isCartPageLoaded(), "Cart page failed to load!");

        double initialOrderTotal = cartPage.getOrderTotal();
        double unitPrice = cartPage.getItemUnitPrice(0);
        int originalQuantity = cartPage.getLineItemQuantity(0);
        int increasedQuantity = originalQuantity + 1;

        cartPage.updateLineItemQuantity(0, increasedQuantity);

        double expectedIncreasedSubtotal = unitPrice * increasedQuantity;
        double actualIncreasedSubtotal = cartPage.getLineItemSubtotal(0);
        Assert.assertEquals(actualIncreasedSubtotal, expectedIncreasedSubtotal, PRICE_DELTA, "First line item subtotal mismatch after quantity increase!");

        double expectedIncreasedOrderTotal = initialOrderTotal + unitPrice * (increasedQuantity - originalQuantity);
        double actualIncreasedOrderTotal = cartPage.getOrderTotal();
        Assert.assertEquals(actualIncreasedOrderTotal, expectedIncreasedOrderTotal, PRICE_DELTA, "Order Total mismatch after quantity increase!");

        cartPage.updateLineItemQuantity(0, originalQuantity);

        double expectedRestoredSubtotal = unitPrice * originalQuantity;
        double restoredLineSubtotal = cartPage.getLineItemSubtotal(0);
        Assert.assertEquals(restoredLineSubtotal, expectedRestoredSubtotal, PRICE_DELTA, "First line item subtotal mismatch after restoring quantity!");

        double restoredOrderTotal = cartPage.getOrderTotal();
        Assert.assertEquals(restoredOrderTotal, initialOrderTotal, PRICE_DELTA, "Order Total mismatch after restoring quantity!");
    }
}