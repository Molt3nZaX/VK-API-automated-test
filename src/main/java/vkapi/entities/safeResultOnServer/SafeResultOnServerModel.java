package vkapi.entities.safeResultOnServer;

import lombok.Data;

import java.util.List;

@Data
public class SafeResultOnServerModel {
    private List<ResponseItem> response;
}