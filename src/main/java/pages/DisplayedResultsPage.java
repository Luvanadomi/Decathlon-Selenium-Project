package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DisplayedResultsPage extends ProductListingPage {

    @FindBy(css = "input[type='search']")
    private WebElement searchInput;

    @FindBy(css = ".search-results__no-results")
    private WebElement noResultsMessage;

    public DisplayedResultsPage(WebDriver driver) {
        super(driver);
    }

    public String getSearchInputValue() {
        return elementUtils.getAttribute(searchInput, "value");
    }

    public boolean isNoResultsMessageDisplayed() {
        return elementUtils.isDisplayed(noResultsMessage);
    }
}