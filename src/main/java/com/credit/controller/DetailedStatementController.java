package com.credit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.credit.dto.DetailedStatementResponseDTO;
import com.credit.service.DetailedStatementService;


/**
 * @author Shiva
 *
 */

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })

@RestController
public class DetailedStatementController {
	
	private static final Logger logger = LoggerFactory.getLogger(DetailedStatementController.class);

	@Autowired
	DetailedStatementService detailedStatementService;

	@GetMapping("/bank/creditcard/statement/card/{cardNo}/month/{month}/year/{year}")
	public ResponseEntity<List<DetailedStatementResponseDTO>> detailedStatement(@PathVariable Long cardNo,
			@PathVariable Integer month, @PathVariable Integer year) {
		
		logger.info("Inside detailed statement controller");

		return new ResponseEntity<>(detailedStatementService.detailedStatement(cardNo, month, year), HttpStatus.OK);
	}

}
