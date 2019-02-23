package com.epam.auction.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.auction.domain.Lot;
import com.epam.auction.repository.LotRepository;
import com.sun.istack.NotNull;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LotService {
	@Autowired
	private LotRepository repository;

	public Lot create(Lot lot) {
		return repository.save(lot);
	}

	public Optional<Lot> read(@NotNull Long id) {
		log.debug("Lot with id %d requested", id);
		return repository.findById(id);
	}

	public Set<Lot> readAll() {
		HashSet<Lot> lotSet = new HashSet<>();
		repository.findAll()
				.forEach(lotSet::add);
		return lotSet;
	}

	public Lot update(Long id, Lot newLot) {
		return repository.findById(id)
				.map(lot -> {
					lot.setName(newLot.getName());
					lot.setDescription(lot.getDescription());
					return repository.save(lot);
				})
				.orElseGet(() -> {
					newLot.setId(id);
					return repository.save(newLot);
				});
	}

	public void delete(@NotNull Long id) {
		repository.deleteById(id);
	}
}
