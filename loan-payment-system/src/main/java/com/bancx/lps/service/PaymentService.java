package com.bancx.lps.service;

import com.bancx.lps.dto.PaymentDto;
import com.bancx.lps.exceptions.PaymentException;
import com.bancx.lps.model.Payment;

public interface PaymentService {

  Payment makePayment(PaymentDto payment) throws PaymentException;

}
