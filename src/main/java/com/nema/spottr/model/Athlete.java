package com.nema.spottr.model;

import lombok.Data;

@Data
public class Athlete {

	private String id;
	private String name;
	private String email;
	private String phoneNumber;
	
	public Athlete(String id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
}
