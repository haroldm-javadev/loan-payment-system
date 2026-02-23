package com.bancx.lps.controller;

import com.bancx.lps.dto.PaymentDto;
import com.bancx.lps.dto.Response;
import com.bancx.lps.exceptions.PaymentException;
import com.bancx.lps.model.Payment;
import com.bancx.lps.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/payment")
public class PaymentController {

  private final PaymentService paymentService;

  @Autowired
  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("")
  public ResponseEntity<Response> payment(@RequestBody PaymentDto payment) {

    try {
      Payment results = paymentService.makePayment(payment);
      if (results == null) {
        return new ResponseEntity<>(Response.builder().result("Unsuccessful Payment").build(),
            HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(Response.builder().result(results).build(), HttpStatus.CREATED);
    } catch (PaymentException px) {
      return new ResponseEntity<>(Response.builder().result(px.getMessage()).build(),
          HttpStatus.BAD_REQUEST);
    }
  }
}
