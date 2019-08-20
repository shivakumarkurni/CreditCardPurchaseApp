package com.credit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.credit.entity.CardUsers;

@Repository
public interface CardUsersRepository extends JpaRepository<CardUsers, Integer> {
	

}
