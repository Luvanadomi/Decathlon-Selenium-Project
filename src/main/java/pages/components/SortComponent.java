package pages.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;

public class SortComponent extends BasePage {

    @FindBy(css = "summary.facets__summary[aria-controls*='sorting-options']")
    private WebElement sortToggle;

    @FindBy(xpath = "//label[contains(@for, 'price-ascending')]")
    private WebElement priceLowToHighOption;

    @FindBy(xpath = "//label[contains(@for, 'price-descending')]")
    private WebElement priceHighToLowOption;

    public SortComponent(WebDriver driver) {
        super(driver);
    }

    public void selectOption(String optionText) {
        clickSafely(sortToggle);
        switch (optionText.toLowerCase()) {
            case "price: low to high" -> clickSafely(priceLowToHighOption);
            case "price: high to low" -> clickSafely(priceHighToLowOption);
            default -> throw new IllegalArgumentException("Unsupported sort option: " + optionText);
        }
        closeDropdownIfOpen();
    }

    private void closeDropdownIfOpen() {
        if ("true".equals(sortToggle.getAttribute("aria-expanded"))) {
            clickSafely(sortToggle);
        }
    }

    private void clickSafely(WebElement element) {
        try {
            elementUtils.click(element);
        } catch (Exception e) {
            jsUtils.click(element);
        }
    }
}