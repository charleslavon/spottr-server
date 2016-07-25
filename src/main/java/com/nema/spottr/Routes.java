package com.nema.spottr;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nema.spottr.model.*;

@Controller
@RequestMapping("/wod")
public class Routes {
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
    public Wod sample(@RequestParam(value="description", required=true, defaultValue="Fran") String description,
    					@RequestParam(value="id", required=true) String id,
    					@RequestParam(value="author", required=true) String author) {
		
		Location location = new Location();
		Athlete athlete = new Athlete("123", author, "charles@gmail.com");
		
        return new Wod(id, athlete, location, LocalDateTime.now(), description);
    }

}
