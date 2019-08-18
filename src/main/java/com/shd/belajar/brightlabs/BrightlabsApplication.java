package com.shd.belajar.brightlabs;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BrightlabsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrightlabsApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initDatabase(BookRepository bookRepository) {
		return args -> {
			bookRepository.save(new Book("A Guide to the Bodhisattva Way of Life", "Santideva", new BigDecimal("15.41")));
			bookRepository.save(new Book("The Life-Changing Magic of Tidying Up", "Marie Kondo", new BigDecimal("9.69")));
			bookRepository.save(new Book("Refactoring: Improving the Design of Existing Code", "Martin Fowler", new BigDecimal("47.99")));
		};
	}

}
