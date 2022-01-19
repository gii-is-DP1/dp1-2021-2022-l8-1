package org.springframework.samples.SevenIslands;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication()
public class SevenIslandsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SevenIslandsApplication.class, args);
		log.info("Game running!");
	}

}
