package com.vividseats.teamanagment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TeamManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamManagmentApplication.class, args);
	}
}
