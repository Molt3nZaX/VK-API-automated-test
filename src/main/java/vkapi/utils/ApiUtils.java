package vkapi.utils;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class ApiUtils {
    public static HttpResponse<JsonNode> getRequest(String url) {
        return Unirest.get(url).asJson();
    }

    public static HttpResponse<JsonNode> postRequest(String url) {
        return Unirest.post(url).asJson();
    }

    public static HttpRequestWithBody postRequestWithBody(String url) {
        return Unirest.post(url);
    }
}