package com.spottr.ws.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spottr.ws.data.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	Comment findById(long id);
	List<Comment> findAll();
	List<Comment> findByWodId(long wod_id);

}
