package pages.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;

public class PopUpComponent extends BasePage {


    @FindBy(id = "spicegems_cr_btn_no")
    private WebElement stayOnUsButton;

    @FindBy(css = ".spicegems_cr_modal, #sg_country_redirect_mod")
    private WebElement countryRedirectModal;

    @FindBy(css = "button.klaviyo-close-form, button[class*='klaviyo-close']")
    private WebElement newsletterCloseButton;

    public PopUpComponent(WebDriver driver) {
        super(driver);
    }

    public void dismissPopupIfPresent() {
        dismissCountryPopup();
        dismissNewsletterPopup();
    }


    private void dismissCountryPopup() {
        try {
            elementUtils.click(stayOnUsButton);
            waitUtils.waitForInvisibility(countryRedirectModal);

        } catch (Exception e) {
            System.out.println("Country popup not displayed.");
        }
    }


    private void dismissNewsletterPopup() {
        try {
            elementUtils.click(newsletterCloseButton);
        } catch (Exception e) {
            System.out.println("Newsletter popup not displayed.");
        }
    }
}