package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {

    private final JavascriptExecutor js;

    public JavaScriptUtils(WebDriver driver) {
        this.js = (JavascriptExecutor) driver;
    }

    //used for hidden and not displayed elements
    public void click(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    public void setInputValue(WebElement element, String value) {
        js.executeScript("arguments[0].value = arguments[1];" + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" + "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", element, value);
    }
}