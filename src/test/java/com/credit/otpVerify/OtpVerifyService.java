package com.credit.otpVerify;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.credit.dto.OtpDto;
import com.credit.entity.Otp;
import com.credit.repository.OtpRepository;
import com.credit.service.VerifyServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class OtpVerifyService {
	
	@InjectMocks
	private VerifyServiceImpl verifyServiceImpl;
	
	@Mock
	private OtpRepository otpRepository;
	
	@Test
	public void testVerifyOtp() {
		
		Otp otp = new Otp();
		otp.setOtpId(101);
		otp.setOtpValue(1234);
		otp.setTransactionId(1010);
		otp.setDate(LocalDateTime.of(2019, 07, 20, 14, 30));

		Optional<Otp> otpOptional = Optional.of(otp);
		
		OtpDto otpDto = new OtpDto(1010, 1234);
		
		String expectedResult = "Successfully verified.";
		
		Mockito.when(otpRepository.findByTransactionIdAndOtpValue(Mockito.anyInt(), Mockito.anyInt())).thenReturn(otpOptional);
		String actualResult = verifyServiceImpl.verifyOtp(otpDto);
		
		assertEquals(expectedResult, actualResult);
	}

}
