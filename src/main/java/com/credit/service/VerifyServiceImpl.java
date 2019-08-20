package com.credit.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.dto.OtpDto;
import com.credit.entity.CreditCard;
import com.credit.entity.Otp;
import com.credit.repository.CreditCardRepository;
import com.credit.repository.OtpRepository;

@Service
public class VerifyServiceImpl implements VerifyService {
	
	final static Logger LOGGER = LoggerFactory.getLogger(VerifyServiceImpl.class);
	
	@Autowired
	private OtpRepository otpRepository;

	@Override
	public String verifyOtp(OtpDto otpDto) {
		
		LOGGER.info("VerifyServiceImpl :: otpDto : "+otpDto);
		
		String returnValue = "Successfully verified.";
		
		Optional<Otp> otpList = otpRepository.findByTransactionIdAndOtpValue(otpDto.getTransactionId(), otpDto.getOtp());
		if(!otpList.isPresent()) {
			returnValue = "Otp is wrong.";
		}
		LOGGER.info("VerifyServiceImpl :: End ");
		
		return returnValue;
	}

}
