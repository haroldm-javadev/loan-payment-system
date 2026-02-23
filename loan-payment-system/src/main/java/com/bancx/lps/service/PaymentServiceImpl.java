package com.bancx.lps.service;

import com.bancx.lps.dto.PaymentDto;
import com.bancx.lps.enums.LoanStatusEnum;
import com.bancx.lps.exceptions.PaymentException;
import com.bancx.lps.model.Loan;
import com.bancx.lps.model.Payment;
import com.bancx.lps.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;
  private final LoanService loanService;

  @Autowired
  public PaymentServiceImpl(PaymentRepository paymentRepository, LoanService loanService) {
    this.paymentRepository = paymentRepository;
    this.loanService = loanService;
  }

  @Override
  @Transactional
  public Payment makePayment(PaymentDto paymentDto) {

    Loan loan = loanService.getLoanById(paymentDto.getLoanId());

    if (loan != null) {

      if (loan.getStatus().equals(LoanStatusEnum.SETTLED.getStatus())) {
        throw new PaymentException("Loan with Id " + loan.getLoanId() + " has status " + loan.getStatus());
      }

      if (paymentDto.getPaymentAmount() > loan.getRemainingBalance()) {
        throw new PaymentException("Payment exceeds the remaining balance.");
      }

      Payment recordPayment = recordPayment(paymentDto);
      loanService.updateLoan(loan.getLoanId(), paymentDto.getPaymentAmount());
      return recordPayment;

    } else {
      throw new PaymentException("Loan with id " + paymentDto.getLoanId() + " not found.");
    }
  }

  private Payment recordPayment(PaymentDto paymentDto){
    Payment payment = Payment.builder()
        .loanId(paymentDto.getLoanId())
        .paymentAmount(paymentDto.getPaymentAmount())
        .build();
    return paymentRepository.save(payment);
  }
}
