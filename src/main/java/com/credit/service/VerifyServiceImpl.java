package com.credit.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.dto.OtpDto;
import com.credit.entity.CreditCard;
import com.credit.entity.Otp;
import com.credit.entity.Transaction;
import com.credit.repository.CreditCardRepository;
import com.credit.repository.OtpRepository;
import com.credit.repository.TransactionRepository;

@Service
public class VerifyServiceImpl implements VerifyService {

	final static Logger LOGGER = LoggerFactory.getLogger(VerifyServiceImpl.class);

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CreditCardRepository creditCardRepository;

	@Override
	public String verifyOtp(OtpDto otpDto) {

		LOGGER.info("VerifyServiceImpl :: otpDto : " + otpDto);

		String returnValue = null;

		Optional<Otp> otpList = otpRepository.findByTransactionIdAndOtpValue(otpDto.getTransactionId(),
				otpDto.getOtp());
		if (otpList.isPresent()) {
			settelTransaction(otpDto);
			returnValue = "Successfully verified.";
		} else {
			returnValue = "Otp is wrong.";
		}
		LOGGER.info("VerifyServiceImpl :: End ");

		return returnValue;
	}

	public void settelTransaction(OtpDto otpDto) {

		Optional<Transaction> transaction = transactionRepository.findById(otpDto.getTransactionId());
		if (transaction.isPresent()) {
			transaction.get().setStatus("SUCCSES");
			transactionRepository.save(transaction.get());
			Optional<CreditCard> creditCard = creditCardRepository.findById(transaction.get().getCardId());
			if (creditCard.isPresent()) {
				creditCard.get().setAvailableBalance(
						(creditCard.get().getAvailableBalance()) - (transaction.get().getAmount()));
				creditCardRepository.save(creditCard.get());
			}
		}
	}
}