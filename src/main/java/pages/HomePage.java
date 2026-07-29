package pages;

import base.BasePage;
import constants.AppUrls;
import constants.ProductCategory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import pages.components.PopUpComponent;

public class HomePage extends BasePage {

    @FindBy(css = ".header__column--right button[aria-label='Search']")
    private WebElement searchIconButton;

    @FindBy(id = "cmdk-input")
    private WebElement searchBox;

    @FindBy(css = "a[href='/collections/mens']")
    private WebElement menCategoryMenu;

    @FindBy(css = "a[href='/collections/mens-jackets']")
    private WebElement menJacketsSubMenu;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public DisplayedResultsPage searchForProduct(String searchTerm) {
        elementUtils.click(searchIconButton);
        elementUtils.sendKeys(searchBox, searchTerm);
        searchBox.sendKeys(Keys.ENTER);

        return new DisplayedResultsPage(driver);
    }
    public CategoryProductListingPage hoverMenAndSelectJackets() {
        Actions actions = new Actions(driver);
        actions.moveToElement(menCategoryMenu).perform();
        waitUtils.waitForVisibility(menJacketsSubMenu);
        elementUtils.click(menJacketsSubMenu);

        return new CategoryProductListingPage(driver);
    }

    public CategoryProductListingPage openCategory(ProductCategory category) {
        driver.get(AppUrls.getUrl(category));
        new PopUpComponent(driver).dismissPopupIfPresent();
        return new CategoryProductListingPage(driver);
    }
    
    public CartPage openCartPage() {
        driver.get(AppUrls.CART_URL);
        return new CartPage(driver);
    }
}