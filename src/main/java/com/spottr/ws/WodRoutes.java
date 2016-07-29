package com.spottr.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spottr.ws.data.model.Athlete;
import com.spottr.ws.data.model.Comment;
import com.spottr.ws.data.model.Wod;
import com.spottr.ws.data.repository.AthleteRepository;
import com.spottr.ws.data.repository.CommentRepository;
import com.spottr.ws.data.repository.WodRepository;

@Controller
@RequestMapping("/wod")
public class WodRoutes {

	@Autowired
	WodRepository wods;

	@Autowired
	CommentRepository comments;

	@Autowired
	AthleteRepository athletes;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Wod> getAllWods() {

		return wods.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Wod createNewWod(@RequestBody Wod newWod) {

		return wods.save(newWod);
	}

	@RequestMapping(value = "/{wodId}/comment", method = RequestMethod.POST)
	@ResponseBody
	public Wod createNewWod(@PathVariable long wodId, @RequestBody Comment newComment) {

		Wod w = wods.findById(wodId);
		w.getComments().add(comments.save(newComment));
		return wods.save(w);
	}

	@RequestMapping(value = "/{wodId}/like", method = RequestMethod.PUT)
	@ResponseBody
	public Wod likeAWod(@PathVariable long wodId) {

		Wod w = wods.findById(wodId);
		w.setLikes(w.getLikes() + 1);
		return wods.save(w);
	}

	@RequestMapping(value = "/{wodId}/attendee/{athleteId}", method = RequestMethod.PUT)
	@ResponseBody
	public Wod addAttendee(@PathVariable long wodId, @PathVariable long athleteId) {

		Wod w = wods.findById(wodId);

		// verify the athlete exists
		Athlete a = athletes.findById(athleteId);
		w.getAttendees().add(a);

		return wods.save(w);
	}

}
