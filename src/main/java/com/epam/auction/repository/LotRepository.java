package com.epam.auction.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.epam.auction.domain.Lot;

@Transactional
public interface LotRepository extends CrudRepository<Lot, Long> {
	@Override
	Optional<Lot> findById(Long userId);
}