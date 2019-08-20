package com.credit.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

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


@RunWith(MockitoJUnitRunner.class)
public class CreditCardServiceImplTest {
	
	@InjectMocks CreditCardServiceImpl creditCardService;
	
	@Mock
	CreditCardRepository creditCardRepository;
	@Mock
	TransactionRepository transactionRepository;

	@Mock
	OtpRepository otpRepository;
	
	CreditCard creditCard;
	List<CreditCard> creditCardList;
	
	Transaction transaction;
	List<Transaction> transactionList;
	
	CreditCardInputDto creditCardInputDto;
	CreditCardOtpVerificationInput creditCardOtpVerificationInput;
	
	Otp otp;
	List<Otp> otplist;
	@Before
	public void setUp() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
		String date = "01/08/20";
		LocalDate cardExpiryDate = LocalDate.parse(date, formatter);
		
		creditCard=new CreditCard();
		creditCard.setAvailableBalance(1000000);
		creditCard.setAvailableLimit(100000);
		creditCard.setCardId(1);
		creditCard.setCardNumber(123456789L);
		creditCard.setCvv(123);
		creditCard.setExpireDate(LocalDate.now());
		creditCard.setUserId(1);
		
		creditCardList=new ArrayList<>();
		creditCardList.add(creditCard);
		
		transaction=new Transaction();
		transaction.setAmount(100);
		transaction.setCardId(creditCard.getCardId());
		transaction.setDate(LocalDateTime.now());
		transaction.setDescription("yasdgwkd");
		transaction.setStatus(ETransctionStatus.PENDING.name());
		transaction.setTransactionId(1);
		transaction.setTransactionType(ETransactionType.DEBIT.name());
		
		
		transactionList=new ArrayList<>();
		transactionList.add(transaction);
		
		otp=new Otp();
		otp.setOtpId(3);
		otp.setDate(LocalDateTime.now());
		otp.setOtpValue(1234);
		otp.setTransactionId(transaction.getTransactionId());
		
		otplist=new ArrayList<>();
		otplist.add(otp);
		
		creditCardInputDto=new CreditCardInputDto();
		creditCardInputDto.setAmount(1000);
		creditCardInputDto.setCvc(creditCard.getCvv());
		creditCardInputDto.setExpiry("08/19");
		creditCardInputDto.setNumber("123456789");
		creditCardInputDto.setUserId(1);
		
		creditCardOtpVerificationInput=new CreditCardOtpVerificationInput();
		creditCardOtpVerificationInput.setOtpValue(1234);
		creditCardOtpVerificationInput.setTransctionId(transaction.getTransactionId());
	}


	@Test(expected = BankException.class)
	public void testCardCheck() { 
		
				Mockito.when(creditCardRepository.findByCardNumberAndCvvAndExpireDate(
				creditCard.getCardNumber(), creditCard.getCvv(), creditCard.getExpireDate())).thenReturn(creditCardList);
	
				Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
				
				Mockito.when(otpRepository.save(otp)).thenReturn(otp);
				
				creditCardService.cardCheck(creditCardInputDto);

				
	}

//	@Test
//	public void testCardSave() {
//		creditCardInputDto.setNumber("123456789");
//		creditCardInputDto.setCvc(123);
//		creditCardInputDto.setExpiry("08/20");
//		
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
//		String date = "01/" + creditCardInputDto.getExpiry();
//		LocalDate cardExpiryDate = LocalDate.parse(date, formatter);
//		
//		
//		Mockito.when(creditCardRepository.findByCardNumberAndCvvAndExpireDate(
//				123456789L, 123, cardExpiryDate)).thenReturn(creditCardList);
//
//		Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
//		
//		Mockito.when(otpRepository.save(otp)).thenReturn(otp);
//		
//		ResponseEntity<CreditCardOutputDto> actual = creditCardService.cardCheck(creditCardInputDto);
//
//		Assert.assertEquals(200, actual.getStatusCode());
//		 
//}

	@Test
	public void testCardCheckOtpVerification() {
		Mockito.when(otpRepository.findByTransactionId(creditCardOtpVerificationInput.getTransctionId())).thenReturn(otplist);
		Mockito.when(transactionRepository
		.findById(creditCardOtpVerificationInput.getTransctionId())).thenReturn(Optional.of(transaction));
		
		
		Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
		Mockito.when(creditCardRepository.findById(transaction.getCardId())).thenReturn(Optional.of(creditCard));
		Mockito.when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
		
		ResponseEntity<ResponseDto> actual = creditCardService.cardCheckOtpVerification(creditCardOtpVerificationInput);

		Assert.assertEquals(200, actual.getStatusCode().value());
		
	}
	
	@Test(expected = BankException.class)
	public void testCardCheckOtpVerificationNegative1() {
//		Mockito.when(otpRepository.findByTransactionId(creditCardOtpVerificationInput.getTransctionId())).thenReturn(otplist);
		Mockito.when(transactionRepository
		.findById(creditCardOtpVerificationInput.getTransctionId())).thenReturn(Optional.of(transaction));
		
		
		Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
		Mockito.when(creditCardRepository.findById(transaction.getCardId())).thenReturn(Optional.of(creditCard));
		Mockito.when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
		
		 creditCardService.cardCheckOtpVerification(creditCardOtpVerificationInput);

		
	}
	
	
	@Test(expected = BankException.class)
	public void testCardCheckOtpVerificationNegative2() {
		Mockito.when(otpRepository.findByTransactionId(creditCardOtpVerificationInput.getTransctionId())).thenReturn(otplist);
		Mockito.when(transactionRepository
		.findById(creditCardOtpVerificationInput.getTransctionId())).thenReturn(Optional.of(transaction));
		
		
		Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
//		Mockito.when(creditCardRepository.findById(transaction.getCardId())).thenReturn(Optional.of(creditCard));
		Mockito.when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
		
		 creditCardService.cardCheckOtpVerification(creditCardOtpVerificationInput);

		
	}
	
	
	@Test(expected = BankException.class)
	public void testCardCheckOtpVerificationNegative3() {
		creditCardOtpVerificationInput.setOtpValue(56456);
		Mockito.when(otpRepository.findByTransactionId(creditCardOtpVerificationInput.getTransctionId())).thenReturn(otplist);
		Mockito.when(transactionRepository
		.findById(creditCardOtpVerificationInput.getTransctionId())).thenReturn(Optional.of(transaction));
		
		
		Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
		Mockito.when(creditCardRepository.findById(transaction.getCardId())).thenReturn(Optional.of(creditCard));
		Mockito.when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
		
		 creditCardService.cardCheckOtpVerification(creditCardOtpVerificationInput);

		
	} 

}
