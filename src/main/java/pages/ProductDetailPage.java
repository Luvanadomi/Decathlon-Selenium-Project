package pages;

import base.BasePage;
import constants.AppUrls;
import constants.Patterns;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.components.RebuySliderComponent;

import java.util.List;

public class ProductDetailPage extends BasePage {

    private final RebuySliderComponent cartDrawer;

    @FindBy(css = "div[class*='text-block'] h3, h3")
    private WebElement productTitle;

    @FindBy(css = "button[name='add'], #AddToCart")
    private WebElement addButton;

    @FindBy(css = "[ref='priceContainer'] div:not(.price__hidden) .price, [ref='priceContainer'] .price-item--sale:not(.visually-hidden)")
    private List<WebElement> priceCandidates;

    @FindBy(css = "input[type='radio'][data-option-available='true']")
    private List<WebElement> availableVariants;

    @FindBy(css = "input[type='radio'][data-option-available='false'], input[type='radio'][aria-disabled='true']")
    private List<WebElement> soldOutRadioInputs;

    @FindBy(css = "[id*='BIS_trigger']")
    private WebElement notifyMeButton;

    @FindBy(css = "input[name='quantity'], input.quantity__input")
    private WebElement quantityInput;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
        this.cartDrawer = new RebuySliderComponent(driver);
    }

    public String getProductTitle() {
        return elementUtils.getText(productTitle);
    }

    public String getProductPrice() {
        waitUtils.waitUntilListIsNotEmpty(priceCandidates);
        for (WebElement element : priceCandidates) {
            if (element.isDisplayed()) {
                String text = element.getText().trim();
                if (!text.isEmpty()) {
                    return text;
                }
            }
        }
        throw new NoSuchElementException("No visible price found for product.");
    }

    public boolean isProductPriceValid() {

        return getProductPrice().matches(Patterns.CURRENCY_PATTERN);
    }

    public boolean isAddToCartButtonPresent() {
        return elementUtils.isDisplayed(addButton) && addButton.isEnabled();
    }

    public void selectUnavailableVariant() {
        waitUtils.waitUntilListIsNotEmpty(soldOutRadioInputs);
        jsUtils.click(soldOutRadioInputs.get(0));
    }

    public boolean isNotifyMeButtonDisplayed() {

        return elementUtils.isDisplayed(notifyMeButton);
    }

    public boolean isSoldOutButtonDisplayedAndDisabled() {
        return elementUtils.isDisplayed(addButton) && (waitUtils.waitForDisabled(addButton, 3) || waitUtils.waitForTextToContain(addButton, "sold out", 3));
    }

    public void selectFirstAvailableOption() {
        if (!availableVariants.isEmpty()) {
            jsUtils.click(availableVariants.get(0));
        }
    }

    public void setQuantity(int quantity) {
        waitUtils.waitForVisibility(quantityInput);
        jsUtils.setInputValue(quantityInput, String.valueOf(quantity));
    }

    public void addToCartWithQuantity(int quantity) {
        int badgeBefore = cartDrawer.getBadgeCount();
        selectFirstAvailableOption();
        setQuantity(quantity);
        elementUtils.click(addButton);
        cartDrawer.waitForBadgeCount(badgeBefore + quantity, 3);
    }

    public void closeCartDrawerIfPresent() {
        cartDrawer.closeFlyoutIfPresent();
    }

    public int getCartBadgeCount() {
        return cartDrawer.getBadgeCount();
    }

    public CartPage openCart() {
        driver.get(AppUrls.CART_URL);
        return new CartPage(driver);
    }
}