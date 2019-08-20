package com.credit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.entity.Otp;
import java.lang.Integer;
import java.util.List;

public interface OtpRepository extends JpaRepository<Otp, Integer>{
	List<Otp> findByTransactionId(Integer transactionid);

}
