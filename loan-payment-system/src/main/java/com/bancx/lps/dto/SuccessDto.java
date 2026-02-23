package com.bancx.lps.dto;

import lombok.Data;

@Data
public class SuccessDto {

  private double initialAmount;
  private double remainingBalance;
  private Long term;
  private double paymentAmount;
  private String status;

}
