package com.spottr.ws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spottr.ws.config.AppConfig;
import com.spottr.ws.data.model.Athlete;
import com.spottr.ws.data.model.Comment;
import com.spottr.ws.data.model.Wod;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AppConfig.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class RoutesIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getWodShouldReturnArrayofWods() throws Exception {

		ResponseEntity<String> responseEntity = restTemplate.exchange("/wod", HttpMethod.GET, null, String.class);
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();

		Wod[] wods = objectMapper.readValue(responseEntity.getBody(), Wod[].class);

		assertThat(statusCode.is2xxSuccessful()).isEqualTo(true);
		assertThat(contentType.getType()).isEqualTo("application");
		assertThat(contentType.getSubtype()).isEqualTo("json");

		assertThat(wods).isNotNull();
		assertThat(wods.length).isNotEqualTo(0);
		assertTrue(wods[0].getAuthorName().equalsIgnoreCase("charles"));
	}

	@Test
	public void postWodShouldCreateNewWod() throws Exception {

		String author = "charles";
		long id = 0L;
		String date = "Friday July 29, 2016";
		String description = "Let's do some mobility & gymnastics";
		long locationId = 1L;
		final String POSTURI = "/wod";

		Wod newWod = new Wod(id, author, date, locationId, description);

		RequestEntity<Wod> requestEntity = RequestEntity.post(new URI(POSTURI)).contentType(MediaType.APPLICATION_JSON)
				.body(newWod);

		ResponseEntity<Wod> responseEntity = restTemplate.exchange(POSTURI, HttpMethod.POST, requestEntity, Wod.class);
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();

		Wod savedWod = responseEntity.getBody();

		assertThat(statusCode.is2xxSuccessful()).isEqualTo(true);
		assertThat(contentType.getType()).isEqualTo("application");
		assertThat(contentType.getSubtype()).isEqualTo("json");

		assertTrue(savedWod.getAuthorName().equals(author));
		assertEquals(id, savedWod.getAuthorId());
		assertTrue(savedWod.getDate().equals(date));
		assertTrue(savedWod.getDescription().equals(description));
		assertEquals(locationId, savedWod.getLocationId());
		assertEquals(0, savedWod.getLikes());
	}

	// @Test
	// public void putWodShouldUpdateASpecificWod() {
	//
	// }

	// @Test
	// public void deleteWodShouldDeleteASpecificWod() {
	//
	// }

	@Test
	public void postWodCommentShouldAddCommentToASpecificWod() throws Exception {

		String author = "Jorge";
		long wodId = 1L;
		String comment = "See you there.";
		final String POSTURI = "/wod/1/comment";

		Comment newComment = new Comment(wodId, author, comment);

		RequestEntity<Comment> requestEntity = RequestEntity.post(new URI(POSTURI))
				.contentType(MediaType.APPLICATION_JSON).body(newComment);

		ResponseEntity<Wod> responseEntity = restTemplate.exchange(POSTURI, HttpMethod.POST, requestEntity, Wod.class);
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();

		Wod wodWithComment = responseEntity.getBody();

		assertThat(statusCode.is2xxSuccessful()).isEqualTo(true);
		assertThat(contentType.getType()).isEqualTo("application");
		assertThat(contentType.getSubtype()).isEqualTo("json");

		assertEquals(author, wodWithComment.getComments().get(0).getAuthorName());
		assertEquals(wodId, wodWithComment.getComments().get(0).getWodId());
		assertEquals(comment, wodWithComment.getComments().get(0).getComment());
	}

	// @Test void putWodCommentShouldUpdateASpecificComment() {
	//
	// }

	// @Test
	// public void deleteWodCommentShouldDeleteASpecificComment() {
	//
	// }

	@Test
	public void putWodLikeShouldIncrementNumLikesForASpecificWod() throws Exception {

		// get the current number of likes
		ResponseEntity<String> response = restTemplate.exchange("/wod", HttpMethod.GET, null, String.class);
		Wod[] wods = objectMapper.readValue(response.getBody(), Wod[].class);

		final int numLikesBefore = wods[0].getLikes();

		// call to increment number of likes
		ResponseEntity<Wod> responseEntity = restTemplate.exchange("/wod/1/like", HttpMethod.PUT, null, Wod.class);
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();

		Wod wodWithLike = responseEntity.getBody();

		assertThat(statusCode.is2xxSuccessful()).isEqualTo(true);
		assertThat(contentType.getType()).isEqualTo("application");
		assertThat(contentType.getSubtype()).isEqualTo("json");

		assertEquals(numLikesBefore + 1, wodWithLike.getLikes());
	}

	@Test
	public void postWodAttendeeShouldAddAttendeeToASpecificWod() throws Exception {

		// verify the current state of the wod
		ResponseEntity<String> response = restTemplate.exchange("/wod", HttpMethod.GET, null, String.class);
		Wod[] wods = objectMapper.readValue(response.getBody(), Wod[].class);
		assertThat(wods[0].getAttendees().isEmpty()).isEqualTo(true);

		// call to add attendee
		long newAttendeeAthleteId = 1L;
		ResponseEntity<Wod> responseEntity = restTemplate.exchange("/wod/1/attendee/" + newAttendeeAthleteId,
				HttpMethod.PUT, null, Wod.class);
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();

		Wod wodWithAttendee = responseEntity.getBody();

		assertThat(statusCode.is2xxSuccessful()).isEqualTo(true);
		assertThat(contentType.getType()).isEqualTo("application");
		assertThat(contentType.getSubtype()).isEqualTo("json");

		assertThat(wodWithAttendee.getAttendees().isEmpty()).isEqualTo(false);
		Athlete addedAttendee = wodWithAttendee.getAttendees().get(0);
		assertThat(addedAttendee.getId()).isEqualTo(newAttendeeAthleteId);

	}

}
