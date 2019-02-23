package com.epam.auction.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.auction.domain.Lot;
import com.epam.auction.domain.UserData;
import com.epam.auction.repository.LotRepository;
import com.epam.auction.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(LotRepository lotRepository, UserRepository userRepository) {
		Set<Lot> lotSet = new HashSet<>();
		lotSet.add(new Lot(1L, "car", "Cars description for sale"));
		lotSet.add(new Lot(2L, "coin", "Old coin."));
		lotSet.add(new Lot(3L, "statue", "Garden dwarf."));

		Set<UserData> userSet = new HashSet<>();
		userSet.add(new UserData(1L, "some@gmail.com", "Admin"));
		return args -> {
			lotRepository.saveAll(lotSet);
			userRepository.saveAll(userSet);
			log.debug("DB initialized");
		};
	}
}