package com.epam.auction.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.auction.domain.Lot;
import com.epam.auction.service.LotService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
class LotController {
	@Autowired
	private LotService service;

	@PostMapping("/lot")
	Lot add(@Valid @RequestBody Lot newLot) {
		log.debug("POST lot " + newLot);
		return service.create(newLot);
	}

	@GetMapping("/lot/{id}")
	Lot get(@PathVariable Long id) {
		log.debug("GET for lot by id: " + id);
		return service.read(id)
				.orElse(null);
	}

	@GetMapping("/lot")
	Set<Lot> getAll() {
		log.debug("get all lot");
		return service.readAll();
	}

	@PutMapping("/lot/{id}")
	Lot replace(@Valid @RequestBody Lot newLot, @PathVariable Long id) {
		log.debug("PUT lot:" + newLot + " for id:" + id);
		return service.update(id, newLot);
	}

	@DeleteMapping("/lot/{id}")
	void delete(@PathVariable Long id) {
		log.debug("DELETE lot by id:" + id);
		service.delete(id);
	}
}