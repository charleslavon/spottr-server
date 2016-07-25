package com.spottr.ws.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Athlete {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	private String name;
	private String email;
	private String phoneNumber;
	
	public Athlete(){}
	
	public Athlete(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	@Override
    public String toString() {
        return String.format(
                "Athlete[id=%d, name='%s', email='%s']",
                id, name, email);
    }
}
