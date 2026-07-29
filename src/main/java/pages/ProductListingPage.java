package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.PriceUtils;

import java.util.List;
import java.util.Objects;

public abstract class ProductListingPage extends BasePage {

    @FindBy(css = "product-card")
    protected List<WebElement> productCards;

    protected ProductListingPage(WebDriver driver) {
        super(driver);
    }

    public int getProductCount() {
        return getProductCardsSafely().size();
    }

    public List<String> getProductTitles() {
        return getProductCardsSafely().stream()
                .map(card -> card.findElement(By.cssSelector("p[role='heading']")).getDomProperty("textContent"))
                .filter(Objects::nonNull)
                .map(String::strip)
                .filter(text -> !text.isEmpty())
                .toList();
    }

    public List<Double> getProductPrices() {
        return getProductCardsSafely().stream()
                .map(this::extractCardPriceText)
                .map(PriceUtils::extractPrice)
                .filter(Objects::nonNull)
                .toList();
    }

    public ProductDetailPage clickProductAtIndex(int index) {
        List<WebElement> cards = getProductCardsSafely();
        if (cards.size() <= index) {
            throw new IllegalArgumentException("Index " + index + " out of bounds for " + cards.size() + " products.");
        }
        elementUtils.click(cards.get(index));
        return new ProductDetailPage(driver);
    }

    protected List<WebElement> getProductCardsSafely() {
        try {
            return waitUtils.waitForVisibilityOfAll(productCards);
        } catch (StaleElementReferenceException e) {
            return waitUtils.waitForVisibilityOfAll(productCards);
        }
    }

    protected void waitForGridRefresh() {
        if (!productCards.isEmpty()) {
            try {
                waitUtils.waitForInvisibilityOfAll(productCards);
            } catch (Exception ignored) {
            }
        }
        waitUtils.waitForVisibilityOfAll(productCards);
    }

    protected void clickElementSafely(WebElement element) {
        try {
            elementUtils.click(element);
        } catch (Exception e) {
            jsUtils.click(element);
        }
    }

    private String extractCardPriceText(WebElement product) {
        try {
            List<WebElement> salePrices = product.findElements(By.cssSelector(".price-item--sale, .price-item--last"));
            if (!salePrices.isEmpty() && salePrices.get(0).isDisplayed()) {
                return salePrices.get(0).getText();
            }
            return product.findElement(By.cssSelector("product-price, .price-item, .price")).getText();
        } catch (Exception e) {
            return null;
        }
    }
}