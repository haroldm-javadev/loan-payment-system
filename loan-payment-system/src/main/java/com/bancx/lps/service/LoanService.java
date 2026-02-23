package com.bancx.lps.service;

import com.bancx.lps.dto.LoanDto;
import com.bancx.lps.model.Loan;

import java.util.List;

public interface LoanService {

  List<Loan> getAllLoans();

  Loan getLoanById(Long id);

  Loan addLoan(LoanDto loan);

  void updateLoan(Long loanId, double remainingBalance);

}
