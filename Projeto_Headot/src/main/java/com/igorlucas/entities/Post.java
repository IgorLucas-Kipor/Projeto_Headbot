package com.igorlucas.entities;

import lombok.Data;

@Data
public class Post {
	
	public Post() {}
	
	public Post(long userId, long id, String title, String body) {
		super();
		this.userId = userId;
		this.id = id;
		this.title = title;
		this.body = body;
	}

	private long userId;
	
	private long id;
	
	private String title;
	
	private String body;

	@Override
	public String toString() {
		return "Post [userId=" + userId + ", id=" + id + ", title=" + title + ", body=" + body + "]";
	}

}
