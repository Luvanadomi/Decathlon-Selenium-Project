package pages.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;

public class RebuySliderComponent extends BasePage {

    @FindBy(css = "div[ref='cartBubble'].cart-bubble")
    private WebElement cartBadge;

    @FindBy(css = "div#rebuy-cart.rebuy-cart")
    private WebElement flyout;

    @FindBy(css = "button#rebuy-cart-close.rebuy-cart__flyout-close")
    private WebElement closeButton;

    public RebuySliderComponent(WebDriver driver) {
        super(driver);
    }

    public int getBadgeCount() {
        if (!elementUtils.isDisplayed(cartBadge)) return 0;
        String text = elementUtils.getText(cartBadge).replaceAll("[^0-9]", "");
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    public void waitForBadgeCount(int expectedCount, int timeoutInSeconds) {
        waitUtils.waitForTextToContain(cartBadge, String.valueOf(expectedCount), timeoutInSeconds);
    }

    public void closeFlyoutIfPresent() {
        if (!elementUtils.isDisplayed(flyout)) {
            return;
        }

        try {
            jsUtils.click(closeButton);
        } catch (Exception e) {
            return;
        }

        try {
            waitUtils.waitForInvisibility(flyout);
        } catch (Exception ignored) {
        }
    }

}