package com.epam.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.auction.domain.UserData;
import com.epam.auction.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public void createUser(UserData user) {
		userRepository.save(user);
		log.debug("User was created: %s", user);
	}

	public void deleteUser(UserData user) {
		userRepository.delete(user);
		log.debug("User was deleted: %s", user);
	}

	public void changeUserEmail(UserData user, String newEmail) {
		user.setEmail(newEmail);
		userRepository.save(user);
		log.debug("User was updated: %s", user);
	}
}
