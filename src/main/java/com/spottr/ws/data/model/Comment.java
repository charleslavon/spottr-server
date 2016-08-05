package com.spottr.ws.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
@Embeddable
public class Comment {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="WOD_ID")
	private long wodId;
	private String authorName;
	private String comment;
	
	public Comment(){}
	
	public Comment(long wodId, String authorName, String comment) {
		this.wodId = wodId;
		this.authorName = authorName;
		this.comment = comment;
	}
	
	@Override
    public String toString() {
        return String.format(
                "Comment[id=%d, wodId=%d, authorName='%s', comment='%s']",
                id, wodId, authorName, comment);
    }
}
