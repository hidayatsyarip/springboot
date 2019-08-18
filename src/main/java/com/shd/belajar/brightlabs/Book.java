package com.shd.belajar.brightlabs;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Book {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String author;
	private BigDecimal price;
	
	public Book() {
	}

    public Book(Long id, String name, String author, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
    }
    
    public Book(String name, String author, BigDecimal price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

}
