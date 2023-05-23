package vkapi.entities.safeResultOnServer;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ResponseItem {
    private int date;
    private List<SizesItem> sizes;
    @SerializedName("owner_id")
    private int ownerId;
    @SerializedName("access_key")
    private String accessKey;
    @SerializedName("album_id")
    private int albumId;
    @SerializedName("has_tags")
    private boolean hasTags;
    private int id;
    private String text;
}