package com.epam.auction.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.auction.domain.UserData;

@Transactional
public interface UserRepository extends CrudRepository<UserData, Long> {
	UserData findByEmail(String email);
}
