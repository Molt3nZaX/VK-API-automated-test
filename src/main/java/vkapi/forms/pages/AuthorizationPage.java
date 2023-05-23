package vkapi.forms.pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AuthorizationPage extends Form {
    private final ITextBox emailTextBox = getElementFactory().getTextBox(By.id("index_email"), "Email text box");
    private final IButton singInButton = getElementFactory().getButton(By.xpath("//button[contains(@type, 'submit')]//span[contains(@class, 'FlatButton')]"), "\"Sing in\" button");

    public AuthorizationPage() {
        super(By.id("index_email"), "Authorization page");
    }

    public void clickOnSingInButton() {
        singInButton.click();
    }

    public void fillEmailTextBox(String email) {
        emailTextBox.sendKeys(email);
    }
}