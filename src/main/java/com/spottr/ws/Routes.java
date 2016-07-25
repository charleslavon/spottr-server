package com.spottr.ws;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spottr.ws.data.model.*;

@Controller
@RequestMapping("/")
public class Routes {
	
	
	@RequestMapping(value="wod", method=RequestMethod.GET)
	@ResponseBody
    public List<Wod> getAllWods() {
		
		return null;
    }
	
	@RequestMapping(value="athlete", method=RequestMethod.GET)
	@ResponseBody
    public List<Athlete> getAllAthletes() {
		
		return null;
    }
	
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
    public Wod sample(@RequestParam(value="description", required=true, defaultValue="Fran") String description,
    					@RequestParam(value="author", required=true) String author) {
		
		Location location = new Location();
		Athlete athlete = new Athlete(author, "charles@gmail.com");
		
        return new Wod(athlete.getId(), athlete.getName(), LocalDateTime.now(), location.getId(), description);
    }

}
