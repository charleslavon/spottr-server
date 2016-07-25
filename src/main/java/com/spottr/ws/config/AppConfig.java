package com.spottr.ws.config;



import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.spottr.ws.data.model.Athlete;
import com.spottr.ws.data.model.Comment;
import com.spottr.ws.data.model.Location;
import com.spottr.ws.data.model.Wod;
import com.spottr.ws.data.repository.AthleteRepository;
import com.spottr.ws.data.repository.CommentRepository;
import com.spottr.ws.data.repository.LocationRepository;
import com.spottr.ws.data.repository.WodRepository;

@SpringBootApplication
@ComponentScan("com.spottr.ws")
@EntityScan("com.spottr.ws.data.model")
@EnableJpaRepositories("com.spottr.ws.data.repository")
public class AppConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }
    
    @Bean
	public CommandLineRunner demo(AthleteRepository athleteRepo,
									WodRepository wodRepo,
									CommentRepository commentRepo,
									LocationRepository locationRepo) {
		return (args) -> {
			
			// save some athletes
			athleteRepo.save(new Athlete("Jack", "Bauer@.com"));
			athleteRepo.save(new Athlete("Chloe", "O'Brian@.com"));
			athleteRepo.save(new Athlete("Kim", "Bauer@a.com"));
			athleteRepo.save(new Athlete("David", "Palmer@.com"));
			athleteRepo.save(new Athlete("Michelle", "Dessler@.com"));

			// fetch all athletes
			logger.info("Athletes found with findAll():");
			logger.info("-------------------------------");
			for (Athlete a : athleteRepo.findAll()) {
				logger.info(a.toString());
			}
            logger.info("");

			logger.info("Athlete found with findByName('Jack'):");
			logger.info("--------------------------------------------");
			for (Athlete bauer : athleteRepo.findByName("Jack")) {
				logger.info("Found jack! " + bauer.toString());
			}
			logger.info("");
			
			logger.info("create some wods");
			logger.info("--------------------------------------------");
			wodRepo.save(new Wod(0L, "Charles", LocalDateTime.now(), 0L, "open box tomorrow at poble nou"));
			for(Wod w : wodRepo.findByAuthorId(0L)) {
				logger.info("Found wod! " + w.toString());
			}
			logger.info("");
			
			logger.info("create some comments");
			logger.info("--------------------------------------------");
			commentRepo.save(new Comment(0L, "mike", "sounds good. I'll join."));
			for(Comment c: commentRepo.findByWodId(1L)) {
				logger.info("Found comment! " + c.toString());
			}
			logger.info("");
			
			logger.info("create some Locations");
			logger.info("--------------------------------------------");
			locationRepo.save(new Location("Crossfit Poble Nou", 41.390205, 2.154007));
			
			Location laHuella = new Location("La Huella Crossfit", 41.390205, 2.154007);
			laHuella.setWebLink("http://lahuellacrossfitbarcelona.com/");
			locationRepo.save(laHuella);
			
			for(Location l : locationRepo.findAll()) {
				logger.info("Found location " + l.toString());
			}
			logger.info("--------------------------------------------");
			logger.info("Find location by name " + locationRepo.findByName("La Huella Crossfit").get(0).toString());
			logger.info("Find location by Id " + locationRepo.findById(2L).toString());
			
		};
	}

}