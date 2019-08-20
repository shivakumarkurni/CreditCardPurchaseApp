package com.credit.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.credit.entity.Transaction;

public interface DetailedStatementRespository extends JpaRepository<Transaction, Integer> {

	@Query(value = "select * from transaction  where card_id=:cardId AND date BETWEEN :fromDate AND :toDate And status=:status", nativeQuery = true)
	List<Transaction> findByCardIdAndDateAndStatus(@Param("cardId") Integer cardId,
			@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("status") String status);

}
