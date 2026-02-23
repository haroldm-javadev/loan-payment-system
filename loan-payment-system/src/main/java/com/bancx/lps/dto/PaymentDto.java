package com.bancx.lps.dto;

import lombok.Data;

@Data
public class PaymentDto {
  private Long loanId;
  private Double paymentAmount;
}
