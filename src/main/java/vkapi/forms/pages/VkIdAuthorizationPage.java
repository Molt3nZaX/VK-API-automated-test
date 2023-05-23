package vkapi.forms.pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class VkIdAuthorizationPage extends Form {
    private ITextBox passwordTextBox = getElementFactory().getTextBox(By.xpath("//input[@name='password']"), "Password text box");
    private IButton continueButton = getElementFactory().getButton(By.xpath("//span[contains(@class,'Button')]"), "Continue button");

    public VkIdAuthorizationPage() {
        super(By.xpath("//input[@name='password']"), "VK ID Authorization page");
    }

    public void clickOnContinueButton() {
        continueButton.state().waitForClickable();
        continueButton.click();
    }

    public void fillPasswordTextBox(String password) {
        passwordTextBox.sendKeys(password);
    }
}