package com.credit.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.credit.entity.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

	Optional<CreditCard> findByUserId(Integer userId);

	List<CreditCard> findByCardNumber(Long cardNumber);

	List<CreditCard> findByCardNumberAndCvvAndExpireDate(Long cardnumber, Integer cvv, LocalDate expireDate);

}
