package com.credit.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.credit.dto.CreditCardInputDto;
import com.credit.dto.CreditCardOtpVerificationInput;
import com.credit.dto.CreditCardOutputDto;
import com.credit.dto.ETransactionType;
import com.credit.dto.ETransctionStatus;
import com.credit.dto.ResponseDto;
import com.credit.entity.CreditCard;
import com.credit.entity.Otp;
import com.credit.entity.Transaction;
import com.credit.exception.BankException;
import com.credit.repository.CreditCardRepository;
import com.credit.repository.OtpRepository;
import com.credit.repository.TransactionRepository;

@Service
public class CreditCardServiceImpl implements CreditCardService {
	private final Logger logger = LoggerFactory.getLogger(CreditCardServiceImpl.class);

	@Autowired
	CreditCardRepository creditCardRepository;
	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	OtpRepository otpRepository;

	@Override
	public ResponseEntity<CreditCardOutputDto> cardCheck(CreditCardInputDto creditCardInputDto) {
		logger.info("CreditCardServiceImpl------>  cardCheck");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

		String date = "01/" + creditCardInputDto.getExpiry();

		LocalDate cardExpiryDate = LocalDate.parse(date, formatter);

		Long cardNumber = Long.parseLong(creditCardInputDto.getNumber().replace(" ", ""));

		List<CreditCard> creditCards = creditCardRepository.findByCardNumberAndCvvAndExpireDate(cardNumber,
				creditCardInputDto.getCvc(), cardExpiryDate);
		
		logger.info(" creditCards length:{}", creditCards.size());

		
		if (creditCards.isEmpty())
			throw new BankException(" incorrect card details");

		if (creditCards.get(0).getAvailableBalance() < creditCardInputDto.getAmount())
			throw new BankException(" no sufficient balance");

		// transaction Details added
		Transaction transaction = new Transaction();
		transaction.setAmount(creditCardInputDto.getAmount());
		transaction.setCardId(creditCards.get(0).getCardId());
		transaction.setDate(LocalDateTime.now());
		transaction.setStatus(ETransctionStatus.PENDING.name());
		transaction.setTransactionType(ETransactionType.DEBIT.name());
		Transaction transactionSave = transactionRepository.save(transaction);

		// otp generation

		RestTemplate template = new RestTemplate();
		ResponseEntity<Integer> otpValue = template.getForEntity("http://10.117.189.248:9090/bankUtility/otp",
				Integer.class);

		// try {
		// EmailDto emailDto = new EmailDto();
		// emailDto.setEmailId("sairam4smile@gmail.com");
		// emailDto.setSubject("subject for delete"); 
		// emailDto.setTextBody(" delete for otp " + otpValue);
		// template.postForEntity("http://10.117.189.248:9090/bankUtility/email",
		// emailDto, String.class);
		// } catch (Exception e) {
		//
		// }

		// otp saving
		Otp otp = new Otp();
		otp.setTransactionId(transactionSave.getTransactionId());
		otp.setDate(LocalDateTime.now());
		otp.setOtpValue(otpValue.getBody());
		otpRepository.save(otp);

		CreditCardOutputDto creditCardOutputDto = new CreditCardOutputDto();
		creditCardOutputDto.setMessage("succsesully matched");
		creditCardOutputDto.setStatusCode(HttpStatus.OK.value());
		creditCardOutputDto.setTransctionId(transactionSave.getTransactionId());

		return ResponseEntity.status(HttpStatus.OK).body(creditCardOutputDto);

	}

	@Override
	public CreditCard cardSave(CreditCard creditCard) {
		logger.info("CreditCardServiceImpl------>  cardSave");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String date = "01/09/2022";
		LocalDate cardExpiryDate = LocalDate.parse(date, formatter);

		creditCard.setExpireDate(cardExpiryDate);

		return creditCardRepository.save(creditCard);
	}

	//card otp verification
	@Override
	public ResponseEntity<ResponseDto> cardCheckOtpVerification(
			CreditCardOtpVerificationInput creditCardOtpVerificationInput) {

		logger.info("CreditCardServiceImpl------>  cardCheckOtpVerification");
		List<Otp> otps = otpRepository.findByTransactionId(creditCardOtpVerificationInput.getTransctionId());

//		if (otps.isEmpty())
//			throw new BankException("no transaction");

		//card otp validation
		if (!otps.get(0).getOtpValue().equals(creditCardOtpVerificationInput.getOtpValue()))
			throw new BankException("wrong otp");

		Optional<Transaction> transaction = transactionRepository
				.findById(creditCardOtpVerificationInput.getTransctionId());

		transaction.get().setStatus(ETransctionStatus.SUCCSES.name());
		transactionRepository.save(transaction.get());
		Optional<CreditCard> creditCard = creditCardRepository.findById(transaction.get().getCardId());

		if (!creditCard.isPresent())
			throw new BankException("no card available");

		creditCard.get()
				.setAvailableBalance((creditCard.get().getAvailableBalance()) - (transaction.get().getAmount()));

		creditCardRepository.save(creditCard.get());

		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage("payement succsessfully");

		responseDto.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(responseDto);

	}

}
