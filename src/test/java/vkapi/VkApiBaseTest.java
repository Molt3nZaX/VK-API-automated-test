package vkapi;

import aquality.selenium.browser.Browser;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public abstract class VkApiBaseTest {
    protected JsonSettingsFile vKAuthorizationDataFile = new JsonSettingsFile("vkAuthorizationData.json");

    @BeforeMethod
    public void setUp() {
        Browser browser = getBrowser();
        browser.maximize();
        browser.goTo(vKAuthorizationDataFile.getValue("/baseUrl").toString());
        browser.waitForPageToLoad();
    }

    @AfterMethod
    public void tearDown() {
        Browser browser = getBrowser();
        browser.quit();
    }
}