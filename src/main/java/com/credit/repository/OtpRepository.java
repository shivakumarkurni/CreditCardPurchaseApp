package com.credit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.credit.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

	Optional<Otp> findByTransactionIdAndOtpValue(int transactionId, int otpValue);
}
