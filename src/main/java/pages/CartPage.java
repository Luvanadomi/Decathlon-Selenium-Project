package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PriceUtils;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class CartPage extends BasePage {

    @FindBy(css = "tbody[role='rowgroup'] tr.cart-items__table-row")
    private List<WebElement> cartRows;

    @FindBy(css = ".cart-page--empty, .cart-title h1, h1.h4")
    private List<WebElement> emptyCartMessages;

    @FindBy(css = "td[headers='productTotal']")
    private List<WebElement> itemPrices;

    @FindBy(css = "[data-testid='cart-total-value']")
    private List<WebElement> orderTotalElements;

    @FindBy(css = ".cart-items__quantity-controls button[name='plus']")
    private List<WebElement> lineItemPlusButtons;

    @FindBy(css = ".cart-items__quantity-controls button[name='minus']")
    private List<WebElement> lineItemMinusButtons;

    @FindBy(css = ".cart-items__quantity-controls input[name='updates[]']")
    private List<WebElement> quantityInputs;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartPageLoaded() {
        return driver.getCurrentUrl().contains("/cart");
    }

    public List<Double> getItemPrices() {
        waitUtils.waitForVisibilityOfAll(itemPrices);
        return itemPrices.stream()
                .map(elementUtils::getText)
                .map(PriceUtils::extractPrice)
                .filter(Objects::nonNull)
                .toList();
    }

    public double getLineItemSubtotal(int index) {
        return getItemPrices().get(index);
    }

    public double getItemUnitPrice(int index) {
        int currentQuantity = getLineItemQuantity(index);
        return getLineItemSubtotal(index) / currentQuantity;
    }

    public int getLineItemQuantity(int index) {
        waitUtils.waitForVisibilityOfAll(quantityInputs);
        return readCurrentQuantity(quantityInputs.get(index));
    }

    public void updateLineItemQuantity(int index, int targetQuantity) {
        double unitPrice = getItemUnitPrice(index);
        int currentQuantity = getLineItemQuantity(index);

        setQuantityTo(index, quantityInputs.get(index), currentQuantity, targetQuantity);
        waitForSubtotalToReach(index, unitPrice * targetQuantity);
    }

    public double getOrderTotal() {
        new WebDriverWait(driver, Duration.ofSeconds(8)).until(d -> !orderTotalElements.isEmpty());

        String value = elementUtils.getText(orderTotalElements.get(0));
        Double total = PriceUtils.extractPrice(value);

        if (total == null) {
            throw new IllegalStateException("Cannot extract order total from: " + value);
        }

        return total;
    }

    public void deleteFirstItem() {
        List<WebElement> rowsBefore = cartRows;

        if (!rowsBefore.isEmpty()) {
            int initialSize = rowsBefore.size();
            WebElement removeBtn = rowsBefore.get(0).findElement(By.cssSelector("button.cart-items__remove"));
            elementUtils.click(removeBtn);
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> initialSize > getCartRowCount());
        }
    }

    public int getCartRowCount() {
        return driver.findElements(By.cssSelector("tbody[role='rowgroup'] tr.cart-items__table-row")).size();
    }

    public boolean isEmptyCartMessageDisplayed() {
        for (WebElement message : emptyCartMessages) {
            try {
                if (elementUtils.isDisplayed(message) && message.getText().toLowerCase().contains("empty")) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    private int readCurrentQuantity(WebElement qtyInput) {
        String rawValue = elementUtils.getAttribute(qtyInput, "value");
        if (rawValue == null || rawValue.isBlank()) {
            return 1;
        }
        return Integer.parseInt(rawValue);
    }

    private void setQuantityTo(int index, WebElement qtyInput, int currentQuantity, int targetQuantity) {
        if (currentQuantity < targetQuantity && !lineItemPlusButtons.isEmpty()) {
            while (currentQuantity++ < targetQuantity) {
                waitUtils.waitForVisibilityOfAll(lineItemPlusButtons);
                elementUtils.click(lineItemPlusButtons.get(index));
            }
        } else if (currentQuantity > targetQuantity && !lineItemMinusButtons.isEmpty()) {
            while (currentQuantity-- > targetQuantity) {
                waitUtils.waitForVisibilityOfAll(lineItemMinusButtons);
                elementUtils.click(lineItemMinusButtons.get(index));
            }
        } else {
            elementUtils.sendKeys(qtyInput, String.valueOf(targetQuantity));
            qtyInput.sendKeys(Keys.ENTER);
        }
    }

    private void waitForSubtotalToReach(int index, double expectedSubtotal) {
        String expectedSubtotalStr = String.format("%.2f", expectedSubtotal);
        waitUtils.waitForTextToContain(itemPrices.get(index), expectedSubtotalStr, 8);
    }
}