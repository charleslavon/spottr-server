package com.spottr.ws.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Location {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private double latitude;
	private double longitude;
	private String name;
	private String webLink;
	
	public Location(){}
	
	public Location(String name, double latitude, double longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
    public String toString() {
        return String.format(
                "Location[id=%d, name='%s', latitude='%s', longitude='%s']",
                id, name, latitude, longitude);
    }

}
