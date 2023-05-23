package vkapi.utils;

import aquality.selenium.core.utilities.JsonSettingsFile;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import vkapi.entities.likeUsersList.LikeUsersListModel;
import vkapi.entities.safeResultOnServer.ResponseItem;
import vkapi.entities.safeResultOnServer.SafeResultOnServerModel;
import vkapi.entities.sendFilesToServer.SendFilesToServerModel;
import vkapi.entities.wallPostResponse.WallPostModel;

import java.io.File;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static vkapi.utils.ApiUtils.*;

public class VkApiUtils {
    static JsonSettingsFile vkApiRequestsData = new JsonSettingsFile("vkApiRequestsData.json");

    public static WallPostModel getWallPostResponse(String randomValue) {
        getLogger().info("Add new post on wall");
        String request = createRequest(vkApiRequestsData.getValue("/wall.post").toString(),
                vkApiRequestsData.getValue("/message").toString() + randomValue);
        HttpResponse<JsonNode> body = postRequestWithBody(request).asJson();
        return JsonUtils.getObjectFromString(body.getBody().toString(), WallPostModel.class);
    }

    public static String getWallUploadServerRequest() {
        getLogger().info("Get wall upload server response");
        HttpResponse<JsonNode> request = getRequest(createRequest(vkApiRequestsData.getValue("/photos.getWallUploadServer").toString(),
                vkApiRequestsData.getValue("/tokenParameter").toString() + vkApiRequestsData.getValue("/tokenValue")));
        return request.getBody().getObject().getJSONObject("response").get("upload_url").toString();
    }

    public static SendFilesToServerModel sendFilesToServerRequest(String uploadUrl, String pathToSendingFile) {
        getLogger().info("Send files to upload server");
        HttpResponse<String> sendPhotoToServerRequest = postRequestWithBody(uploadUrl).field("photo",
                new File(pathToSendingFile)).asString();
        return JsonUtils.getObjectFromString(sendPhotoToServerRequest.getBody(), SendFilesToServerModel.class);
    }

    public static SafeResultOnServerModel safeResultOnServer(SendFilesToServerModel objectFromSendFilesResponse) {
        getLogger().info("Safe files on the server");
        HttpResponse<String> safeWallPhotoRequest = postRequestWithBody(vkApiRequestsData.getValue("/baseApiUrl").toString() +
                vkApiRequestsData.getValue("/photos.saveWallPhoto").toString())
                .field("access_token", vkApiRequestsData.getValue("/tokenValue"))
                .field("photo", objectFromSendFilesResponse.getPhoto())
                .field("server", String.valueOf(objectFromSendFilesResponse.getServer()))
                .field("hash", objectFromSendFilesResponse.getHash())
                .field("v", vkApiRequestsData.getValue("/versionValue").toString())
                .asString();
        return JsonUtils.getObjectFromString(safeWallPhotoRequest.getBody(), SafeResultOnServerModel.class);
    }

    public static HttpResponse<String> editWallPostRequest(int postId, String randomText, ResponseItem responseItem) {
        getLogger().info("Edit wall post");
        String objectForAttachmentsParameter = vkApiRequestsData.getValue("/photoType").toString() + responseItem.getOwnerId() + "_" + responseItem.getId();
        return postRequestWithBody(createRequest(vkApiRequestsData.getValue("/wall.edit").toString(),
                vkApiRequestsData.getValue("/post_id").toString() + postId +
                        vkApiRequestsData.getValue("/message").toString() + randomText +
                        vkApiRequestsData.getValue("/attachments").toString() + objectForAttachmentsParameter)).asString();
    }

    public static HttpResponse<JsonNode> addCommentToPost(String randomValue, int postId) {
        getLogger().info("Adds comment to post");
        String parameters = vkApiRequestsData.getValue("/message").toString() +
                randomValue + vkApiRequestsData.getValue("/post_id") + postId;
        return postRequest(createRequest(vkApiRequestsData.getValue("/wall.createComment").toString(),
                parameters));
    }

    public static LikeUsersListModel getLikeUsersList(int itemId) {
        getLogger().info("Get list of users who liked the post");
        String parameters = vkApiRequestsData.getValue("/type").toString() +
                vkApiRequestsData.getValue("/postType").toString() +
                vkApiRequestsData.getValue("/item_id").toString() + itemId;
        String request = createRequest(vkApiRequestsData.getValue("/likes.getList").toString(), parameters);
        return JsonUtils.getObjectFromString(getRequest(request).getBody().toString(), LikeUsersListModel.class);
    }

    public static HttpResponse<JsonNode> deletePost(int itemId) {
        getLogger().info("Delete last post");
        return getRequest(createRequest(vkApiRequestsData.getValue("/wall.delete").toString(),
                vkApiRequestsData.getValue("/post_id").toString() + itemId));
    }

    private static String createRequest(String method, String parameters) {
        return vkApiRequestsData.getValue("/baseApiUrl").toString() + method + parameters +
                vkApiRequestsData.getValue("/tokenParameter").toString() +
                vkApiRequestsData.getValue("/tokenValue").toString() +
                vkApiRequestsData.getValue("/version").toString() +
                vkApiRequestsData.getValue("/versionValue").toString();
    }
}