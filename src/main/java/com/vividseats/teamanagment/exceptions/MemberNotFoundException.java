package com.vividseats.teamanagment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1835077205697652480L;

  public MemberNotFoundException(String msg) {
    super(msg);
  }


}
