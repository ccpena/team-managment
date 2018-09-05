package com.vividseats.teamanagment.exceptions;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ExceptionResponse {

  private LocalDate date;
  private String message;

}
