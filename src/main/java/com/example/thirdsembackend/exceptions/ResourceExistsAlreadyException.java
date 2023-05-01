package com.example.thirdsembackend.exceptions;

import java.io.Serial;

public class ResourceExistsAlreadyException extends RuntimeException{

  @Serial
  private static final long serialVersionUID = 1L;

  public ResourceExistsAlreadyException(String message) {
    super(message);
  }
}
