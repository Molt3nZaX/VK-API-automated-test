package vkapi.entities.wallPostResponse;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Response {
    @SerializedName("post_id")
    private int postId;
}