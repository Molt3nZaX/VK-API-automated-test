package vkapi.forms.pages;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MyHomePage extends Form {
    public MyHomePage() {
        super(By.xpath("//div[@class='ProfileHeaderButton']"), "My page");
    }
}