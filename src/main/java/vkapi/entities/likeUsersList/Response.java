package vkapi.entities.likeUsersList;

import lombok.Data;

import java.util.List;

@Data
public class Response{
	private int count;
	private List<Integer> items;
}