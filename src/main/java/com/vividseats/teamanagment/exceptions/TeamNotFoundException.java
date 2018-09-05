package com.vividseats.teamanagment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TeamNotFoundException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1835077205697652480L;

  public TeamNotFoundException(String msg) {
    super(msg);
  }


}
