package com.shd.belajar.brightlabs;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shd.belajar.brightlab.error.BookNotFoundException;
import com.shd.belajar.brightlab.error.BookUnSupportedFieldPatchException;

@RestController
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	// Find
	@GetMapping("/books")
	List<Book> findAll(){
		return bookRepository.findAll();
	}
	
	// Save
	//return 201 instead of 200
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/books")
	Book newBook(@RequestBody Book newBook) {
		return bookRepository.save(newBook);
	}
	
	// Find
	@GetMapping("/books/{id}")
	Book findOne(@PathVariable Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException(id));
	}
	
	// Save or Update
	@PutMapping("/books/{id}")
	Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {
		return bookRepository.findById(id)
				.map(x -> {	
					x.setName(newBook.getName());
					x.setAuthor(newBook.getAuthor());
					x.setPrice(newBook.getPrice());
					return bookRepository.save(x);
				})
				.orElseGet(() -> {
					newBook.setId(id);
					return bookRepository.save(newBook);
				});
	}
	
	// Update author only
	@PatchMapping("/books/{id}")
	Book patch(@RequestBody Map<String, String> update, @PathVariable Long id) {
		
		return bookRepository.findById(id)
				.map(x -> {
					
					String author = update.get("author");
					if(!StringUtils.isEmpty(author)) {
						x.setAuthor(author);
						
						// better create a custom method to update a value = :neValue where id = :id
						return bookRepository.save(x);
					} else {
						throw new BookUnSupportedFieldPatchException(update.keySet());
					}
				})
				.orElseGet(() -> {
					throw new BookNotFoundException(id);
				});
	}
	
	@DeleteMapping("/books/{id}")
	void deleteBook(@PathVariable Long id) {
		bookRepository.deleteById(id);
	}
}
