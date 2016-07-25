package com.spottr.ws.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spottr.ws.data.model.Location;

public interface LocationRepository extends CrudRepository<Location, Long> {
	
	Location findById(long id);
	List<Location> findByName(String name);
	List<Location> findAll();

}
