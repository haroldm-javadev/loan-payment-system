package com.bancx.lps.service;

import com.bancx.lps.dto.PaymentDto;
import com.bancx.lps.enums.LoanStatusEnum;
import com.bancx.lps.exceptions.PaymentException;
import com.bancx.lps.model.Loan;
import com.bancx.lps.model.Payment;
import com.bancx.lps.repository.LoanRepository;
import com.bancx.lps.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {

  @Mock
  private PaymentRepository paymentRepository;

  @Mock
  private LoanServiceImpl loanService;

  @InjectMocks
  private PaymentServiceImpl paymentService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this); // Initialize mocks
  }

  @Test
  public void payLoan_shouldSaveAndReturnPayment() {
    // Payment Dto
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setLoanId(1L);
    paymentDto.setPaymentAmount(100.00);

    Loan loan = Loan.builder()
        .loanId(paymentDto.getLoanId())
        .initialLoanAmount(2000.00)
        .remainingBalance(2000.00)
        .term(24L)
        .status(LoanStatusEnum.ACTIVE.getStatus())
        .build();


    Mockito.when(loanService.getLoanById(paymentDto.getLoanId())).thenReturn(loan);

    Payment payment = Payment.builder()
        .loanId(paymentDto.getLoanId())
        .paymentAmount(paymentDto.getPaymentAmount())
        .build();
    Mockito.when(paymentRepository.save(payment)).thenReturn(payment);

    // Act: call the method
    Payment created = paymentService.makePayment(paymentDto);

    // Assert: check if returned object is correct
    assertNotNull(created);
    assertEquals(1L, created.getLoanId());
    assertEquals(100.00, created.getPaymentAmount());
    Mockito.verify(paymentRepository, Mockito.times(1)).save(payment); // verify repository interaction
  }

  @Test
  public void overPayment_shouldSaveAndReturnPayment() {
    PaymentDto payment = new PaymentDto();
    payment.setLoanId(1L);
    payment.setPaymentAmount(2100.00);

    assertThrows(PaymentException.class, () -> {
      paymentService.makePayment(payment); // This will throw IOException
    });
  }

  @Test
  public void settledLoan_shouldSaveAndReturnPayment() {
    PaymentDto payment = new PaymentDto();
    payment.setLoanId(1L);
    payment.setPaymentAmount(200.00);

    Loan loan = Loan.builder()
        .loanId(payment.getLoanId())
        .initialLoanAmount(2000.00)
        .remainingBalance(2000.00)
        .term(24L)
        .status(LoanStatusEnum.SETTLED.getStatus())
        .build();

    Mockito.when(loanService.getLoanById(payment.getLoanId())).thenReturn(loan);

    assertThrows(PaymentException.class, () -> {
      paymentService.makePayment(payment); // This will throw PaymentException
    });
  }
}
