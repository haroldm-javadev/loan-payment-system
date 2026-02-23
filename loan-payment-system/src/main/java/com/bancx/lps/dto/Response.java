package com.bancx.lps.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Response {
  @Builder.Default
  private Boolean success = true;
  private Object result;
  private ErrorDto error;
}
