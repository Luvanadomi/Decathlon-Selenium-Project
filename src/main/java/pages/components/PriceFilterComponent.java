package pages.components;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;

import java.util.List;

public class PriceFilterComponent extends BasePage {

    @FindBy(css = "[data-testid='price-filter']")
    private List<WebElement> priceAccordions;

    @FindBy(css = "input[name='filter.v.price.gte']")
    private List<WebElement> minPriceInputs;

    @FindBy(css = "input[name='filter.v.price.lte']")
    private List<WebElement> maxPriceInputs;



    public PriceFilterComponent(WebDriver driver) {
        super(driver);
    }

    public void applyRange(double minimum, double maximum) {
        try {
            if (!priceAccordions.isEmpty() && (minPriceInputs.isEmpty() || !minPriceInputs.get(0).isDisplayed())) {
                elementUtils.click(priceAccordions.get(0));
            }
            if (!minPriceInputs.isEmpty() && !maxPriceInputs.isEmpty()) {
                WebElement minInput = waitUtils.waitForVisibility(minPriceInputs.get(0));
                WebElement maxInput = waitUtils.waitForVisibility(maxPriceInputs.get(0));

                jsUtils.setInputValue(minInput, String.valueOf(minimum));
                jsUtils.setInputValue(maxInput, String.valueOf(maximum));
                maxInput.sendKeys(Keys.ENTER);
            }
        } catch (Exception ignored) {
        }
    }


}