package com.spottr.ws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
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
import com.spottr.ws.data.repository.AthleteRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AppConfig.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AthleteRoutesIntegrationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AthleteRepository athleteRepository;

	@Test
	public void getAthleteShouldReturnListOfAthletes() {

		ResponseEntity<Athlete[]> responseEntity = restTemplate.exchange("/athlete", HttpMethod.GET, null,
				Athlete[].class);
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();

		Athlete[] athletes = responseEntity.getBody();

		assertThat(statusCode.is2xxSuccessful()).isEqualTo(true);
		assertThat(contentType.getType()).isEqualTo("application");
		assertThat(contentType.getSubtype()).isEqualTo("json");

		assertTrue(athletes.length != 0);
		assertNotNull(athletes[0].getFirstName());
	}

	@Test
	public void getAthleteByIdShouldReturnValidAthete() {

		final long athleteId = 1L;
		ResponseEntity<Athlete> responseEntity = restTemplate.exchange("/athlete/" + athleteId, HttpMethod.GET, null,
				Athlete.class);
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();

		Athlete a = responseEntity.getBody();

		assertThat(statusCode.is2xxSuccessful()).isEqualTo(true);
		assertThat(contentType.getType()).isEqualTo("application");
		assertThat(contentType.getSubtype()).isEqualTo("json");

		assertNotNull(a);
		assertNotNull(a.getFirstName());
		assertThat(a.getId()).isEqualTo(athleteId);
	}

	@Test
	public void postAthleteShouldCreateAthlete() throws Exception {

		String firstName = "cl";
		String lastName = "g";
		String email = "cl@g.com";
		final String POSTURI = "/athlete";

		Athlete newAthlete = new Athlete(firstName, lastName, email);
		RequestEntity<Athlete> requestEntity = RequestEntity.post(new URI(POSTURI))
				.contentType(MediaType.APPLICATION_JSON).body(newAthlete);

		ResponseEntity<Athlete> responseEntity = restTemplate.exchange(POSTURI, HttpMethod.POST, requestEntity,
				Athlete.class);
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();

		Athlete savedAthlete = responseEntity.getBody();

		assertThat(statusCode.is2xxSuccessful()).isEqualTo(true);
		assertThat(contentType.getType()).isEqualTo("application");
		assertThat(contentType.getSubtype()).isEqualTo("json");

		assertTrue(savedAthlete.getFirstName().equals(firstName));
		assertTrue(savedAthlete.getLastName().equals(lastName));
		assertTrue(savedAthlete.getEmail().equals(email));
	}

	@Test
	public void putAthleteShouldUpdateASpecificAthlete() throws Exception {

		final String PUTURI = "/athlete";
		Athlete athlete = athleteRepository.findById(1);
		final String firstNameBefore = athlete.getFirstName();
		String newName = "Josep";

		athlete.setFirstName(newName);
		RequestEntity<Athlete> requestEntity = RequestEntity.post(new URI(PUTURI))
				.contentType(MediaType.APPLICATION_JSON).body(athlete);

		ResponseEntity<Athlete> responseEntity = restTemplate.exchange(PUTURI, HttpMethod.PUT, requestEntity,
				Athlete.class);
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();

		Athlete updatedAthlete = responseEntity.getBody();

		assertThat(statusCode.is2xxSuccessful()).isEqualTo(true);
		assertThat(contentType.getType()).isEqualTo("application");
		assertThat(contentType.getSubtype()).isEqualTo("json");

		assertThat(updatedAthlete.getFirstName()).isNotEqualTo(firstNameBefore);
		assertThat(updatedAthlete.getFirstName()).isEqualTo(newName);

	}

	@Test
	public void deleteAthleteShouldDeleteSpecificAthlete() throws Exception {

		Athlete a = new Athlete("Sofia", "Vagara", "s@v.com");
		Athlete athlete = athleteRepository.save(a);
		long athleteId = athlete.getId();
		assertThat(athlete).isNotNull();
		
		ResponseEntity<String> responseEntity = restTemplate.exchange("/athlete/" + athleteId, HttpMethod.DELETE, null,
				String.class);
		HttpStatus statusCode = responseEntity.getStatusCode();

		responseEntity.getBody();

		assertThat(statusCode.is2xxSuccessful()).isEqualTo(true);

		assertThat(athleteRepository.findById(athleteId)).isNull();
	}

}
