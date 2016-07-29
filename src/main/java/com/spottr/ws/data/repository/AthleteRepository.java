package com.spottr.ws.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.spottr.ws.data.model.Athlete;


public interface AthleteRepository extends CrudRepository<Athlete, Long> {
	
	List<Athlete> findAll();

    Athlete findById(long id);
    
    List<Athlete> findByFirstName(String firstName);
    
    List<Athlete> findByLastName(String lastName);
    
    List<Athlete> findByLastNameAndLastName(String firstName, String lastName);
    
    List<Athlete> findByEmail(String email);
    
    @SuppressWarnings("unchecked")
	Athlete save(Athlete athlete);
    
}
