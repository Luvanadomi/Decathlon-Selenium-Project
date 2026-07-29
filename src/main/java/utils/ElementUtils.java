package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementUtils {

    private final WaitUtils waitUtils;

    public ElementUtils(WebDriver driver) {
        this.waitUtils = new WaitUtils(driver);
    }

    public void click(WebElement element) {

        waitUtils.waitForClickable(element).click();
    }

    public void sendKeys(WebElement element, String text) {
        waitUtils.waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    public String getText(WebElement element) {

        return waitUtils.waitForVisibility(element).getText();
    }

    public String getAttribute(WebElement element, String attribute) {
        return waitUtils.waitForVisibility(element).getAttribute(attribute);
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return waitUtils.waitForVisibility(element) != null;
        } catch (Exception e) {
            return false;
        }
    }
}