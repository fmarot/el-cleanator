package com.teamtter.elcleanator;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.teamtter.elcleanator.repository.services.PomParser;
import com.teamtter.elcleanator.repository.services.RepositoryParser;
import com.teamtter.elcleanator.repository.services.RepositoryPathRepository;

@SpringBootApplication
public class ElClenatorApplication implements CommandLineRunner {

	@Value("${repository-to-scan}")
	private Path repositoryToScan;

	@Bean
	public RepositoryPathRepository repo() throws IOException {
		return new RepositoryPathRepository(repositoryToScan, new RepositoryParser(new PomParser()));
	}

	public static void main(String[] args) {
		SpringApplication.run(ElClenatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repo().scan();
	}

}
