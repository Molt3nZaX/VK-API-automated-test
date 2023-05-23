package vkapi.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class SideMenuForm extends Form {
    private IButton myPage = getElementFactory().getButton(By.xpath("//li[contains(@id, 'pr')]"), "\"My Page\" button");

    public SideMenuForm() {
        super(By.xpath("//ol[contains(@class, 'side')]"), "Side menu");
    }

    public void clickOnMyPageButton() {
        myPage.state().waitForEnabled();
        myPage.click();
    }
}