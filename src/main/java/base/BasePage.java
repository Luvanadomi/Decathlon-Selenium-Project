package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtils;
import utils.JavaScriptUtils;
import utils.WaitUtils;

public class BasePage {
    protected WebDriver driver;
    protected ElementUtils elementUtils;
    protected WaitUtils waitUtils;
    protected JavaScriptUtils jsUtils;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new ElementUtils(driver);
        this.waitUtils = new WaitUtils(driver);
        this.jsUtils = new JavaScriptUtils(driver);

        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}