package com.discpline.discipline_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
public class DisciplineTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisciplineTrackerApplication.class, args);
	}

}
