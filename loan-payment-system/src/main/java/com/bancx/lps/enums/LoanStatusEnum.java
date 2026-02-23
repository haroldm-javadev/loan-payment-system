package com.bancx.lps.enums;

import lombok.Getter;

@Getter
public enum LoanStatusEnum {

  SETTLED("SETTLED"),
  ACTIVE("ACTIVE");

  private final String status;

  LoanStatusEnum(String status) {
    this.status = status;
  }
}
