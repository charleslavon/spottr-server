package com.spottr.ws.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spottr.ws.data.model.Wod;

public interface WodRepository extends CrudRepository<Wod, Long> {

	List<Wod> findAll();
	Wod findById(long wodId);
	List<Wod> findByAuthorId(long authorId);
	List<Wod> findByAuthorName(String authorName);
}
