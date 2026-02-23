package com.bancx.lps.service;


import com.bancx.lps.dto.LoanDto;
import com.bancx.lps.dto.PaymentDto;
import com.bancx.lps.enums.LoanStatusEnum;
import com.bancx.lps.model.Loan;
import com.bancx.lps.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

  @Mock
  private LoanRepository loanRepository;

  @InjectMocks
  private LoanServiceImpl loanService;

  // A loan is successfully created and stored in the database.
  @Test
  void addLoan_shouldSaveAndReturnLoan() {
    // LoanDto
    LoanDto loanDto = new LoanDto();
    loanDto.setTerm(23L);
    loanDto.setInitialAmount(2000.00);

    // Loan Object to be saved
    Loan loan =  Loan.builder()
        .initialLoanAmount(loanDto.getInitialAmount())
        .remainingBalance(loanDto.getInitialAmount())
        .term(loanDto.getTerm())
        .status(LoanStatusEnum.ACTIVE.getStatus())
        .build();
    Mockito.when(loanRepository.save(loan)).thenReturn(loan);

    // Act: call the method
    Loan created = loanService.addLoan(loanDto);

    // Assert: check if returned object is correct
    assertNotNull(created);
    assertEquals(2000.00, created.getInitialLoanAmount());
    assertEquals("ACTIVE", created.getStatus());
    Mockito.verify(loanRepository, Mockito.times(1)).save(loan); // verify repository interaction
  }
}
