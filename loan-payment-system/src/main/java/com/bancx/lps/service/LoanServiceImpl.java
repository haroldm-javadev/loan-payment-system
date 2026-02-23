package com.bancx.lps.service;

import com.bancx.lps.dto.LoanDto;
import com.bancx.lps.enums.LoanStatusEnum;
import com.bancx.lps.model.Loan;
import com.bancx.lps.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class LoanServiceImpl implements LoanService {

  private final LoanRepository loanRepository;

  @Autowired
  public LoanServiceImpl(LoanRepository loanRepository) {
    this.loanRepository = loanRepository;
  }

  @Override
  public List<Loan> getAllLoans() {
    return loanRepository.findAll();
  }

  @Override
  public Loan getLoanById(Long id) {
    Optional<Loan> loan = loanRepository.findById(id);
    return loan.orElseThrow(
        () -> new NoSuchElementException("No Loan present with Id = " + id));
  }

  @Override
  public Loan addLoan(LoanDto loan) {
    Loan newLoan = Loan.builder()
        .initialLoanAmount(loan.getInitialAmount())
        .remainingBalance(loan.getInitialAmount())
        .term(loan.getTerm())
        .status(LoanStatusEnum.ACTIVE.getStatus())
        .build();

    return loanRepository.save(newLoan);
  }

  @Transactional
  @Override
  public void updateLoan(Long loanId, double paymentAmount) {

    Loan loan = getLoanById(loanId);
    double loanBalance = loan.getRemainingBalance() - paymentAmount;

    if (loanBalance == 0) {
      loan.setStatus(LoanStatusEnum.SETTLED.getStatus());
    }

    loan.setRemainingBalance(loanBalance);
    loan.setTerm(loan.getTerm() - 1);
    loanRepository.save(loan);
  }
}
