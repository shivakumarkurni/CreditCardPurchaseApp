package com.credit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.credit.dto.DetailedStatementResponseDTO;

@Service
public interface DetailedStatementService {
	
	public List<DetailedStatementResponseDTO> detailedStatement(Long cardNo,Integer month,Integer year);
		
		
	

}
