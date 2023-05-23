package vkapi.forms;

import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class WallForm extends Form {
    private ILabel wall = getElementFactory().getLabel(By.xpath("//div[contains(@id,'wall_posts')]"), "Wall");
    private ILabel unshownLatestPost = wall.findChildElement(By.xpath("//div[contains(@class, 'unshown')]"), "Unshown(deleted) post", ElementType.LABEL);
    private ILabel lastestPostText = wall.findChildElement(By.xpath("//div[contains(@class, 'wall_text')]//div"), "Text from latest post", ElementType.LABEL);
    private IElement commentsList = wall.findChildElement(By.xpath("//div[contains(@class, 'replies')]"), "Comments list of post", ElementType.LABEL);
    private ILabel firstComment = wall.findChildElement(By.xpath("//div[contains(@class, 'reply_text')]//div"), "First comment text of post", ElementType.LABEL);
    private IButton showNextCommentButton = commentsList.findChildElement(By.xpath("//span[contains(@class, 'label')]"), "\"Show next comment\" button", ElementType.BUTTON);
    private IButton likeButton = wall.findChildElement(By.xpath("//div[contains(@class, 'PostBottomActionContainer')]"), "Like button", ElementType.BUTTON);
    private IButton activeLikeButton = likeButton.findChildElement(By.xpath("//div[contains(@class,'active')]"), "Active Like button", ElementType.BUTTON);

    public WallForm() {
        super(By.xpath("//div[@class='WallLegacy']"), "Wall form");
    }

    public String getTextFromLastPost() {
        lastestPostText.getJsActions().scrollToTheCenter();
        return lastestPostText.getText();
    }

    public String getLatestPostId() {
        lastestPostText.getJsActions().scrollToTheCenter();
        return lastestPostText.getAttribute("id");
    }

    public String getCommentText() {
        firstComment.state().waitForEnabled();
        firstComment.getJsActions().scrollToTheCenter();
        return firstComment.getText();
    }

    public String getCommentsIdAttribute() {
        firstComment.getJsActions().scrollToTheCenter();
        return firstComment.getAttribute("id");
    }

    public void clickOnShowCommentButton() {
        likeButton.getJsActions().scrollToTheCenter();
        showNextCommentButton.click();
    }

    public void clickOnLikeButton() {
        likeButton.getJsActions().scrollToTheCenter();
        likeButton.click();
        activeLikeButton.state().waitForEnabled();
    }

    public boolean postWithUnshownAttributeIsDisplayed() {
        return unshownLatestPost.state().waitForEnabled();
    }
}