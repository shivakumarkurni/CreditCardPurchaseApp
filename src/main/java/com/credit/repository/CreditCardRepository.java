package com.credit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.entity.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
	
	
	List<CreditCard> findByCardNumber(Long cardNumber);

}
