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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
	
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	
	public Athlete(){}
	
	public Athlete(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	@Override
    public String toString() {
        return String.format(
                "Athlete[id=%d, firstName='%s', email='%s']",
                id, firstName, email);
    }
}
