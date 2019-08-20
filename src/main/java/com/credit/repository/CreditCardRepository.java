package com.credit.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.entity.CreditCard;

public interface CreditCardRepository  extends JpaRepository<CreditCard, Integer>{
	List<CreditCard> findByCardNumberAndCvvAndExpireDate(Integer cardnumber, Integer cvv, LocalDate expireDate);

}
