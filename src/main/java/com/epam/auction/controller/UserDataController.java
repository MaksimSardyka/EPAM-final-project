package com.epam.auction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.auction.domain.UserData;
import com.epam.auction.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
class UserDataController {
	@Autowired
	private UserRepository repository;

	@PostMapping("/user")
	UserData post(@RequestBody UserData newUser) {
		log.debug("POST user:" + newUser);
		return repository.save(newUser);
	}

	// Single item
	@GetMapping("/user/{id}")
	UserData get(@PathVariable Long id) {
		log.debug("GET user:" + id);
		return repository.findById(id)
				.orElseThrow(() -> new RuntimeException(String.format("User by ID %d not found", id)));
	}

	@GetMapping("/user")
	List<UserData> getAll() {
		log.debug("GET all users");
		return (List<UserData>) repository.findAll();
	}

	@PutMapping("/user/{id}")
	UserData replace(@RequestBody UserData newUser, @PathVariable Long id) {
		log.debug("PUT user:" + newUser + " for id:" + id);
		return repository.findById(id)
				.map(user -> {
					user.setUsername(newUser.getUsername());
					user.setEmail(newUser.getEmail());
					return repository.save(user);
				})
				.orElseGet(() -> {
					newUser.setId(id);
					return repository.save(newUser);
				});
	}

	@DeleteMapping("/user/{id}")
	void delete(@PathVariable Long id) {
		log.debug("DELETE userr by id:" + id);
		repository.deleteById(id);
	}
}
