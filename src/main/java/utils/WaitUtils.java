package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public List<WebElement> waitForVisibilityOfAll(List<WebElement> elements) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public List<WebElement> waitUntilListIsNotEmpty(List<WebElement> elements) {
        return wait.until(driver -> elements.isEmpty() ? null : elements);
    }

    public boolean waitForInvisibility(WebElement element) {
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public boolean waitForInvisibilityOfAll(List<WebElement> elements) {
        return wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
    }

    public boolean waitForTextToContain(WebElement element, String text, int timeoutInSeconds) {
        try {
            return newWait(timeoutInSeconds).until(d -> element.getText().toLowerCase().contains(text.toLowerCase()));
        } catch (Exception e) {
            return false;
        }
    }
    public boolean waitForDisabled(WebElement element, int timeoutInSeconds) {
        try {
            return newWait(timeoutInSeconds).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element)));
        } catch (Exception e) {
            return false;
        }
    }

    private WebDriverWait newWait(int timeoutInSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }
}