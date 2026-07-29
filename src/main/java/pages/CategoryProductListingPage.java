package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.components.PriceFilterComponent;
import pages.components.SortComponent;

import java.util.List;

public class CategoryProductListingPage extends ProductListingPage {

    private final SortComponent sort;
    private final PriceFilterComponent priceFilter;

    public CategoryProductListingPage(WebDriver driver) {
        super(driver);
        this.sort = new SortComponent(driver);
        this.priceFilter = new PriceFilterComponent(driver);
    }

    public void selectColorFilter(String color) {
        String xpath = String.format("//input[@type='checkbox' and @aria-label='%s']/ancestor::label[contains(@class,'variant-option__button-label')][1]", color);
        WebElement colorFilterTarget = driver.findElement(By.xpath(xpath));
        clickElementSafely(colorFilterTarget);
        waitForGridRefresh();
    }

    public boolean areAllDisplayedProductsMatchingColor(String color) {

        List<WebElement> cards = getProductCardsSafely();
        if (cards.isEmpty()) {
            return false;
        }
        return cards.stream().allMatch(card -> {
            try {
                WebElement selectedColor = card.findElement(By.cssSelector("input[type='radio'][aria-label]:checked"));
                String productColor = selectedColor.getAttribute("aria-label");
                System.out.println("Product color: " + productColor);
                return productColor.toLowerCase().contains(color.toLowerCase());
            } catch (NoSuchElementException e) {
                System.out.println("No selected color found");
                return false;
            }
        });
    }

    public void applyPriceRange(double minimum, double maximum) {
        priceFilter.applyRange(minimum, maximum);
        waitForGridRefresh();
    }

    public boolean areAllProductsWithinPriceRange(double minimum, double maximum) {
        List<Double> prices = getProductPrices();
        if (prices.isEmpty()) return false;
        return prices.stream().allMatch(price -> price >= minimum && price <= maximum);
    }

    public void selectSortOption(String optionText) {
        sort.selectOption(optionText);
        waitForGridRefresh();
    }
}