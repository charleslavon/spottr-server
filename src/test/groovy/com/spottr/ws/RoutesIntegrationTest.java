package com.spottr.ws;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spottr.ws.config.AppConfig;
import com.spottr.ws.data.model.Wod;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={AppConfig.class}, webEnvironment=WebEnvironment.RANDOM_PORT)
public class RoutesIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate; 
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void setup() {
	}
	
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
	}
	
	@Test
	public void postWodShouldCreateNewWod() {
		
	}
	
//	@Test
//	public void putWodShouldUpdateASpecificWod() {
//		
//	}
	
//	@Test
//	public void deleteWodShouldDeleteASpecificWod() {
//		
//	}
	
	@Test
	public void postWodCommentShouldAddCommentToASpecificWod() {
		
	}
	
	@Test
	public void postWodCommentShouldReturnCompleteListOfCommentsForASpecificWod() {
		
	}
	
//	@Test void putWodCommentShouldUpdateASpecificComment() {
//		
//	}
	
//	@Test 
//	public void deleteWodCommentShouldDeleteASpecificComment() {
//		
//	}
	
	@Test
	public void putWodLikeShouldIncrementNumLikesForASpecificWod() {
		
	}
	
	@Test
	public void putWodLikeShouldReturnNumLikesForASpecificWod() {
		
	}
	
	@Test
	public void postWodAttendeeShouldAddAttendeeToASpecificWod() {
		
	}
	
	@Test
	public void postWodAttendeeShouldReturnCompleteListOfAttendees() {
		
	}
	


}
