package vkapi.utils;

import com.google.gson.Gson;

public class JsonUtils {
    private static Gson gsonInstance;

    public static Gson getGsonInstance() {
        if (gsonInstance == null) {
            gsonInstance = new Gson();
        }
        return gsonInstance;
    }

    public static <T> T getObjectFromString(String content, Class<T> clazz) {
        return getGsonInstance().fromJson(content, clazz);
    }
}