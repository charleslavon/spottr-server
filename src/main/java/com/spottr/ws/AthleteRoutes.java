package com.spottr.ws;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spottr.ws.data.model.Athlete;
import com.spottr.ws.data.repository.AthleteRepository;

@Controller
@RequestMapping("/athlete")
public class AthleteRoutes {

	@Autowired
	AthleteRepository athletes;
	
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Athlete> getAll() {
		
		return athletes.findAll();
	}

	@RequestMapping(value = "/{athleteId}", method = RequestMethod.GET)
	@ResponseBody
	public Athlete getById(@PathVariable long athleteId) {
		
		return athletes.findById(athleteId);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Athlete createNewWod(@RequestBody Athlete athlete) {
		
		return athletes.save(athlete);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Athlete saveWod(@RequestBody Athlete athlete) {

		return athletes.save(athlete);
	}
	
	@RequestMapping(value = "/{athleteId}", method = RequestMethod.DELETE)
	public void deleteWod(@PathVariable long athleteId, HttpServletResponse response) {

		try {
			athletes.delete(athleteId);
			response.setStatus(HttpStatus.OK.value());
		}
		catch(Exception e) {
			response.setStatus(HttpStatus.CONFLICT.value());
		}
	}
	
}
