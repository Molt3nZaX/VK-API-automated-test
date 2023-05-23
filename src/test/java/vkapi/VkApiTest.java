package vkapi;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.Test;
import vkapi.entities.safeResultOnServer.ResponseItem;
import vkapi.entities.safeResultOnServer.SafeResultOnServerModel;
import vkapi.entities.wallPostResponse.WallPostModel;
import vkapi.forms.SideMenuForm;
import vkapi.forms.WallForm;
import vkapi.forms.pages.AuthorizationPage;
import vkapi.forms.pages.MyHomePage;
import vkapi.forms.pages.VkIdAuthorizationPage;
import vkapi.utils.ImageComparatorUtils;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static aquality.selenium.browser.AqualityServices.getLogger;
import static org.testng.Assert.*;
import static vkapi.utils.RandomTestDataUtils.generateRandomText;
import static vkapi.utils.VkApiUtils.*;

public class VkApiTest extends VkApiBaseTest {
    @Test(testName = "VK API test")
    public void vkApiTest() {
        getLogger().info("Step 1,2: [UI] Navigate to 'https://vk.com' and authorization");
        AuthorizationPage authPage = new AuthorizationPage();
        JsonSettingsFile vkAuthorizationDataFile = new JsonSettingsFile("vkAuthorizationData.json");
        authPage.fillEmailTextBox(vkAuthorizationDataFile.getValue("/email").toString());
        authPage.clickOnSingInButton();
        VkIdAuthorizationPage vkIdAuthorizationPage = new VkIdAuthorizationPage();
        vkIdAuthorizationPage.fillPasswordTextBox(vkAuthorizationDataFile.getValue("/password").toString());
        vkIdAuthorizationPage.clickOnContinueButton();

        getLogger().info("Step 3: [UI] Navigate to \"My homepage\"");
        SideMenuForm sideMenu = new SideMenuForm();
        sideMenu.clickOnMyPageButton();

        getLogger().info("Step 4: [API] Create a post with randomly generated wall text and get the post id from the response.");
        MyHomePage myHomePage = new MyHomePage();
        myHomePage.state().waitForDisplayed();
        String firstRandomText = generateRandomText();
        WallPostModel wallPost = getWallPostResponse(firstRandomText);

        getLogger().info("Step 5: [UI] Without refreshing the page, make sure that a post with the right text from the right user appeared on the wall.");
        WallForm wallForm = new WallForm();
        ISettingsFile vkApiRequestsDataFile = new JsonSettingsFile("vkApiRequestsData.json");
        assertEquals(wallForm.getTextFromLastPost(), firstRandomText, "Text in post is incorrect");
        assertTrue(wallForm.getLatestPostId().contains(vkApiRequestsDataFile.getValue("/user_id").toString()),
                "The text in the post was written by another user");

        getLogger().info("Step 6: [API] Edit a post via an API request - change the text and upload any image.");
        String pathToTestPicture = "src/test/resources/testPicture.jpg";
        SafeResultOnServerModel safeResultOnServerModel = safeResultOnServer(sendFilesToServerRequest(getWallUploadServerRequest(), pathToTestPicture));
        ResponseItem responseItem1 = safeResultOnServerModel.getResponse().get(0);
        String secondRandomText = generateRandomText();
        int postId = wallPost.getResponse().getPostId();
        editWallPostRequest(postId, secondRandomText, responseItem1);

        getLogger().info("Step 7: [UI] Without refreshing the page, make sure that the text of the message has changed " +
                "and the uploaded picture has been added (make sure that the pictures are the same).");
        assertNotEquals(firstRandomText, wallForm.getTextFromLastPost(), "Text in post is not exchanged");
        byte[] actualPicture = getBrowser().getScreenshot();
        assertTrue(ImageComparatorUtils.comparePicture(actualPicture, pathToTestPicture) < 50,
                "Pictures are not the same");

        getLogger().info("Step 8: [API] Using an API request, add a comment to a post with random text.");
        String thirdRandomText = generateRandomText();
        addCommentToPost(thirdRandomText, postId);

        getLogger().info("Step 9: [UI] Without refreshing the page, make sure that a comment from the correct user has been added" +
                " to the desired entry.");
        wallForm.clickOnShowCommentButton();
        assertEquals(wallForm.getCommentText(), thirdRandomText, "Comment with random value is not added");
        assertTrue(wallForm.getCommentsIdAttribute().contains(vkApiRequestsDataFile.getValue("/user_id").toString()),
                "Comment adds from unknown user");

        getLogger().info("Step 10: [UI] Like the post via UI.");
        wallForm.clickOnLikeButton();

        getLogger().info("Step 11: [API] Through an API request, make sure that the post has a like from the correct user.");
        assertTrue(getLikeUsersList(postId).getResponse().getItems()
                        .contains(Integer.parseInt(vkApiRequestsDataFile.getValue("/user_id").toString())),
                "Like from the right user did not appear");

        getLogger().info("Step 12: [API] Through an API request, delete the created post");
        deletePost(postId);

        getLogger().info("Step 13: [UI] Without refreshing the page, make sure the post is deleted.");
        assertTrue(wallForm.postWithUnshownAttributeIsDisplayed(), "Latest post is not deleted");
    }
}