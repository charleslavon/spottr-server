package com.nema.spottr.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Wod {

	private String id;
	private Athlete author;
	private String description;
	private Location location;
	private List<String> attendees;
	private List<Comment> comments;
	private int likes;
	private LocalDateTime date;
	
	public Wod(String id, Athlete author, Location location, 
			LocalDateTime date, String description) {
		this.id = id;
		this.author = author;
		this.location = location;
		this.date = date;
		this.description = description;
	}
}
