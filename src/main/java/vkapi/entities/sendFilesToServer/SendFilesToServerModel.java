package vkapi.entities.sendFilesToServer;

import lombok.Data;

@Data
public class SendFilesToServerModel {
    private int server;
    private String photo;
    private String hash;
}