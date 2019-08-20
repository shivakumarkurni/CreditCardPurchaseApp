package com.credit.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.dto.DetailedStatementResponseDTO;
import com.credit.entity.CreditCard;
import com.credit.entity.Transaction;
import com.credit.exception.NoCard;
import com.credit.exception.NoRecordsFound;
import com.credit.repository.CreditCardRepository;
import com.credit.repository.DetailedStatementRespository;

@Service
public class DetailedStatementServiceImpl implements DetailedStatementService {

	private static final Logger logger = LoggerFactory.getLogger(DetailedStatementServiceImpl.class);

	@Autowired
	DetailedStatementRespository detailedStatementRespository;

	@Autowired
	CreditCardRepository creditCardRepository;

	@Override
	public List<DetailedStatementResponseDTO> detailedStatement(Long cardNo, Integer month, Integer year) {

		logger.info("Inside statement service");

		DetailedStatementResponseDTO detailedStatementResponseDTO;

		List<DetailedStatementResponseDTO> statementList = new ArrayList<>();

		String years = "20" + year;

		int finalYears = Integer.parseInt(years);

		LocalDate fromDate = LocalDate.of(finalYears, month, 01);

		LocalDate toDate = fromDate.plusDays(29);

		List<CreditCard> cardList = creditCardRepository.findByCardNumber(cardNo);

		if (cardList.isEmpty()) {

			throw new NoCard("Incorrect card Number");

		}

		else {
			CreditCard creditCard = cardList.get(0);

			Integer cardId = creditCard.getCardId();
			String status = "SUCCESS";
			List<Transaction> cardTransactions = detailedStatementRespository.findByCardIdAndDateAndStatus(cardId,
					fromDate, toDate, status);

			for (Transaction transaction : cardTransactions) {

				detailedStatementResponseDTO = new DetailedStatementResponseDTO();

				detailedStatementResponseDTO.setAmount(transaction.getAmount());
				detailedStatementResponseDTO.setDate(transaction.getDate());
				detailedStatementResponseDTO.setDescription(transaction.getDescription());
				detailedStatementResponseDTO.setTransactionType(transaction.getTransactionType());
				statementList.add(detailedStatementResponseDTO);

			}

		}

		if (statementList.isEmpty()) {

			throw new NoRecordsFound("No Records Found");
		}

		else {
			return statementList;
		}

	}

}
