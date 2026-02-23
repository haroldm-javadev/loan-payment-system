package com.bancx.lps.controller;

import com.bancx.lps.dto.LoanDto ;
import com.bancx.lps.dto.Response;
import com.bancx.lps.model.Loan;
import com.bancx.lps.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/loans")
public class LoanController {

  private final LoanService loanService;

  @Autowired
  public LoanController(LoanService loanService) {
    this.loanService = loanService;
  }

  @GetMapping("/")
  public ResponseEntity<Response> getAllLoans() {
    List<Loan> loans = loanService.getAllLoans();

    if (loans.isEmpty()) {
      new ResponseEntity<>(Response.builder().result(loans).build(), HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(Response.builder().result(loans).build(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response> getLoanById(@PathVariable("id") Long id) {
    Loan results = loanService.getLoanById(id);

    if (results == null) {
      new ResponseEntity<>(Response.builder().result(null).build(), HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(Response.builder().result(results).build(), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<Response> addLoan(@RequestBody LoanDto loan) {

    try {
      Loan results = loanService.addLoan(loan);
      return new ResponseEntity<>(Response.builder().result(results).build(), HttpStatus.CREATED);
    } catch (Exception ex) {
      return new ResponseEntity<>(Response.builder().result(ex.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
