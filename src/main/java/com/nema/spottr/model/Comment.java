package com.nema.spottr.model;

import lombok.Data;

@Data
public class Comment {

	private String id;
	private String author;
	private String comment;
	
	public Comment(String id, String author, String comment) {
		this.id = id;
		this.author = author;
		this.comment = comment;
	}
}
