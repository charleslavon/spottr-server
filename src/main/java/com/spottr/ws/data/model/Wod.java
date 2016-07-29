package com.spottr.ws.data.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.Data;

@Entity
@Data
public class Wod {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="AUTHOR_ATHLETE_ID")
	private long authorId;
	
	@Column(name="AUTHOR_ATHLETE_NAME")
	private String authorName;
	
	@Column(name="WOD_LOCATION_ID")
	private long locationId;
	
	private String description;
	private int likes;
	private String date;
	
	@ElementCollection
	@CollectionTable(
		 name="WOD_ATTENDEES",
         joinColumns=@JoinColumn(name="WOD_ID")
	)
	@Column(name="ATTENDEES")
	private List<Athlete> attendees;
	
	@ElementCollection
	@CollectionTable(
        name="WOD_COMMENTS",
        joinColumns=@JoinColumn(name="WOD_ID")
    )
	private List<Comment> comments;
	
	
	public Wod(){}
	
	public Wod(long authorId, String authorName, 
			String date, long locationId, String description) {
		this.authorId = authorId;
		this.authorName = authorName;
		this.locationId = locationId;
		this.date = date;
		this.description = description;
	}
	
	@Override
    public String toString() {
        return String.format(
                "Wod[id=%d, authorName='%s', description='%s', date='%s']",
                id, authorName, description, date);
    }

}
